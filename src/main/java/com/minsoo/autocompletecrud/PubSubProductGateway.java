package com.minsoo.autocompletecrud;

import com.minsoo.autocompletecrud.domain.ProductPubSub;
import org.springframework.integration.annotation.MessagingGateway;

/**
 * an interface that allows sending a person to Pub/Sub.
 */
@MessagingGateway(defaultRequestChannel = "pubSubOutputChannel")
public interface PubSubProductGateway {
    void sendProductToPubSub(ProductPubSub product);
}
