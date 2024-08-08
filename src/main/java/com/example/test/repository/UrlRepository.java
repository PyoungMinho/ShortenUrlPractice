package com.example.test.repository;

import com.example.test.model.urls;

public interface UrlRepository {

    urls findByUrl(String originalUrl);
}
