package com.myblog7.controller;

import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;
import com.myblog7.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }
    @PreAuthorize("hasRole('ADMIN')")

    @PostMapping //     2.PostDto         1.response entity show the response back in postman response section.

    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result){//here PostDto is returntype //requestBody take the json data from postman

                if (result.hasErrors()){
                    return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
        PostDto dto = postService.savePost(postDto);//after save postDto -> data goes to save() of service layer                                                    //and put it in postDto obj.

        return new ResponseEntity<>(dto, HttpStatus.CREATED) ;//201
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post is deleted",HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    //localhost://8080/api/post/1
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto){
        PostDto dto = postService.updatepost(id, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @GetMapping("/{id}")//http://localhost:8080/api/post/2
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


    @GetMapping//http://localhost:8080/api/post?pageNo=0&pageSize=5/sortDir=asc
    public PostResponse getPosts(
            @RequestParam(value = "pageNo",defaultValue ="0",required = false ) int pageNo,
            @RequestParam(value = "pageSize",defaultValue ="7",required = false ) int pageSize,
            @RequestParam(value = "sortBy",defaultValue ="id",required = false ) String sortBy,
            @RequestParam(value = "sortDir",defaultValue ="asc",required = false ) String sortDir
    ){
        //List<PostDto> postDtos =postService.getPosts(pageNo,pageSize,sortBy,sortDir);
        //return postDtos; it only shows only posts and not giving page nos,
        PostResponse postResponse = postService.getPosts(pageNo,pageSize,sortBy,sortDir);
        return postResponse;
    }
}
