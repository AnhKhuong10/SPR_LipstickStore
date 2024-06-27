package org.example.mystoreh.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "user_address" )
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_address_id")
    private Long id;

    @Column(name = "user_address_name")
    private String userAddressName;

    @Column(name = "receiver_name", length = 100)
    private String receiverName;

    @Column(name = "receiver_mobile", length = 11)
    private String receiverPhone;

    @Column(name = "default_address", length = 2)
    private boolean defaultAddress;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
