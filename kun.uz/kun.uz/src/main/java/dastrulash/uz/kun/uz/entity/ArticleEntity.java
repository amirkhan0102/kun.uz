package dastrulash.uz.kun.uz.entity;

import dastrulash.uz.kun.uz.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "moderator_id")
    private Integer moderatorId;

    @Column(name = "publisher_id")
    private Integer publisherId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus status = ArticleStatus.NOT_PUBLISHED;

    @Column(name = "read_time")
    private Integer readTime;

    @Column(name = "shared_count")
    private Integer sharedCount = 0;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column(name = "visible")
    private Boolean visible = true;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;
}