/*
 * Copyright 2022 Orkes, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.client.model.event.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import io.orkes.conductor.client.model.event.QueueWorkerConfiguration;

public class KafkaConsumer extends QueueWorkerConfiguration {
    private static final String MAX_POLL_RECORDS_CONFIG = "1000";

    public KafkaConsumer(String bootstrapServersConfig) throws Exception {
        super(ConsumerConfig.configNames());
        withConfiguration(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
        withConfiguration(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, MAX_POLL_RECORDS_CONFIG);
    }
}
