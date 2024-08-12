package com.example.test.repository;

import com.example.test.model.urls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlJpaRepository extends JpaRepository<urls,Long> {

    @Query("SELECT u FROM urls u WHERE u.destinationUrl = :originUrl")
    urls findByUrl(@Param("originUrl") String url);

}
