SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS creative_library DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE creative_library;

CREATE TABLE address (
  addressID int(11) NOT NULL,
  firstname varchar(50) NOT NULL,
  lastname varchar(50) NOT NULL,
  email varchar(35) NOT NULL,
  phone varchar(12) NOT NULL,
  address1 varchar(300) NOT NULL,
  address2 varchar(300) DEFAULT NULL,
  country varchar(25) NOT NULL,
  city varchar(25) NOT NULL,
  postalcode varchar(25) DEFAULT NULL,
  userID int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE books (
  bookID int(11) NOT NULL,
  imagePath varchar(250) NOT NULL,
  bookName varchar(35) NOT NULL,
  bookISBN varchar(25) NOT NULL,
  bookDescription varchar(2000) NOT NULL,
  author varchar(35) NOT NULL,
  publisher varchar(35) NOT NULL,
  quantityInStock int(80) NOT NULL,
  genre enum('Fantasy','Comedy','Horror','Romance','Adventure','Thriller','Cooking','History','Health','Science Fiction','Motivational') NOT NULL DEFAULT 'Fantasy'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO books (bookID, imagePath, bookName, bookISBN, bookDescription, author, publisher, quantityInStock, genre) VALUES
(1, 'acourtoffrostandstarlight.jpg', 'A Court of Frost and Starlight', '9781408890325', 'A gorgeously written tale as lush and romantic as it is ferocious ... Absolutely spellbinding - New York Times bestselling author Alexandra Bracken In this companion tale to the bestselling A Court of Thorns and Roses series, Feyre, Rhys and their friends are working to rebuild the Night Court and the vastly changed world beyond after the events of A Court of Wings and Ruin . But Winter Solstice is finally near, and with it a hard-earned reprieve.', 'Sarah J. Maas', 'Penguin Random House', 56, 'Fantasy'),
(2, 'shatterme.jpg', 'Shatter Me', '978-1-60309-025-5', 'This is no ordinary teenager. Juliette is a threat to The Reestablishment\'s power', 'Tahereh Mafi ', 'Penguin Random House', 49, 'Romance'),
(3, 'redqueen.jpg', 'Red Queen', '9781409150725', 'The Reds are commoners, ruled by a Silver elite in possession of god-like superpowers.', 'Victoria Aveyard', 'Harper Collins', 56, 'Fantasy'),
(4, 'courtofmistandfury.jpg', 'Court of Mist and Fury', '778-1-60309-025-4', 'Alexandra Bracken on A Court of Thorns and Roses Feyre survived Amarantha\'s clutches to return to the Spring Court - but at a steep cost. Though she now possesses the powers of the High Fae, her heart remains human, and it can\'t forget the terrible deeds she performed to save Tamlin\'s people.', 'Sarah J. Maas', 'Harper Collins', 46, 'Fantasy'),
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

CREATE TABLE loans (
  loanID int(11) NOT NULL,
  loanUserID int(11) NOT NULL,
  loanBookID int(11) NOT NULL,
  loanStarted datetime NOT NULL,
  loanEnds datetime NOT NULL,
  loanReturned datetime DEFAULT NULL,
  fees double(16,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE password_reset (
  id int(3) NOT NULL,
  ip_address varchar(50) NOT NULL,
  attempts int(1) DEFAULT NULL,
  created_at datetime DEFAULT current_timestamp(),
  timeout datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO password_reset (id, ip_address, attempts, created_at, timeout) VALUES
(10, '0:0:0:0:0:0:0:1', 3, '2020-12-10 16:59:53', '2020-12-10 17:16:44');

CREATE TABLE payment_details (
  id int(11) NOT NULL,
  userID int(11) NOT NULL,
  cardNumber blob NOT NULL,
  cardOwner blob NOT NULL,
  expirationDate blob NOT NULL,
  cardNumberSum blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO payment_details (id, userID, cardNumber, cardOwner, expirationDate, cardNumberSum) VALUES
(5, 50, 0x9c6c07113bca43456dcb51d86ed5bd4aea4aeefef569ba32910d3eb4eb3bcb05, 0x647aa075062bed1d75cd9b1ea1a851ad, 0xd477d6d948a3a300398217355be6d3b7, 0x12a9c03bfba28393f61f7d563a2982ba),
(6, 51, 0x14cc221f18d9fa6485761876e320df7d2d73604b4e7c8718e7f7a6f50fa05742, 0xff5620b3381b427618f5b8a5da90089f, 0xd140dc22c0d0322d582c38a92b1481b6, 0xb8921df93c5f4221c15d7b3e97f6efae);

CREATE TABLE security_answers (
  id int(11) NOT NULL,
  userID int(11) NOT NULL,
  questionsID int(11) NOT NULL DEFAULT 1,
  schoolAnswer varchar(200) NOT NULL,
  foodAnswer varchar(200) NOT NULL,
  placeAnswer varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO security_answers (id, userID, questionsID, schoolAnswer, foodAnswer, placeAnswer) VALUES
(16, 50, 1, '$2a$15$mZg7BjpF37SjZkETTpFV5ul3BoXcW0uIpOLcFq56/0GqnK3ArUqn2', '$2a$15$v.yLaUmlAflD0U9YDKiKsOJ.HouqHFAeDln8QHYcNh1TWbKsMnHj.', '$2a$15$lD7gQNRCP7J8E5aMhKYhbeq3hs5a8goij20ko/3p022vLjpYnYAF2'),
(17, 51, 1, '$2a$15$yMYgtq032BC/LJesDMtCx.zRWUP8CoAQ2ReUFoSweHV/MydgWgT26', '$2a$15$rc2.S.oFPniXHk.bZcOIIeuhgZm55znQTYrNA0oe3Lkcd.nWMQOLG', '$2a$15$06M8w3dtZ/niUM0Iuism6u6WqVN1Hcf3LN4NrDYAEu2aWcGBXRvmC');

CREATE TABLE security_questions (
  id int(11) NOT NULL,
  schoolSecurityQuestion varchar(150) NOT NULL,
  foodSecurityQuestion varchar(150) NOT NULL,
  placeSecurityQuestion varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO security_questions (id, schoolSecurityQuestion, foodSecurityQuestion, placeSecurityQuestion) VALUES
(1, 'What was your primary school teacher\'s name ?', 'What was the first thing your learned to cook ?', 'What is the name of your favourite restaurant ?');

CREATE TABLE users (
  userID int(11) NOT NULL,
  type enum('Admin','Member') NOT NULL DEFAULT 'Member',
  username varchar(50) NOT NULL,
  email varchar(60) NOT NULL,
  password varchar(250) NOT NULL,
  dateRegistered timestamp NOT NULL DEFAULT current_timestamp(),
  activeAccount tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO users (userID, `type`, username, email, `password`, dateRegistered, activeAccount) VALUES
(50, 'Member', 'Sam', 'samuelponik@gmail.com', '$2a$15$6h2fL635dK7xxNPQMvYmBOLyThI7RacrUGCgJOSyXu2aEmtQhXX5y', '2020-12-10 15:29:27', 1),
(51, 'Member', 'David', 'david@gmail.com', '$2a$15$75NaNshdXuWYTMIgmWJ66u4ruNLJ1z4nBdZDPZVEPC3ChQlwgLZ/.', '2020-12-10 16:54:20', 1);


ALTER TABLE address
  ADD PRIMARY KEY (addressID),
  ADD UNIQUE KEY email (email,phone),
  ADD UNIQUE KEY userID (userID),
  ADD UNIQUE KEY postalcode (postalcode);

ALTER TABLE books
  ADD PRIMARY KEY (bookID),
  ADD UNIQUE KEY book_name (bookName),
  ADD UNIQUE KEY book_isbn (bookISBN);

ALTER TABLE loans
  ADD PRIMARY KEY (loanID),
  ADD KEY fk_loan_userID (loanUserID),
  ADD KEY fk_loan_bookID (loanBookID);

ALTER TABLE password_reset
  ADD PRIMARY KEY (id),
  ADD UNIQUE KEY uc_ip_address (ip_address);

ALTER TABLE payment_details
  ADD PRIMARY KEY (id),
  ADD UNIQUE KEY userID (userID),
  ADD KEY fk_payment_userID (userID);

ALTER TABLE security_answers
  ADD PRIMARY KEY (id),
  ADD KEY fk_userID (userID),
  ADD KEY fk_questionID (questionsID);

ALTER TABLE security_questions
  ADD PRIMARY KEY (id);

ALTER TABLE users
  ADD PRIMARY KEY (userID),
  ADD UNIQUE KEY username (username),
  ADD UNIQUE KEY email (email);


ALTER TABLE address
  MODIFY addressID int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

ALTER TABLE books
  MODIFY bookID int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

ALTER TABLE loans
  MODIFY loanID int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE password_reset
  MODIFY id int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

ALTER TABLE payment_details
  MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

ALTER TABLE security_answers
  MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

ALTER TABLE security_questions
  MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE users
  MODIFY userID int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;


ALTER TABLE address
  ADD CONSTRAINT fk_address_userID FOREIGN KEY (userID) REFERENCES `users` (userID);

ALTER TABLE loans
  ADD CONSTRAINT fk_loan_bookID FOREIGN KEY (loanBookID) REFERENCES books (bookID),
  ADD CONSTRAINT fk_loan_userID FOREIGN KEY (loanUserID) REFERENCES `users` (userID);

ALTER TABLE payment_details
  ADD CONSTRAINT fk_payment_userID FOREIGN KEY (userID) REFERENCES `users` (userID);

ALTER TABLE security_answers
  ADD CONSTRAINT fk_questionID FOREIGN KEY (questionsID) REFERENCES security_questions (id),
  ADD CONSTRAINT fk_userID FOREIGN KEY (userID) REFERENCES `users` (userID);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
