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

  # TODO
  # Scenario: Check that testuser is listed

  # TODO
#  Scenario: Check that all users aren't missing any data
#    * call read('../TestBasis/ReqresBaseScenarios.feature@GetUsers') {'expCode': 200}
#    * match


