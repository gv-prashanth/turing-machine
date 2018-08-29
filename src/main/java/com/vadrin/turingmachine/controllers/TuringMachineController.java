package com.vadrin.turingmachine.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.turingmachine.commons.InsufficientTapeException;
import com.vadrin.turingmachine.commons.InvalidOrTerminatedMachineException;
import com.vadrin.turingmachine.models.ActionRow;
import com.vadrin.turingmachine.models.ActionTable;
import com.vadrin.turingmachine.models.Tape;
import com.vadrin.turingmachine.services.TuringMachine;

@RestController
public class TuringMachineController {

	private static final Logger log = LoggerFactory.getLogger(TuringMachineController.class);

	private Map<String, TuringMachine> activeMachines;

	// Sample Request
	/*
	 * { "tapeSize": "10", "actionTable": [ { "initialState": 0,
	 * "initialSymbol": " ", "finalState": 1, "finalSymbol": "0", "moveTo": "R"
	 * }, { "initialState": 1, "initialSymbol": " ", "finalState": 0,
	 * "finalSymbol": "1", "moveTo": "R" } ] }
	 */

	@RequestMapping(method = RequestMethod.POST, value = "/turningMachine")
	public ResponseEntity<Map<String, String>> createTuringMachine(@RequestBody JsonNode machineInfo) {
		try {
			TuringMachine thisMachine = constructMachineUsingTableInfo(machineInfo);
			String id = UUID.randomUUID().toString();
			activeMachines.put(id, thisMachine);
			log.info("Constructed new machine {}", id);
			return ResponseEntity.created(new URI("/turningMachine/" + id)).build();
		} catch (NullPointerException | URISyntaxException e) {
			Map<String, String> toReturn = new HashMap<String, String>();
			toReturn.put("Exception",
					"Invalid Input. Please see sample at https://github.com/gv-prashanth/turning-machine");
			return ResponseEntity.badRequest().body(toReturn);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/turningMachine/{id}")
	public ResponseEntity<Map<String, String>> getTuringMachine(@PathVariable("id") String id) {
		Map<String, String> toReturn = new HashMap<String, String>();
		try {
			TuringMachine thisMachine = fetchMachine(id);
			thisMachine.computeSingleStep();
			toReturn.put("Tape", thisMachine.toString());
			toReturn.put("Head", String.valueOf(thisMachine.headIndex() + 1));
			return ResponseEntity.ok(toReturn);
		} catch (InsufficientTapeException e) {
			activeMachines.remove(id);
			toReturn.put("Exception", "Insufficient Tape. This Machine will Self Terminate now.");
			return ResponseEntity.badRequest().body(toReturn);
		} catch (InvalidOrTerminatedMachineException e) {
			toReturn.put("Exception", "Machine not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(toReturn);
		}
	}

	@PostConstruct
	private void init() {
		activeMachines = new HashMap<String, TuringMachine>();
	}

	@Scheduled(fixedDelayString = "${com.vadrin.turing-machine.cleanupFrequency}")
	private void clearAllActiveMachines() {
		log.info("Cleaning up the machines...");
		activeMachines = new HashMap<String, TuringMachine>();
	}

	private TuringMachine constructMachineUsingTableInfo(JsonNode machineInfo) {
		ActionRow[] actionRow = new ActionRow[machineInfo.get("actionTable").size()];
		for (int i = 0; i < machineInfo.get("actionTable").size(); i++) {
			actionRow[i] = new ActionRow(machineInfo.get("actionTable").get(i).get("initialState").asInt(),
					machineInfo.get("actionTable").get(i).get("initialSymbol").asText().charAt(0),
					machineInfo.get("actionTable").get(i).get("finalState").asInt(),
					machineInfo.get("actionTable").get(i).get("finalSymbol").asText().charAt(0),
					machineInfo.get("actionTable").get(i).get("moveTo").asText().charAt(0));
		}
		ActionTable actionTable = new ActionTable(actionRow);
		Tape tape = new Tape(machineInfo.get("tapeSize").asInt());
		return new TuringMachine(actionTable, tape);
	}

	private TuringMachine fetchMachine(String id) throws InvalidOrTerminatedMachineException {
		if (!activeMachines.containsKey(id)) {
			throw new InvalidOrTerminatedMachineException();
		}
		return activeMachines.get(id);
	}

}
