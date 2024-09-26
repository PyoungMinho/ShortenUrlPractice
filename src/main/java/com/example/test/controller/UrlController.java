package com.example.test.controller;

import com.example.test.dto.GetAccessLogResponse;
import com.example.test.dto.GetUrlsResponse;
import com.example.test.service.UrlService;
import com.example.test.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/urls")
@Slf4j
public class UrlController {

    private final UrlService urlService;

    @GetMapping("/test")
    public String tests() {
       return "postApi";
    }

    @PostMapping("/shorten")
    public ApiResponse<String> createShortUrl(@RequestParam("url") String destinationUrl, HttpServletRequest request){
        return urlService.checExistkUrl(destinationUrl,request);
    }

    @GetMapping("/shorten/url/info")
    public ApiResponse<GetUrlsResponse> showUrlInfo(@RequestParam("url") String originalUrl, HttpServletRequest request){
        return ApiResponse.ok(urlService.findUrlsInfoByUrl(originalUrl));
    }

    @GetMapping("/shorten/log/info")
    public ApiResponse<List<GetAccessLogResponse>> getAccessLog(@RequestParam("url") String url){
        return ApiResponse.ok(urlService.findAccessLogByUrl(url));
    }

    @GetMapping("/shorten/log/info/page")
    public ApiResponse<List<GetAccessLogResponse>> getAccessLogWithPage(@RequestParam("url") String url,
                                                              @PageableDefault(value = 10, sort = {"ip"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ApiResponse.ok(urlService.accessLogsWithPage(url, pageable));
    }

    @GetMapping("/shorten/{shortUrl}")
    public void redirect(@PathVariable("shortUrl") String shortUrl, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String destinationUrl = urlService.getOriginalUrlByShortUrl(shortUrl);
        String encodeURL = response.encodeRedirectURL(destinationUrl);
        response.sendRedirect(encodeURL);
        urlService.addUrlsAccessLogs(destinationUrl,request);
    }
}
