package AWSManagement;

import static RequestBody.CarRequest.getCarBody;
import static RequestBody.CarRequest.getRandomCarObject;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AWSSQSClient {

  //try {
  //sqs.createQueue(create_request);
  //    } catch (AmazonSQSException e) {
  //      if (!e.getErrorCode().equals("QueueAlreadyExists")) {
  //        throw e;
  //      }
  //    }
  private static final String sqsEndpoint = "http://localhost:4566";
  private static final String sqsRegion = "us-west-2";


  public static AmazonSQS getSQSClient() {
    return AmazonSQSAsyncClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsEndpoint, sqsRegion))
        .build();
  }

  public static void createQueue(AmazonSQS client, String queueName) {
    CreateQueueRequest createRequest = new CreateQueueRequest(queueName)
        .addAttributesEntry("DelaySeconds", "0")
        .addAttributesEntry("MessageRetentionPeriod", "86400");
    client.createQueue(createRequest);
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

  public static List<Message> receiveMessages(AmazonSQS client, String queueUrl) {
    return client.receiveMessage(queueUrl).getMessages();
  }

  public static void main(String[] args) throws JsonProcessingException {
    String messageToSend = getCarBody(getRandomCarObject());
    AmazonSQS sqs = getSQSClient();
    createQueue(sqs, "cars");
    String queueUrl = getQueueUrl(sqs, "cars");
    sendMessage(sqs, queueUrl, messageToSend);
    System.out.println(receiveMessages(sqs, queueUrl));
  }

}
