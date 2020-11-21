-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 28, 2020 at 08:13 AM
-- Server version: 5.5.24-log
-- PHP Version: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `gestioncabinet`
--

-- --------------------------------------------------------

--
-- Table structure for table `facture`
--

CREATE TABLE IF NOT EXISTS `facture` (
  `id_facture` int(11) NOT NULL AUTO_INCREMENT,
  `date_facture` varchar(10) NOT NULL,
  `montant` double NOT NULL,
  `id_patient` int(11) NOT NULL,
  PRIMARY KEY (`id_facture`),
  KEY `id_patient` (`id_patient`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `facture`
--

INSERT INTO `facture` (`id_facture`, `date_facture`, `montant`, `id_patient`) VALUES
(1, '2020/06/17', 2500, 1),
(2, '2020/06/18', 3000, 1),
(3, '2020/06/19', 1090, 4),
(4, '2020/06/19', 1000, 4),
(5, '2020/06/19', 1000, 4),
(6, '2020/06/19', 2000, 11),
(7, '2020/06/19', 1000, 11),
(8, '2020/06/19', 1000, 11),
(9, '2020/07/08', 1200, 1),
(10, '2020/07/08', 1234, 1),
(11, '2020/07/08', 23412, 1);

-- --------------------------------------------------------

--
-- Table structure for table `ordonnance`
--

CREATE TABLE IF NOT EXISTS `ordonnance` (
  `id_ordonnance` int(11) NOT NULL AUTO_INCREMENT,
  `conjeMaladie` int(11) NOT NULL,
  `traitement` text NOT NULL,
  `date_ord` varchar(10) NOT NULL,
  `id_patient` int(11) NOT NULL,
  PRIMARY KEY (`id_ordonnance`),
  KEY `id_patient` (`id_patient`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `ordonnance`
--

INSERT INTO `ordonnance` (`id_ordonnance`, `conjeMaladie`, `traitement`, `date_ord`, `id_patient`) VALUES
(1, 0, '', '2020/06/17', 1),
(2, 1, 'asdfasdf', '2020/06/18', 1),
(3, 0, 'vitamine c', '2020/06/19', 4),
(4, 3, 'paralgon', '2020/06/19', 11),
(5, 5, 'vitamine', '2020/06/19', 11),
(6, 2, 'asdfasdf\nsadfasdf\nAnalyses Demande :\nasdfasdf\nsadfsaf', '2020/07/08', 1),
(7, 1, '\nAnalyses Demande :\nzzzzzzzzzzz', '2020/07/08', 1),
(8, 0, '\nAnalyses Demande :\nzzzzzzzzzz', '2020/07/08', 1);

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE IF NOT EXISTS `patient` (
  `id_patient` int(11) NOT NULL AUTO_INCREMENT,
  `date_naiss` varchar(10) NOT NULL,
  `groupage` varchar(3) NOT NULL,
  `maladeChro` tinyint(1) NOT NULL,
  `malades` text NOT NULL,
  `nom` varchar(25) NOT NULL,
  `prenom` varchar(25) NOT NULL,
  `tel` varchar(15) NOT NULL,
  `adr` varchar(50) NOT NULL,
  `sexe` varchar(2) NOT NULL,
  `date_inscp` varchar(10) NOT NULL,
  `email` varchar(50) NOT NULL,
  `taille` double NOT NULL,
  `poids` double NOT NULL,
  `tension` double NOT NULL,
  `consultation` text NOT NULL,
  `analyse` text NOT NULL,
  PRIMARY KEY (`id_patient`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`id_patient`, `date_naiss`, `groupage`, `maladeChro`, `malades`, `nom`, `prenom`, `tel`, `adr`, `sexe`, `date_inscp`, `email`, `taille`, `poids`, `tension`, `consultation`, `analyse`) VALUES
(1, '1995/02/14', 'O+', 1, 'zzzzzzzzzzzz', 'hello', 'good', '12345678912', '', 'H', '2020/06/16', '', 1, 1, 1, 'benrabah ,sp ,2020/06/18\n------------------------------------------\nadfasdf\n****************************************************\nbenrabah ,sp ,2020/07/08\n------------------------------------------\n* Malades Diagnostiques :\nasdfasdf\n* Observation :\nasdfasdfsadf\nasdfsad\nsdafsdaf\n* Analyses Demande :\nasdfasdf\nsadfsaf\n* Conje Maladie : 2\n****************************************************\nbenrabah ,sp ,2020/07/08\n------------------------------------------\n* Malades Diagnostiques :\n\n* Observation :\n\n* Analyses Demande :\nzzzzzzzzzz\n* Conje Maladie : 0\n****************************************************\n', 'zzzzzzzzzz'),
(2, '1998/05/24', 'B-', 0, '', 'hello', 'good', '98765432198', '', 'F', '2020/06/16', '', 0, 0, 0, '', ''),
(3, '2020/06/18', 'AB+', 0, '', 'hello', 'good', '123412341234', '', 'H', '2020/06/18', '', 2, 2, 2, '', ''),
(4, '1980/03/01', 'O+', 1, 'vih', 'silas', 'park', '0765487643', '', 'H', '2002/06/19', 'silaspar@gmail.com', 1.7, 67, 11, 'hamza ,cardiologe ,2020/06/19\n------------------------------------------\nazez\n****************************************************\nhamza ,cardiologe ,2020/06/19\n------------------------------------------\nAZEZ\n****************************************************\nhamza ,cardiologe ,2020/06/19\n------------------------------------------\nAZEZ\n****************************************************\n', ''),
(5, '1999/09/23', 'O+', 0, '', 'silas', 'park', '0646435634', 'alger', 'H', '2020/06/19', '', 1.7, 76, 12, '', ''),
(6, '1999/09/23', 'A-', 1, 'diabete', 'carmelo', 'joyce', '0564687567', '', 'H', '2020/06/19', 'carmeljoy@gmail.com', 1.72, 67, 11, '', ''),
(7, '2000/03/12', 'B-', 0, '', 'carty', 'white', '0764312422', '', 'F', '2020/06/19', '', 1.67, 61, 11, '', ''),
(8, '2000/03/02', 'A-', 1, 'vih', 'rodrich', 'stanter', '0743562623', 'tipaza', 'H', '2020/06/19', '', 1.73, 56, 11, '', ''),
(9, '1998/10/03', 'O-', 1, 'canseres', 'justin', 'salas', '0743678964', 'zougala', 'F', '2020/06/19', 'justinsal@gmail.com', 1.78, 65, 11, '', ''),
(10, '1970/07/04', 'O+', 0, '', 'hadey', 'hawe', '0765432143', 'chlef', 'H', '2020/06/19', 'hadeyhaw@outlook.com', 1.67, 66, 11, '', ''),
(11, '1991/05/16', 'O+', 1, 'canceres', 'maria', 'morris', '0556888754', '', 'H', '2020/06/19', 'mariamo@yahoo.com', 1.78, 74.6, 11, 'hamza ,cardiologe ,2020/06/19\n------------------------------------------\ndaju\n****************************************************\nhamza ,cardiologe ,2020/06/19\n------------------------------------------\ndaju\n****************************************************\nhamza ,cardiologe ,2020/06/19\n------------------------------------------\nDaju\n****************************************************\n', ''),
(12, '1995/11/30', 'B+', 0, '', 'justin', 'salas', '0765432167', 'paris', 'H', '2020/06/19', 'jussal@gmail.Com', 1.6, 65, 12, '', ''),
(13, '1995/11/30', 'A-', 0, '', 'noelle', 'wlster', '0667543675', 'miliana', 'F', '2020/06/19', 'noellxwll@gmail.com', 1.78, 66, 11, '', ''),
(14, '1995/11/10', 'B-', 1, 'vih', 'sarrian', 'mcdowell', '0754357775', '', 'H', '2020/06/19', '', 1.67, 63, 10, '', ''),
(15, '1990/08/29', 'B-', 1, 'diabete', 'ezkirel', 'hley', '0765489762', 'ain defla', 'F', '2020/06/19', 'ezkirelhk@yahoo.com', 1.56, 59, 11, '', ''),
(16, '2020/07/05', 'O+', 0, '', 'gg', 'gg', '1231241341241', '', 'H', '2020/07/08', 'fadsffa@', 2, 23, 2, '', ''),
(17, '2020/07/08', 'O+', 0, '', 'hhhhh', 'hhhhh', '1234123412', '', 'H', '2020/07/08', '', 2, 2, 2, '', ''),
(18, '2020/07/08', 'O+', 1, 'asdf', 'len', 'len', '1234123514', '', 'H', '2020/07/08', '', 2, 2, 2, '', ''),
(19, '2020/07/08', 'O+', 1, 'asfdasdfsadf', 'tel', 'tel', '1234514321', 'asfdasdfasf', 'H', '2020/07/08', '', 2, 2, 2, '', '');

-- --------------------------------------------------------

--
-- Table structure for table `rdv`
--

CREATE TABLE IF NOT EXISTS `rdv` (
  `id_rdv` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(10) NOT NULL,
  `heure` varchar(5) NOT NULL,
  `nomMedecin` varchar(50) NOT NULL,
  `inscrit` tinyint(1) NOT NULL,
  `id_patient` int(11) NOT NULL,
  PRIMARY KEY (`id_rdv`),
  KEY `id_patient` (`id_patient`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `rdv`
--

INSERT INTO `rdv` (`id_rdv`, `date`, `heure`, `nomMedecin`, `inscrit`, `id_patient`) VALUES
(1, '2020/06/18', '15:00', 'benrabah mohamed SP', 1, 1),
(2, '2020/07/07', '09:00', 'benrabah djillali DENTISTE', 1, 4),
(3, '2020/07/05', '08:30', 'hamza boul CARDIOLOGE', 1, 4),
(4, '2020/07/09', '09:00', 'boujel hamid ', 1, 4),
(5, '2020/06/25', '08:15', 'benrabah djillali DENTISTE', 1, 5),
(6, '2020/06/27', '08:45', 'hamza boul CARDIOLOGE', 1, 5),
(7, '2020/06/28', '10:00', 'hamza boul CARDIOLOGE', 1, 5),
(8, '2020/06/30', '12:00', 'boujel hamid ', 1, 5),
(9, '2020/06/20', '08:00', 'boujel hamid ', 1, 11),
(10, '2020/06/24', '14:00', 'benrabah djillali DENTISTE', 1, 11),
(11, '2020/06/30', '11:45', 'hamza boul CARDIOLOGE', 1, 11),
(12, '2020/06/27', '08:00', 'benrabah mohamed SP', 1, 1),
(13, '2020/06/27', '11:00', 'benrabah mohamed SP', 1, 1),
(15, '2020/07/08', '19:00', 'benrabah djillali DENTISTE', 1, 1),
(16, '2020/07/08', '19:45', 'benrabah djillali DENTISTE', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id_utilisateur` int(11) NOT NULL AUTO_INCREMENT,
  `nomUser` varchar(60) NOT NULL,
  `mdp` varchar(30) NOT NULL,
  `nom` varchar(25) NOT NULL,
  `prenom` varchar(25) NOT NULL,
  `tel` varchar(15) NOT NULL,
  `adr` varchar(50) NOT NULL,
  `sexe` varchar(2) NOT NULL,
  `date_inscrit` varchar(10) NOT NULL,
  `medecin` tinyint(1) NOT NULL,
  `email` varchar(50) NOT NULL,
  `specialite` varchar(50) NOT NULL,
  PRIMARY KEY (`id_utilisateur`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`id_utilisateur`, `nomUser`, `mdp`, `nom`, `prenom`, `tel`, `adr`, `sexe`, `date_inscrit`, `medecin`, `email`, `specialite`) VALUES
(2, 'benrabah.mohamed', '1234', 'benrabah', 'mohamed', '0697879921', 'miliana', 'H', '2020/06/15', 1, 'benrabahmohamed530@gmail.com', 'sp'),
(4, 'hamoudi.youba', '12345678', 'hamoudi', 'youba', '07987023123', 'setif', 'H', '2020/57/18', 1, 'youbahamoudi@gamil.com', ''),
(6, 'baaziz.adel', 'qweqweqwe', 'baaziz', 'adel', '0697342133', 'bouira', 'H', '2020/12/18', 0, 'baazizadel23@yahoo.com', ''),
(7, 'benrabah.djillali', '12345678a', 'benrabah', 'djillali', '0654543545', 'khmis', 'H', '2020/00/15', 1, 'benrabahdjillali@gmail.com', 'dentiste'),
(8, 'hamza.boul', 'azazaz11', 'hamza', 'boul', '0765489765', 'boumerdas', 'F', '2020/00/15', 1, 'hamzabou@gmail.com', 'cardiologe'),
(9, 'boujel.hamid', '123456AZQS', 'boujel', 'hamid', '0554532167', 'miliana', 'H', '2020/00/15', 1, 'boujelgh@gmail.com', ''),
(10, 'samira.mira', '123456AZE', 'samira', 'mira', '0775467858', 'miliana', 'F', '2020/00/15', 0, 'samirami@yaho.com', ''),
(11, 'kader.hacen', 'azerty123', 'kader', 'hacen', '0734534534', '', 'H', '2020/00/15', 0, 'kaderka@outlook.com', '');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `facture_ibfk_1` FOREIGN KEY (`id_patient`) REFERENCES `patient` (`id_patient`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ordonnance`
--
ALTER TABLE `ordonnance`
  ADD CONSTRAINT `ordonnance_ibfk_1` FOREIGN KEY (`id_patient`) REFERENCES `patient` (`id_patient`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `rdv`
--
ALTER TABLE `rdv`
  ADD CONSTRAINT `rdv_ibfk_1` FOREIGN KEY (`id_patient`) REFERENCES `patient` (`id_patient`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
