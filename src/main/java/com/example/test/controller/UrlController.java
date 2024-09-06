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
        //TODO 분기처리를 컨트롤러단에서 제거하기 done!
//        boolean urlExist = urlService.checExistkUrl(destinationUrl,request);
//
//        if(urlExist){
//            urlService.createShortUrl(destinationUrl);
//            urlService.addUrlsAccessLogs(destinationUrl,request);
//            return ApiResponse.create();
//        }else{
//            urlService.modifyUrl(destinationUrl);
//            urlService.addUrlsAccessLogs(destinationUrl,request);
//            return ApiResponse.ok("이미 등록된 url");
//        }
        ApiResponse<String> response = urlService.checExistkUrl(destinationUrl,request); // 쉬운 코드파악을 위해 인라인 X
        return response ;
    }

    @GetMapping("/shorten/url/info")
    public ApiResponse<GetUrlsResponse> showUrlInfo(@RequestParam("url") String originalUrl, HttpServletRequest request){
        GetUrlsResponse response = urlService.findUrlsInfoByUrl(originalUrl);
        return ApiResponse.ok(response);
    }

    @GetMapping("/shorten/log/info")
    public ApiResponse<List<GetAccessLogResponse>> getAccessLog(@RequestParam("url") String url){
        List<GetAccessLogResponse> accessLogResponseList = urlService.findAccessLogByUrl(url);
        return ApiResponse.ok(accessLogResponseList);
    }

    @GetMapping("/shorten/log/info/page")
    public ApiResponse<List<GetAccessLogResponse>> getAccessLogWithPage(@RequestParam("url") String url,
                                                              @PageableDefault(value = 10, sort = {"ip"}, direction = Sort.Direction.ASC) Pageable pageable) {
        List<GetAccessLogResponse> accessLogResponseList = urlService.accessLogsWithPage(url, pageable);
        return ApiResponse.ok(accessLogResponseList);
    }

    @GetMapping("/shorten/{shortUrl}")
    public void redirect(@PathVariable("shortUrl") String shortUrl, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String destinationUrl = urlService.getOriginalUrlByShortUrl(shortUrl);
        String encodeURL = response.encodeRedirectURL(destinationUrl);
        response.sendRedirect(encodeURL);
        urlService.addUrlsAccessLogs(destinationUrl,request);
    }
}
