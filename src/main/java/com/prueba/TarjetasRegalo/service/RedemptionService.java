package com.prueba.TarjetasRegalo.service;

import com.prueba.TarjetasRegalo.entity.GiftCard;
import com.prueba.TarjetasRegalo.entity.Redemption;
import com.prueba.TarjetasRegalo.repository.RedemptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RedemptionService {

    @Autowired
    private RedemptionRepository redemptionRepository;

    @Autowired
    private GiftCardService giftCardService;

    @Autowired
    private EmailService emailService;

    public void redeemGiftCard(String cardCode, String recipientEmail) {
        // Obtener la tarjeta por código
        GiftCard giftCard = giftCardService.findByCode(cardCode);

        // Verificar si la tarjeta existe
        if (giftCard == null) {
            System.out.println("No se encontró la tarjeta con código " + cardCode + ".");
            return;
        }

        // Verificar si la tarjeta está activa
        if (!"Active".equalsIgnoreCase(giftCard.getState())) {
            System.out.println("La tarjeta con código " + cardCode + " no está activa o ya ha sido redimida.");
            return;
        }

        // Marcar la tarjeta como redimida
        giftCard.setState("Redeemed");
        giftCardService.updateGiftCard(giftCard.getIdCard(), giftCard);

        // Crear y guardar el registro de redención
        Redemption redemption = new Redemption();
        redemption.setGiftCard(giftCard);
        redemption.setDataRedemption(new Date());
        redemptionRepository.save(redemption);

        // Enviar notificación de redención
        emailService.sendRedemptionNotification(recipientEmail, giftCard.getCode());
    }
}
