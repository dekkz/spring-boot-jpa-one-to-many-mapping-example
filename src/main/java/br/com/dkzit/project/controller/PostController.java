package br.com.dkzit.project.controller;

import br.com.dkzit.project.exception.ResourceNotFoundException;
import br.com.dkzit.project.model.Post;
import br.com.dkzit.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public Page<Post> getAllPosts(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    @PostMapping
    public Post createPost(@Valid @RequestBody Post post){
        return postRepository.save(post);
    }

    @PutMapping
    public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest){
        return postRepository.findById(postId).map(post -> {
            post.setTitle(postRequest.getTitle());
            post.setDescription(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return postRepository.findById(postId).map(post ->{
            postRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }
}
