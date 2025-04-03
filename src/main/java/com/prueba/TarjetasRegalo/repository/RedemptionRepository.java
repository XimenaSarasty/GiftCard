package com.prueba.TarjetasRegalo.repository;

import com.prueba.TarjetasRegalo.entity.Redemption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedemptionRepository extends JpaRepository<Redemption, Long> {

}