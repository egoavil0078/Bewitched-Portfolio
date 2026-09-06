-- Tallas dinámicas configuradas por el administrador para cada producto.
-- La unidad no se guarda aquí: Anillos usan mm y Pulseras/Colgantes usan cm.
-- El stock sigue siendo general a nivel de producto.

CREATE TABLE producto_tallas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    producto_id BIGINT NOT NULL,
    valor DECIMAL(7,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_producto_talla_valor UNIQUE (producto_id, valor),
    CONSTRAINT fk_producto_tallas_producto
        FOREIGN KEY (producto_id) REFERENCES productos(id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE INDEX idx_producto_tallas_producto
    ON producto_tallas(producto_id);
