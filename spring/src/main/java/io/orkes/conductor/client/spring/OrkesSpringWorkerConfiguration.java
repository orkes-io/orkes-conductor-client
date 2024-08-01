/*
 * Copyright 2023 Orkes, Inc.
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
package io.orkes.conductor.client.spring;


import io.orkes.conductor.sdk.workflow.executor.task.WorkerConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class OrkesSpringWorkerConfiguration extends WorkerConfiguration {

    private final Environment environment;

    public OrkesSpringWorkerConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Override
    public int getPollingInterval(String taskName) {
        return getProperty(taskName, "pollingInterval", Integer.class, 0);
    }

    @Override
    public int getThreadCount(String taskName) {
        return getProperty(taskName, "threadCount", Integer.class, 0);
    }

    @Override
    public String getDomain(String taskName) {
        return getProperty(taskName, "domain", String.class, null);
    }

    private <T>T getProperty(String taskName, String property, Class<T> type, T defaultValue) {
        String key = "conductor.worker." + taskName + "." + property;
        T value = environment.getProperty(key, type, defaultValue);
        if(value == defaultValue) {
            key = "conductor.worker.all." + property;
            value = environment.getProperty(key, type, defaultValue);
        }
        return value;
    }
}
