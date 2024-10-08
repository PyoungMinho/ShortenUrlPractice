package com.example.test.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;


@Getter
@Entity
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class urls{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "urls_id")
    private Long id;

    private String destinationUrl;

    private String shortUrl;

    private Long totalClick;

    private LocalDateTime createdAt;

    private LocalDateTime lastedClicked;

    @OneToMany(mappedBy = "urls" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<accessLogs> accessLogsList = new ArrayList<>(); // 연관관계의 주인이 아닌 객체에서 mappedBy 속성을 사용해서 주인을 지정해준다.
    // TIP 외래 키(FK)가 있는 곳을 연간관계의 주인으로 정한다.
    public void updateShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void updateClick(Long clikcs){
        this.totalClick = clikcs;
    }
    public void updateLastedClick(LocalDateTime lastedClick){
        this.lastedClicked = lastedClick;
    }

    //urls <-> accesslogs 연관관계 편의 메서드
    public void addAccessLogs(accessLogs accessLogs){


        if (this.accessLogsList == null) {
            this.accessLogsList = new ArrayList<>();
        }

        if(!this.accessLogsList.contains(accessLogs)){
            this.accessLogsList.add(accessLogs);
            accessLogs.setAccessLog(this);
        }

    }
}


