package sparta.batch.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.batch.batch.domain.adcalculate.AdCalculate;
import sparta.batch.batch.domain.adcalculate.AdCalculateRepository;
import sparta.batch.streaming.domain.video.AdWatchHistoryRepository;

@Service
public class BatchProcessingService {

    @Autowired
    private AdWatchHistoryRepository adWatchHistoryRepository;

    @Autowired
    private AdCalculateRepository adCalculateRepository;

    @Transactional
    public void calculateAdWatchCounts() {
        int[] videoIds = {1, 2, 3};

        for (int videoId : videoIds) {
            long count = adWatchHistoryRepository.countByVideoId(videoId);
            AdCalculate adCalculate = new AdCalculate();
            adCalculate.setVideoId(videoId);
            adCalculate.setAdCalculateAmount((int) count);
            adCalculateRepository.save(adCalculate);
        }
    }
}
