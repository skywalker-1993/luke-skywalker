@parallel=true
Feature: Demo Challenge Task #3 - Mockserver

  Background: Get mockserver dependencies
    * url mockserverUrl

  Scenario: Successfully get users list - Validate Response Structure
    Given path "/someEndpoint"
    When method GET
    Then status 200
    And print response.someAttribute
