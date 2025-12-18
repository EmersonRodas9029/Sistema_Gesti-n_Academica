-- Base de datos y tablas para el proyecto
CREATE DATABASE IF NOT EXISTS lab_estructura;
USE lab_estructura;

CREATE TABLE IF NOT EXISTS empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(60),
    puesto VARCHAR(50),
    salario DECIMAL(10,2),
    nivel_academico VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS descuentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50),
    porcentaje DECIMAL(5,2)
);
