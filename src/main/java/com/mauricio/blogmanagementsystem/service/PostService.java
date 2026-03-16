package com.mauricio.blogmanagementsystem.service;

import com.mauricio.blogmanagementsystem.dto.request.CreatePostRequest;
import com.mauricio.blogmanagementsystem.dto.request.UpdatePostRequest;
import com.mauricio.blogmanagementsystem.dto.response.PostResponse;
import com.mauricio.blogmanagementsystem.entity.AppUser;
import com.mauricio.blogmanagementsystem.entity.Post;
import com.mauricio.blogmanagementsystem.enums.UserRole;
import com.mauricio.blogmanagementsystem.exception.ResourceNotFoundException;
import com.mauricio.blogmanagementsystem.mapper.PostMapper;
import com.mauricio.blogmanagementsystem.repository.AppUserRepository;
import com.mauricio.blogmanagementsystem.repository.PostRepository;
import com.mauricio.blogmanagementsystem.util.AuthorizationValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AppUserRepository appUserRepository;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public List<PostResponse> findAll() {
        return postRepository.findAllWithComments()
                .stream()
                .map(postMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse findById(Long id) {
        Post post = postRepository.findByIdWithComments(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado con id: " + id));
        return postMapper.toResponse(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findByAuthorId(Long authorId) {
        return postRepository.findByAuthorIdWithComments(authorId)
                .stream()
                .map(postMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse create(CreatePostRequest request, Long userId) {
        AppUser authorRef = appUserRepository.getReferenceById(userId);

        Post post = postMapper.toEntity(request);
        post.setAuthor(authorRef);

        Post savedPost = postRepository.save(post);
        log.info("Post creado por usuario con id: {}", userId);
        return postMapper.toResponse(savedPost);
    }

    @Transactional
    public PostResponse update(Long id, UpdatePostRequest request, Long userId) {
        Post post = findPostOrThrow(id);

        AuthorizationValidator.validateOwnership(post.getAuthor().getId(), userId, "posts");

        postMapper.updateEntityFromRequest(request, post);

        Post updatedPost = postRepository.save(post);
        log.info("Post actualizado por usuario con id: {}", userId);
        return postMapper.toResponse(updatedPost);
    }

    @Transactional
    public void delete(Long id, Long userId, UserRole role) {
        Post post = findPostOrThrow(id);

        AuthorizationValidator.validateOwnershipOrAdmin(post.getAuthor().getId(), userId, role, "post");

        post.setDeletedAt(LocalDateTime.now());
        postRepository.save(post);
        log.info("Post eliminado por usuario con id: {}", userId);
    }

    private Post findPostOrThrow(Long id) {
        return postRepository.findById(id)
                .filter(post -> post.getDeletedAt() == null)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado con id: " + id));
    }
}
