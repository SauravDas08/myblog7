package com.myblog7.service.impl;


import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto savePost(PostDto postDto) { //returntype is postDto.

//        Post post = new Post();// Dto class data can't go directly so convert Dto to entity obj.
//
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        Post post = mapToEntity(postDto);

        Post savedPost = postRepository.save(post);//save the data and assigned to a variable savedPost whose returntype is Post

        PostDto dto = mapToDto(savedPost);

        //again convert entity class data to dto object
//        PostDto dto = new PostDto();
//
//        dto.setId(savedPost.getId());
//        dto.setTitle(savedPost.getTitle());
//        dto.setDescription(savedPost.getDescription());
//        dto.setContent(savedPost.getContent());


          return dto;//now this data will go back to controller Postdto dto=postService.savePost(postDto);

    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostDto updatepost(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(//if id not found throw exception
                () -> new ResourceNotFound("Post not found with id:"+id)
        );
        //if id found save in (Post post= ) and continue further.
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postRepository.save(post);
        PostDto dto = mapToDto(updatePost);
        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post not found with this id " + id)
        );
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        //Sort sort = sortDir.equalsIgnoreCase(sort.Direction.ASC.name())?
        //      Sort.by(sortBy).ascending():
        //       Sort.by(sortBy).descending;
        Sort.Direction direction = "asc".equalsIgnoreCase(sortDir) ?
                Sort.Direction.ASC :
                Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        Page<Post> pagePosts = postRepository.findAll(pageable);

        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDto(postDtos);
        postResponse.setPageNo(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setLast(pagePosts.isLast());
        postResponse.setTotalPages(pagePosts.getTotalPages());

        return postResponse;
    }

    PostDto mapToDto(Post post){ //it is a method that convert entity to dto to make development easier.

        PostDto dto = modelMapper.map(post, PostDto.class);

  //      PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());

        return dto;
    }
    Post mapToEntity(PostDto postDto){

        Post post = modelMapper.map(postDto, Post.class);

//        Post post = new Post();// Dto class data can't go directly so convert Dto to entity obj.
//
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
       return post;
     }




}
