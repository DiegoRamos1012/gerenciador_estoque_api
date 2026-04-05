-- Seed de produtos para loja temática de videogames
-- Inserir usando: sqlite3 data\database.db < src\main\resources\db\seed_products.sql
-- As colunas seguem as migrations V1__create_product_table.sql

INSERT INTO product (id, name, product_code, price, quantity, description, status, deleted, deleted_at, created_at, last_time_changed)
VALUES
('d3f9a1a8-1a2b-4c3d-8e9f-111111111111', 'Console RetroStation X', 'CON-RX-001', 499.99, 10, 'Console home estilo retrô com suporte a cartuchos e HDMI', 'ACTIVE', 0, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('b1a2c3d4-5e6f-47a8-9b0c-222222222222', "Game 'Legend of Pixels' (Switch)", 'GAME-PIX-001', 59.90, 25, 'Aventura pixelada com mundo aberto e chefes épicos - edição padrão', 'ACTIVE', 0, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('f0e1d2c3-b4a5-4687-8c9d-333333333333', 'Collector''s Edition - Dragon Quest', 'GAME-DRQ-CE', 129.90, 3, 'Edição de colecionador com artbook e mapa, tiragem limitada', 'ACTIVE', 0, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('a9b8c7d6-e5f4-4123-9a0b-444444444444', 'Wireless Pro Controller', 'CTRL-WL-PRO', 89.90, 40, 'Controle sem fio ergonômico com gatilhos programáveis', 'ACTIVE', 0, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('11223344-5566-4789-8abc-555555555555', 'RGB Gaming Headset', 'HS-RGB-100', 69.50, 15, 'Headset com som estéreo, microfone destacável e iluminação RGB', 'ACTIVE', 0, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('99887766-5544-4b3c-9d1e-666666666666', 'Limited Poster - Pixel Heroes', 'POST-PIX-01', 14.99, 0, 'Poster limitado de parede com ilustração exclusiva (sem estoque)', 'OUT_OF_STOCK', 0, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('22334455-6677-4f2e-8a9b-777777777777', 'Retro Cartridge Adapter', 'ADP-RETRO-01', 24.99, 7, 'Adaptador para usar cartuchos retro em consoles modernos via USB', 'ACTIVE', 0, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('33445566-7788-49ab-9c0d-888888888888', 'Mini Arcade Cabinet', 'ARC-MINI-01', 249.00, 2, 'Arcade de mesa em escala reduzida com 200 jogos clássicos', 'ACTIVE', 0, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('44556677-8899-4cde-0f1a-999999999999', 'Foam Handheld Grip', 'GRP-HNDL-01', 9.99, 100, 'Grip de espuma para maior conforto em controles portáteis', 'ACTIVE', 0, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('55667788-9900-4abc-1b2c-aaaaaaaaaaaa', 'Collectible Figurine - Heroic Knight', 'FIG-HK-001', 79.99, 0, 'Estatueta colecionável pintada à mão do Cavaleiro Heróico (edição limitada)', 'OUT_OF_STOCK', 0, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- Fim do seed de produtos

