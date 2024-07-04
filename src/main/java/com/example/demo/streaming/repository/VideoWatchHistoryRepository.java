package com.example.demo.streaming.repository;

import com.example.demo.streaming.domain.video.VideoWatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VideoWatchHistoryRepository extends JpaRepository<VideoWatchHistory, Integer> {
    @Query("SELECT v.videoId, COUNT(v) as viewCount FROM VideoWatchHistory v WHERE v.viewDate BETWEEN :startDate AND :endDate GROUP BY v.videoId ORDER BY v.videoId ASC")
    List<Object[]> countViewsByVideoIdAndDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    List<VideoWatchHistory> findAll();
}
