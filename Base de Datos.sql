-- MANADA WOOF - BASE DE DATOS
DROP DATABASE IF EXISTS manada_woof;
CREATE DATABASE manada_woof CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE manada_woof;

-- TABLAS DE CATÁLOGOS
CREATE TABLE rol (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

INSERT INTO rol (nombre) VALUES ('admin'),('recepcion'),('groomer'),('veterinario');

CREATE TABLE especie (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

INSERT INTO especie (nombre) VALUES ('Perro'),('Gato'),('Otro');

CREATE TABLE sexo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE
) ENGINE=InnoDB;

INSERT INTO sexo (nombre) VALUES ('Macho'),('Hembra');

CREATE TABLE estado_cita (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

INSERT INTO estado_cita (nombre) VALUES
('PENDIENTE'),('CONFIRMADA'),('EN PROCESO'),
('COMPLETADA'),('CANCELADA'),('NO ASISTIO');

CREATE TABLE estado_historia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

INSERT INTO estado_historia (nombre) VALUES 
('ABIERTA'),('EN TRATAMIENTO'),('COMPLETADA'),('CANCELADA');

CREATE TABLE tipo_archivo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE
) ENGINE=InnoDB;

INSERT INTO tipo_archivo (nombre) VALUES 
('Foto Clínica'),('Receta'),('Análisis'),('Radiografía'),('Otro');

CREATE TABLE medio_solicitud (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

INSERT INTO medio_solicitud (nombre) VALUES 
('TELÉFONO'),('WHATSAPP'),('WEB'),('PRESENCIAL');

CREATE TABLE medio_pago (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

INSERT INTO medio_pago (nombre) VALUES 
('EFECTIVO'),('YAPE'),('PLIN'),('TARJETA CRÉDITO'),('TRANSFERENCIA');

CREATE TABLE categoria_servicio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE
) ENGINE=InnoDB;

INSERT INTO categoria_servicio (nombre) VALUES
('Estética y Grooming'),('Spa y Relajación'),('Hospedaje'),('Paseos y Guardería');

-- TABLAS DE CONFIGURACIÓN Y HORARIOS
CREATE TABLE horario_atencion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dia_semana INT NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    activo TINYINT(1) DEFAULT 1,
    UNIQUE(dia_semana)
) ENGINE=InnoDB;

INSERT INTO horario_atencion (dia_semana, hora_inicio, hora_fin, activo) VALUES
(2, '09:00:00', '18:00:00', 1),
(3, '09:00:00', '18:00:00', 1),
(4, '09:00:00', '18:00:00', 1),
(5, '09:00:00', '18:00:00', 1),
(6, '09:00:00', '18:00:00', 1);

CREATE TABLE festivos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL UNIQUE,
    descripcion VARCHAR(200),
    bloqueado TINYINT(1) DEFAULT 1
) ENGINE=InnoDB;

INSERT INTO festivos (fecha, descripcion, bloqueado) VALUES
('2025-12-25', 'Navidad', 1),
('2026-01-01', 'Año Nuevo', 1),
('2026-02-14', 'Día del Amor', 1);

CREATE TABLE bloqueos_fecha (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    descripcion VARCHAR(200),
    bloqueado TINYINT(1) DEFAULT 1
) ENGINE=InnoDB;

-- TABLAS PRINCIPALES
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    id_rol INT NOT NULL,
    activo TINYINT(1) DEFAULT 1,
    FOREIGN KEY (id_rol) REFERENCES rol(id)
) ENGINE=InnoDB;

INSERT INTO usuarios (nombre, usuario, password, id_rol, activo) VALUES
('Administrador', 'admin', '123456', 1, 1),
('Ana Recepcionista', 'ana', '123456', 2, 1),
('Luis Groomer', 'luis', '123456', 3, 1),
('Dr. Veterinario', 'doc_vet', '123456', 4, 1);

CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni VARCHAR(8) UNIQUE NOT NULL,
    celular VARCHAR(15) NOT NULL,
    celular2 VARCHAR(15),
    email VARCHAR(100),
    direccion VARCHAR(200),
    distrito VARCHAR(80),
    referencia VARCHAR(200)
) ENGINE=InnoDB;

INSERT INTO clientes (nombres, apellidos, dni, celular, celular2, email, direccion, distrito, referencia) VALUES
('Juan Carlos', 'Pérez Gómez', '71234567', '987654321', '951234567', 'juan@gmail.com', 'Av. Larco 1234', 'Miraflores', 'Frente al parque'),
('María Fernanda', 'López Ruiz', '72345678', '912345678', NULL, 'maria@hotmail.com', 'Calle Las Begonias 456', 'San Isidro', 'Edificio gris'),
('Pedro Alberto', 'Sánchez Torres', '73456789', '999888777', '963852741', 'pedro@outlook.com', 'Jr. Los Pinos 789', 'Surco', 'Portón negro'),
('Carla Valentina', 'Ramírez Díaz', '74567890', '926173845', '987123456', 'carla@gmail.com', 'Av. Brasil 2345', 'Magdalena', 'Supermercado'),
('Diego Andrés', 'Vega Castro', '75678901', '935482617', NULL, 'diego@yahoo.es', 'Calle Los Laureles 567', 'Jesús María', 'Casa verde');

CREATE TABLE mascotas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    id_especie INT NOT NULL,
    raza VARCHAR(80),
    id_sexo INT NOT NULL,
    fecha_nacimiento DATE,
    color VARCHAR(50),
    esterilizado TINYINT(1) DEFAULT 0,
    peso_actual DECIMAL(5,2),
    observaciones TEXT,
    foto VARCHAR(255),
    id_cliente BIGINT NOT NULL,
    FOREIGN KEY (id_especie) REFERENCES especie(id),
    FOREIGN KEY (id_sexo) REFERENCES sexo(id),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

INSERT INTO mascotas (nombre, id_especie, raza, id_sexo, fecha_nacimiento, color, esterilizado, peso_actual, observaciones, foto, id_cliente) VALUES
('Firulais', 1, 'Golden Retriever', 1, '2021-06-15', 'Dorado', 1, 32.50, 'Muy juguetón', 'firulais.jpg', 1),
('Luna', 2, 'Persa', 2, '2022-09-10', 'Blanco', 1, 4.20, 'Le encanta el spa', 'luna.jpg', 2),
('Rocky', 1, 'French Poodle', 1, '2020-03-20', 'Blanco', 1, 6.80, 'Corte cada mes', 'rocky.jpg', 1),
('Mía', 1, 'Shih Tzu', 2, '2023-01-12', 'Blanco y café', 1, 5.10, 'Piel delicada', 'mia.jpg', 3),
('Max', 1, 'Labrador', 1, '2019-11-05', 'Negro', 1, 38.00, 'Ama el agua', 'max.jpg', 4),
('Coco', 1, 'Chihuahua', 1, '2022-04-18', 'Café', 0, 2.90, 'Miedoso con secador', 'coco.jpg', 5),
('Nala', 2, 'Siamés', 2, '2021-08-30', 'Crema', 1, 4.50, 'Baño en seco', 'nala.jpg', 2),
('Pancho', 1, 'Mestizo', 1, '2020-12-01', 'Café', 1, 18.00, 'Rescatado', 'pancho.jpg', 3);

CREATE TABLE personal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    especialidad VARCHAR(100),
    color_calendario VARCHAR(7) DEFAULT '#3498db',
    activo TINYINT(1) DEFAULT 1
) ENGINE=InnoDB;

