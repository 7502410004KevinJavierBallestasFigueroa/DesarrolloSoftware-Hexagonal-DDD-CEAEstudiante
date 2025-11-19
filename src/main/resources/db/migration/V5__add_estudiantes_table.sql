-- Tabla de estudiantes para contexto CEA
CREATE TABLE estudiantes (
    id UUID PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    apellido VARCHAR(20) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    semestre INT NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    genero VARCHAR(10) NOT NULL,
    telefono VARCHAR(10) NOT NULL,
    programa VARCHAR(100) NOT NULL,
    universidad VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_estudiantes_email ON estudiantes(email);
CREATE INDEX idx_estudiantes_programa ON estudiantes(programa);
CREATE INDEX idx_estudiantes_universidad ON estudiantes(universidad);

