@parallel=false
Feature: Help scenarios to be called only once for every background

  @DefinedUser
  Scenario: Get User To Register
    # userName & userPassword are defined globally
    * def userObject = reqresLib.getUserObject(userName, userPassword)
    * def userBody = reqresLib.getUserBody(userObject)
