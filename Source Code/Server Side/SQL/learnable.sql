-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- 主機: localhost
-- 產生日期: 2012 年 05 月 14 日 03:23
-- 伺服器版本: 5.5.20
-- PHP 版本: 5.3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 資料庫: `learnable`
--

-- --------------------------------------------------------

--
-- 表的結構 `course`
--

CREATE TABLE IF NOT EXISTS `course` (
  `courseID` varchar(255) NOT NULL,
  `courseName` varchar(255) NOT NULL,
  `courseDesc` varchar(255) NOT NULL,
  PRIMARY KEY (`courseID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 轉存資料表中的資料 `course`
--

INSERT INTO `course` (`courseID`, `courseName`, `courseDesc`) VALUES
('android', 'Android', 'Android'),
('ios', 'IOS', 'IOS'),
('se', 'Software Engineering', 'Software Engineering');

-- --------------------------------------------------------

--
-- 表的結構 `lesson`
--

CREATE TABLE IF NOT EXISTS `lesson` (
  `lessonID` int(10) NOT NULL AUTO_INCREMENT,
  `CoursecourseID` varchar(255) NOT NULL,
  `lessonName` varchar(255) NOT NULL,
  `lessonDesc` varchar(255) NOT NULL,
  `lesson_chatRoom` varchar(255) DEFAULT NULL,
  `lesson_pic_url` varchar(255) DEFAULT NULL,
  `lesson_video_url` varchar(255) DEFAULT NULL,
  `lesson_question_url` varchar(255) DEFAULT NULL,
  `lesson_ppt_url` varchar(255) DEFAULT NULL,
  `lesson_available` int(1) NOT NULL,
  `lesson_time` int(8) NOT NULL,
  PRIMARY KEY (`lessonID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- 轉存資料表中的資料 `lesson`
--

INSERT INTO `lesson` (`lessonID`, `CoursecourseID`, `lessonName`, `lessonDesc`, `lesson_chatRoom`, `lesson_pic_url`, `lesson_video_url`, `lesson_question_url`, `lesson_ppt_url`, `lesson_available`, `lesson_time`) VALUES
(1, 'se', 'SE Lesson1', 'Software Engineering Lesson One', '1', '1', '1', '', '1', 0, 11111111),
(2, 'se', 'SE Lesson2', 'Software Engineering Lesson Two', '2', NULL, '2', '', NULL, 2, 111111111),
(3, 'android', 'Android Lesson 1', 'Android Lesson 1', NULL, NULL, NULL, NULL, NULL, 2, 11111111);

-- --------------------------------------------------------

--
-- 表的結構 `lesson_memeber`
--

CREATE TABLE IF NOT EXISTS `lesson_memeber` (
  `MemberuserID` varchar(255) NOT NULL,
  `LessonlessonID` int(10) NOT NULL,
  `loginTime` int(10) DEFAULT NULL,
  `logoutTime` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 轉存資料表中的資料 `lesson_memeber`
--

INSERT INTO `lesson_memeber` (`MemberuserID`, `LessonlessonID`, `loginTime`, `logoutTime`) VALUES
('student1', 1, 1336829339, 1336830573),
('student1', 2, 1336930321, 1336930331),
('student1', 3, 1336895975, 1336896021),
('student2', 1, NULL, NULL),
('student2', 2, 1336844977, 1336845054);

-- --------------------------------------------------------

--
-- 表的結構 `member`
--

CREATE TABLE IF NOT EXISTS `member` (
  `userID` varchar(255) NOT NULL,
  `userPassWord` varchar(255) NOT NULL,
  `userName` varchar(255) NOT NULL,
  `userSession` varchar(255) NOT NULL,
  `userPosition` varchar(255) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 轉存資料表中的資料 `member`
--

INSERT INTO `member` (`userID`, `userPassWord`, `userName`, `userSession`, `userPosition`) VALUES
('student1', 'student1', 'Student1', 'e1eg7ma7trdns18eslicgjkd76', 'student'),
('student2', 'student2', 'student2', 'mth2e1a8tiqk41q4agk7gm8fg7', 'student'),
('teacher', 'teacher', 'Teacher', '6rjc24ackalcpq1ajlrp4fvtg4', 'teacher'),
('teacher2', 'teacher2', 'teacher2', 'k3aos3abnlj44i8q27c1hn0i01', 'teacher');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
