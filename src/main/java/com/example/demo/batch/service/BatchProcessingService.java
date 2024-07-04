package com.example.demo.batch.service;

import com.example.demo.batch.domain.DailyViewsTop5.DailyTop5Videos;
import com.example.demo.batch.domain.DailyViewsTop5.DailyTop5VideosRepository;
import com.example.demo.batch.domain.adcalculate.AdCalculate;
import com.example.demo.batch.domain.adcalculate.AdCalculateRepository;
import com.example.demo.streaming.repository.AdWatchHistoryRepository;
import com.example.demo.streaming.repository.VideoWatchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class BatchProcessingService {

    @Autowired
    private AdWatchHistoryRepository adWatchHistoryRepository;

    @Autowired
    private AdCalculateRepository adCalculateRepository;

    @Autowired
    private VideoWatchHistoryRepository videoWatchHistoryRepository;

    @Autowired
    private DailyTop5VideosRepository dailyTop5VideosRepository;

    @Transactional
    public void calculateAdWatchCounts() {
        int[] videoIds = {1, 2, 3};

        for (int videoId : videoIds) {
            int count = adWatchHistoryRepository.countByVideoId(videoId);
            AdCalculate adCalculate = new AdCalculate();
            adCalculate.setVideoId(videoId);
            adCalculate.setAdCalculateAmount(count);
            adCalculateRepository.save(adCalculate);
        }
    }

    @Transactional
    public void DailyTop5Videos() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        LocalDateTime startOfDay = yesterday.atStartOfDay(); // 어제의 시작 시간 (00:00)
        LocalDateTime endOfDay = yesterday.atTime(LocalTime.MAX); // 어제의 종료 시간 (23:59:59.999999999)

        System.out.println("Start of Day : " + startOfDay);
        System.out.println("End of Day : " + endOfDay);

        List<Object[]> top5Videos = videoWatchHistoryRepository.findTop5VideosByViewDateBetween(startOfDay, endOfDay);

        System.out.println("Top 5 Videos : " + top5Videos);

        top5Videos.forEach(video -> {
            int videoId = (int) video[0];
            long viewCount = (long) video[1];

            System.out.println("Video ID : " + videoId);
            System.out.println("View Count : " + viewCount);

            DailyTop5Videos dailyVideoViewsTop5 = new DailyTop5Videos();
            dailyVideoViewsTop5.setVideoId(videoId);
            dailyVideoViewsTop5.setViews((int) viewCount);
            dailyVideoViewsTop5.setDate(yesterday.atStartOfDay());

            dailyTop5VideosRepository.save(dailyVideoViewsTop5);
        });
    }

//    @Transactional
//    public void DailyTop5Videos() {
//        LocalDate today = LocalDate.now();
//        LocalDate yesterday = today.minusDays(1);
//
//        LocalDateTime startOfDay = yesterday.atStartOfDay(); // 어제의 시작 시간 (00:00)
//        LocalDateTime endOfDay = yesterday.atTime(LocalTime.MAX); // 어제의 종료 시간 (23:59:59.999999999)
//        System.out.println(videoWatchHistoryRepository.findAllByViewDateBetween(today, yesterday));
//    }

}
