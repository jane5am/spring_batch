package com.example.demo.batch.domain.dailyStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyVideoPlayTimeRepository extends JpaRepository<DailyVideoPlayTime, Integer> {
}
