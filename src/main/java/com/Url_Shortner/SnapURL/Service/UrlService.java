package com.Url_Shortner.SnapURL.Service;

import com.Url_Shortner.SnapURL.Config.AppConfig;
import com.Url_Shortner.SnapURL.DTO.CreateShortUrlRequest;
import com.Url_Shortner.SnapURL.DTO.ShortUrlResponse;
import com.Url_Shortner.SnapURL.DTO.UserUrlResponse;
import com.Url_Shortner.SnapURL.Entity.Url;
import com.Url_Shortner.SnapURL.Repository.UrlRepository;
import com.Url_Shortner.SnapURL.Util.Base62Encoder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    private final AppConfig appConfig;


    public UrlService(UrlRepository urlRepository, AppConfig appConfig) {
        this.urlRepository = urlRepository;
        this.appConfig = appConfig;
    }

    public ShortUrlResponse createShortUrl(
            CreateShortUrlRequest requestDto,
            Authentication authentication
    ) {

        Url url = new Url();

        url.setOriginalUrl(requestDto.getOriginalUrl());

        Url savedUrl = urlRepository.saveAndFlush(url);

        String shortCode = Base62Encoder.encode(savedUrl.getId());

        //String shortCode =ShortCodeGenerator.generateCode(savedUrl.getId());

        savedUrl.setShortCode(shortCode);

        if(authentication != null) {
            savedUrl.setEmail(authentication.getName());
        }

        savedUrl.setCreatedAt(LocalDateTime.now());

        urlRepository.save(savedUrl);

        return new ShortUrlResponse(
                shortCode,
                appConfig.getBaseUrl() + "/" + shortCode
        );
    }

    public String getOriginalUrl(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(()->new RuntimeException("URL Not Found"));

        url.setClickCount(
                url.getClickCount() + 1
        );
        urlRepository.save(url);

        return url.getOriginalUrl();
    }

    public List<UserUrlResponse> userUrls(String email) {

        return urlRepository.findByUserEmail(email)
                .stream()
                .map(url -> new UserUrlResponse(
                        url.getOriginalUrl(),
                        appConfig.getBaseUrl() + "/" + url.getShortCode()
                ))
                .toList();
    }
}