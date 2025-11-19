-- Migración inicial: Creación de todas las tablas

-- Tabla de usuarios
CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    role VARCHAR(20) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);

-- Tabla de tokens de reseteo de contraseña
CREATE TABLE password_reset_tokens (
    token VARCHAR(255) PRIMARY KEY,
    user_id UUID NOT NULL,
    expiration_time TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_password_reset_tokens_user_id ON password_reset_tokens(user_id);
CREATE INDEX idx_password_reset_tokens_expiration ON password_reset_tokens(expiration_time);

-- Tabla de blacklist de tokens JWT
CREATE TABLE token_blacklist (
    token VARCHAR(500) PRIMARY KEY,
    expiration_time TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_token_blacklist_expiration ON token_blacklist(expiration_time);

-- Tabla de contratos
CREATE TABLE contratos (
    id UUID PRIMARY KEY,
    fecha_firma TIMESTAMP WITH TIME ZONE NOT NULL,
    fecha_inicio TIMESTAMP WITH TIME ZONE NOT NULL,
    fecha_fin TIMESTAMP WITH TIME ZONE,
    empresa VARCHAR(100) NOT NULL,
    empleado_id UUID NOT NULL,
    funciones VARCHAR(2000) NOT NULL,
    monto NUMERIC(18,2) NOT NULL,
    frecuencia_pago VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_contratos_empresa_empleado ON contratos(empresa, empleado_id);
CREATE INDEX idx_contratos_empleado_id ON contratos(empleado_id);

-- Tabla de empleos
CREATE TABLE empleos (
    id UUID PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    area_trabajo VARCHAR(100) NOT NULL,
    empresa VARCHAR(100) NOT NULL,
    nivel VARCHAR(50) NOT NULL,
    sueldo NUMERIC(18,2) NOT NULL,
    funciones VARCHAR(2000) NOT NULL,
    cargo_jefe VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_empleos_empresa_nombre ON empleos(empresa, nombre);
CREATE INDEX idx_empleos_empresa ON empleos(empresa);

-- Insertar usuario admin por defecto (password: Admin123!)
-- Hash BCrypt para "Admin123!" (generado con BCrypt)
INSERT INTO users (id, username, email, password_hash, role, is_active, created_at)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'admin',
    'admin@example.com',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'Admin',
    true,
    CURRENT_TIMESTAMP
);

