package sparta.batch.streaming.domain.video;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdWatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int adWatchHistoryId;

    @Column(nullable = false)
    private int videoId;

    @Column(nullable = false)
    private int adId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime viewDate; // 시청시각 (기본값 현재시각)

    @Column(nullable = false)
    private String sourceIP; // 시청한 사용자 IP주소
    @jakarta.persistence.Id
    private Long id;

    @PrePersist
    protected void onCreate() {
        this.viewDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

}