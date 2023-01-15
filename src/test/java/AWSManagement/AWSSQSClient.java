package AWSManagement;

import static RequestBody.CarRequest.getCarBody;
import static RequestBody.CarRequest.getRandomCarObject;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AWSSQSClient {


  private static final String sqsEndpoint = "http://" + System.getenv("LOCALSTACK_HOST")
      + ":" + System.getenv("LOCALSTACK_PORT"); //"http://localhost:4566";
  private static final String sqsRegion = System.getenv("LOCALSTACK_REGION"); //"us-west-2";

  private static final int MESSAGES_TOTAL = 3;


  public static AmazonSQS getSQSClient() {
    return AmazonSQSAsyncClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsEndpoint, sqsRegion))
        .build();
  }

  public static void createQueue(AmazonSQS client, String queueName) {
    CreateQueueRequest createRequest = new CreateQueueRequest(queueName)
        .addAttributesEntry("DelaySeconds", "0")
        .addAttributesEntry("MessageRetentionPeriod", "86400");
    try {
      client.createQueue(createRequest);
    } catch (AmazonSQSException e) {
      if (!"QueueAlreadyExists".equals(e.getErrorCode())) {
        throw e;
      }
    }
  }

  public static String getQueueUrl(AmazonSQS client, String queueName) {
    for (String url: client.listQueues().getQueueUrls()) {
      if (url.endsWith(queueName)) return url;
    }
    return "";
  }

  public static void sendMessage(AmazonSQS client, String queueUrl, String msg) {
    SendMessageRequest sendMsgRequest = new SendMessageRequest()
        .withQueueUrl(queueUrl)
        .withMessageBody(msg)
        .withDelaySeconds(0);
    client.sendMessage(sendMsgRequest);
  }

  public static void sendSeveralMessages(AmazonSQS client, String queueUrl, List<String> messages) {
    for (String msg: messages) {
      sendMessage(client, queueUrl, msg);
    }
  }

  public static List<String> createMessagesToSend() throws JsonProcessingException {
    List<String> messagesToSend = new ArrayList<>();
    for (int i = 0; MESSAGES_TOTAL > i; i++) {
      messagesToSend.add(getCarBody(getRandomCarObject()));
    }
    return messagesToSend;
  }

  public static List<Message> receiveMessages(AmazonSQS client, String queueUrl) {
    return client.receiveMessage(queueUrl).getMessages();
  }

  public static boolean checkReceivedMessages(AmazonSQS client, String queueUrl, List<String> sentMessages) {
    List<String> msgMatch = new ArrayList<>();
    for (int msgIndex = 0; MESSAGES_TOTAL > msgIndex; msgIndex++) {
      List<Message> messages = receiveMessages(client, queueUrl);
      if (sentMessages.get(msgIndex).equals(messages.get(0).getBody())) {
        msgMatch.add(sentMessages.get(msgIndex));
      }
      client.deleteMessage(queueUrl, messages.get(0).getReceiptHandle());
    }
    return (MESSAGES_TOTAL == msgMatch.size());
  }

  public static void main(String[] args) throws JsonProcessingException {
    List<String> messagesToSend = createMessagesToSend();
    AmazonSQS sqs = getSQSClient();
    createQueue(sqs, "cars");
    String queueUrl = getQueueUrl(sqs, "cars");
    //Make sure that queue is empty first!!!
    sendSeveralMessages(sqs, queueUrl, messagesToSend);
    System.out.println(checkReceivedMessages(sqs, queueUrl, messagesToSend));
  }

}
