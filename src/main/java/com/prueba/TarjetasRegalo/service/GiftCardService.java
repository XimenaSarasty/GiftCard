package com.prueba.TarjetasRegalo.service;

import com.prueba.TarjetasRegalo.entity.GiftCard;

import java.util.List;
import java.util.Optional;

public interface GiftCardService {
    List<GiftCard> findAllGiftCards();
    Optional<GiftCard> findById(Long id);
    GiftCard saveGiftCard(GiftCard giftCard);
    GiftCard updateGiftCard(Long idCard, GiftCard giftCard);
    Optional<GiftCard> findByCode(String cardCode);
    void deleteGiftCard(Long id);
    GiftCard updateState(String cardCode, String newState);
}
