package com.mauricio.blogmanagementsystem.mapper;

import com.mauricio.blogmanagementsystem.dto.request.CreateCommentRequest;
import com.mauricio.blogmanagementsystem.dto.request.UpdateCommentRequest;
import com.mauricio.blogmanagementsystem.dto.response.CommentResponse;
import com.mauricio.blogmanagementsystem.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Comment toEntity(CreateCommentRequest request);

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "author.email", target = "authorEmail")
    CommentResponse toResponse(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    void updateEntityFromRequest(UpdateCommentRequest request, @MappingTarget Comment comment);
}

