package com.example.test.service;

import com.example.test.dto.GetAccessLogResponse;
import com.example.test.dto.GetUrlsResponse;
import com.example.test.model.accessLogs;
import com.example.test.model.urls;
import com.example.test.repository.AccessLogsRepository;
import com.example.test.repository.UrlJpaRepository;
import com.example.test.repository.UrlRepository;
import com.example.test.utils.ApiResponse;
import com.example.test.utils.Base62;
import com.example.test.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlService {

    private final UrlJpaRepository urlJpaRepository;
    private final UrlRepository urlRepository;
    private final AccessLogsRepository accessLogsRepository;
    private final Base62 base62;


    @Transactional
    public ApiResponse<String> checExistkUrl(String originalUrl, HttpServletRequest request) {
        urls existUrl = urlRepository.findByUrl(originalUrl);

        if (existUrl == null) { //내부 분기
            createShortUrl(originalUrl);
            addUrlsAccessLogs(originalUrl,request);
            return ApiResponse.create();
        } else {
            modifyUrl(originalUrl);
            addUrlsAccessLogs(originalUrl,request);
            return ApiResponse.ok("이미 등록된 url");
        }
    }


    @Transactional
    public void modifyUrl(String originalUrl) {
        urls targetUrls = urlRepository.findByUrl(originalUrl);
        if(targetUrls != null){
            Long clicks = targetUrls.getTotalClick();
            targetUrls.updateClick(++clicks);
        }
    }


    @Transactional
    public void createShortUrl(String originalUrl) {
        urls urlShorten = getUrls(originalUrl);

        urlJpaRepository.save(urlShorten);

        Long urlShortenId =  urlShorten.getId();
        String encodedUrl = base62.encode(urlShortenId);
        encodedUrl = "minho.pe.kr/"+encodedUrl;

        urlShorten.updateShortUrl(encodedUrl);

    }

    private static urls getUrls(String originalUrl) {
        urls urlShorten = urls.builder()
                .destinationUrl(originalUrl)
                .totalClick(1L)
                .createdAt(LocalDateTime.now())
                .build();
        return urlShorten;
    }

    public String getOriginalUrlByShortUrl(String shortenUrl) {
        long decodeUrl = base62.decode(shortenUrl);

        urls urlShorten = urlJpaRepository.findById(decodeUrl)
                .orElseThrow(
                        () -> new IllegalArgumentException("오류")
                );

        log.info("Original URL={}", urlShorten.getDestinationUrl());

        return urlShorten.getDestinationUrl();
    }

    public List<GetAccessLogResponse> findAccessLogByUrl(String originalUrl) {
        urls urls = urlRepository.findByUrl(originalUrl);
        List<accessLogs> accessLogsList = accessLogsRepository.findByUrlId(urls.getId());
        return accessLogsList.stream().map(GetAccessLogResponse::of).collect(Collectors.toList());
    }

    public List<GetAccessLogResponse> accessLogsWithPage(String originalUrl, Pageable pageable) {
        urls urls = urlRepository.findByUrl(originalUrl);
        Page<accessLogs> accessLogsPage = accessLogsRepository.findByUrlIdWithPage(urls.getId(), pageable);

        int pageNumber = accessLogsPage.getNumber();
        int pageSize = accessLogsPage.getSize();
        int totalPages = accessLogsPage.getTotalPages();

        return accessLogsPage.stream()
                .map(accessLog -> GetAccessLogResponse.of(accessLog, pageNumber, pageSize, totalPages))
                .collect(Collectors.toList());
    }

    public GetUrlsResponse findUrlsInfoByUrl(String originalUrl) {
        urls urls = urlRepository.findByUrl(originalUrl);
        urls.updateLastedClick(LocalDateTime.now());
        return GetUrlsResponse.of(urls);
    }

    @Transactional
    public void addUrlsAccessLogs(String originalUrl, HttpServletRequest request) {
        urls searchUrls = urlRepository.findByUrl(originalUrl);

        String ip = IpUtils.getClientIp(request);
        String comments = request.getHeader("user-Agent");

        accessLogs accessLogs = com.example.test.model.accessLogs.builder()
                .ip(ip)
                .userAgent(comments)
                .urls(searchUrls)
                .build();

        searchUrls.addAccessLogs(accessLogs);

        urlJpaRepository.save(searchUrls);

    }

}
