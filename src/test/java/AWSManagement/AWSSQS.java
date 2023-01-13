package AWSManagement;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AWSSQS {

  public static void main(String[] args) {
    AmazonSQS sqs = AmazonSQSAsyncClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-west-2"))
        .build();

    // AmazonSQS sqs = AmazonSQSAsyncClientBuilder.standard().build().setEndpoint("http://localhost:4566");
    //AmazonSQS sqs = SqsAsyncClient.builder().endpointOverride(URI.create("http://localhost:4566"));
    System.out.println(sqs.listQueues());


//    CreateQueueRequest create_request = new CreateQueueRequest("demo2")
//        .addAttributesEntry("DelaySeconds", "60")
//        .addAttributesEntry("MessageRetentionPeriod", "86400");
//    sqs.createQueue(create_request);

    System.out.println(sqs.listQueues());

    //System.out.println(sqs.getQueueUrl("demo2"));

//    SendMessageRequest send_msg_request = new SendMessageRequest()
//        .withQueueUrl("http://localhost:4566/000000000000/demo2")
//        .withMessageBody("{\"ola\": \"adeus\"}")
//        .withDelaySeconds(5);
//    sqs.sendMessage(send_msg_request);
//
//    System.out.println(sqs.sendMessage("http://localhost:4566/000000000000/demo2", "{\"ola\": \"adeus\"}"));
    //System.out.println(sqs.receiveMessage("http://localhost:4566/000000000000/demo2").getMessages());
    //try {
      //sqs.createQueue(create_request);
//    } catch (AmazonSQSException e) {
//      if (!e.getErrorCode().equals("QueueAlreadyExists")) {
//        throw e;
//      }
//    }

     List<Message> messages = sqs.receiveMessage("http://localhost:4566/000000000000/demo2").getMessages();
     System.out.println(messages);
  }

}
