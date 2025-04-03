package com.prueba.TarjetasRegalo.service;

import com.prueba.TarjetasRegalo.entity.GiftCard;
import com.prueba.TarjetasRegalo.entity.Redemption;
import com.prueba.TarjetasRegalo.repository.RedemptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RedemptionService {

    @Autowired
    private RedemptionRepository redemptionRepository;

    @Autowired
    private GiftCardService giftCardService;

    @Autowired
    private EmailService emailService;

    public void redeemGiftCard(String cardCode, String recipientEmail) {
        Optional<GiftCard> giftCardOptional = giftCardService.findByCode(cardCode);
        if (giftCardOptional.isPresent()) {
            GiftCard giftCard = giftCardOptional.get();
            if ("Active".equalsIgnoreCase(giftCard.getState())) {
                // Lógica para marcar la tarjeta como redimida (actualizar el estado)
                giftCard.setState("Redeemed");
                giftCardService.updateGiftCard(giftCard.getIdCard(), giftCard);

                // Crear el registro de redención
                Redemption redemption = new Redemption();
                redemption.setGiftCard(giftCard);
                redemption.setDataRedemption(new Date());
                redemptionRepository.save(redemption);

                // Enviar la notificación
                emailService.sendRedemptionNotification(recipientEmail, giftCard.getCode());
            } else {
                System.out.println("La tarjeta con código " + cardCode + " no está activa o ya ha sido redimida.");
            }
        } else {
            System.out.println("No se encontró la tarjeta con código " + cardCode + ".");
        }
    }
}