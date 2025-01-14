/*
 * Copyright (C) Scott Cranton and Jakub Korab
 * https://github.com/CamelCookbook
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camelcookbook.error.synchronizations;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spi.Synchronization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor that starts a mock remote operation, then commits or cancels it depending on whether the Exchange
 * was successfully processed through the rest of the route.
 */
public class ConfirmCancelProcessor implements Processor {
    private final Logger log = LoggerFactory.getLogger(ConfirmCancelProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Starting two-phase operation");

        //final ProducerTemplate producerTemplate =
        //    exchange.getContext().createProducerTemplate();
        try (ProducerTemplate producerTemplate =
                     exchange.getContext().createProducerTemplate()) {

            producerTemplate.send("mock:start", exchange);

        } catch (Exception e) {
            log.info("Exception occurred - cancelling");

        } finally {
            log.info("Completed - confirming");

        }

//        exchange.addOnCompletion(new Synchronization() {
//            @Override
//            public void onComplete(Exchange exchange) {
//                log.info("Completed - confirming");
//                producerTemplate.send("mock:confirm", exchange);
//            }
//
//            @Override
//            public void onFailure(Exchange exchange) {
//                log.info("Failed - cancelling");
//                producerTemplate.send("mock:cancel", exchange);
//            }
//        });
    }
}
