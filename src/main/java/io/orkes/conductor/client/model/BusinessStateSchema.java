package io.orkes.conductor.client.model;

import lombok.Data;

import java.util.Map;

@Data
public class BusinessStateSchema {

    private Map<String, Map<String, Object>> schema;
}
