package com.example.test.utils;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtils {

    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getLocalAddr();
        if (ipAddress != null && !ipAddress.isEmpty()) {
            // X-Forwarded-For 헤더에는 여러 IP가 쉼표로 구분될 수 있음
            String[] ipArray = ipAddress.split(",");
            ipAddress = ipArray[0].trim(); // 첫 번째 IP 주소가 클라이언트의 실제 IP일 가능성이 높음
        } else {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
