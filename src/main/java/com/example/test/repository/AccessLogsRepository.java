package com.example.test.repository;

import com.example.test.model.accessLogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface AccessLogsRepository {

    List<accessLogs> findByUrlId(Long urlId);

    Page<accessLogs> findByUrlIdWithPage(Long urlId, Pageable pageable);
}
