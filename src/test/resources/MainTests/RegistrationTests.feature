@parallel=true
Feature: Demo Challenge Task #1 - Registration

  Background:
    * callonce read('../HelpFeatures/CallOnceScenarios.feature@DefinedUser')

  Scenario: Successful registration
    * call read('../TestBasis/ReqresBaseScenarios.feature@RegisterUser') {'reqBody': #(userBody), 'expCode': 200}
    * assert checkType("number", response.id)
    * match response.token != null
    * match response.token != ""
    * def token = response.token

  Scenario: Unsuccessful registration - Missing Password
    * def userBody = { 'email': #(userName) }
    * call read('../TestBasis/ReqresBaseScenarios.feature@RegisterUser') {'reqBody': #(userBody), 'expCode': 400}
    * match karate.lowerCase(response.error) contains "missing password"

  Scenario Outline: Unsuccessful registration for missing attributes - <requestBody>
    * call read('../TestBasis/ReqresBaseScenarios.feature@RegisterUser') {'reqBody': <requestBody>, 'expCode': 400}
    * match karate.lowerCase(response.error) contains <expectedMessage>
    Examples:
      | requestBody                                  | expectedMessage             |
      | {}                                           | "missing email or username" |
      | {'email': null, 'password': null}            | "missing email or username" |
      | {'email': #(userName), 'password': null}     | "missing password"          |
      | {'email': null, 'password': #(userPassword)} | "missing email"             |

  Scenario: Unsuccessful registration for extra attribute
    * def reqBody = { 'email': #(userName), 'password': #(userPassword), 'newAttribute': #(genericLib.getRandomUUID()) }
    * def newReqBody = renameKeyFromObject(reqBody, "newAttribute")
    * call read('../TestBasis/ReqresBaseScenarios.feature@RegisterUser') {'reqBody': #(newReqBody), 'expCode': 200}
    # NOTE: This should in fact return a 400, as extra attributes might turn out to be security issues

  Scenario: Unsuccessful registration for non-existent user
    * def reqBody =
    """
    {
      "email": #(genericLib.getRandomBoundedString() + "@reqres.in"),
      "password": #(genericLib.getRandomUUID())
    }
    """
    * call read('../TestBasis/ReqresBaseScenarios.feature@RegisterUser') {'reqBody': #(reqBody), 'expCode': 400}
    * match karate.lowerCase(response.error) contains "only defined users succeed registration"