package project.avatar.api.controller.posts;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import project.avatar.api.entity.Posts;
import project.avatar.api.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
    
    private final PostService postService;

    @Autowired
    PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<String> createPost(
        @RequestParam String title,
        @RequestParam String gender,
        @RequestParam String username,
        @RequestParam String style,
        //@RequestParam List<String> postIds,
        @RequestParam("imageFiles") List<MultipartFile> imageFiles){
        try{
            Posts posts = new Posts();
            posts.setTitle(title);
            posts.setGender(gender);
            posts.setUsername(username);
            posts.setStyle(style);
            //posts.setPostIds(null);
            for(MultipartFile imageFile : imageFiles){
                if(imageFile != null && !imageFile.isEmpty()){
                    String imagePath = saveImageFile(imageFile);
                    posts.addImagePath(imagePath);
                }
            }
            postService.createPost(posts);

            return new ResponseEntity<>("게시물 성공적으로 등록", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("게시물 등록 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Posts>> getPostsByUsername(@PathVariable String username){
        List<Posts> posts = postService.getPostsByUsername(username);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Autowired 
    private GridFsTemplate gridFsTemplate;

    /*@GetMapping("/images/{id}")
    public void serveImage(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if (file != null) {
            GridFsResource resource = null;
            try {
                resource = gridFsTemplate.getResource(file);
                IOUtils.copy(resource.getInputStream(), response.getOutputStream());
            } finally {
                if (resource != null && resource.getInputStream() != null) {
                    resource.getInputStream().close();
                }
            }
        } else {
            throw new FileNotFoundException("No file with id: " + id);
        }
    }*/

    @GetMapping("/images/{id}")
    public void serveImage(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));

        if (file != null) {
            GridFsResource resource = gridFsTemplate.getResource(file);
            InputStream inputStream = null;
            try {
                // 바이트 배열로 데이터를 모두 읽어오기
                inputStream = resource.getInputStream();
                byte[] imageBytes = IOUtils.toByteArray(inputStream);
                // 바이트 배열을 HTTP 응답에 쓰기
                IOUtils.write(imageBytes, response.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException("Error while reading file from GridFS", e);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } else {
            throw new FileNotFoundException("No file with id: " + id);
        }
    }



    //@Autowired GridFsOperations gridFsOperations;
    public String saveImageFile(MultipartFile file){
        try{
            ObjectId id = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType()
            );
            return id.toString();
        } catch(IOException e){
          throw new RuntimeException("Could not store the file " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) {
        try {
            // Find the post
            Posts post = postService.getPostById(id);
            if (post == null) {
                return new ResponseEntity<>("No post found with id: " + id, HttpStatus.NOT_FOUND);
            }

            // Delete images from GridFS
            for (String imagePath : post.getImagePaths()) {
                gridFsTemplate.delete(new Query(Criteria.where("_id").is(imagePath)));
            }

            // Delete the post
            postService.deletePost(post);

            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
        return new ResponseEntity<>("Failed to delete", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Posts>> getAllPosts() {
        List<Posts> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updatePost(@PathVariable("id") String id, @RequestBody Posts updatedPost) {
        try {
            // Find the post
            Posts existingPost = postService.getPostById(id);
            if (existingPost == null) {
                return new ResponseEntity<>("No post found with id: " + id, HttpStatus.NOT_FOUND);
            }

            // Update the post
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setGender(updatedPost.getGender());
            existingPost.setStyle(updatedPost.getStyle());

            // Save the updated post
            postService.save(existingPost);

        return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
        } catch (Exception e) {
        return new ResponseEntity<>("Failed to update", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
