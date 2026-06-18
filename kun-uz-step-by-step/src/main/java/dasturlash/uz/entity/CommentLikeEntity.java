package dasturlash.uz.entity;

import dasturlash.uz.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment_like",
        uniqueConstraints = @UniqueConstraint(columnNames = {"comment_id", "profile_id"}))
public class CommentLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comment_id", nullable = false)
    private Integer commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    private CommentEntity comment;

    @Column(name = "profile_id", nullable = false)
    private Integer profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}