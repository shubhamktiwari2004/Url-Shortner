package com.Url_Shortner.SnapURL.Repository;

import com.Url_Shortner.SnapURL.Entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortCode(String shortCode);


    @Query(value = "select * from urls where email = ?1", nativeQuery = true)
    List<Url> findByUserEmail(String email);
}
