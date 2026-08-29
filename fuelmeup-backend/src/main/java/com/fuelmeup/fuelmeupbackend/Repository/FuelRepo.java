package com.fuelmeup.fuelmeupbackend.Repository;

import com.fuelmeup.fuelmeupbackend.Model.Creator;
import com.fuelmeup.fuelmeupbackend.Model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FuelRepo extends JpaRepository<Fuel, UUID> {
    Optional<Fuel> findByRazorpayOrderId(String razorpayOrderId);
    Optional<List<Fuel>> findByCreator(Creator creator);

    @Query("SELECT SUM(f.amount) FROM Fuel f WHERE f.creator.creatorId = :creatorId AND f.paymentStatus = 'SUCCESSFUL'")
    BigDecimal getTotalEarningsByCreatorId(@Param("creatorId") UUID creatorId);
}