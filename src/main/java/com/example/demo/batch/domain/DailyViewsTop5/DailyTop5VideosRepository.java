package com.example.demo.batch.domain.DailyViewsTop5;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyTop5VideosRepository extends JpaRepository<DailyTop5Videos, Integer> {
}
