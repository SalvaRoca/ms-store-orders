package com.bassmania.msstoreorders.controller;

import com.bassmania.msstoreorders.model.Order;
import com.bassmania.msstoreorders.model.OrderRequest;
import com.bassmania.msstoreorders.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Controlador para la gestión de pedidos")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @Operation(
            operationId = "Consultar todos los pedidos",
            description = "Operación de lectura",
            summary = "La API devuelve la lista completa de pedidos registrados en la base de datos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de pedidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No hay pedidos registrados en la base de datos"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor"
                    )
            }
    )
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orderList = orderService.getOrders();
        if (!orderList.isEmpty()) {
            return ResponseEntity.ok(orderList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(
            operationId = "Consultar un pedido por ID",
            description = "Operación de lectura",
            summary = "La API devuelve un pedido registrado en la base de datos según su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Detalles del pedido",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se ha encontrado el pedido"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor"
                    )
            }
    )
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        Order order = orderService.getOrderById(id);

        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(
            operationId = "Crear un pedido",
            description = "Operación de escritura",
            summary = "La API crea un pedido en la base de datos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Detalles del pedido creado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Alguno de los datos de entrada es incorrecto"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor"
                    )
            }
    )
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);

        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.badRequest().body("{\"errorMessage\": \"Some input data might be incorrect, please check and try again\"}");
        }
    }

    @PatchMapping("{id}")
    @Operation(
            operationId = "Actualizar el estado de un pedido",
            description = "Operación de escritura",
            summary = "La API actualiza el estado de un pedido en la base de datos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Detalles del pedido modificado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Alguno de los datos de entrada es incorrecto"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se ha encontrado el pedido"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor"
                    )
            }
    )
    public ResponseEntity<?> patchOrder(@PathVariable String id, @RequestParam String status) {
        Order existingOrder = orderService.getOrderById(id);
        if (existingOrder != null) {
            Order updatedProduct = orderService.updateOrder(existingOrder, status);

            if (updatedProduct != null) {
                return ResponseEntity.ok(updatedProduct);
            } else {
                return ResponseEntity.badRequest().body("{\"errorMessage\": \"Some input data might be incorrect, please check and try again\"}");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            operationId = "Eliminar un pedido",
            description = "Operación de escritura",
            summary = "La API elimina un pedido de la base de datos según su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Pedido eliminado con éxito"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se ha encontrado el pedido"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor"
                    )
            }
    )
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        Order order = orderService.getOrderById(id);

        if (order != null) {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
