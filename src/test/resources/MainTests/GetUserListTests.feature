@parallel=true
Feature: Demo Challenge Task #1 - Get User List

  Scenario: Successfully get users list - Validate Response Structure
    * call read('../TestBasis/ReqresBaseScenarios.feature@GetUsersDefault') {'expCode': 200}
    * configure continueOnStepFailure = { enabled: true, continueAfter: true, keywords: ['match', 'assert'] }
    * match response.page == 1
    * assert checkType("number", response.per_page)
    * assert checkType("number", response.total)
    * assert checkType("number", response.total_pages)
    * assert isObjectArray(response.data)
    * assert !isObjectArray(response.support)
    * configure continueOnStepFailure = { enabled: false, continueAfter: true, keywords: ['match', 'assert'] }

  Scenario: Check total number of listed users
    * def pageCheckInstance = paginationLib.getPageCheckInstance()
    * def countedUsers = paginationLib.getTotalUserCount(pageCheckInstance)
    * match pageCheckInstance.getTotalUsers() == countedUsers

  Scenario: Check that testuser is listed
    * def pageCheckInstance = paginationLib.getPageCheckInstance()
    * def foundUser = lookupUserLib.lookForUser(pageCheckInstance, "tobias.funke@reqres.in")
    * def parsedJSON = JSON.parse(foundUser)
    * configure continueOnStepFailure = { enabled: true, continueAfter: true, keywords: ['assert'] }
    * assert 0 <= parsedJSON.id
    * assert checkType("string", parsedJSON.first_name)
    * assert checkType("string", parsedJSON.last_name)
    * assert parsedJSON.avatar.endsWith(".jpg")
    * configure continueOnStepFailure = { enabled: false, continueAfter: true, keywords: ['assert'] }
