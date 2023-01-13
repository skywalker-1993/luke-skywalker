@parallel=false
Feature: Import JS Functions

  Background:

    #############
    # LIBRARIES #
    #############
    * def reqresLib = Java.type('RequestBody.ReqresUser')
    * def genericLib = Java.type('RequestBody.GenericRequest')
    * def paginationLib = Java.type('PaginationCheck.PagesCheck')

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

    #######################
    # RESPONSE VALIDATION #
    #######################


  Scenario: Import JS Functions
