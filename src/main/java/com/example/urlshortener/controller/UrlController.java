package com.example.urlshortener.controller;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<Url> shortenUrl(String originalUrl){
        Url shortUrl = urlService.createShortUrl(originalUrl);
        return new ResponseEntity<>(shortUrl, HttpStatus.CREATED);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> redirect(@PathVariable(value="shortUrl") String shortUrl){
        return urlService.getOriginalUrl(shortUrl)
                .map(url -> ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.getOriginalUrl())).build())
                .orElse(ResponseEntity.notFound().build());
    }
}
