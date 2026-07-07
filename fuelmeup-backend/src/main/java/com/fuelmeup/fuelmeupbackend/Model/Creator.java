package com.fuelmeup.fuelmeupbackend.Model;


import com.fuelmeup.fuelmeupbackend.Enum.CreatorStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID creatorId;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = true)
    private String bio="This is your bio";

    @Column(nullable = true)
    private String profileImage="This is your profile image";

    @Column(nullable = true)
    private String coverImage="This is your cover image";

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CreatorStatus status=CreatorStatus.ACTIVE;

}
