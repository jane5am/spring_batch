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

    // 동영상 daily 조회수 통계
    @Scheduled(cron = "*/500 * * * * ?")
    public void runVideoDailyViewsJob() {
        System.out.println("Batch runVideoDailyViewsJob started");
        batchProcessingService.getVideoDailyViews();
    }

    // 동영상 daily 재생시간 통계
    @Scheduled(cron = "*/5 * * * * ?")
    public void runVideoDailyPlayTimeJob() {
        System.out.println("Batch VideoDailyPlayTimeJob started");
        batchProcessingService.getVideoDailyPlayTime();
    }
}
