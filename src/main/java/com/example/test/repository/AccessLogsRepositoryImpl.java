package com.example.test.repository;

import com.example.test.model.accessLogs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccessLogsRepositoryImpl implements AccessLogsRepository {

    private final AccessLogsJpaRepository accessLogsJpaRepository;

    @Override
    public List<accessLogs> findByUrlId(Long urlId) {
        return accessLogsJpaRepository.findByUrlId(urlId);
    }
}
