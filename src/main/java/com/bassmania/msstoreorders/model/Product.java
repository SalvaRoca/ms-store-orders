package com.bassmania.msstoreorders.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {
    private String id;
    private String productRef;
    private String category;
    private String brand;
    private String model;
    private String description;
    private String img;
    private Double price;
}
