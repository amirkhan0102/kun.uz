package dastrulash.uz.kun.uz.controller;


import dastrulash.uz.kun.uz.dto.RegionCreateDTO;
import dastrulash.uz.kun.uz.dto.RegionDTO;
import dastrulash.uz.kun.uz.dto.RegionInfoDTO;
import dastrulash.uz.kun.uz.enums.LanguageEnum;
import dastrulash.uz.kun.uz.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/region")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    // 1. Create (ADMIN)
    @PostMapping
    public ResponseEntity<RegionInfoDTO> create(@Valid @RequestBody RegionCreateDTO dto) {
        return ResponseEntity.ok(regionService.create(dto));
    }

    // 2. Update (ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<RegionInfoDTO> update(@PathVariable Long id,
                                                @Valid @RequestBody RegionCreateDTO dto) {
        return ResponseEntity.ok(regionService.update(id, dto));
    }

    // 3. Delete (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(regionService.delete(id));
    }

    // 4. Get List (ADMIN)
    @GetMapping
    public ResponseEntity<List<RegionInfoDTO>> getList() {
        return ResponseEntity.ok(regionService.getList());
    }

    // 5. Get By Lang (PUBLIC)
    @GetMapping("/lang/{lang}")
    public ResponseEntity<List<RegionDTO>> getByLang(@PathVariable LanguageEnum lang) {
        return ResponseEntity.ok(regionService.getByLang(lang));
    }
}