package com.example.test.repository;


import com.example.test.model.urls;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UrlRepositoryImpl implements UrlRepository{

    private final UrlJpaRepository urlJpaRepository;

    @Override
    public urls findByUrl(String originalUrl) {
        return urlJpaRepository.findByUrl(originalUrl);
    }
}
