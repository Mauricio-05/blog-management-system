package com.mauricio.blogmanagementsystem.repository;

import com.mauricio.blogmanagementsystem.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments c LEFT JOIN FETCH p.author WHERE p.deletedAt IS NULL AND (c.deletedAt IS NULL OR c IS NULL)")
    List<Post> findAllWithComments();

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments c LEFT JOIN FETCH p.author WHERE p.id = :id AND p.deletedAt IS NULL AND (c.deletedAt IS NULL OR c IS NULL)")
    Optional<Post> findByIdWithComments(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments c LEFT JOIN FETCH p.author WHERE p.author.id = :authorId AND p.deletedAt IS NULL AND (c.deletedAt IS NULL OR c IS NULL)")
    List<Post> findByAuthorIdWithComments(@Param("authorId") Long authorId);
}

