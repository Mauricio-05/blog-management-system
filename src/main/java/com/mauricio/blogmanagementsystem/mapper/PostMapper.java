package com.mauricio.blogmanagementsystem.mapper;

import com.mauricio.blogmanagementsystem.dto.request.CreatePostRequest;
import com.mauricio.blogmanagementsystem.dto.request.UpdatePostRequest;
import com.mauricio.blogmanagementsystem.dto.response.PostResponse;
import com.mauricio.blogmanagementsystem.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Post toEntity(CreatePostRequest request);

    @Mapping(source = "author.email", target = "authorEmail")
    PostResponse toResponse(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    void updateEntityFromRequest(UpdatePostRequest request, @MappingTarget Post post);
}

