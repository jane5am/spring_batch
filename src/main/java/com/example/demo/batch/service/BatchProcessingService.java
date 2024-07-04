package com.example.demo.batch.service;

import com.example.demo.batch.domain.adcalculate.AdCalculate;
import com.example.demo.batch.domain.adcalculate.AdCalculateRepository;
import com.example.demo.batch.domain.dailyStatus.DailyVideoViews;
import com.example.demo.batch.domain.dailyStatus.DailyVideoViewsRepository;
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
    private DailyVideoViewsRepository dailyVideoViewsRepository;

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

    // 스트리밍의 모든 동영상 조회수 가져오기
    @Transactional
    public void getVideoDailyViews() {

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        LocalDateTime startOfDay = yesterday.atStartOfDay(); // 어제의 시작 시간 (00:00)
        LocalDateTime endOfDay = yesterday.atTime(LocalTime.MAX); // 어제의 종료 시간 (23:59:59.999999999)

        logger.info("Start of Day : " + startOfDay);
        logger.info("End of Day : " + endOfDay);

        List<Object[]> videoViews = videoWatchHistoryRepository.countViewsByVideoIdAndDateRange(startOfDay, endOfDay);

        logger.info("Video Views : " + videoViews);

        videoViews.forEach(view -> {
            int videoId = (int) view[0];
            int viewCount = ((Number) view[1]).intValue(); // Casting to int

            logger.info("Video ID : " + videoId);
            logger.info("View Count : " + viewCount);

            DailyVideoViews dailyVideoViews = new DailyVideoViews();
            dailyVideoViews.setVideoId(videoId);
            dailyVideoViews.setViews(viewCount);
            dailyVideoViews.setDate(startOfDay);

            try {
                dailyVideoViewsRepository.save(dailyVideoViews);
            } catch (Exception e) {
                logger.error("Error saving dailyVideoViews for videoId: " + videoId, e);
                throw e;
            }
        });
    }

}
