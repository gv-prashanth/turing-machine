# turing-machine
A Turing machine implementation in Java. You can play around at <a href="#"> Turing Machine </a>

## Construct a Turing Machine
### URL
HOST/turningMachine
### HttpMethod
POST
### Request Body
{
	"tapeSize": "50",
	"actionTable": [
		{
			"initialState": 0,
			"initialSymbol": " ",
			"finalState": 1,
			"finalSymbol": "0",
			"moveTo": "R"
		},
		{
			"initialState": 1,
			"initialSymbol": " ",
			"finalState": 0,
			"finalSymbol": "1",
			"moveTo": "R"
		}
	]
}
### Sample Response Body
201 Status Code with location header containing the path of newly created turing machine.

## Process the Turing Machine
### URL
HOST/turningMachine/{machineId}
### HttpMethod
GET
### Sample Response Body
{
    "tape": {
        "symbols": "0111111111"
    },
    "state": 1,
    "head": {
        "position": 1
    },
    "actionTable": {
        "actionRows": [
            {
                "initialState": 0,
                "initialSymbol": " ",
                "finalState": 1,
                "finalSymbol": "0",
                "moveTo": "R"
            },
            {
                "initialState": 1,
                "initialSymbol": " ",
                "finalState": 0,
                "finalSymbol": "1",
                "moveTo": "R"
            }
        ]
    }
}
