package com.myblog7.service;

import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;

public interface PostService {
    PostDto savePost(PostDto postDto);

    void deletePost(long id);
//
    PostDto updatepost(long id, PostDto postDto);

    PostDto getPostById(long id);

    PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
