-- =====================================================================
-- Base de datos del proyecto Kirei Kanri
-- Evidencia GA7-220501096-AA5-EV01 - Diseño del servicio de autenticación
-- Autora: Daniela Perlaza Ordoñez
-- =====================================================================

-- Creación de la base de datos con soporte completo de caracteres
CREATE DATABASE IF NOT EXISTS kirei_kanri
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE kirei_kanri;

-- Tabla que almacena las credenciales de acceso al sistema
CREATE TABLE IF NOT EXISTS usuarios (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    usuario        VARCHAR(50)  NOT NULL UNIQUE,
    contrasena     VARCHAR(100) NOT NULL,   -- hash BCrypt, nunca texto plano
    fecha_registro TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- Índice para acelerar la búsqueda por nombre de usuario durante el login
CREATE INDEX idx_usuarios_usuario ON usuarios (usuario);

-- Usuario de prueba para validar el servicio
-- Contraseña en texto plano: Kirei2026
INSERT INTO usuarios (usuario, contrasena)
VALUES ('daniela', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');

-- Verificación del registro creado
SELECT id, usuario, fecha_registro FROM usuarios;
