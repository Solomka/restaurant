-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 14, 2020 at 02:28 
-- Server version: 5.1.41
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `restaurant`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `id_category` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id_category`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id_category`, `name`) VALUES
(1, 'meat'),
(2, 'dessert'),
(3, 'main dishes'),
(4, 'first dishes'),
(5, 'snack');

-- --------------------------------------------------------

--
-- Table structure for table `dish`
--

CREATE TABLE IF NOT EXISTS `dish` (
  `id_dish` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  `weight` double NOT NULL,
  `cost` decimal(13,2) NOT NULL,
  `id_category` int(11) NOT NULL,
  PRIMARY KEY (`id_dish`),
  KEY `id_category` (`id_category`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `dish`
--

INSERT INTO `dish` (`id_dish`, `name`, `description`, `weight`, `cost`, `id_category`) VALUES
(1, 'fried pork ', 'fried pork with vegetables', 120, '150.00', 1),
(2, 'baked chicken', 'baked chiken wirh oranges', 120, '130.00', 1),
(4, 'turkey', 'delicious turkey', 120.5, '150.20', 1),
(5, 'cheesecake', 'delicious dessert', 120, '80.00', 2),
(6, 'Zaher cake', 'chocolate cake', 125, '90.00', 2),
(7, 'vareniky', 'with cheese and potato', 200, '120.00', 3),
(8, 'buckwheat', 'with vegetables', 210, '60.00', 3),
(9, 'borscht', 'with pork', 250, '130.00', 4),
(10, 'bouillon', 'with chicken', 220, '150.00', 4),
(11, 'chocolate ice cream', 'delicious', 180, '90.00', 2),
(12, 'chips', 'with cheese taste', 200, '95.99', 5);

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE IF NOT EXISTS `order` (
  `id_order` int(11) NOT NULL AUTO_INCREMENT,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(100) NOT NULL,
  `total` decimal(13,2) NOT NULL,
  `id_user` int(11) NOT NULL,
  PRIMARY KEY (`id_order`),
  KEY `id_user` (`id_user`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`id_order`, `date`, `status`, `total`, `id_user`) VALUES
(1, '2020-05-12 12:03:09', 'paid', '150.00', 1),
(2, '2020-05-12 12:07:02', 'paid', '130.00', 1),
(3, '2020-05-12 12:08:02', 'paid', '150.00', 2),
(4, '2020-05-25 13:18:35', 'new', '80.00', 1),
(5, '2020-05-25 13:20:58', 'new', '120.00', 2),
(6, '2020-05-25 13:22:45', 'in progress', '250.00', 1),
(7, '2020-05-25 16:59:26', 'prepared', '250.00', 1),
(8, '2020-05-25 16:27:16', 'prepared', '60.00', 1),
(10, '2020-05-25 17:00:08', 'new', '220.00', 1),
(11, '2020-05-26 18:22:18', 'paid', '210.00', 1),
(12, '2020-07-14 13:13:54', 'in progress', '300.00', 1),
(13, '2020-07-14 13:16:28', 'paid', '280.20', 1),
(14, '2020-07-14 13:13:01', 'prepared', '90.00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `order_item`
--

CREATE TABLE IF NOT EXISTS `order_item` (
  `id_order` int(11) NOT NULL,
  `id_dish` int(11) NOT NULL,
  KEY `id_order` (`id_order`),
  KEY `id_dish` (`id_dish`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order_item`
--

INSERT INTO `order_item` (`id_order`, `id_dish`) VALUES
(1, 1),
(2, 2),
(3, 1),
(4, 5),
(5, 7),
(6, 9),
(6, 7),
(7, 7),
(7, 10),
(8, 8),
(10, 6),
(10, 9),
(11, 5),
(11, 9),
(12, 5),
(12, 6),
(12, 9),
(13, 2),
(13, 4),
(14, 6);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `role` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(64) NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `name`, `surname`, `address`, `phone`, `role`, `email`, `password`) VALUES
(1, 'vika', 'pytlyk', 'klonovicha 12, app 7', '0957987664', 'waiter', 'pytlyk@gmail.com', 'pytlyk777'),
(2, 'mariana', 'leskiv', 'klonovicha 10, app 11', '0987654321', 'waiter', 'leskiv@gmail.com', 'leskiv777'),
(3, 'olexandr', 'chaika', 'klonovicha 7, app 7', '0987650987', 'manager', 'chaika@gmail.com', 'chaika777'),
(4, 'zenovii', 'gordiichuk', 'klonovicha 3, app 3', '0123457885', 'chief', 'gordiichuk@gmail.com', 'gordiichuk777'),
(5, 'sofiya', 'chaban', 'bla str., app. 34', '0986789558', 'manager', 'chaban@gmail.com', 'chaban777');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `dish`
--
ALTER TABLE `dish`
  ADD CONSTRAINT `dish_ibfk_1` FOREIGN KEY (`id_category`) REFERENCES `category` (`id_category`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `order_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `order_item`
--
ALTER TABLE `order_item`
  ADD CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`id_order`) REFERENCES `order` (`id_order`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`id_dish`) REFERENCES `dish` (`id_dish`) ON DELETE NO ACTION ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
