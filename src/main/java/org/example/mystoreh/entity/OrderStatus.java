package org.example.mystoreh.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "[order_status]")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    private Long id;

    @Column(name = "order_status")
    private String orderStatus;

    @OneToOne(mappedBy = "orderStatus", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Order order;
}
