package project.avatar.api.controller.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.avatar.api.entity.Closet;
import project.avatar.api.repo.ClothesRepository;

import java.util.Optional;

@RestController
@RequestMapping("/closet")
public class ClosetController {

    private final ClothesRepository clothesRepository;

    @Autowired
    public ClosetController(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<Closet> addToCloset(@RequestBody Closet closet) {
        Closet addedClothes = clothesRepository.save(closet);
        return new ResponseEntity<>(addedClothes, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeFromCloset(@PathVariable String id) {
        Optional<Closet> optionalClothes = clothesRepository.findById(id);
        if (optionalClothes.isPresent()) {
            Closet closet = optionalClothes.get();
            clothesRepository.delete(closet);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
