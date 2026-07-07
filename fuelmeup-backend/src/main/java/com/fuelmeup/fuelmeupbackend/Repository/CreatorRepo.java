package com.fuelmeup.fuelmeupbackend.Repository;

import com.fuelmeup.fuelmeupbackend.Model.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreatorRepo extends JpaRepository<Creator, UUID> {
    boolean existsByUserId(UUID userId);
    boolean existsCreatorByUserUsername(String creatorName);
    Optional<Creator> findByUserUsername(String creatorName);
}
