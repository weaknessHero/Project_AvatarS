package project.avatar.api.controller.posts;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    @GetMapping("/images/{id}")
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
}
