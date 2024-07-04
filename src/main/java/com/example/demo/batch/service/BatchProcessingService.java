package com.example.demo.batch.service;

import com.example.demo.batch.domain.DailyViewsTop5.DailyTop5Videos;
import com.example.demo.batch.domain.DailyViewsTop5.DailyTop5VideosRepository;
import com.example.demo.batch.domain.adcalculate.AdCalculate;
import com.example.demo.batch.domain.adcalculate.AdCalculateRepository;
import com.example.demo.streaming.repository.AdWatchHistoryRepository;
import com.example.demo.streaming.repository.VideoWatchHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.mysql.cj.conf.PropertyKey.logger;

@Service
public class BatchProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(BatchProcessingService.class);

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

        List<Object[]> top5Videos = videoWatchHistoryRepository.findTop5VideosByViewDateBetween(startOfDay, endOfDay);

        for (int i = 0; i < Math.min(5, top5Videos.size()); i++) {
            Object[] video = top5Videos.get(i);
            int videoId = (int) video[0];
            long viewCount = (long) video[1]; // PQL 쿼리에서 COUNT 함수는 기본적으로 Long 타입의 결과를 반환

            System.out.println("Video ID : " + videoId);
            System.out.println("View Count : " + viewCount);

            DailyTop5Videos dailyVideoViewsTop5 = new DailyTop5Videos();
            dailyVideoViewsTop5.setVideoId(videoId);
            dailyVideoViewsTop5.setViews((int) viewCount);
            dailyVideoViewsTop5.setViewsRank(i + 1);
            dailyVideoViewsTop5.setDate(yesterday.atStartOfDay());

            try {
                dailyTop5VideosRepository.save(dailyVideoViewsTop5);
            } catch (Exception e) {
                logger.error("Error saving dailyVideoViewsTop5 for videoId: " + videoId, e);
            }
        }
    }

}
