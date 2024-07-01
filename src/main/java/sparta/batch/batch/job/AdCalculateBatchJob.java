package sparta.batch.batch.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sparta.batch.batch.service.BatchProcessingService;

@Component
public class AdCalculateBatchJob {

    @Autowired
    private BatchProcessingService batchProcessingService;

    @Scheduled(cron = "*/300 * * * * ?")
    public void runBatchJob() {
        System.out.println("Batch job started");
        batchProcessingService.calculateAdWatchCounts();
    }
}
