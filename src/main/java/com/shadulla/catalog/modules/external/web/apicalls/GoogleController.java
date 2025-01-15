package com.shadulla.catalog.modules.external.web.apicalls;

import com.shadulla.catalog.utils.GoogleFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GoogleController {

    private final GoogleFeignClient googleClient;

    @Autowired
    public GoogleController(GoogleFeignClient googleClient) {
        this.googleClient = googleClient;
    }

    @GetMapping("/google-home")
    public String getGoogleHome() {
        return googleClient.getHomePage();
    }

}
