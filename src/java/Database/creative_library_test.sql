-- phpMyAdmin SQL Dump
-- version 4.9.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 13, 2020 at 11:41 AM
-- Server version: 5.7.26
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: creative_library_test
--
CREATE DATABASE IF NOT EXISTS creative_library_test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE creative_library_test;

-- --------------------------------------------------------

--
-- Table structure for table address
--

CREATE TABLE address (
  addressID int(11) NOT NULL,
  firstname varchar(50) NOT NULL,
  lastname varchar(50) NOT NULL,
  phone varchar(12) NOT NULL,
  address1 varchar(300) NOT NULL,
  address2 varchar(300) DEFAULT NULL,
  city varchar(25) NOT NULL,
  state varchar(25) DEFAULT NULL,
  country varchar(25) NOT NULL,
  postalcode varchar(25) NOT NULL,
  userID int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table password_reset
--

CREATE TABLE password_reset (
  id int(3) NOT NULL,
  ip_address varchar(50) NOT NULL,
  attempts int(1) DEFAULT NULL,
  created_at datetime DEFAULT CURRENT_TIMESTAMP,
  timeout datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table payment_details
--

CREATE TABLE payment_details (
  id int(11) NOT NULL,
  userID int(11) NOT NULL,
  cardNumber blob NOT NULL,
  cardOwner blob NOT NULL,
  expirationDate blob NOT NULL,
  cardNumberSum blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table payment_details
--

INSERT INTO payment_details (id, userID, cardNumber, cardOwner, expirationDate, cardNumberSum) VALUES
(3, 39, 0xe3af04a87c9442d74303d447b35117357d4363ab521bcdfdb0f3d88f287d5cda, 0x3dc5c578d795ac178356d9930421703f, 0x040315a05e0eaa7405cb6f017faada78, 0x1d996fb1c3de5d78d8c0b66a72598024);

-- --------------------------------------------------------

--
-- Table structure for table security_answers
--

CREATE TABLE security_answers (
  id int(11) NOT NULL,
  userID int(11) NOT NULL,
  questionsID int(11) NOT NULL DEFAULT '1',
  schoolAnswer varchar(200) NOT NULL,
  foodAnswer varchar(200) NOT NULL,
  placeAnswer varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table security_answers
--

INSERT INTO security_answers (id, userID, questionsID, schoolAnswer, foodAnswer, placeAnswer) VALUES
(5, 39, 1, '$2a$15$dJxhK1Y/ez0JONVUlxDztuuop03fmqm1NiyMc.3Dp4mzia2LrewW.', '$2a$15$Co7gGFJ3b//0ok/DKkl1hOegcF4sTqVRNq/DlEmin5SmquAy3U5kW', '$2a$15$Hd7rp8e71AsxBTB31Brp1uMWqeNoym1me6DnoRpNZf9tMN0llFEk2'),
(6, 40, 1, '$2a$15$.7dzAenpqmmoXTpsFjUwSeKsGFcs0S0e/UEWgK4JXtc4uFNXA44lm', '$2a$15$e6bcnNxHyt1mf54aGd8u5eEk/IRMXez9rxHoTr6ezg3eY5wdD8l8O', '$2a$15$sIthXGhL7LAgg3E.hk.Y/OpQEmQFyLm/GahsBTC4MbM8I42llBI6S'),
(18, 58, 1, '$2a$15$5FD/mo7Xbyni1AvX6BHFXeVyjGibwrfr7pH/97rQeJPiOccd8QgHK', '$2a$15$B2dIDVKZUOgo1AUpQT714eucurnzFtyiJETV/GppOMje/tf8Dhd3m', '$2a$15$CBNe.OmA.y06vcgdxM9F..fd725Yo8L71QOZN0ErLy0APWY/4zPcm');

-- --------------------------------------------------------

--
-- Table structure for table security_questions
--

CREATE TABLE security_questions (
  id int(11) NOT NULL,
  schoolSecurityQuestion varchar(150) NOT NULL,
  foodSecurityQuestion varchar(150) NOT NULL,
  placeSecurityQuestion varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table security_questions
--

INSERT INTO security_questions (id, schoolSecurityQuestion, foodSecurityQuestion, placeSecurityQuestion) VALUES
(1, 'What was your primary school teacher\'s name ?', 'What was the first thing your learned to cook ?', 'What is the name of your favourite restaurant ?');

-- --------------------------------------------------------

--
-- Table structure for table users
--

CREATE TABLE users (
  userID int(11) NOT NULL,
  type enum('Admin','Member') NOT NULL DEFAULT 'Member',
  username varchar(25) NOT NULL,
  email varchar(50) NOT NULL,
  password varchar(250) NOT NULL,
  dateRegistered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  activeAccount tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table users
--

INSERT INTO users (userID, `type`, username, email, `password`, dateRegistered, activeAccount) VALUES
(39, 'Member', 'Sam', 'sam@gmail.com', '$2a$15$wBPRaUQKrtlaCg3oKfGUf.L7xvR31LIuTB94pNsE1x9kViGsHpLsC', '2020-11-29 15:00:23', 1),
(40, 'Member', 'David', 'david@gmail.com', '$2a$15$8sdrxPl3YMypQ8NlH7and.K.ikR067zu7G3G5LNrv4I0sUIZ9F1/i', '2020-11-29 18:38:34', 0),
(57, 'Member', 'TEST_USERNAME', 'TEST_EMAIL', '$2a$15$glExUkqxSyLuXn.l.UM1r.5PXcWxxP/UXCT.3QPa3xdKJ4X1.Kc7q', '2020-12-11 11:02:22', 1),
(58, 'Member', 'TEST_USERNAME', 'TEST_EMAIL', '$2a$15$r/EPeHwQ6cmEYTUCf3aZEuh0AyrLJSYLUFSGpmb1KPnhiDd6G6L6K', '2020-12-11 11:15:38', 1),
(59, 'Member', 'TEST_USERNAME', 'TEST_EMAIL', '$2a$15$Zuzj3evQwkoSqfGnU0rNo.kGmSOPJsoGigL0tBagISsylXGmrAVmq', '2020-12-12 15:37:16', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table address
--
ALTER TABLE address
  ADD PRIMARY KEY (addressID),
  ADD KEY fk_address_userID (userID);

--
-- Indexes for table password_reset
--
ALTER TABLE password_reset
  ADD PRIMARY KEY (id),
  ADD UNIQUE KEY uc_ip_address (ip_address);

--
-- Indexes for table payment_details
--
ALTER TABLE payment_details
  ADD PRIMARY KEY (id),
  ADD KEY fk_payment_userID (userID);

--
-- Indexes for table security_answers
--
ALTER TABLE security_answers
  ADD PRIMARY KEY (id),
  ADD KEY fk_userID (userID),
  ADD KEY fk_questionID (questionsID);

--
-- Indexes for table security_questions
--
ALTER TABLE security_questions
  ADD PRIMARY KEY (id);

--
-- Indexes for table users
--
ALTER TABLE users
  ADD PRIMARY KEY (userID);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table address
--
ALTER TABLE address
  MODIFY addressID int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table password_reset
--
ALTER TABLE password_reset
  MODIFY id int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=415;

--
-- AUTO_INCREMENT for table payment_details
--
ALTER TABLE payment_details
  MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table security_answers
--
ALTER TABLE security_answers
  MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table security_questions
--
ALTER TABLE security_questions
  MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table users
--
ALTER TABLE users
  MODIFY userID int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- Constraints for dumped tables
--

--
-- Constraints for table address
--
ALTER TABLE address
  ADD CONSTRAINT fk_address_userID FOREIGN KEY (userID) REFERENCES `users` (userID);

--
-- Constraints for table payment_details
--
ALTER TABLE payment_details
  ADD CONSTRAINT fk_payment_userID FOREIGN KEY (userID) REFERENCES `users` (userID);

--
-- Constraints for table security_answers
--
ALTER TABLE security_answers
  ADD CONSTRAINT fk_questionID FOREIGN KEY (questionsID) REFERENCES security_questions (id),
  ADD CONSTRAINT fk_userID FOREIGN KEY (userID) REFERENCES `users` (userID);
