package dastrulash.uz.kun.uz.entity;


import dastrulash.uz.kun.uz.enums.ProfileStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String surname;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private ProfileStatusEnum status= ProfileStatusEnum.NOT_ACTIVE;


    private Boolean visible =true;

    @Column(name = "photo_id")
    private String photoId;


    private LocalDateTime createdDate = LocalDateTime.now();

}
