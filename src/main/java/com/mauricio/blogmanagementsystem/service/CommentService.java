package com.mauricio.blogmanagementsystem.service;

import com.mauricio.blogmanagementsystem.dto.request.CreateCommentRequest;
import com.mauricio.blogmanagementsystem.dto.request.UpdateCommentRequest;
import com.mauricio.blogmanagementsystem.dto.response.CommentResponse;
import com.mauricio.blogmanagementsystem.entity.AppUser;
import com.mauricio.blogmanagementsystem.entity.Comment;
import com.mauricio.blogmanagementsystem.entity.Post;
import com.mauricio.blogmanagementsystem.enums.UserRole;
import com.mauricio.blogmanagementsystem.exception.ResourceNotFoundException;
import com.mauricio.blogmanagementsystem.mapper.CommentMapper;
import com.mauricio.blogmanagementsystem.repository.AppUserRepository;
import com.mauricio.blogmanagementsystem.repository.CommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AppUserRepository appUserRepository;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public List<CommentResponse> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .filter(comment -> comment.getDeletedAt() == null)
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentResponse findById(Long id) {
        Comment comment = findCommentOrThrow(id);
        return commentMapper.toResponse(comment);
    }

    @Transactional
    public CommentResponse create(CreateCommentRequest request, Long userId) {
        Post post = postRepository.findById(request.getPostId())
                .filter(p -> p.getDeletedAt() == null)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado con id: " + request.getPostId()));

        AppUser authorRef = appUserRepository.getReferenceById(userId);

        Comment comment = commentMapper.toEntity(request);
        comment.setPost(post);
        comment.setAuthor(authorRef);

        Comment savedComment = commentRepository.save(comment);
        log.info("Comentario creado por usuario con id: {}", userId);
        return commentMapper.toResponse(savedComment);
    }

    @Transactional
    public CommentResponse update(Long id, UpdateCommentRequest request, Long userId) {
        Comment comment = findCommentOrThrow(id);

        AuthorizationValidator.validateOwnership(comment.getAuthor().getId(), userId, "comentarios");

        commentMapper.updateEntityFromRequest(request, comment);

        Comment updatedComment = commentRepository.save(comment);
        log.info("Comentario actualizado por usuario con id: {}", userId);
        return commentMapper.toResponse(updatedComment);
    }

    @Transactional
    public void delete(Long id, Long userId, UserRole role) {
        Comment comment = findCommentOrThrow(id);

        AuthorizationValidator.validateOwnershipOrAdmin(comment.getAuthor().getId(), userId, role, "comentario");

        comment.setDeletedAt(LocalDateTime.now());
        commentRepository.save(comment);
        log.info("Comentario eliminado por usuario con id: {}", userId);
    }

    private Comment findCommentOrThrow(Long id) {
        return commentRepository.findById(id)
                .filter(comment -> comment.getDeletedAt() == null)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado con id: " + id));
    }
}

