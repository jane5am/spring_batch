package com.example.demo.batch.job;

import com.example.demo.batch.service.BatchProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AdCalculateBatchJob {

    @Autowired
    private BatchProcessingService batchProcessingService;

//    @Scheduled(cron = "*/10 * * * * ?")
//    public void runBatchJob() {
//        System.out.println("Batch job started");
//        batchProcessingService.calculateAdWatchCounts();
//    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void rundailyViewsTop5job() {
        System.out.println("Batch rundailyViewsTop5job started");
        batchProcessingService.DailyTop5Videos();
    }
}
