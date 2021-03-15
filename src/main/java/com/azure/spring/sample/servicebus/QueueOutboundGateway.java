package com.azure.spring.sample.servicebus;

public interface QueueOutboundGateway {
    void send(String text);
}
