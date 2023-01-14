@parallel=true
Feature: Demo Challenge Task #2 - Stream processing test

  Scenario: Successfully send and consume data
    Given def messagesToSend = awsSQSLib.createMessagesToSend();
    And def sqs = awsSQSLib.getSQSClient();
    When awsSQSLib.createQueue(sqs, "cars");
    And def queueUrl = awsSQSLib.getQueueUrl(sqs, "cars");
    Then awsSQSLib.sendSeveralMessages(sqs, queueUrl, messagesToSend)
    And print awsSQSLib.checkReceivedMessages(sqs, queueUrl, messagesToSend)