INSERT INTO personal (nombres, apellidos, telefono, especialidad, color_calendario, activo) VALUES
('Luis Miguel', 'Torres Vega', '987123456', 'Grooming Profesional', '#e74c3c', 1),
('Camila Sofía', 'Ramírez Díaz', '951456789', 'Spa y Masajes', '#9b59b6', 1),
('Diego Alonso', 'Castro Mendoza', '963258741', 'Paseos y Hospedaje', '#f1c40f', 1),
('Valeria Paz', 'Herrera Soto', '978456123', 'Baños Medicados', '#3498db', 1);

CREATE TABLE servicios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    id_categoria INT NOT NULL,
    duracion_minutos INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    descripcion TEXT,
    activo TINYINT(1) DEFAULT 1,
    FOREIGN KEY (id_categoria) REFERENCES categoria_servicio(id)
) ENGINE=InnoDB;

INSERT INTO servicios (nombre, id_categoria, duracion_minutos, precio, descripcion, activo) VALUES
('Baño + Corte + Secado', 1, 90, 130.00, 'Completo con corte de raza', 1),
('Baño Medicado', 1, 70, 110.00, 'Piel sensible', 1),
('Corte de Uñas + Limpieza', 1, 40, 50.00, 'Mantenimiento básico', 1),
('Spa Relax Total', 2, 80, 180.00, 'Aromaterapia + Masaje', 1),
('Hidratación Pelaje', 2, 50, 90.00, 'Mascarilla profunda', 1),
('Hospedaje 24h', 3, 1440, 90.00, 'Habitación individual', 1),
('Guardería Diurna', 3, 480, 55.00, '8 horas', 1),
('Paseo Individual 60 min', 4, 60, 40.00, 'Personalizado', 1),
('Paseo Grupal', 4, 60, 30.00, 'Con otros perritos', 1);

CREATE TABLE disponibilidad_personal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_personal BIGINT NOT NULL,
    fecha DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    activo TINYINT(1) DEFAULT 1,
    UNIQUE(id_personal, fecha, hora_inicio),
    FOREIGN KEY (id_personal) REFERENCES personal(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- TABLAS OPERATIVAS
CREATE TABLE agenda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    id_cliente BIGINT NOT NULL,
    id_mascota BIGINT NOT NULL,
    id_personal BIGINT,   
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    duracion_estimada_min INT NOT NULL,
    abono_inicial DECIMAL(10,2) DEFAULT 0.00,
    total_cita DECIMAL(10,2) DEFAULT 0.00,
    id_estado INT NOT NULL,
    id_medio_solicitud INT,
    observaciones TEXT,
    id_usuario_registro INT NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id),
    FOREIGN KEY (id_mascota) REFERENCES mascotas(id),
    FOREIGN KEY (id_personal) REFERENCES personal(id),
    FOREIGN KEY (id_estado) REFERENCES estado_cita(id),
    FOREIGN KEY (id_medio_solicitud) REFERENCES medio_solicitud(id),
    FOREIGN KEY (id_usuario_registro) REFERENCES usuarios(id),
    INDEX(fecha),
    INDEX(id_personal)
) ENGINE=InnoDB;

INSERT INTO agenda (codigo, id_cliente, id_mascota, fecha, hora, duracion_estimada_min, abono_inicial, total_cita, id_estado, id_medio_solicitud, observaciones, id_usuario_registro) VALUES
('CITA-2025-001', 1, 1, '2025-12-03', '10:00:00', 90, 50.00, 130.00, 1, 1, 'Corte clásico', 1),
('CITA-2025-002', 2, 2, '2025-12-04', '14:00:00', 80, 100.00, 180.00, 1, 2, 'Spa completo', 2);

