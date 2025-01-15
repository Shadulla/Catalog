package com.shadulla.catalog.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "googleClient",
        url = "https://www.google.com")
public interface GoogleFeignClient {

    @GetMapping("/")
    String getHomePage();

    @Component
    class GoogleFeignClientFallback implements GoogleFeignClient {

        @Override
        public String getHomePage() {
            return "Google service is currently unavailable. Please try again later.";
        }
    }
}
