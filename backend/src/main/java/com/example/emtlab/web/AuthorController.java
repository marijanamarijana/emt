package com.example.emtlab.web;

import com.example.emtlab.dto.*;
import com.example.emtlab.model.projections.AuthorProjection;
import com.example.emtlab.model.views.AuthorsPerCountryView;
import com.example.emtlab.service.application.AuthorApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Authors", description = "Operations related to authors")
public class AuthorController {

    private final AuthorApplicationService authorService;

    public AuthorController(AuthorApplicationService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Get all authors", description = "Returns a list of all authors.")
    @GetMapping
    public List<AuthorDisplayDto> findAll() {
        return authorService.findAll();
    }

    @Operation(summary = "Get an author by ID", description = "Returns an author based on the provided ID.")
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDisplayDto> findById(@PathVariable Long id) {
        return authorService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new author", description = "Creates a new author with the provided details.")
    @PostMapping("/add")
    public ResponseEntity<AuthorDisplayDto> save(@RequestBody AuthorUpdateDto author) {
        return authorService.save(author)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an existing author", description = "Updates an author's information based on the provided ID.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<AuthorDisplayDto> update(@PathVariable Long id, @RequestBody AuthorUpdateDto author) {
        return authorService.update(id, author)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an author", description = "Deletes an author based on the provided ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (authorService.findById(id).isPresent()) {
            authorService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get number of authors by country", description = "Returns a list of the number of authors by each country")
    @GetMapping("/by-country")
    public ResponseEntity<List<AuthorsPerCountryView>> getAuthorsByCountry() {
        return ResponseEntity.ok(authorService.getAuthorsByCountry());
    }

    @Operation(summary = "Get names of authors", description = "Returns the name and surname of all the authors")
    @GetMapping("/names")
    public ResponseEntity<List<AuthorProjection>> getNames(){
        return ResponseEntity.ok(authorService.takeNameAndSurnameByProjection());
    }
}
