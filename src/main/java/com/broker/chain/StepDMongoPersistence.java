package com.broker.chain;

import com.broker.model.ProcessedResult;
import com.broker.repository.FinalResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class StepDMongoPersistence extends BaseHandler {

    private final FinalResultRepository finalResultRepository;

    @Override
    public void handle(ChainState state) {
        System.out.println("[PASO D] Persistiendo en MongoDB final...");

        ProcessedResult result = new ProcessedResult();
        result.setJobId(state.getJobId());
        result.setJobType(state.getJobType());
        result.setData(state.getData());
        result.setProcessedAt(LocalDateTime.now());
        result.setStatus("SUCCESS");

        finalResultRepository.save(result);
        
        next(state);
    }
}
