@parallel=false
Feature: Includes Basic Scenarios To Be Used For The Configuration Controller Test Cases

  Background:
    * url reqresUrl
    * header Content-Type = 'application/json; charset=utf-8'

  @RegisterUser
  Scenario: Register User - ${reqBody} ${expCode}
    Given path "/api/register"
    And request reqBody
    When method POST
    Then match expCode == responseStatus

  @GetUsersDefault
  Scenario: Get Users - ${expCode}
    Given path "/api/users"
    When method GET
    Then match expCode == responseStatus

  @GetUsersPage
  Scenario: Get Users - ${page} ${expCode}
    Given path "/api/users?page=" + page
    When method GET
    Then match expCode == responseStatus
#
#  Scenario Outline: Retrieve page users
#    * call read('@GetUsersPage') {'expCode': 200}
#    Examples:
#      | dynamicIterator |
