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

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.netflix.conductor.sdk.workflow.executor.task.WorkerConfiguration;

@Component
public class OrkesSpringWorkerConfiguration extends WorkerConfiguration {

    private final Environment environment;

    public OrkesSpringWorkerConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Override
    public int getPollingInterval(String taskName) {
        String key = "conductor.worker." + taskName + ".pollingInterval";
        return environment.getProperty(key, Integer.class, 0);
    }

    @Override
    public int getThreadCount(String taskName) {
        String key = "conductor.worker." + taskName + ".threadCount";
        return environment.getProperty(key, Integer.class, 0);
    }

    @Override
    public String getDomain(String taskName) {
        String key = "conductor.worker." + taskName + ".domain";
        return environment.getProperty(key, String.class, null);
    }
}