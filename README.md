# Turning-machine
A Turing machine implementation in Java

# Website
You can play around at <a href="http://localhost"> Turing Machine </a>

# REST Endpoints
## Construct a Turing Machine
### URL
http://localhost:8080/turningMachine
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
{
    "machineId": "d447423d-2fe5-4d0c-a297-d9ec2dd9c7c8"
}

## Process the Turing Machine
### URL
http://localhost:8080/turningMachine/{machineId}
### HttpMethod
GET
### Sample Response Body
{
    "Head": "22",
    "Tape": "0101010101010101010101****************************"
}
