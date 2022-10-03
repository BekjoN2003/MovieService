package com.example.test.repository;

import com.example.test.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepo extends JpaRepository<Image, Integer>, JpaSpecificationExecutor<Image> {

    Optional<Image> findByToken(String token);
}
