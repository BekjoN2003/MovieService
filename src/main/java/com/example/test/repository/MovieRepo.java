package com.example.test.repository;
import com.example.test.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MovieRepo extends JpaRepository<Movie, Integer>, JpaSpecificationExecutor<Movie> {
    Optional<Movie> findByIdAndDeletedAtIsNull(Integer id);

    @Query(value = "SELECT * FROM movies where deleted_at is null", nativeQuery = true)
    Page<Movie> pageMovie(Pageable pageable);
    @Query ("SELECT count(id) FROM Movie where deletedAt is null")
    Long movieCount();

}
