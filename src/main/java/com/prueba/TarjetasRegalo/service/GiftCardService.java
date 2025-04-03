package com.prueba.TarjetasRegalo.service;

import com.prueba.TarjetasRegalo.entity.GiftCard;

import java.util.List;
import java.util.Optional;

public interface GiftCardService {
    List<GiftCard> findAllGiftCards();
    GiftCard findById(Long id);
    GiftCard findByCode(String cardCode);
    GiftCard saveGiftCard(GiftCard giftCard);
    GiftCard updateGiftCard(Long idCard, GiftCard giftCard);
    void deleteGiftCard(Long id);
    GiftCard updateState(String cardCode, String newState);
}
