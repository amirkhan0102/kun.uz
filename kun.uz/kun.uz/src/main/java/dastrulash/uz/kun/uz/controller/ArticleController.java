package dastrulash.uz.kun.uz.controller;

import dastrulash.uz.kun.uz.dto.article.ArticleCreateDTO;
import dastrulash.uz.kun.uz.dto.article.ArticleFullInfoDTO;
import dastrulash.uz.kun.uz.dto.article.ArticleShortInfoDTO;
import dastrulash.uz.kun.uz.entity.ProfileEntity;
import dastrulash.uz.kun.uz.enums.ArticleStatus;
import dastrulash.uz.kun.uz.enums.LanguageEnum;
import dastrulash.uz.kun.uz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 1. CREATE (Moderator)
    @PostMapping
    public ResponseEntity<ArticleShortInfoDTO> create(@Valid @RequestBody ArticleCreateDTO dto) {
        Integer moderatorId = getCurrentProfileId();
        return ResponseEntity.ok(articleService.create(dto, moderatorId));
    }

    // 2. UPDATE (Moderator)
    @PutMapping("/{id}")
    public ResponseEntity<ArticleShortInfoDTO> update(@PathVariable String id,
                                                      @Valid @RequestBody ArticleCreateDTO dto) {
        Integer moderatorId = getCurrentProfileId();
        return ResponseEntity.ok(articleService.update(id, dto, moderatorId));
    }

    // 3. DELETE (Moderator)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        Integer moderatorId = getCurrentProfileId();
        return ResponseEntity.ok(articleService.delete(id, moderatorId));
    }

    // 4. Change status (Publisher)
    @PutMapping("/{id}/status")
    public ResponseEntity<String> changeStatus(@PathVariable String id,
                                               @RequestParam ArticleStatus status) {
        Integer publisherId = getCurrentProfileId();
        return ResponseEntity.ok(articleService.changeStatus(id, status, publisherId));
    }

    // 5. Get Last N by sectionId
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<ArticleShortInfoDTO>> getBySectionId(
            @PathVariable Integer sectionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(articleService.getBySectionId(sectionId, page, size));
    }

    // 6. Get Last 12 except given idi
    @GetMapping("/last12")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast12(
            @RequestParam List<String> ids,
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(articleService.getLast12ExceptIds(ids, page));
    }

    // 7. Get Last N by categoryId
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ArticleShortInfoDTO>> getByCategoryId(
            @PathVariable Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(articleService.getByCategoryId(categoryId, page, size));
    }

    // 8. Get Last N by regionId
    @GetMapping("/region/{regionId}")
    public ResponseEntity<List<ArticleShortInfoDTO>> getByRegionId(
            @PathVariable Integer regionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(articleService.getByRegionId(regionId, page, size));
    }

    // 9. Get by id and lang
    @GetMapping("/{id}")
    public ResponseEntity<ArticleFullInfoDTO> getByIdAndLang(
            @PathVariable String id,
            @RequestHeader(name = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        return ResponseEntity.ok(articleService.getByIdAndLang(id, lang));
    }

    // Helper — current profile id olish
    private Integer getCurrentProfileId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ProfileEntity profile = (ProfileEntity) auth.getPrincipal();
        return profile.getId();
    }
}