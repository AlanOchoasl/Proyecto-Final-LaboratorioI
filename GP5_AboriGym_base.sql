-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-10-2026 a las 07:02:47
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `aborigym`
--
CREATE DATABASE IF NOT EXISTS `aborigym` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `aborigym`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE `asistencia` (
  `id_Asistencia` int(11) NOT NULL,
  `id_Socio` int(11) NOT NULL,
  `id_Clase` int(11) NOT NULL,
  `fecha_Asistencia` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clasegym`
--

CREATE TABLE `clasegym` (
  `id_Clase` int(11) NOT NULL,
  `Disciplina` varchar(50) NOT NULL,
  `id_Entrenador` int(11) NOT NULL,
  `Horario` time NOT NULL,
  `Capacidad` int(11) NOT NULL,
  `Estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entrenador`
--

CREATE TABLE `entrenador` (
  `id_Entrenador` int(11) NOT NULL,
  `Dni` varchar(30) NOT NULL,
  `Nombre` varchar(40) NOT NULL,
  `Apellido` varchar(40) NOT NULL,
  `Especialidad` varchar(40) NOT NULL,
  `Estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pase`
--

CREATE TABLE `pase` (
  `id_Pase` int(11) NOT NULL,
  `id_Socio` int(11) NOT NULL,
  `cantidadEntradas` int(11) NOT NULL,
  `fecha_Inicio` date NOT NULL,
  `fecha_Fin` date NOT NULL,
  `costo` decimal(10,0) NOT NULL,
  `Estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `socio`
--

CREATE TABLE `socio` (
  `id_Socio` int(11) NOT NULL,
  `dni` varchar(30) NOT NULL,
  `Nombre` varchar(40) NOT NULL,
  `Apellido` varchar(40) NOT NULL,
  `Edad` int(11) NOT NULL,
  `Correo` varchar(40) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `Estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD PRIMARY KEY (`id_Asistencia`),
  ADD KEY `FKSocio` (`id_Socio`),
  ADD KEY `FKClase` (`id_Clase`);

--
-- Indices de la tabla `clasegym`
--
ALTER TABLE `clasegym`
  ADD PRIMARY KEY (`id_Clase`),
  ADD KEY `FKEntrenador` (`id_Entrenador`);

--
-- Indices de la tabla `entrenador`
--
ALTER TABLE `entrenador`
  ADD PRIMARY KEY (`id_Entrenador`),
  ADD UNIQUE KEY `dni` (`Dni`);

--
-- Indices de la tabla `pase`
--
ALTER TABLE `pase`
  ADD PRIMARY KEY (`id_Pase`),
  ADD KEY `FKSocio` (`id_Socio`);

--
-- Indices de la tabla `socio`
--
ALTER TABLE `socio`
  ADD PRIMARY KEY (`id_Socio`),
  ADD UNIQUE KEY `dni` (`dni`),
  ADD UNIQUE KEY `correo` (`Correo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `id_Asistencia` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `clasegym`
--
ALTER TABLE `clasegym`
  MODIFY `id_Clase` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `entrenador`
--
ALTER TABLE `entrenador`
  MODIFY `id_Entrenador` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pase`
--
ALTER TABLE `pase`
  MODIFY `id_Pase` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `socio`
--
ALTER TABLE `socio`
  MODIFY `id_Socio` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD CONSTRAINT `fk_asist_clase` FOREIGN KEY (`id_Clase`) REFERENCES `clasegym` (`id_Clase`),
  ADD CONSTRAINT `fk_asist_socio` FOREIGN KEY (`id_Socio`) REFERENCES `socio` (`id_Socio`);

--
-- Filtros para la tabla `clasegym`
--
ALTER TABLE `clasegym`
  ADD CONSTRAINT `fk_clase_entrenador` FOREIGN KEY (`id_Entrenador`) REFERENCES `entrenador` (`id_Entrenador`);

--
-- Filtros para la tabla `pase`
--
ALTER TABLE `pase`
  ADD CONSTRAINT `fk_pase_socio` FOREIGN KEY (`id_Socio`) REFERENCES `socio` (`id_Socio`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
