package com.mauricio.blogmanagementsystem.controller;

import com.mauricio.blogmanagementsystem.dto.request.CreatePostRequest;
import com.mauricio.blogmanagementsystem.dto.request.UpdatePostRequest;
import com.mauricio.blogmanagementsystem.dto.response.ApiResponse;
import com.mauricio.blogmanagementsystem.dto.response.PostResponse;
import com.mauricio.blogmanagementsystem.security.CustomUserDetails;
import com.mauricio.blogmanagementsystem.service.PostService;
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
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<PostResponse>>> findAll() {
        List<PostResponse> posts = postService.findAll();
        return ResponseEntity.ok(ApiResponseBuilder.success(HttpStatus.OK.value(), "Posts obtenidos exitosamente", posts));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PostResponse>> findById(@PathVariable Long id) {
        PostResponse post = postService.findById(id);
        return ResponseEntity.ok(ApiResponseBuilder.success(HttpStatus.OK.value(), "Post obtenido exitosamente", post));
    }

    @GetMapping("/author/{authorId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<PostResponse>>> findByAuthorId(@PathVariable Long authorId) {
        List<PostResponse> posts = postService.findByAuthorId(authorId);
        return ResponseEntity.ok(ApiResponseBuilder.success(HttpStatus.OK.value(), "Posts del autor obtenidos exitosamente", posts));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PostResponse>> create(@Valid @RequestBody CreatePostRequest request, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        PostResponse post = postService.create(request, userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseBuilder.success(HttpStatus.CREATED.value(), "Post creado exitosamente", post));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PostResponse>> update(@PathVariable Long id, @Valid @RequestBody UpdatePostRequest request, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        PostResponse post = postService.update(id, request, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponseBuilder.success(HttpStatus.OK.value(), "Post actualizado exitosamente", post));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        postService.delete(id, userDetails.getUserId(), userDetails.getRole());
        return ResponseEntity.ok(ApiResponseBuilder.success(HttpStatus.OK.value(), "Post eliminado exitosamente"));
    }
}


