package dasturlash.uz.controller;

import dasturlash.uz.dto.CommentLikeDTO;
import dasturlash.uz.service.CommentLikeService;
import dasturlash.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment-like")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;



    @PostMapping
    public ResponseEntity<String> like(@RequestBody CommentLikeDTO dto) {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(commentLikeService.like(dto, profileId));
    }

    @GetMapping("/like-count/{commentId}")
    public ResponseEntity<Long> getLikeCount(@PathVariable Integer commentId) {
        return ResponseEntity.ok(commentLikeService.getLikeCount(commentId));
    }

    @GetMapping("/dislike-count/{commentId}")
    public ResponseEntity<Long> getDislikeCount(@PathVariable Integer commentId) {
        return ResponseEntity.ok(commentLikeService.getDislikeCount(commentId));
    }


}