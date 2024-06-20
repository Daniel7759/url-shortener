package com.example.urlshortener.service;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private String generateShortUrl() {
        Integer leftLimit = 48;
        Integer rightLimit = 122;
        Integer targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit +1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public Url createShortUrl(String originalUrl){
        Optional<Url> existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if(existingUrl.isPresent()){
            return existingUrl.get();
        }

        String shortUrl;
        do{
            shortUrl = generateShortUrl();
        }while (urlRepository.findByShortUrl(shortUrl).isPresent());

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);

        return urlRepository.save(url);
    }

    public Optional<Url> getOriginalUrl(String shortUrl){
        return urlRepository.findByShortUrl(shortUrl);
    }
}
