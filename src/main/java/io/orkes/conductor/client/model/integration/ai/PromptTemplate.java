package io.orkes.conductor.client.model.integration.ai;

import java.util.List;

import io.orkes.conductor.client.model.TagObject;
import lombok.Data;

@Data
public class PromptTemplate {

    private String createdBy;
    private Long createdOn;
    private String description;
    private List<String> integrations;
    private String name;
    private List<TagObject> tags;
    private String template;
    private String updatedBy;
    private Long updatedOn;
    private List<String> variables;
}
