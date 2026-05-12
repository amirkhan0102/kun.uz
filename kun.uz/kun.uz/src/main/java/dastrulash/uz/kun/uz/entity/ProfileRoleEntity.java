package dastrulash.uz.kun.uz.entity;

import dastrulash.uz.kun.uz.enums.ProfileRoleEnum;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "profile_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @Enumerated(EnumType.STRING)
    private ProfileRoleEnum role;
}