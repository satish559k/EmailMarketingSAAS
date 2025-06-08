package com.example.EmailMarketingSAAS.entity;

import com.example.EmailMarketingSAAS.enums.EmailStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "email_logs")
public class EmailLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "email_id")
    private String emailID;
    @Enumerated(EnumType.STRING)
    private EmailStatus status;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "campaign_id")
    private EmailCampaign campaign;
    @Column(name = "sent_datetime")
    private LocalDateTime sentDateTime;
    @Column(name = "error_message")
    private String errorMessage;
}
