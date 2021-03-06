package com.minsoo.autocompletecrud.pubsub.configuration;

import com.minsoo.autocompletecrud.domain.ProductPubSub;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

import java.util.ArrayList;

@Configuration
public class ReceiverConfiguration {
    private static final String SUBSCRIPTION_NAME = "product_sub";

    private final ArrayList<ProductPubSub> processedProductList = new ArrayList<>();

    @Bean
    public DirectChannel pubSubInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("pubSubInputChannel") MessageChannel inputChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, SUBSCRIPTION_NAME);
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(ProductPubSub.class);
        return adapter;
    }

    @ServiceActivator(inputChannel = "pubSubInputChannel")
    public void messageReceiver(ProductPubSub payload,
                                @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        System.out.println("Message arrived! Payload: " + payload);
        this.processedProductList.add(payload);
        message.ack();
    }

    @Bean
    @Qualifier("ProcessedProductList")
    public ArrayList<ProductPubSub> processedPorductList() {
        return this.processedProductList;
    }
}
