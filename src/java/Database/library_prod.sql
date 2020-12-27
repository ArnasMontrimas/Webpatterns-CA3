-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 27, 2020 at 10:41 PM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library_prod`
--
CREATE DATABASE IF NOT EXISTS `library_prod` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `library_prod`;

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `imagePath` varchar(250) NOT NULL,
  `bookName` varchar(35) NOT NULL,
  `bookIsbn` varchar(25) NOT NULL,
  `bookDescription` varchar(2000) NOT NULL,
  `author` varchar(35) NOT NULL,
  `publisher` varchar(35) NOT NULL,
  `quantityInStock` int(80) NOT NULL,
  `genre` enum('Fantasy','Comedy','Horror','Romance','Adventure','Thriller','Cooking','History','Health','Science Fiction','Motivational') NOT NULL DEFAULT 'Fantasy'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `imagePath`, `bookName`, `bookIsbn`, `bookDescription`, `author`, `publisher`, `quantityInStock`, `genre`) VALUES
(1, 'acourtoffrostandstarlight.jpg', 'A Court of Frost and Starlight', '9781408890325', 'A gorgeously written tale as lush and romantic as it is ferocious ... Absolutely spellbinding - New York Times bestselling author Alexandra Bracken In this companion tale to the bestselling A Court of Thorns and Roses series, Feyre, Rhys and their friends are working to rebuild the Night Court and the vastly changed world beyond after the events of A Court of Wings and Ruin . But Winter Solstice is finally near, and with it a hard-earned reprieve.', 'Sarah J. Maas', 'Penguin Random House', 56, 'Fantasy'),
(2, 'shatterme.jpg', 'Shatter Me', '978-1-60309-025-5', 'This is no ordinary teenager. Juliette is a threat to The Reestablishment\'s power', 'Tahereh Mafi ', 'Penguin Random House', 49, 'Romance'),
(3, 'redqueen.jpg', 'Red Queen', '9781409150725', 'The Reds are commoners, ruled by a Silver elite in possession of god-like superpowers.', 'Victoria Aveyard', 'Harper Collins', 56, 'Fantasy'),
(4, 'courtofmistandfury.jpg', 'Court of Mist and Fury', '778-1-60309-025-4', 'Alexandra Bracken on A Court of Thorns and Roses Feyre survived Amarantha\'s clutches to return to the Spring Court - but at a steep cost. Though she now possesses the powers of the High Fae, her heart remains human, and it can\'t forget the terrible deeds she performed to save Tamlin\'s people.', 'Sarah J. Maas', 'Harper Collins', 45, 'Fantasy'),
(5, 'courtofwingsandruin.jpg', 'Court of Wings and Ruin', '978-1-60309-029-4', 'As war bears down upon them all, Feyre must decide who to trust amongst the dazzling and lethal High Lords and hunt for allies in unexpected places.', 'Sarah J. Maas', 'Macmillan Publishers', 15, 'Fantasy'),
(6, 'courtofthornsandroses.jpg', 'Court of Thorns and Roses', '978-1-60309-089-4', 'Dragged away from her family for the murder of a faerie, Feyre discovers that her captor, his face obscured by a jewelled mask, is hiding even more than his piercing green eyes suggest.', 'Sarah J. Maas', 'Macmillan Publishers', 0, 'Fantasy'),
(7, 'unravelme.jpg', 'Unravel Me', '678-1-60209-025-4', 'She is free from The Reestablishment, free from their plan to use her as a weapon, and free to love Adam. But Juliette will never be free from her lethal touch.', 'Tahereh Mafi ', 'HarperCollins', 48, 'Romance'),
(8, 'themidnightstar.jpg', 'The Midnight Star', '678-1-60309-925-4', 'She\'s turned her back on those who have betrayed her and achieved the ultimate revenge: victory. Her reign as the White Wolf has been a triumphant one, but the darkness within her has begun to spiral out of control, threatening to destroy everything.', 'Marie Lu', 'Macmillan Publishers', 47, 'Thriller'),
(9, 'restoreme.jpg', 'Restore Me', '678-1-60709-025-4', 'She took over Sector 45, was named Supreme Commander, and now has Warner by her side. But when tragedy strikes, she must confront the darkness that dwells both around and inside her.', 'Tahereh Mafi ', 'Penguin Random House', 9, 'Romance'),
(10, 'igniteme.jpg', 'Ignite Me', '778-1-60307-025-4', 'With Omega Point destroyed, Juliette doesn\'t know if the rebels, her friends, or even Adam are alive. But that won\'t keep her from trying to take down The Reestablishment once and for all. Now she must rely on Warner. The one person she never thought she could trust.', 'Tahereh Mafi ', 'Macmillan Publishers', 47, 'Romance'),
(11, 'theyoungelites.jpg', 'The Young Elites', '678-1-60389-025-4', 'Adelina Amouteru is a survivor of the blood fever. A decade ago, the deadly illness swept through her nation. Most of the infected perished, while many of the children who survived were left with strange markings.', 'Marie Lu', 'Penguin Random House', 7, 'Fantasy'),
(12, 'theendlessking.jpg', 'The Endless King', '678-1-67389-025-4', 'Denizen Hardwick has travelled to Daybreak, the ancestral home of the Order of the Borrowed Dark, to continue his training as a knight. But lessons have barely begun before an unexpected arrival appears with news that throws the fortress into uproar.', 'Dave Rudden', 'HarperCollins', 13, 'Fantasy'),
(17, 'knightsoftheborroweddark.jpg', 'Knights of the Borrowed Dark', '9780141356600', 'An orphan boy who discovers he is part of a secret army that protects the world from a race of shadowy monsters', 'Dave Rudden', 'Macmillian', 74, 'Fantasy'),
(18, 'heirtofire.jpg', 'Heir of Fire', '678-1-50309-025-4', 'Celaena has survived deadly contests and shattering heartbreak-but at an unspeakable cost. Now, she must travel to a new land to confront her darkest truth . . . a truth about her heritage that could change her life-and her future-forever.', 'Sarah J. Maas', 'Bloomsbury', 41, 'Fantasy'),
(22, 'crownofmidnight.jpg', 'Crown Of Midnight', '978-1-67309-025-4', 'Celaena Sardothien is the King\'s Champion, yet she is far from loyal to the crown. But secretly working against her master is no easy task. As she untangles the glass castle\'s mysteries, no one is above questioning her allegiance-not Crown Prince Dorian; not Chaol, Captain of the Guard; not even Nehemia, her friend and rebel princess.', 'Sarah J. Maas', 'Bloomsbury', 60, 'Fantasy'),
(23, 'obamaBookImage.jpg', 'Promised Land', '777-1-60309-025-4', 'Barack Obama tells the story of his improbable odyssey from young man searching for his identity to leader of the free world, describing in strikingly personal detail both his political education and the landmark moments of the first term of his historic presidency-a time of dramatic transformation and turmoil.', 'Barack Obama', 'HarperCollins', 45, 'History'),
(24, 'kingscage.jpg', 'Kings Cage', '777-1-40309-025-4', 'As Mare remains trapped in the palace, the remnants of the Red Rebellion continue organizing and expanding. As they prepare for war, no longer able to linger in the shadows, Cal - the exiled prince with his own claim on Mare\'s heart - will stop at nothing to bring her back.', 'Victoria Aveyard', 'HarperCollins', 48, 'Fantasy'),
(31, 'therosesociety.jpg', 'The Rose Society', '978-1-60709-025-4', 'Adelina Amouteru\'s heart is set on revenge. Now known and feared as the White Wolf, she and her sister flee Kenettra to find other Young Elites in the hopes of building her own army. Her goal: to strike down the Inquisition Axis, the white-cloaked soldiers who nearly killed her.', 'Marie Lu', 'Penguin Random House', 17, 'Fantasy'),
(32, 'towerofdawn.jpg', 'Tower of Dawn', '979-1-60309-089-4', 'It\'s Chaol\'s one shot at recovery, and with war looming back home, Dorian and Aelin\'s survival could depend on Chaol and Nesryn convincing Antica\'s rulers to ally with them. But what they discover there will change them both - and be more vital to saving Erilea than they could have imagined. ', 'Sarah J.Maas', 'Bloomsbury', 19, 'Fantasy'),
(33, 'skullduggerypleasant.jpg', 'Skullduggery Pleasant', '777-2-60309-025-4', 'Just when you think you\'ve saved the world. \"You will kill her?\" the Torment asked. Skulduggery sagged. \"Yes.\" He hesitated, then took his gun from his jacket. \"I\'m sorry, Valkyrie,\" he said softly. \"Don\'t talk to me,\" Valkyrie said. \"Just do what you have to do.\" Valkyrie parted her tunic, and Skulduggery pointed the gun at the vest beneath.', 'Derek Landy', 'Bloomsbury', 87, 'Adventure'),
(34, 'skullduggerybedlam.jpg', 'Skullduggery Pleasant Bedlam', '777-2-60309-725-4', 'Third bone-breaking, belly-busting adventure in the bestselling series that puts the \"funny\" back in. um. funny bestselling series. That didn\'t really work, did it? If you\'ve read the previous Skulduggery books then you know what the Faceless Ones are - and if you know what the Faceless Ones are, then you can probably take a wild guess that things in this book are going to get AWFULLY sticky for our skeletal hero', 'Derek Landy', 'Bloomsbury', 71, 'Adventure'),
(35, 'kingdomofash.jpg', 'Kingdom of Ash', '977-2-70309-725-4', 'Aelin Galathynius\'s journey from slave to assassin to queen reaches its heart-rending finale as war erupts across her world . She has risked everything to save her people - but at a tremendous cost.', 'Sarah J.Maas', 'Bloomsbury', 69, 'Adventure'),
(36, 'glasssword.jpg', 'Glass Sword', '977-2-60309-725-4', 'Mare\'s blood is red - the colour of common folk - but her Silver ability, the power to control lightning, has turned her into a weapon that the royal court wants to control.', 'Victoria Aveyard', 'Bloomsbury', 65, 'Adventure');

-- --------------------------------------------------------

--
-- Table structure for table `loans`
--

DROP TABLE IF EXISTS `loans`;
CREATE TABLE `loans` (
  `loanID` int(11) NOT NULL,
  `loanUserID` int(11) NOT NULL,
  `loanBookID` int(11) NOT NULL,
  `loanStarted` datetime NOT NULL,
  `loanEnds` datetime NOT NULL,
  `loanReturned` datetime DEFAULT NULL,
  `feesPaid` double(16,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loans`
