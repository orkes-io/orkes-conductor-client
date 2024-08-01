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
package io.orkes.conductor.sdk.workflow.def.tasks;

import io.orkes.conductor.client.model.metadata.tasks.TaskType;
import io.orkes.conductor.client.model.metadata.workflow.WorkflowTask;

import com.google.common.base.Strings;

/**
 * JQ Transformation task See https://stedolan.github.io/jq/ for how to form the queries to parse
 * JSON payloads
 */
public class JQ extends Task<JQ> {

    private static final String QUERY_EXPRESSION_PARAMETER = "queryExpression";

    public JQ(String taskReferenceName, String queryExpression) {
        super(taskReferenceName, TaskType.JSON_JQ_TRANSFORM);
        if (Strings.isNullOrEmpty(queryExpression)) {
            throw new AssertionError("Null/Empty queryExpression");
        }
        super.input(QUERY_EXPRESSION_PARAMETER, queryExpression);
    }

    JQ(WorkflowTask workflowTask) {
        super(workflowTask);
    }

    public String getQueryExpression() {
        return (String) getInput().get(QUERY_EXPRESSION_PARAMETER);
    }
}
