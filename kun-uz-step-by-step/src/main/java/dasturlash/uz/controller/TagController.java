package dasturlash.uz.controller;

import dasturlash.uz.dto.tag.TagCreateDTO;
import dasturlash.uz.dto.tag.TagResponseDTO;
import dasturlash.uz.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/tag")
public class TagController {


    @Autowired
    private TagService tagService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity <TagResponseDTO> create(@RequestBody TagCreateDTO tagCreateDTO) {
        return ResponseEntity.ok(tagService.create(tagCreateDTO));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDTO> update(@PathVariable Integer id,
                                                 @RequestBody TagCreateDTO tagCreateDTO) {
        return ResponseEntity.ok(tagService.update(id, tagCreateDTO));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(tagService.delete(id));
    }


    @GetMapping("/list")
    public ResponseEntity<List<TagResponseDTO>> getList(){
        return ResponseEntity.ok(tagService.getAll());
    }



}
