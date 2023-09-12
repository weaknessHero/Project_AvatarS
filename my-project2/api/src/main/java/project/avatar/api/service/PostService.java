package project.avatar.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.avatar.api.entity.Posts;
import project.avatar.api.repo.PostRepository;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public void createPost(Posts posts){
        postRepository.save(posts);
    }

    public List<Posts> getPostsByUsername(String username){
        return postRepository.findByUsername(username);
    }

    public Posts getPostById(String id){
        Optional<Posts> post = postRepository.findById(id);
        return post.orElse(null);
    }
    
    public void deletePost(Posts posts){
        postRepository.delete(posts);
    }

    public List<Posts> getAllPosts(){
        return postRepository.findAll();
    }

    public void save(Posts post){
        postRepository.save(post);
    }
}
