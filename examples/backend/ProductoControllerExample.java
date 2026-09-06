package com.kevin.marketplace.catalogo.controller;

import com.kevin.marketplace.catalogo.descuento.ProductoResponse;
import com.kevin.marketplace.catalogo.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoControllerExample {

    private final ProductoService productoService;

    // Lista de productos visibles para usuarios
    // Ejemplos:
    // /api/productos
    // /api/productos?categoria=Pulseras
    // /api/productos?categoria=Pulseras&mineral=Amatista
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listarVisibles(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String mineral
    ) {
        return ResponseEntity.ok(productoService.listarVisibles(categoria, mineral));
    }

    // Ver detalle de un producto
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerProducto(id));
    }
}