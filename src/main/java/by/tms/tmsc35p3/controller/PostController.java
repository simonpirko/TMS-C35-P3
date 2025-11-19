package by.tms.tmsc35p3.controller;

import by.tms.tmsc35p3.dto.CreatePostDto;
import by.tms.tmsc35p3.dto.UpdatePostDto;
import by.tms.tmsc35p3.entity.Post;
import by.tms.tmsc35p3.entity.User;
import by.tms.tmsc35p3.exception.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import by.tms.tmsc35p3.repository.UserRepository;
import by.tms.tmsc35p3.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository; // удалить, когда сделают UserService

    @PostMapping()
    public ResponseEntity<?> createPost(@RequestBody CreatePostDto postDto){
        try {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Post post = postService.savePost(postDto, currentUser);
            return ResponseEntity.ok(post);
        } catch (ClassCastException e) {
            return GlobalExceptionHandler.createErrorResponse("UNAUTHORIZED", "Пользователь не авторизован");
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        Post post = postService.findById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllPosts(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            return GlobalExceptionHandler.createErrorResponse("NOT_FOUND", "Пользователь с id " + id + " не найден");
        }
        List<Post> posts = postService.findAllByUserId(id);
        if(posts.isEmpty()){
            return GlobalExceptionHandler.createErrorResponse("NOT_FOUND", "Не найдены посты у пользователя с id " + id);
        }
        return ResponseEntity.ok(posts);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody UpdatePostDto updateDto){
        Post post = postService.partialPostUpdate(id, updateDto);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        if(!postService.existsById(id)){
            return GlobalExceptionHandler.createErrorResponse("NOT_FOUND", "Пост с id " + id + " не найден");
        }
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
