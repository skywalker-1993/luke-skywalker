@parallel=false
Feature: Import JS Functions

  Background:

    #############
    # LIBRARIES #
    #############
    * def reqresLib = Java.type('RequestBody.ReqresUser')
    * def genericLib = Java.type('RequestBody.GenericRequest')
    * def paginationLib = Java.type('PaginationCheck.PagesCheck')
    * def lookupUserLib = Java.type('PaginationCheck.UserLookup')
    * def awsSQSLib = Java.type('AWSManagement.AWSSQSRunner')

    ###################
    # GENERIC METHODS #
    ###################
    * def checkType =
    """
    function checkType(type, val) {
      return (typeof val) === type;
    }
    """
    * def isObjectArray =
    """
    function isObjectArray(obj) {
      if ((typeof obj) !== "object") throw new Error(`Provided value \"${obj}\" is not an object!`);
      return Array.isArray(obj);
    }
    """
    * def renameKeyFromObject =
    """
    function renameKeyFromObject(obj, oldKey) {
      const newObj = obj;
      newObj[genericLib.getRandomBoundedString()] = obj[oldKey];
      delete obj[oldKey];
      return newObj;
    }
    """

  Scenario: Import JS Functions
    * print env