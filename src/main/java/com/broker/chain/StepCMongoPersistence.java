package com.broker.chain;

import com.broker.model.ProcessedResult;
import com.broker.repository.FinalResultRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class StepCMongoPersistence extends BaseHandler {
    private final FinalResultRepository mongoRepo;
    public StepCMongoPersistence(FinalResultRepository mongoRepo) { this.mongoRepo = mongoRepo; }
    @Override
    public void handle(ChainState state) {
        System.out.println("[PASO D] Persistiendo en MongoDB final...");
        ProcessedResult result = ProcessedResult.builder()
                .originalId(state.getDbId())
                .type(state.getType())
                .data(state.getData())
                .result("SUCCESS")
                .processedAt(LocalDateTime.now())
                .build();
        mongoRepo.save(result);
        state.setFinalResult("COMPLETED");
    }
}
