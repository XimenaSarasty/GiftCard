package com.prueba.TarjetasRegalo.controller;

import com.prueba.TarjetasRegalo.entity.GiftCard;
import com.prueba.TarjetasRegalo.service.EmailService;
import com.prueba.TarjetasRegalo.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
public class GiftCardController {

    @Autowired
    GiftCardService giftCardService;

    @GetMapping("/findAllGiftCards")
    public List<GiftCard> findAllGiftCards() {
        return giftCardService.findAllGiftCards();
    }

    @GetMapping("/findById/{id}")
    public Optional<GiftCard> findById(@PathVariable Long id) {
        return giftCardService.findById(id);
    }

    @PostMapping("/saveGiftCard")
    public GiftCard saveGiftCard(@RequestBody GiftCard giftCard) {
        return giftCardService.saveGiftCard(giftCard);
    }

    @PutMapping("/updateGiftCard/{id}")
    public GiftCard updateGiftCard(@PathVariable Long id, @RequestBody GiftCard giftCard) {
        return giftCardService.updateGiftCard(id, giftCard);
    }
    @DeleteMapping("/deleteGiftCard/{id}")
    public void deleteGiftCard(@PathVariable Long id) {
        giftCardService.deleteGiftCard(id);
    }

}
