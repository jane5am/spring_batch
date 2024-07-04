package com.example.demo.batch.domain.DailyViewsTop5;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyTop5Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int DailyTop5AdsId;

    @Column(nullable = false)
    private int adId;

    @Column(nullable = false)
    private int viewsRank; //등수

    @Column(nullable = false)
    private int views; //조회수

    @Column(nullable = false)
    private LocalDateTime date; // 날짜
}