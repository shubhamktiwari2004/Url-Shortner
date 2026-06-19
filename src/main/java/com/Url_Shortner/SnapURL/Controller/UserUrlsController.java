package com.Url_Shortner.SnapURL.Controller;


import com.Url_Shortner.SnapURL.DTO.UserUrlResponse;
import com.Url_Shortner.SnapURL.Service.UrlService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserUrlsController {

    private final UrlService urlService;

    public UserUrlsController(UrlService urlService) {
        this.urlService = urlService;
    }


    @GetMapping("/my-urls")
    public List<UserUrlResponse> userUrls(Authentication authentication) {

        String email = authentication.getName();
        return urlService.userUrls(email);
    }

}
