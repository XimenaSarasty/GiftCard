package com.prueba.TarjetasRegalo.controller;

import com.prueba.TarjetasRegalo.service.RedemptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redemptions")
public class RedemptionController {

    @Autowired
    private RedemptionService redemptionService;

    @PostMapping("/redeem/{cardCode}")
    public ResponseEntity<String> redeemGiftCard(
            @PathVariable String cardCode,
            @RequestParam String recipientEmail) {
        redemptionService.redeemGiftCard(cardCode, recipientEmail);
        return ResponseEntity.ok("Solicitud de redención procesada para la tarjeta: " + cardCode + ". Se enviará una notificación a: " + recipientEmail);
    }
}