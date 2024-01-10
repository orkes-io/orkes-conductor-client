package io.orkes.conductor.client.ai;

import lombok.*;
import lombok.extern.slf4j.*;

@Data
@Slf4j
@Builder
public class AIConfiguration {
    private String llmProvider;
    private String textCompleteModel;
    private String chatCompleteModel;
    private String embeddingModel;
    private String vectorDB;
}
