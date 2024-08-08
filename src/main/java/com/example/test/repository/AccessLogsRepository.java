package com.example.test.repository;

import com.example.test.model.accessLogs;

import java.util.List;

public interface AccessLogsRepository {

    List<accessLogs> findByUrlId(Long urlId);
}
