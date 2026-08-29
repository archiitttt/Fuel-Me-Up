package com.fuelmeup.fuelmeupbackend.Model;

import com.fuelmeup.fuelmeupbackend.Enum.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Fuel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID fuelId;

    @ManyToOne
    @JoinColumn(name = "creatorId", nullable = false)
    private Creator creator;

    @ManyToOne
    @JoinColumn(name = "fuelerId", nullable = true)
    private User user;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus=PaymentStatus.PENDING;

    @Column(nullable = true)
    private String message;

    @Column(nullable = true)
    private String razorpayOrderId;

    @Column(nullable = true)
    private String razorpayPaymentId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
