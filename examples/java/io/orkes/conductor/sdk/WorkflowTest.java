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
package io.orkes.conductor.sdk;

public class WorkflowTest extends AbstractWorkflowTests {
    public void testWF(){
        WorkflowDef def = null;
        Map<String, List<WorkflowTestRequest.TaskMock>> testInputs=null;
        try {
            def = getWorkflowDef("/workflows/calculate_loan_workflow.json");
            assertNotNull(def);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
       try{
           testInputs = getTestInputs("input.json");
           assertNotNull(testInputs);
       }catch(Exception e){
           throw new RuntimeException(e);
       }
        WorkflowTestRequest testRequest = new WorkflowTestRequest();
        testRequest.setWorkflowDef(def);
        WorkflowInput workflowInput = new WorkflowInput("Orkes");
        testRequest.setInput(objectMapper.convertValue(workflowInput, Map.class));

        testRequest.setTaskRefToMockOutput(testInputs);
        testRequest.setName(def.getName());
        testRequest.setVersion(def.getVersion());

        Workflow execution = workflowClient.testWorkflow(testRequest);
        assertNotNull(execution);

        //Assert that the workflow completed successfully
        assertEquals(Workflow.WorkflowStatus.COMPLETED, execution.getStatus());

        //Ensure the inputs were captured correctly
        assertEquals(workflowInput.getName(), String.valueOf(execution.getInput().get("name")));

        //Assert that 1 task has been completed
        assertEquals(1, execution.getTasks().size());

        //fetch the correct input from the workflow
        assertEquals(workflowInput.getName(), execution.getTasks().get(0).getInputData().get("name"));

        //And that the task produced the right output
        String result = "Hello Orkes";
        assertEquals(result, execution.getTasks().get(0).getOutputData().get("result"));


    }
}
