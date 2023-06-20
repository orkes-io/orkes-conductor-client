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
package io.orkes.conductor.client.automator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads and processes environmental variables related to Conductor worker
 * configuration. The following environmental variable pattern is supported:
 * - CONDUCTOR_WORKER_TASK_NAME_DOMAIN
 *
 * The pattern allows users to define custom task-specific configuration by
 * setting environmental variables with a specific format. The "TASK_NAME" part
 * in the pattern represents the name of the task for which the configuration is
 * intended. The actual task name can replace the ".*" portion in the pattern.
 *
 * Note: To ensure compatibility with all operating systems, it is recommended
 * to use underscores (_) instead of dots (.) within the task name.
 *
 * Examples:
 * - CONDUCTOR_WORKER_EMAIL_NOTIFICATION_DOMAIN: Configuration for the
 * "EMAIL_NOTIFICATION" task.
 * - CONDUCTOR_WORKER_Em@il_N0T1F1C4TI0N_DOMAIN: Configuration for the
 * "Em@il_N0T1F1C4TI0N" task.
 * - CONDUCTOR_WORKER_DATA_PROCESSING_DOMAIN: Configuration for the
 * "DATA_PROCESSING" task.
 *
 * If a task-specific configuration is not found, default configuration values
 * will be used.
 */
public class EnvironmentVariableReader {
    private static final Logger LOGGER;

    private static final Map<String, String> taskNameToDomain;

    static {
        LOGGER = LoggerFactory.getLogger(TaskRunnerConfigurer.class);
        taskNameToDomain = readTaskNameToDomain();
    }

    private static Map<String, String> readTaskNameToDomain() {
        String ENV_VARIABLE_PATTERN = "CONDUCTOR_WORKER_(.*?)_DOMAIN";
        Map<String, String> taskConfigurations = new HashMap<>();
        Pattern pattern = Pattern.compile(ENV_VARIABLE_PATTERN);
        Map<String, String> envVars = System.getenv();
        for (Map.Entry<String, String> entry : envVars.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Matcher matcher = pattern.matcher(key);
            if (!matcher.matches()) {
                continue;
            }
            String taskName = matcher.group(1);
            taskConfigurations.put(taskName, value);
            LOGGER.debug("Found domain: " + value + " for taskName: " + taskName + " from env var");
        }
        return taskConfigurations;
    }

    public static Map<String, String> getTaskNameToDomain() {
        return taskNameToDomain;
    }
}
