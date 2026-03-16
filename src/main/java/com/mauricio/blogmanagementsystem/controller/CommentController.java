package com.mauricio.blogmanagementsystem.controller;

import com.mauricio.blogmanagementsystem.dto.request.CreateCommentRequest;
import com.mauricio.blogmanagementsystem.dto.request.UpdateCommentRequest;
import com.mauricio.blogmanagementsystem.dto.response.ApiResponse;
import com.mauricio.blogmanagementsystem.dto.response.CommentResponse;
import com.mauricio.blogmanagementsystem.security.CustomUserDetails;
import com.mauricio.blogmanagementsystem.service.CommentService;
import com.mauricio.blogmanagementsystem.util.ApiResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/post/{postId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> findByPostId(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.findByPostId(postId);
        return ResponseEntity.ok(ApiResponseBuilder.success(HttpStatus.OK.value(), "Comentarios obtenidos exitosamente", comments));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<CommentResponse>> findById(@PathVariable Long id) {
        CommentResponse comment = commentService.findById(id);
        return ResponseEntity.ok(ApiResponseBuilder.success(HttpStatus.OK.value(), "Comentario obtenido exitosamente", comment));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<CommentResponse>> create(@Valid @RequestBody CreateCommentRequest request, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        CommentResponse comment = commentService.create(request, userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseBuilder.success(HttpStatus.CREATED.value(), "Comentario creado exitosamente", comment));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<CommentResponse>> update(@PathVariable Long id, @Valid @RequestBody UpdateCommentRequest request, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        CommentResponse comment = commentService.update(id, request, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponseBuilder.success(HttpStatus.OK.value(), "Comentario actualizado exitosamente", comment));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        commentService.delete(id, userDetails.getUserId(), userDetails.getRole());
        return ResponseEntity.ok(ApiResponseBuilder.success(HttpStatus.OK.value(), "Comentario eliminado exitosamente"));
    }
}

