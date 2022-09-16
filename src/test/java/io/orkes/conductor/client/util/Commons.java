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
package io.orkes.conductor.client.util;

import com.netflix.conductor.common.metadata.tasks.TaskDef;

import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.TagString;

public class Commons {
    public static String WORKFLOW_NAME = "test-sdk-java-workflow";
    public static String TASK_NAME = "test-sdk-java-task";
    public static String OWNER_EMAIL = "example@orkes.io";
    public static int WORKFLOW_VERSION = 1;
    public static String GROUP_ID = "sdk-test-group";
    public static String USER_NAME = "Orkes User";
    public static String USER_EMAIL = "user@orkes.io";
    public static String APPLICATION_ID = "46f0bf10-b59d-4fbd-a053-935307c8cb86";

    public static TagObject getTagObject() {
        TagObject tagObject = new TagObject();
        tagObject.setType(TagObject.TypeEnum.METADATA);
        tagObject.setKey("a");
        tagObject.setValue("b");
        return tagObject;
    }

    public static TagString getTagString() {
        TagString tagString = new TagString();
        tagString.setType(TagString.TypeEnum.METADATA);
        tagString.setKey("a");
        tagString.setValue("b");
        return tagString;
    }

    public static TaskDef getTaskDef() {
        TaskDef taskDef = new TaskDef();
        taskDef.setName(Commons.TASK_NAME);
        return taskDef;
    }
}
