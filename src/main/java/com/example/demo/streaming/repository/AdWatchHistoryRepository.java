package com.example.demo.streaming.repository;

import com.example.demo.streaming.domain.video.AdWatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdWatchHistoryRepository extends JpaRepository<AdWatchHistory, Integer> {
    @Query("SELECT v.adId, COUNT(v) as viewCount FROM AdWatchHistory v WHERE v.viewDate BETWEEN :startDate AND :endDate GROUP BY v.adId ORDER BY viewCount DESC")
    List<Object[]> findTop5AdsByViewDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(a) FROM AdWatchHistory a WHERE a.videoId = ?1")
    int countByVideoId(int videoId);
}
