// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.servicebus;

import com.azure.spring.integration.core.AzureHeaders;
import com.azure.spring.integration.core.api.CheckpointConfig;
import com.azure.spring.integration.core.api.CheckpointMode;
import com.azure.spring.integration.core.api.Checkpointer;
import com.azure.spring.integration.servicebus.inbound.ServiceBusQueueInboundChannelAdapter;
import com.azure.spring.integration.servicebus.queue.ServiceBusQueueOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @author Warren Zhu
 */

public class QueueReceiveController {

    @Autowired
    MessageChannel inputChannel;

    private static final String QUEUE_NAME = "testsb-sbqueue";

    public void messageReceiver(byte[] payload, @Header(AzureHeaders.CHECKPOINTER) Checkpointer checkpointer) {
        String message = new String(payload);
        System.out.printf("New message received: '%s'%n", message);
        checkpointer.success().handle((r, ex) -> {
            if (ex == null) {
                System.out.printf("Message '%s' successfully checkpointed.%n", message);
            }
            return null;
        });
    }

    @Bean
    public ServiceBusQueueInboundChannelAdapter queueMessageChannelAdapter(
        MessageChannel inputChannel, ServiceBusQueueOperation queueOperation) {
        queueOperation.setCheckpointConfig(CheckpointConfig.builder().checkpointMode(CheckpointMode.MANUAL).build());
        ServiceBusQueueInboundChannelAdapter adapter = new ServiceBusQueueInboundChannelAdapter(QUEUE_NAME,
            queueOperation);
        adapter.setOutputChannel(inputChannel);
        return adapter;
    }
}
