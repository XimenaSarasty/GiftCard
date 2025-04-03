package com.prueba.TarjetasRegalo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "giftCards")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GiftCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCard;

    @Column(name = "code", unique = true, length = 16)
    private String code;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "creation_date_card")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date creationDateCard;

    @Column(name = "expiration_date_card")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date expirationDateCard;

    @Column(name = "state")
    private String state = "Active";

    @OneToMany(mappedBy = "giftCard")
    private List<Redemption> redemptions;
}