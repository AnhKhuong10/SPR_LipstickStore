package org.example.mystoreh.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.mystoreh.utils.FormatUtil;

import java.text.Format;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "[order]")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_shipped_date", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)   // Dùng cho kiểu date hoặc calendar
    private Date orderShippedDate;

    @Column(name = "order_created_at", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)   // Dùng cho kiểu date hoặc calendar
    private Date orderCreateAt;

    @Column(name = "order_discount")
    private float orderDiscount;

    @Column(name = "order_total_amount")
    private double orderTotalAmount;

    @Column(name = "order_note")
    private String orderNote;

    @Column(name = "receiver_name", length = 200)
    private String receiverName;

    @Column(name = "receiver_mobile", length = 11)
    private String receiverPhone;

    @Column(name = "shipping_address", length = 11)
    private String shippingAddress;

    @OneToOne
    @JoinColumn(name = "order_status_id", referencedColumnName = "order_status_id")
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetailList;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public String getOrderNumber(){
        return FormatUtil.leftPAD(String.valueOf(id), 6, "0");
    }
}
