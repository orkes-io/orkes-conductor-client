package io.orkes.conductor.client.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ExtendedWorkflowDef extends WorkflowDef {

    private Set<Tag<?>> tags;

    private BusinessStateSchema businessStateSchema;

    @JsonSetter("tags")
    public void setTags(List<Tag<?>> tags) {
        this.tags = new HashSet<>(tags);
    }

    @JsonGetter("tags")
    public List<Tag<?>> getTags() {
        return new ArrayList<>(tags);
    }

    public ExtendedWorkflowDef(WorkflowDef def) {
        this.setCreatedBy(def.getCreatedBy());
        this.setCreateTime(def.getCreateTime());
        this.setDescription(def.getDescription());
        this.setFailureWorkflow(def.getFailureWorkflow());
        this.setInputParameters(def.getInputParameters());
        this.setInputTemplate(def.getInputTemplate());
        this.setName(def.getName());
        this.setOutputParameters(def.getOutputParameters());
        this.setOwnerEmail(def.getOwnerEmail());
        this.setOwnerApp(def.getOwnerApp());
        this.setRestartable(def.isRestartable());
        this.setSchemaVersion(def.getSchemaVersion());
        this.setTasks(def.getTasks());
        this.setTimeoutPolicy(def.getTimeoutPolicy());
        this.setTimeoutSeconds(def.getTimeoutSeconds());
        this.setUpdatedBy(def.getUpdatedBy());
        this.setUpdateTime(def.getUpdateTime());
        this.setVariables(def.getVariables());
        this.setVersion(def.getVersion());
        this.setWorkflowStatusListenerEnabled(def.isWorkflowStatusListenerEnabled());
    }
}
