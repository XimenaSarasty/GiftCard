package com.prueba.TarjetasRegalo.service;

import com.prueba.TarjetasRegalo.entity.GiftCard;
import com.prueba.TarjetasRegalo.repository.GiftCardRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GiftCardServiceTest {

    @Mock
    private GiftCardRepository giftCardRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private GiftCardServiceImpl giftCardService;

    public GiftCardServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByCode_GiftCardExists() {
        String cardCode = "1234567890123456";
        GiftCard mockGiftCard = new GiftCard();
        mockGiftCard.setCode(cardCode);
        mockGiftCard.setAmount(BigDecimal.valueOf(100));

        when(giftCardRepository.findByCode(cardCode)).thenReturn(Optional.of(mockGiftCard));

        GiftCard result = giftCardService.findByCode(cardCode);
        assertNotNull(result);
        assertEquals(cardCode, result.getCode());
    }

    @Test
    void testFindByCode_GiftCardDoesNotExist() {
        String cardCode = "0000000000000000";
        when(giftCardRepository.findByCode(cardCode)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            giftCardService.findByCode(cardCode);
        });

        assertEquals("GiftCard con código " + cardCode + " no encontrada", exception.getMessage());
    }
}
