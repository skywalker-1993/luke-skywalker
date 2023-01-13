@parallel=true
Feature: Demo Challenge Task #1 - Get User List

  # Background:
  #   * callonce read('../HelpFeatures/CallOnceScenarios.feature@DefinedUser')
  # * configure continueOnStepFailure = { enabled: true, continueAfter: true, keywords: ['call', 'match', 'assert'] }

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

  Scenario: Successfully get users list - Validate Response Integrity
    # TODO
    * call read('../TestBasis/ReqresBaseScenarios.feature@GetUsersDefault') {'expCode': 200}
    * configure continueOnStepFailure = { enabled: true, continueAfter: true, keywords: ['match', 'assert'] }
    * match response.page == 1
    * match response.data.length >= 0
    * match response.total >= 0
    * match response.per_page == response.data.length
    * match response.total >= response.total_pages

    * configure continueOnStepFailure = { enabled: false, continueAfter: true, keywords: ['match', 'assert'] }

  Scenario: Check total number of listed users
    * call read('../TestBasis/ReqresBaseScenarios.feature@GetUsersDefault') {'expCode': 200}
    * def totalPages = response.total_pages
    # * def dynamicIterator = function(i){ if (i == totalPages) return null; return {} }


  # TODO
#  Scenario: Check that all users aren't missing any data
#    * call read('../TestBasis/ReqresBaseScenarios.feature@GetUsers') {'expCode': 200}
#    * match

  # TODO
  # Scenario: Check that testuser is listed
