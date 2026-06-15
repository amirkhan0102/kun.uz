package dasturlash.uz.controller;

import dasturlash.uz.dto.article.*;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.service.ArticleService;
import dasturlash.uz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/moderator")
    public ResponseEntity<ArticleDTO> create(@RequestBody @Valid ArticleCreateDTO dto) {
        return ResponseEntity.ok(articleService.create(dto));
    }

    @PutMapping("/moderator/{articleId}")
    public ResponseEntity<ArticleDTO> update(@PathVariable("articleId") String articleId,
                                             @RequestBody @Valid ArticleCreateDTO dto) {
        return ResponseEntity.ok(articleService.update(articleId, dto));
    }

    @DeleteMapping("/moderator/{articleId}")
    public ResponseEntity<String> delete(@PathVariable("articleId") String articleId) {
        return ResponseEntity.ok(articleService.delete(articleId));
    }

    @PutMapping("/moderator/{articleId}/status")
    public ResponseEntity<String> changeStatus(@PathVariable("articleId") String articleId,
                                               @RequestBody @Valid ArticleChangeStatusDTO statusDTO) {
        return ResponseEntity.ok(articleService.changeStatus(articleId, statusDTO.getStatus()));
    }

    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<ArticleDTO>> changeStatus(@PathVariable("sectionId") Integer sectionId,
                                                         @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return ResponseEntity.ok(articleService.getBySectionId(sectionId, limit));
    }

    @PostMapping("/last-12")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast12ExceptIds(@RequestBody ArticleLast12ReqDTO dto){
        return ResponseEntity.ok(articleService.getLast12ExceptIds(dto.getIds()));
    }


}
