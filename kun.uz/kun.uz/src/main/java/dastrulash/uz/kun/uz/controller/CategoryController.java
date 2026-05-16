package dastrulash.uz.kun.uz.controller;



import dastrulash.uz.kun.uz.dto.CategoryDTO;
import dastrulash.uz.kun.uz.enums.LanguageEnum;
import dastrulash.uz.kun.uz.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable("id") Integer id,
                                              @Valid @RequestBody CategoryDTO newDto) {
        return ResponseEntity.ok(categoryService.update(id, newDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> all() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    // /api/v1/category/lang?language=uz
    @GetMapping("/lang")
    public ResponseEntity<List<CategoryDTO>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") LanguageEnum language) {
        return ResponseEntity.ok(categoryService.getAllByLang(language));
    }
}
