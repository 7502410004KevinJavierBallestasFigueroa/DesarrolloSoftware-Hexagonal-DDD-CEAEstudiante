-- Ensure contratos table exists for environments
-- where V1__init.sql was applied without it

CREATE TABLE IF NOT EXISTS contratos (
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

CREATE INDEX IF NOT EXISTS idx_contratos_empresa_empleado
    ON contratos(empresa, empleado_id);

CREATE INDEX IF NOT EXISTS idx_contratos_empleado_id
    ON contratos(empleado_id);

