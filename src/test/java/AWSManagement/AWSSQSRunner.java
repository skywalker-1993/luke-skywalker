package AWSManagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class AWSSQSClient {

  private Map<String, String> sqsInformation = new HashMap<>();

  private static final int MESSAGES_TOTAL = 3;

  private void setSqsInformation() {
    String hostName = null != System.getenv("LOCALSTACK_HOST") ? System.getenv("LOCALSTACK_HOST") : "localhost";
    String hostPort = null != System.getenv("LOCALSTACK_PORT") ? System.getenv("LOCALSTACK_PORT") : "4566";
    sqsInformation.put("LOCALSTACK_ENDPOINT", "http://" + hostName + ":" + hostPort);
    sqsInformation.put("LOCALSTACK_REGION", null != System.getenv("LOCALSTACK_REGION") ? System.getenv(
        "LOCALSTACK_REGION"
    ) : "us-west-2");
    sqsInformation.put("AWS_ACCESS_KEY_ID", null != System.getenv("AWS_ACCESS_KEY_ID") ? System.getenv(
        "AWS_ACCESS_KEY_ID"
    ) : "testuser");
    sqsInformation.put("AWS_SECRET_ACCESS_KEY", null != System.getenv("AWS_SECRET_ACCESS_KEY") ? System.getenv(
        "AWS_ACCESS_KEY_ID"
    ) : "testpassword");
  }



//  public static AmazonSQS getSQSClient() {
//    return AmazonSQSAsyncClientBuilder.standard()
//        .withCredentials(new BasicAWSCredentials(sqsUser, sqsPassword))
//        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsEndpoint, sqsRegion))
//        .build();
//  }
//
//  public static void createQueue(AmazonSQS client, String queueName) {
//    CreateQueueRequest createRequest = new CreateQueueRequest(queueName)
//        .addAttributesEntry("DelaySeconds", "0")
//        .addAttributesEntry("MessageRetentionPeriod", "86400"); //Remove
//    try {
//      client.createQueue(createRequest);
//    } catch (AmazonSQSException e) {
//      if (!"QueueAlreadyExists".equals(e.getErrorCode())) {
//        throw e;
//      }
//    }
//  }
//
//  public static String getQueueUrl(AmazonSQS client, String queueName) {
//    for (String url: client.listQueues().getQueueUrls()) {
//      if (url.endsWith(queueName)) return url;
//    }
//    return "";
//  }
//
//  public static void sendMessage(AmazonSQS client, String queueUrl, String msg) {
//    SendMessageRequest sendMsgRequest = new SendMessageRequest()
//        .withQueueUrl(queueUrl)
//        .withMessageBody(msg)
//        .withDelaySeconds(0);
//    client.sendMessage(sendMsgRequest);
//  }
//
//  public static void sendSeveralMessages(AmazonSQS client, String queueUrl, List<String> messages) {
//    for (String msg: messages) {
//      sendMessage(client, queueUrl, msg);
//    }
//  }
//
//  public static List<String> createMessagesToSend() throws JsonProcessingException {
//    List<String> messagesToSend = new ArrayList<>();
//    for (int i = 0; MESSAGES_TOTAL > i; i++) {
//      messagesToSend.add(getCarBody(getRandomCarObject()));
//    }
//    return messagesToSend;
//  }
//
//  public static List<Message> receiveMessages(AmazonSQS client, String queueUrl) {
//    return client.receiveMessage(queueUrl).getMessages();
//  }
//
//  public static boolean checkReceivedMessages(AmazonSQS client, String queueUrl, List<String> sentMessages) {
//    List<String> msgMatch = new ArrayList<>();
//    for (int msgIndex = 0; MESSAGES_TOTAL > msgIndex; msgIndex++) {
//      List<Message> messages = receiveMessages(client, queueUrl);
//      if (sentMessages.get(msgIndex).equals(messages.get(0).getBody())) {
//        msgMatch.add(sentMessages.get(msgIndex));
//      }
//      client.deleteMessage(queueUrl, messages.get(0).getReceiptHandle());
//    }
//    return (MESSAGES_TOTAL == msgMatch.size());
//  }

  public static void main(String[] args) throws JsonProcessingException {
    AWSSQSClient sqsRunner = new AWSSQSClient();
    sqsRunner.setSqsInformation();
    System.out.println(sqsRunner.getSqsInformation());
//    List<String> messagesToSend = createMessagesToSend();
//    AmazonSQS sqs = getSQSClient();
//    createQueue(sqs, "cars");
//    String queueUrl = getQueueUrl(sqs, "cars");
//    //Make sure that queue is empty first!!!
//    sendSeveralMessages(sqs, queueUrl, messagesToSend);
//    System.out.println(checkReceivedMessages(sqs, queueUrl, messagesToSend));
  }

}
