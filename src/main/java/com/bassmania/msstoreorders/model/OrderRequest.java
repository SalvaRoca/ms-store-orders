package com.bassmania.msstoreorders.model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderRequest {
    @NotNull(message = "`products` cannot be null")
    @NotEmpty(message = "`products` cannot be empty")
    private List<String> products;
}
