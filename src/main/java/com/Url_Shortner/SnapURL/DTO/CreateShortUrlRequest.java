package com.Url_Shortner.SnapURL.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateShortUrlRequest {

    @NotBlank(message = "URL cannot be empty")
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "URL must start with http:// or https://"
    )
    String originalUrl;

    public @NotBlank(message = "URL cannot be empty")
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "URL must start with http:// or https://"
    ) String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(@NotBlank(message = "URL cannot be empty")
                               @Pattern(
                                    regexp = "^(http|https)://.*$",
                                    message = "URL must start with http:// or https://"
    ) String originalUrl) {
        this.originalUrl = originalUrl;
    }
}