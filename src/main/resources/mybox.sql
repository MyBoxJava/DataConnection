-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-02-2016 a las 19:56:39
-- Versión del servidor: 10.1.9-MariaDB
-- Versión de PHP: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `mybox`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `check_user_email` (IN `pemail` VARCHAR(100))  BEGIN


select count(*) = 0 from `users` where `users`.email = pemail;


END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `files_by_user` (IN `pemail` VARCHAR(100), IN `ppassword` VARCHAR(512))  BEGIN
SELECT
  f.*
FROM
  mybox.users AS u,
  mybox.users_files AS uf,
  mybox.files AS f
WHERE
  u.id = uf.user_id AND uf.file_id = f.id AND u.email = pemail AND u.password = encrypt_password(ppassword, salt);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `login_user` (IN `pemail` VARCHAR(100), IN `ppassword` VARCHAR(512))  BEGIN
DECLARE recovered_salt VARCHAR(512) DEFAULT 'EMPTY';
DECLARE full_password VARCHAR(512);
DECLARE inserted BOOL;


-- SELECT salt INTO recovered_salt FROM users WHERE email = pemail AND password = SHA2(CONCAT(SHA2(ppassword,512),salt),512);
SELECT salt INTO recovered_salt FROM users WHERE email = pemail AND password = encrypt_password(ppassword, salt);
select recovered_salt != 'EMPTY' INTO inserted;

IF inserted THEN
SELECT TRUE AS RESULT;
ELSE
SELECT FALSE AS RESULT;
END IF;


END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sing_in_user` (IN `pnombre` VARCHAR(50) CHARSET utf8, IN `pemail` VARCHAR(100) CHARSET utf8, IN `ppassword` VARCHAR(512) CHARSET utf8)  BEGIN
DECLARE used BOOL;
DECLARE full_password VARCHAR(512);
DECLARE auto_salt VARCHAR(512);

SELECT count(*) != 0 INTO used FROM `users` WHERE `users`.`email` = `pemail`;

IF used THEN
	
	SELECT FALSE as RESULT;
	
ELSE

	-- Dinamically create the salt
	SELECT SHA2(UNIX_TIMESTAMP(),512) INTO auto_salt;

	-- Concatenate and hash the re-hashed password and the salt
-- SELECT SHA2(CONCAT(SHA2(ppassword,512), auto_salt),512) INTO full_password;
SELECT encrypt_password(ppassword, auto_salt) INTO full_password;

	
	-- Insert in the database
	INSERT INTO `mybox`.`users` (`username`, `email`, `password`, `salt`) VALUES (pnombre, pemail, full_password, auto_salt);
	
	SELECT TRUE as RESULT;

END IF;

END$$

--
-- Funciones
--
CREATE DEFINER=`root`@`localhost` FUNCTION `encrypt_password` (`ppassword` VARCHAR(512) CHARSET utf8, `salt` VARCHAR(512) CHARSET utf8) RETURNS VARCHAR(512) CHARSET utf8 RETURN CONCAT(SHA2(CONCAT(SHA2(ppassword,512), salt),512),'')$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `files`
--

CREATE TABLE `files` (
  `id` int(11) NOT NULL,
  `filename` varchar(128) NOT NULL COMMENT 'the file name',
  `filehash` varchar(512) NOT NULL COMMENT 'the hash of the file',
  `filedate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'the file of the update of the file',
  `filepath` varchar(512) NOT NULL COMMENT 'the paht to the file'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL COMMENT 'the id of the user',
  `username` varchar(50) NOT NULL COMMENT 'name in the app for the user',
  `email` varchar(100) NOT NULL COMMENT 'email of the user',
  `password` varchar(512) NOT NULL COMMENT 'hahed combination of the hashed password and hashed salt',
  `salt` varchar(512) NOT NULL COMMENT 'hashed random stuff'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users_files`
--

CREATE TABLE `users_files` (
  `user_id` int(11) NOT NULL,
  `file_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `files`
--
ALTER TABLE `files`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `files`
--
ALTER TABLE `files`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'the id of the user', AUTO_INCREMENT=13;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
