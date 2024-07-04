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

//    // 동영상 1일 조회수 Top5
//    @Scheduled(cron = "*/5 * * * * ?")
//    public void runDailyTop5VideosJob() {
//        System.out.println("Batch runDailyTop5VideosJob started");
//        batchProcessingService.DailyTop5Videos();
//    }

    // 광고 1일 조회수 Top5
    @Scheduled(cron = "*/5 * * * * ?")
    public void runDailyTop5AdsJob() {
        System.out.println("Batch runDailyTop5AdsJob started");
        batchProcessingService.DailyTop5Ads();
    }
}
