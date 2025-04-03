package com.prueba.TarjetasRegalo.service;

import com.prueba.TarjetasRegalo.entity.GiftCard;
import com.prueba.TarjetasRegalo.repository.GiftCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class GiftCardServiceImpl implements GiftCardService {

    @Autowired
    GiftCardRepository giftCardRepository;

    @Autowired
    private EmailService emailService;
    private final Random random = new Random();
    private static final int CODE_LENGTH = 16;

    private String generateUniqueCode() {
        String code;
        do {
            code = generateRandomCode(CODE_LENGTH);
        } while (giftCardRepository.findByCode(code).isPresent());
        return code;
    }

    private String generateRandomCode(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

    @Override
    public List<GiftCard> findAllGiftCards () {
        return  giftCardRepository.findAll();
    }

    @Override
    public Optional<GiftCard> findById(Long id) {
        return giftCardRepository.findById(id);
    }

    @Override
    public Optional<GiftCard> findByCode(String cardCode) {
        return giftCardRepository.findByCode(cardCode);
    }

    @Override
    public GiftCard saveGiftCard(GiftCard giftCard) {
        giftCard.setCode(generateUniqueCode());
        if (giftCard.getCreationDateCard() == null) {
            giftCard.setCreationDateCard(new Date());
        }
        GiftCard savedCard = giftCardRepository.save(giftCard);
        String recipientEmail = "laura.limas0207@gmail.com";
        emailService.sendCreationNotification(recipientEmail, savedCard.getCode(), savedCard.getAmount());
        return savedCard;
    }
    @Override
    public GiftCard updateGiftCard(Long id, GiftCard giftCard) {
        return giftCardRepository.findById(id)
                .map(giftCardDb -> {
                    if (giftCard.getAmount() != null) {
                        giftCardDb.setAmount(giftCard.getAmount());
                    }
                    if (giftCard.getExpirationDateCard() != null) {
                        giftCardDb.setExpirationDateCard(giftCard.getExpirationDateCard());
                    }
                    if (giftCard.getState() != null && !giftCard.getState().isEmpty()) {
                        giftCardDb.setState(giftCard.getState());
                    }
                    return giftCardRepository.save(giftCardDb);
                })
                .orElse(null);
    }

    @Override
    public void deleteGiftCard(Long id) {
        giftCardRepository.deleteById(id);
    }

    @Override
    public GiftCard updateState(String cardCode, String newState) {
        return giftCardRepository.findByCode(cardCode)
                .map(giftCardDb -> {
                    if (newState != null && !newState.isEmpty()) {
                        giftCardDb.setState(newState);
                    }
                    return giftCardRepository.save(giftCardDb);
                })
                .orElse(null);
    }
}