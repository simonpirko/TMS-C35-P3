package by.tms.tmsc35p3.service;

import by.tms.tmsc35p3.dto.CreatePostDto;
import by.tms.tmsc35p3.dto.UpdatePostDto;
import by.tms.tmsc35p3.entity.Account;
import by.tms.tmsc35p3.entity.Post;
import by.tms.tmsc35p3.exception.PostNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import by.tms.tmsc35p3.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

//crud
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {


    private final PostRepository postRepository;
    public Post savePost(CreatePostDto postDto, Account account){

        Post post = new Post();
        post.setAuthor(account);
        post.setTitle(postDto.getTitle());
        post.setText(postDto.getContent());

        postRepository.save(post);

        return post;
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("Post not found with id " + id));
    }

    public boolean existsById(Long id) {
        return postRepository.existsById(id);
    }

    public List<Post> findAllByUserId(Long userId){
        return postRepository.findAllByAuthor_Id(userId);
    }

    public Post partialPostUpdate(Long id, UpdatePostDto postDto){
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("Can not update post with id " + id + " because it's not found!"));
        if(!postDto.getTitle().isBlank()){
            post.setTitle(postDto.getTitle());
        }

        if(!postDto.getText().isBlank()){
            post.setText(postDto.getText());
        }
        Post updatedPost = postRepository.save(post);

        if(!post.equals(updatedPost)){
            post.setUpdatedAt(LocalDateTime.now());
            return postRepository.save(post);
        }
        return post;
    }

    public void deletePost(Long id){
        postRepository.deleteById(id);
    }
}
