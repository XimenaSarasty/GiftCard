package com.prueba.TarjetasRegalo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.TarjetasRegalo.entity.GiftCard;
import com.prueba.TarjetasRegalo.service.GiftCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GiftCardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GiftCardService giftCardService;

    @InjectMocks
    private GiftCardController giftCardController;

    private GiftCard giftCard1;
    private GiftCard giftCard2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(giftCardController).build();

        giftCard1 = GiftCard.builder()
                .idCard(1L)
                .code("1234-5678-9012-3456")
                .amount(BigDecimal.valueOf(100.0))
                .creationDateCard(new Date())
                .expirationDateCard(new Date())
                .state("Active")
                .build();

        giftCard2 = GiftCard.builder()
                .idCard(2L)
                .code("5678-9012-3456")
                .amount(BigDecimal.valueOf(50.0))
                .creationDateCard(new Date())
                .expirationDateCard(new Date())
                .state("Active")
                .build();
    }

    @Test
    public void testFindAllGiftCards() throws Exception {
        List<GiftCard> giftCards = new ArrayList<>();
        giftCards.add(giftCard1);
        giftCards.add(giftCard2);

        when(giftCardService.findAllGiftCards()).thenReturn(giftCards);

        mockMvc.perform(get("/api/giftcards/findAllGiftCards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCard").value(1))
                .andExpect(jsonPath("$[1].idCard").value(2));
    }

    @Test
    public void testFindGiftCardById() throws Exception {
        GiftCard giftCard1 = new GiftCard();
        giftCard1.setIdCard(1L);
        when(giftCardService.findById(1L)).thenReturn(giftCard1);
        mockMvc.perform(get("/api/giftcards/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCard").value(1));
    }

    @Test
    public void testSaveGiftCard() throws Exception {
        when(giftCardService.saveGiftCard(any(GiftCard.class))).thenReturn(giftCard1);

        mockMvc.perform(post("/api/giftcards/saveGiftCard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(giftCard1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCard").value(1));
    }

    @Test
    public void testUpdateGiftCard() throws Exception {
        when(giftCardService.updateGiftCard(eq(1L), any(GiftCard.class))).thenReturn(giftCard1);

        mockMvc.perform(put("/api/giftcards/updateGiftCard/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(giftCard1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCard").value(1));
    }

    @Test
    public void testDeleteGiftCard() throws Exception {
        doNothing().when(giftCardService).deleteGiftCard(1L);

        mockMvc.perform(delete("/api/giftcards/deleteGiftCard/1"))
                .andExpect(status().isOk());

        verify(giftCardService, times(1)).deleteGiftCard(1L);
    }
}
