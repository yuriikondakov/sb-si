// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.servicebus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Warren Zhu
 */
@RestController
public class QueueSendController {

    @Autowired
    QueueOutboundGateway messagingGateway;

    @PostMapping("/queues")
    public String send(@RequestParam("message") String message) {
        this.messagingGateway.send(message);
        return message;
    }

}
