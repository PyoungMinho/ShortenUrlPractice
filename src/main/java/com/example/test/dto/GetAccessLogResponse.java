package com.example.test.dto;

import com.example.test.model.accessLogs;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetAccessLogResponse {


    private String ip;

    private String userAgent;

    private Long urlsId;

    private int pageNumber;
    private int pageSize;
    private int totalPages;

    @Builder
    public GetAccessLogResponse(String ip, String userAgent, Long urlsId, int pageNumber, int pageSize, int totalPages) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.urlsId = urlsId;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
    }

    public static GetAccessLogResponse of(accessLogs accessLogs) {
        return GetAccessLogResponse.builder()
                .ip(accessLogs.getIp())
                .userAgent(accessLogs.getUserAgent())
                .urlsId(accessLogs.getUrls().getId())
                .build();
    }

    public static GetAccessLogResponse of(accessLogs accessLogs, int pageNumber, int pageSize, int totalPages) {
        return GetAccessLogResponse.builder()
                .ip(accessLogs.getIp())
                .userAgent(accessLogs.getUserAgent())
                .urlsId(accessLogs.getUrls().getId())
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .build();
    }

}
