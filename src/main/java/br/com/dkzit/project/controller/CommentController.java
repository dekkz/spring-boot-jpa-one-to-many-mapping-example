package br.com.dkzit.project.controller;

import br.com.dkzit.project.exception.ResourceNotFoundException;
import br.com.dkzit.project.model.Comment;
import br.com.dkzit.project.repository.CommentRepository;
import br.com.dkzit.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/{postId}/comments")
    public Page<Comment> getAllcommentsByPostId(@PathVariable(value = "postId") Long postId,
                                                Pageable pageable){
        return commentRepository.findByPostId(postId, pageable);
    }

    @PostMapping("/{postId}/comments")
    public Comment createComment(@PathVariable(value = "postId") Long postId,
                                 @Valid @RequestBody Comment comment){
        return postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public Comment updateComment(@PathVariable(value = "postId") Long postId,
                                 @PathVariable(value = "commentId") Long commentId,
                                 @Valid @RequestBody Comment commentRequest){

        if(!postRepository.existsById(postId)){
            throw new ResourceNotFoundException("PostId " + postId + " not found");
        }

        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(comment.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + " not found"));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "postId") Long postId,
                                           @PathVariable(value = "commentId") Long commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and postId " + postId));
    }
}
