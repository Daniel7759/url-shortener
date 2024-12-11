package com.example.urlshortener.controller;

import com.example.urlshortener.filter.JwtAuthenticationFilter;
import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.JwtService;
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
    private final JwtService jwtService;

    @Autowired
    public UrlController(UrlService urlService, JwtService jwtService) {
        this.urlService = urlService;
        this.jwtService = jwtService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<Url> shortenUrl(String originalUrl){
        Url shortUrl = urlService.createShortUrl(originalUrl);
        return new ResponseEntity<>(shortUrl, HttpStatus.CREATED);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> redirect(@PathVariable(value="shortUrl") String shortUrl){
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    @PostMapping("/custom")
    public ResponseEntity<Url> shortenUrlCustom(@RequestHeader("Authorization") String token, String originalUrl, String customUrl) {
        String username = jwtService.getUsernameFromToken(token);
        Url shortUrl = urlService.creatCustomShortUrl(originalUrl, customUrl, username);
        return new ResponseEntity<>(shortUrl, HttpStatus.CREATED);
    }
}
