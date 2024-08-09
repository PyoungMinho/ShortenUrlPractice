package com.example.test.repository;


import com.example.test.model.accessLogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessLogsJpaRepository extends JpaRepository<accessLogs,Long> {

    @Query("SELECT a FROM accessLogs a WHERE a.urls.id = :urlId")
    List<accessLogs> findByUrlId(@Param("urlId") Long urlId);



    @Query("SELECT a FROM accessLogs a where a.urls.id = :urlId")
    Page<accessLogs> findyByUrlIdWithPage(@Param("urlId") Long urlId, Pageable pageable);
}
