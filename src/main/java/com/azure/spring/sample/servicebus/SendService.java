package com.azure.spring.sample.servicebus;

import com.azure.spring.integration.core.DefaultMessageHandler;
import com.azure.spring.integration.servicebus.queue.ServiceBusQueueOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class SendService {

    private static final Log LOGGER = LogFactory.getLog(SendService.class);
    private static final String QUEUE_NAME = "testsb-sbqueue";

    public MessageHandler queueMessageSender(ServiceBusQueueOperation queueOperation) {
        DefaultMessageHandler handler = new DefaultMessageHandler(QUEUE_NAME, queueOperation);
        handler.setSendCallback(new ListenableFutureCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                LOGGER.info("Message was sent successfully.");
            }

            @Override
            public void onFailure(Throwable ex) {
                LOGGER.info("There was an error sending the message.");
            }
        });

        return handler;
    }
}
