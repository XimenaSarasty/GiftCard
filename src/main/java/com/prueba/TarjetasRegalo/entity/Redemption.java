package com.prueba.TarjetasRegalo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Redemptions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Redemption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRedemption;

    @ManyToOne
    @JoinColumn(name = "idCard")
    private GiftCard giftCard;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRedemption;
}