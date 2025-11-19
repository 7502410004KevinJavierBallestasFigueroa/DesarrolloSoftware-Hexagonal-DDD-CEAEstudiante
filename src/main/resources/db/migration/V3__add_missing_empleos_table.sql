-- Ensure empleos table exists for environments
-- where the initial migration did not create it

CREATE TABLE IF NOT EXISTS empleos (
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

CREATE INDEX IF NOT EXISTS idx_empleos_empresa_nombre
    ON empleos(empresa, nombre);

CREATE INDEX IF NOT EXISTS idx_empleos_empresa
    ON empleos(empresa);

