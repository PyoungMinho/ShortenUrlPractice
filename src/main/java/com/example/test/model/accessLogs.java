package com.example.test.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class accessLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="access_logs_id")
    private Long id;

    private String ip;

    private String userAgent; //user-agent comment 저장

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "urls_id")
    private urls urls;

    //연관관계 편의 메서드 -> accessLogs 쪽에서 한번에 저장
    public void setAccessLog(urls urls) {
        this.urls = urls;
    }
}
