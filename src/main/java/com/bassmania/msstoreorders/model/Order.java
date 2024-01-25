package com.bassmania.msstoreorders.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_ref", unique = true)
    private String orderRef;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "total")
    private Double total;

    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    private List<Product> products;

    @Column(name = "status")
    private String status;

    @PostPersist
    public void postPersist() {
        generateOrderCode();
    }

    public void generateOrderCode() {
        this.orderRef = "PO-" + this.date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + this.id;
    }
}
