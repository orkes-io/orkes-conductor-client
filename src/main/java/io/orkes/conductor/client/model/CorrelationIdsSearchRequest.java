package io.orkes.conductor.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorrelationIdsSearchRequest {

    private List<String> correlationIds;

    private List<String> workflowNames;
}