--

INSERT INTO `loans` (`loanID`, `loanUserID`, `loanBookID`, `loanStarted`, `loanEnds`, `loanReturned`, `feesPaid`) VALUES
(20, 1, 2, '2020-12-27 20:04:22', '2021-01-03 20:04:22', '2021-02-03 00:00:00', NULL),
(21, 1, 3, '2020-12-27 20:10:16', '2021-01-03 20:10:16', NULL, NULL),
(22, 1, 1, '2020-12-27 20:14:35', '2021-01-03 20:14:35', NULL, NULL),
(23, 1, 4, '2020-12-27 20:17:04', '2021-01-03 20:17:04', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `payment_details`
--

DROP TABLE IF EXISTS `payment_details`;
CREATE TABLE `payment_details` (
  `id` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `cardNumber` blob NOT NULL,
  `cardOwner` blob NOT NULL,
  `expirationDate` blob NOT NULL,
  `cardNumberSum` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment_details`
--

INSERT INTO `payment_details` (`id`, `userID`, `cardNumber`, `cardOwner`, `expirationDate`, `cardNumberSum`) VALUES
(1, 1, 0x92d35a411324668651ceb30dbd7ad831d2d23340bed50eaace1a38c5386b5522, 0x7271836b624cb20b461c3d1740e02d44, 0xff2a73797cd92989c01d072aec71e252, 0x0aa28d6cdbc1b0c2088801274c891b69);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(320) NOT NULL,
  `password` text NOT NULL,
  `username` varchar(32) NOT NULL,
  `type` varchar(32) NOT NULL DEFAULT 'member',
  `dateRegistered` datetime NOT NULL DEFAULT current_timestamp(),
  `activeAccount` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `username`, `type`, `dateRegistered`, `activeAccount`) VALUES
(1, 'arnastest@arnastest.com', '$2a$10$gAzF.awacH9XFud55G08guFPZGyCoOJJ8GbZAFD1ePKFbCOjGkZC2', 'arnastest', 'member', '2020-12-27 19:19:47', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `book_name` (`bookName`),
  ADD UNIQUE KEY `book_isbn` (`bookIsbn`);

--
-- Indexes for table `loans`
--
ALTER TABLE `loans`
  ADD PRIMARY KEY (`loanID`),
  ADD KEY `fk_loan_userID` (`loanUserID`),
  ADD KEY `fk_loan_bookID` (`loanBookID`);

--
-- Indexes for table `payment_details`
--
ALTER TABLE `payment_details`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `userID` (`userID`),
  ADD KEY `fk_payment_userID` (`userID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT for table `loans`
--
ALTER TABLE `loans`
  MODIFY `loanID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `payment_details`
--
ALTER TABLE `payment_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
