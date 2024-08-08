package com.example.test.dto;

import com.example.test.model.accessLogs;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.AccessLog;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetAccessLogResponse {


    private String ip;

    private String userAgent;

    private Long urlsId;

    @Builder
    public GetAccessLogResponse(String ip, String userAgent, Long urlsId) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.urlsId = urlsId;
    }

    public static GetAccessLogResponse of(accessLogs accessLogs) {
        return GetAccessLogResponse.builder()
                .ip(accessLogs.getIp())
                .userAgent(accessLogs.getUserAgent())
                .urlsId(accessLogs.getUrls().getId()).build();
    }


}
