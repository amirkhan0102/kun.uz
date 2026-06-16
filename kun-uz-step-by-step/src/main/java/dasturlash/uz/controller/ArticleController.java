package dasturlash.uz.controller;

import dasturlash.uz.dto.article.*;
import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.service.ArticleService;
import dasturlash.uz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<Page<ArticleShortInfoDTO>> getByCategoryId(@PathVariable("categoryId") Integer categoryId,
                                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(articleService.getByCategoryId(categoryId, page, size));
    }

    // 8 GET BY REGION ID
    @GetMapping("/by-region/{regionId}")
    public ResponseEntity<Page<ArticleShortInfoDTO>> getByRegionID(
            @PathVariable("regionId") Integer regionId,
            @RequestParam(value = "page", defaultValue ="0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(articleService.getByRegionId(regionId, page, size));
    }

    // 9 Get by id and lang

    @GetMapping("/{id}/{lang}")
    public ResponseEntity<ArticleFullInfoDTO> getByIdAndLang(
            @PathVariable("id") String id,
            @PathVariable("lang") String lang){
        return ResponseEntity.ok(articleService.getByIdAndLang(id, lang));
    }




    // 11. LAST 4 BY SECTION ID
    @GetMapping("/last-4/{sectionId}/{articleId}")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast4BySectionId(
            @PathVariable("sectionId") Integer sectionId,
            @PathVariable("articleId") String articleId) {
        return ResponseEntity.ok(articleService.getLast4BySectionId(sectionId, articleId));
    }


    // 12 MOST READ TOP 4
    @GetMapping("/most-read")
    public ResponseEntity<List<ArticleShortInfoDTO>> getMostRead(){
        return ResponseEntity.ok(articleService.getMosrtRead());
    }


    // 13 increase viewcount
    @PutMapping("/view/{id}")
    public ResponseEntity<Void> increaseViewCount(@PathVariable("id") String id) {
        articleService.increaseViewCount(id);
        return ResponseEntity.ok().build();
    }


    // 14 increase shared count
    @PutMapping("/share/{id}")
    public ResponseEntity<Void> increaseSharedCount(@PathVariable("id") String id){
        articleService.increaseSharedCount(id);
        return ResponseEntity.ok().build();
    }


    // 15 filter By Publisher
    // 15. FILTER BY PUBLISHER
    @PreAuthorize("hasRole('PUBLISHER')")
    @PostMapping("/filter/publisher")
    public ResponseEntity<Page<ArticleShortInfoDTO>> filterByPublisher(
            @RequestBody ArticleFilterDTO dto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Integer publisherId = getCurrentProfileId();
        return ResponseEntity.ok(articleService.filterByPublisher(dto, publisherId, page, size));
    }




}
