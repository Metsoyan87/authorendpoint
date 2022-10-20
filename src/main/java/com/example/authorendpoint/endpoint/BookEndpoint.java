package com.example.authorendpoint.endpoint;

import com.example.authorendpoint.model.Author;
import com.example.authorendpoint.model.Gender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookEndpoint {

    List<Author> authors = new ArrayList<>(List.of(
            new Author(1, "Petros", "Petrosyan", 25, Gender.FEMALE),
            new Author(2, "Poxos", "Poxosyan", 25, Gender.FEMALE))

    );


    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        return authors;
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getBookById(@PathVariable("id") int id) {
        for (Author author : authors) {
            if (author.getId() == id) {
                return ResponseEntity.ok(author);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/authors")
    public ResponseEntity<?> createAuthor(@RequestBody Author author) {
        for (Author bookFromDB : authors) {
            if (bookFromDB.getName().equals(author.getName())
                    && bookFromDB.getSurname().equals(author.getSurname())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        authors.add(author);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/authors")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        if (author.getId() > 0) {
            for (Author authorFromDB : authors) {
                if (authorFromDB.getId() == author.getId()) {
                    authorFromDB.setGender(author.getGender());
                    authorFromDB.setName(author.getName());
                    authorFromDB.setAge(author.getAge());
                    authorFromDB.setSurname(author.getSurname());
                    return ResponseEntity.ok(authorFromDB);
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable("id") int id) {
        for (Author author : authors) {
            if (author.getId() == id) {
                authors.remove(author);
                ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
