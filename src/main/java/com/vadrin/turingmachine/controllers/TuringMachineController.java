package com.vadrin.turingmachine.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	@RequestMapping(method = RequestMethod.POST, value = "/turningMachine")
	public String createTuringMachine(@RequestBody JsonNode tableInfo) {
		ActionRow[] actionRow = new ActionRow[2];
		actionRow[0] = new ActionRow(0, ' ', 1, '0', 'R');
		actionRow[1] = new ActionRow(1, ' ', 0, '1', 'R');
		ActionTable actionTable = new ActionTable(actionRow);
		Tape tape = new Tape(10);
		TuringMachine thisMachine = new TuringMachine(actionTable, tape);
		String id = UUID.randomUUID().toString();
		activeMachines.put(id, thisMachine);
		log.info("Constructed new machine {}", id);
		return id;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/turningMachine/{id}")
	public String getTuringMachine(@PathVariable("id") String id) {
		try {
			if (!activeMachines.containsKey(id)) {
				throw new InvalidOrTerminatedMachineException();
			}
			TuringMachine thisMachine = activeMachines.get(id);
			thisMachine.computeSingleStep();
			return thisMachine.toString();
		} catch (InvalidOrTerminatedMachineException | InsufficientTapeException e) {
			log.info("Destroying machine {} incase it is still not cleaned up", id);
			activeMachines.remove(id);
			return e.getClass().getName().split("\\.")[e.getClass().getName().split("\\.").length - 1];
		} catch (Exception e) {
			log.error("Unknown Exception", e);
			activeMachines.remove(id);
			return e.getClass().getName().split("\\.")[e.getClass().getName().split("\\.").length - 1];
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

}
