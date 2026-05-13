package dastrulash.uz.kun.uz.entity;


import dastrulash.uz.kun.uz.enums.ProfileStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProfileStatusEnum status= ProfileStatusEnum.NOT_ACTIVE;


    @Column(name = "visible", nullable = false)
    private Boolean visible =true;

    @Column(name = "photo_id")
    private String photoId;


    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<ProfileRoleEntity> roleList;

}
