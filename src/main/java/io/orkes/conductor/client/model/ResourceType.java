package io.orkes.conductor.client.model;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public enum ResourceType {

    WORKFLOW,
    WORKFLOW_DEF,
    WORKFLOW_SCHEDULE,
    TASK_DEF,
    TASK_REF_NAME,
    TASK_ID,
    APPLICATION,
    USER,
    SECRET_NAME,
    TAG,
    DOMAIN,
    INTEGRATION_PROVIDER,
    INTEGRATION,
    PROMPT;

    public static final Set<String> RESOURCE_NAMES = Arrays.stream(ResourceType.values())
            .map(Enum::name)
            .collect(toSet());

    public static boolean isValid(String resourceName) {
        return RESOURCE_NAMES.contains(resourceName.toUpperCase());
    }

    public static ResourceType from(String resourceType) {
        return ResourceType.valueOf(resourceType.toUpperCase());
    }
}
