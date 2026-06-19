package com.Url_Shortner.SnapURL.Controller;


import com.Url_Shortner.SnapURL.Service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectUrlController {

    private final UrlService urlService;

    public RedirectUrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String shortCode){

        String originalUrl = urlService.getOriginalUrl(shortCode);

        System.out.println("Redirecting to: " + originalUrl);
        System.out.println("ShortCode: " + shortCode);


        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location" , originalUrl)
                .build();
    }

}
