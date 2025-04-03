package com.prueba.TarjetasRegalo.task;

import com.prueba.TarjetasRegalo.entity.GiftCard;
import com.prueba.TarjetasRegalo.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class GiftCardExpirationTask {

    @Autowired
    private GiftCardService giftCardService;

    // Se ejecutará todos los días a las 03:00 AM
    @Scheduled(cron = "0 0 3 * * ?")
    public void checkExpiredGiftCards() {
        System.out.println("Ejecutando tarea para verificar tarjetas expiradas...");
        List<GiftCard> activeGiftCards = giftCardService.findAllGiftCards().stream()
                .filter(card -> "Active".equalsIgnoreCase(card.getState()))
                .collect(java.util.stream.Collectors.toList());

        LocalDate today = LocalDate.now(ZoneId.of("America/Medellin"));

        for (GiftCard card : activeGiftCards) {
            Date expirationDate = card.getExpirationDateCard();
            if (expirationDate != null) {
                LocalDate expirationLocalDate = expirationDate.toInstant().atZone(ZoneId.of("America/Medellin")).toLocalDate();
                if (expirationLocalDate.isBefore(today)) {
                    card.setState("Expired");
                    giftCardService.saveGiftCard(card);
                    System.out.println("Tarjeta con código " + card.getCode() + " ha expirado.");
                }
            }
        }
        System.out.println("Verificación de tarjetas expiradas completada.");
    }
}