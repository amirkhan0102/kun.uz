package dastrulash.uz.kun.uz.entity;

import dastrulash.uz.kun.uz.enums.SmsStatus;
import dastrulash.uz.kun.uz.enums.SmsType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sms_history")
public class SmsHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SmsStatus status = SmsStatus.SENT;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SmsType type;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;


}