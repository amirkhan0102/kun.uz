package dastrulash.uz.kun.uz.controller;


import dastrulash.uz.kun.uz.dto.SectionDTO;
import dastrulash.uz.kun.uz.enums.LanguageEnum;
import dastrulash.uz.kun.uz.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/section")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @PostMapping("")
    public ResponseEntity<SectionDTO> create(@Valid @RequestBody SectionDTO dto) {
        return ResponseEntity.ok(sectionService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectionDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody SectionDTO newDto) {
        return ResponseEntity.ok(sectionService.update(id, newDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(sectionService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<List<SectionDTO>> all() {
        return ResponseEntity.ok(sectionService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<SectionDTO>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") LanguageEnum language) {
        return ResponseEntity.ok(sectionService.getAllByLang(language));
    }
}
