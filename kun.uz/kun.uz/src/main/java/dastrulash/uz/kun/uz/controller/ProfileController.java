package dastrulash.uz.kun.uz.controller;

import dastrulash.uz.kun.uz.dto.ProfileCreateDTO;
import dastrulash.uz.kun.uz.dto.ProfileInfoDTO;
import dastrulash.uz.kun.uz.dto.ProfileUpdateDTO;
import dastrulash.uz.kun.uz.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // 1. Create (ADMIN)
    @PostMapping
    public ResponseEntity<ProfileInfoDTO> create(@Valid @RequestBody ProfileCreateDTO dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    // 2. Get By Id (ADMIN)
    @GetMapping("/{id}")
    public ResponseEntity<ProfileInfoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.getById(id));
    }

    // 3. Update (ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<ProfileInfoDTO> update(@PathVariable Long id,
                                                 @Valid @RequestBody ProfileUpdateDTO dto) {
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    // 4. Delete (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.delete(id));
    }

    // 5. Get List (ADMIN)
    @GetMapping
    public ResponseEntity<List<ProfileInfoDTO>> getList() {
        return ResponseEntity.ok(profileService.getList());
    }
}