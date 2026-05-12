package uz.topnews.controller;

import dastrulash.uz.kun.uz.dto.CategoryDTO;
import dastrulash.uz.kun.uz.dto.CategoryInfoDTO;
import dastrulash.uz.kun.uz.enums.LanguageEnum;
import dastrulash.uz.kun.uz.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.topnews.dto.category.CategoryCreateDTO;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 1. Create (ADMIN)
    @PostMapping
    public ResponseEntity<CategoryInfoDTO> create(@Valid @RequestBody CategoryCreateDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    // 2. Update (ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<CategoryInfoDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody CategoryCreateDTO dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    // 3. Delete (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    // 4. Get List (ADMIN)
    @GetMapping
    public ResponseEntity<List<CategoryInfoDTO>> getList() {
        return ResponseEntity.ok(categoryService.getList());
    }

    // 5. Get By Lang (PUBLIC)
    @GetMapping("/lang/{lang}")
    public ResponseEntity<List<CategoryDTO>> getByLang(@PathVariable LanguageEnum lang) {
        return ResponseEntity.ok(categoryService.getByLang(lang));
    }
}