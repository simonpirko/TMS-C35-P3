package by.tms.tmsc35p3.controller;

import by.tms.tmsc35p3.dto.CreatePostDto;
import by.tms.tmsc35p3.dto.UpdatePostDto;
import by.tms.tmsc35p3.entity.Post;
import by.tms.tmsc35p3.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import by.tms.tmsc35p3.repository.UserRepository;
import by.tms.tmsc35p3.service.PostService;

import java.util.List;

@RestController("/api/v1/posts")
@NoArgsConstructor
@AllArgsConstructor
public class PostController {

    PostService postService;
    UserRepository userRepository; //удалить, когда сделают UserService

    @PostMapping()
    public ResponseEntity<Post> createPost(CreatePostDto postDto){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postService.savePost(postDto, currentUser);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
        if(postService.existsById(id)){
            Post post = postService.findById(id);
        return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Post>> getAllPosts(@PathVariable Long userId){
        if(userRepository.existsById(userId)){
            List<Post> posts = postService.findAllByUserId(userId);
            return ResponseEntity.ok(posts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, UpdatePostDto updateDto){
        if(postService.existsById(id)){
            Post post = postService.partialPostUpdate(id, updateDto);
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        if(postService.existsById(id)){
            postService.deletePost(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
