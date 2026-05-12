package dastrulash.uz.kun.uz.controller;

import dastrulash.uz.kun.uz.dto.SectionCreateDTO;
import dastrulash.uz.kun.uz.dto.SectionDTO;
import dastrulash.uz.kun.uz.dto.SectionInfoDTO;
import dastrulash.uz.kun.uz.enums.LanguageEnum;
import dastrulash.uz.kun.uz.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/section")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    // 1. Create (ADMIN)
    @PostMapping
    public ResponseEntity<SectionInfoDTO> create(@Valid @RequestBody SectionCreateDTO dto) {
        return ResponseEntity.ok(sectionService.create(dto));
    }

    // 2. Update (ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<SectionInfoDTO> update(@PathVariable Long id,
                                                 @Valid @RequestBody SectionCreateDTO dto) {
        return ResponseEntity.ok(sectionService.update(id, dto));
    }

    // 3. Delete (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(sectionService.delete(id));
    }

    // 4. Get List (ADMIN)
    @GetMapping
    public ResponseEntity<List<SectionInfoDTO>> getList() {
        return ResponseEntity.ok(sectionService.getList());
    }

    // 5. Get By Lang (PUBLIC)
    @GetMapping("/lang/{lang}")
    public ResponseEntity<List<SectionDTO>> getByLang(@PathVariable LanguageEnum lang) {
        return ResponseEntity.ok(sectionService.getByLang(lang));
    }
}