package io.orkes.conductor.client.model.integration;

import java.util.List;
import java.util.Map;

import io.orkes.conductor.client.model.TagObject;
import lombok.Data;

@Data
public class Integration {

    private Category category;
    private Map<String, Object> configuration;
    private String createdBy;
    private Long createdOn;
    private String description;
    private Boolean enabled;
    private long modelsCount;
    private String name;
    private List<TagObject> tags;
    private String type;
    private String updatedBy;
    private Long updatedOn;

}
