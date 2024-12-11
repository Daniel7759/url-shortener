package com.example.urlshortener.service;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.model.Usuario;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public UrlService(UrlRepository urlRepository, UsuarioService usuarioService) {
        this.urlRepository = urlRepository;
        this.usuarioService = usuarioService;
    }

    private String generateShortUrl() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 6;
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
        url.setCreationDate(LocalDate.now());

        return urlRepository.save(url);
    }

    public String getOriginalUrl(String shortUrl){
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new IllegalArgumentException("Invalid short URL"));
        url.setClicks(url.getClicks() + 1);
        urlRepository.save(url);
        return url.getOriginalUrl();
    }

    public Url creatCustomShortUrl(String originalUrl, String customShortUrl, String username){
        if (customShortUrl == null || customShortUrl.length() < 5 || customShortUrl.length() > 20 || !customShortUrl.matches("[a-zA-Z0-9]*")) {
            throw new IllegalArgumentException("Custom short URL must be between 5 and 20 characters and contain only alphanumeric characters");
        }
        if (urlRepository.findByShortUrl(customShortUrl).isPresent()) {
            throw new IllegalArgumentException("Custom short URL already exists");
        }
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(customShortUrl);
        url.setCreationDate(LocalDate.now());

        Usuario usuario = usuarioService.getUsuarioByEmail(username);
        url.setUser(usuario);
        return urlRepository.save(url);
    }
}
