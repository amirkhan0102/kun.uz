package dasturlash.uz.controller;


import dasturlash.uz.dto.article.ArticleLikeDTO;
import dasturlash.uz.service.ArticleLikeService;
import dasturlash.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article-like")
public class ArticleLikeController {


    @Autowired
    private ArticleLikeService articleLikeService;



    @PostMapping
    public ResponseEntity <String> like(@RequestBody ArticleLikeDTO dto){
       int profileId= SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(articleLikeService.like(dto,profileId));
    }

    // Like soni
    @GetMapping("/like-count/{articleId}")
    public ResponseEntity<Long> getLikeCount(@PathVariable String articleId) {
        return ResponseEntity.ok(articleLikeService.getLikeCount(articleId));
    }

    // Dislike soni
    @GetMapping("/dislike-count/{articleId}")
    public ResponseEntity<Long> getDislikeCount(@PathVariable String articleId) {
        return ResponseEntity.ok(articleLikeService.getDislikeCount(articleId));
    }






}
