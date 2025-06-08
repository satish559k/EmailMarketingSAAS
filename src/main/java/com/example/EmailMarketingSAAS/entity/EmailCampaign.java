package com.example.EmailMarketingSAAS.entity;

import com.example.EmailMarketingSAAS.enums.CampaignStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.EmailMarketingSAAS.enums.CampaignStatus.PENDING;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "email_campaigns")
public class EmailCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "scheduled_time", nullable = false)
    private LocalDateTime scheduledTime;

    private String subject;
    @Lob
    @Column(columnDefinition = "TEXT")
    @Basic(fetch = FetchType.EAGER)
    private String body;
    @Enumerated(EnumType.STRING)
    private CampaignStatus status;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    protected void onCreate() {
        this.status = PENDING;
    }

}
