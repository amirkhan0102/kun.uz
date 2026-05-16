package dastrulash.uz.kun.uz.controller;


import dastrulash.uz.kun.uz.dto.profile.*;
import dastrulash.uz.kun.uz.service.ProfileService;
import dastrulash.uz.kun.uz.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable("id") Integer id,
                                             @Valid @RequestBody ProfileUpdateDTO dto) { // ADMIN
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> byId(@PathVariable("id") Integer id) { // ADMIN
        return ResponseEntity.ok(profileService.getById(id));
    }

    @PutMapping("/detail")
    public ResponseEntity<ProfileDTO> updateDetail(
            @RequestHeader("ProfileId") Integer currentProfileId,
            @Valid @RequestBody ProfileUpdateDetailDTO dto) { // ANY
        return ResponseEntity.ok(profileService.updateDetail(currentProfileId, dto));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<ProfileDTO>> pagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(profileService.pagination(PageUtil.page(page), size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(profileService.delete(id));
    }

    @PutMapping("/password")
    public ResponseEntity<Boolean> password(@RequestHeader("ProfileId") Integer currentProfileId,
                                            @Valid @RequestBody ProfileUpdatePasswordDTO dto) {
        return ResponseEntity.ok(profileService.updatePassword(currentProfileId, dto));
    }

    // Buni to'liq keyinroq qilamiz. Attach mavzusida
    @PutMapping("/photo")
    public ResponseEntity<Boolean> update(@RequestHeader("ProfileId") Integer currentProfileId,
                                          @Valid @RequestBody ProfileUpdatePhotoDTO dto) {
        return ResponseEntity.ok(profileService.updatePhoto(currentProfileId, dto));
    }

}
