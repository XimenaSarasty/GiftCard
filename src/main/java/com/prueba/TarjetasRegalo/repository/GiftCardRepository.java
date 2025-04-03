package com.prueba.TarjetasRegalo.repository;

import com.prueba.TarjetasRegalo.entity.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface GiftCardRepository extends JpaRepository<GiftCard, Long> {
    Optional<GiftCard> findByCode(String code);
}
