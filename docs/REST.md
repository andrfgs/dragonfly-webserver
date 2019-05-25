# Rest API

**Base Rest Path:** http://localhost:8080/dragonfly/rest/ 

## Plantation
*Rest operations involving plantations*

<br>

**GET** /plantation?username="username"&tokenid="tokenid"&unitid=x <br>
Requests all plantations for a given unit belonging to a user, if tokenid is valid.

Returns:
```
200 - If Request Succeds 
400 InvalidCredentialsException - If tokenid has expired or wasn't assigned to user
400 UserNotFoundException - If the username does not exist 
400 UnitNotFoundException - If no unit with that id exists or if it does not belong to the user
400 "message" - If an internal exception occurs within the server
```

Returned Json (on success): 
```
{
    "sector" : 0,
    "unitID" : 0,
    "sowedPlant" : "",
    "sowDate" : 0
}
```

## Plant
*Rest operations involving plants*

**GET** /plants <br>
Requests all plants currently in the DB

Returns:
```
200 - If Request Succeds 
```

Returned Json: 
```
{
    "name" : ""
    "seedDepth" : 0.0
    "spacing" : 0.0
    "growthTime" : 0
    "sunlightRequirements" : 0
    "lightingRequirements" : 0
    "waterRequirements" : 0
    "minTemperature" : 0.0
    "maxTemperature" : 0.0
}
```

## Unit
*Rest operation involving units*

## User
*Rest operations involving users*

<br>

**POST** /user/create <br>
Creates a new user from 

Accepts (JSON):
```
{
    "username" : "",
    "email" : "",
    "firstName" : "",
    "lastName" : "",
    "password" : ""
}
```

Returns:
```
200 - If Request Succeds 
400 UserAlreadyExistsException - If the user already exists in the DB 
400 "message" - If an internal exception occurs within the server
```

<br>

**GET** /user/login?username="username"&password="password" <br>
Requests a new token or the last valid token


Returns:
```
200 - If Request Succeds
400 UserNotFoundException - If the username does not exist 
400 InvalidCredentialsException - If username and/or password are wrong 
400 "message" - If an internal exception occurs within the server
```