CREATE TABLE ingresos_servicios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    id_agenda BIGINT NOT NULL,
    id_servicio INT NOT NULL,
    id_personal BIGINT NOT NULL,
    cantidad INT DEFAULT 1,
    duracion_min INT NOT NULL,
    valor_servicio DECIMAL(10,2) NOT NULL,
    adicionales VARCHAR(200),
    observaciones VARCHAR(300),
    FOREIGN KEY (id_agenda) REFERENCES agenda(id) ON DELETE CASCADE,
    FOREIGN KEY (id_servicio) REFERENCES servicios(id),
    FOREIGN KEY (id_personal) REFERENCES personal(id),
    INDEX(id_agenda),
    INDEX(id_personal)
) ENGINE=InnoDB;

INSERT INTO ingresos_servicios (codigo, id_agenda, id_servicio, id_personal, cantidad, duracion_min, valor_servicio, adicionales) VALUES
('IS-2025-001', 1, 1, 1, 1, 90, 130.00, 'Corte Golden'),
('IS-2025-002', 2, 4, 2, 1, 80, 180.00, 'Aromaterapia lavanda');

CREATE TABLE agenda_pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    id_agenda BIGINT NOT NULL,
    id_medio_pago INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_pago DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_agenda) REFERENCES agenda(id) ON DELETE CASCADE,
    FOREIGN KEY (id_medio_pago) REFERENCES medio_pago(id),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
) ENGINE=InnoDB;

INSERT INTO agenda_pagos (codigo, id_agenda, id_medio_pago, monto, id_usuario) VALUES
('PAGO-2025-001', 1, 1, 50.00, 1),
('PAGO-2025-002', 2, 2, 100.00, 2);

CREATE TABLE historia_clinica (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    id_mascota BIGINT NOT NULL,
    id_personal BIGINT,
    motivo_consulta TEXT,
    diagnostico TEXT,
    tratamiento TEXT,
    fecha DATE NOT NULL,
    hora_inicio TIME,
    hora_fin TIME,
    id_estado INT NOT NULL,
    observaciones TEXT,
    FOREIGN KEY (id_mascota) REFERENCES mascotas(id),
    FOREIGN KEY (id_personal) REFERENCES personal(id),
    FOREIGN KEY (id_estado) REFERENCES estado_historia(id)
) ENGINE=InnoDB;

CREATE TABLE historia_clinica_archivos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_historia_clinica BIGINT NOT NULL,
    id_tipo_archivo INT,
    nombre_archivo VARCHAR(200) NOT NULL,
    ruta_archivo VARCHAR(500) NOT NULL,
    descripcion VARCHAR(255),
    FOREIGN KEY (id_historia_clinica) REFERENCES historia_clinica(id) ON DELETE CASCADE,
    FOREIGN KEY (id_tipo_archivo) REFERENCES tipo_archivo(id)
) ENGINE=InnoDB;

CREATE TABLE recordatorios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_agenda BIGINT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    descripcion VARCHAR(500),
    fecha_programada DATETIME NOT NULL,
    enviado TINYINT(1) DEFAULT 0,
    fecha_envio DATETIME,
    medio VARCHAR(50),
    FOREIGN KEY (id_agenda) REFERENCES agenda(id) ON DELETE CASCADE,
    INDEX(enviado, fecha_programada)
) ENGINE=InnoDB;

INSERT INTO recordatorios (id_agenda, tipo, descripcion, fecha_programada, medio) VALUES
(1, 'PRE_CITA', 'Recordatorio 24 horas antes de la cita', DATE_ADD('2025-12-03 10:00:00', INTERVAL -1 DAY), 'LISTA'),
(2, 'PRE_CITA', 'Recordatorio 24 horas antes de la cita', DATE_ADD('2025-12-04 14:00:00', INTERVAL -1 DAY), 'LISTA');

CREATE TABLE auditoria_citas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_agenda BIGINT NOT NULL,
    accion VARCHAR(100) NOT NULL,
    usuario_id INT,
    fecha_accion DATETIME DEFAULT CURRENT_TIMESTAMP,
    detalles TEXT,
    FOREIGN KEY (id_agenda) REFERENCES agenda(id) ON DELETE CASCADE
) ENGINE=InnoDB;
