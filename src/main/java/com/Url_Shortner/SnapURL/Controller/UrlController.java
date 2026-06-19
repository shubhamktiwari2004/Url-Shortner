package com.Url_Shortner.SnapURL.Controller;
import com.Url_Shortner.SnapURL.DTO.CreateShortUrlRequest;
import com.Url_Shortner.SnapURL.DTO.ShortUrlResponse;
import com.Url_Shortner.SnapURL.Service.UrlService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/urls")
    public ShortUrlResponse createShortUrl(
            @RequestBody @Valid CreateShortUrlRequest requestDto,
            Authentication authentication
    ) {

        return urlService.createShortUrl(requestDto , authentication);
    }




}
