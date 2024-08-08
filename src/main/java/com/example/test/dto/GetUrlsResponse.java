package com.example.test.dto;


import com.example.test.model.urls;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUrlsResponse {

    private String longUrl;

    private String shortUrl;

    private Long totalClick;

    private LocalDateTime createAt;

    private LocalDateTime LastedClicked;


    @Builder
    public GetUrlsResponse(String longUrl, String shortUrl, Long totalClick, LocalDateTime createAt, LocalDateTime LastedClicked) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.totalClick = totalClick;
        this.createAt = createAt;
        this.LastedClicked = LastedClicked;
    }


    public static GetUrlsResponse of(urls url){
        return GetUrlsResponse.builder()
                .longUrl(url.getDestinationUrl())
                .shortUrl(url.getShortUrl())
                .totalClick(url.getTotalClick())
                .createAt(url.getCreatedAt())
                .LastedClicked(url.getLastedClicked())
                .build();
    }
}
