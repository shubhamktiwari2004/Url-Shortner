package com.Url_Shortner.SnapURL.DTO;

public class ShortUrlResponse {

    private String shortCode;

    private String shortUrl;

    public ShortUrlResponse(
            String shortCode,
            String shortUrl
    ) {
        this.shortCode = shortCode;
        this.shortUrl = shortUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}
