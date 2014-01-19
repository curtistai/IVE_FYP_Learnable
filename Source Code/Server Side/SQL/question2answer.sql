-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- 主機: localhost
-- 產生日期: 2012 年 05 月 14 日 03:24
-- 伺服器版本: 5.5.20
-- PHP 版本: 5.3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 資料庫: `question2answer`
--

-- --------------------------------------------------------

--
-- 表的結構 `qa_blobs`
--

CREATE TABLE IF NOT EXISTS `qa_blobs` (
  `blobid` bigint(20) unsigned NOT NULL,
  `format` varchar(20) CHARACTER SET ascii NOT NULL,
  `content` mediumblob NOT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `userid` int(10) unsigned DEFAULT NULL,
  `cookieid` bigint(20) unsigned DEFAULT NULL,
  `createip` int(10) unsigned DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`blobid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `qa_cache`
--

CREATE TABLE IF NOT EXISTS `qa_cache` (
  `type` char(8) CHARACTER SET ascii NOT NULL,
  `cacheid` bigint(20) unsigned NOT NULL DEFAULT '0',
  `content` mediumblob NOT NULL,
  `created` datetime NOT NULL,
  `lastread` datetime NOT NULL,
  PRIMARY KEY (`type`,`cacheid`),
  KEY `lastread` (`lastread`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `qa_categories`
--

CREATE TABLE IF NOT EXISTS `qa_categories` (
  `categoryid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parentid` int(10) unsigned DEFAULT NULL,
  `title` varchar(80) NOT NULL,
  `tags` varchar(200) NOT NULL,
  `content` varchar(800) NOT NULL DEFAULT '',
  `qcount` int(10) unsigned NOT NULL DEFAULT '0',
  `position` smallint(5) unsigned NOT NULL,
  `backpath` varchar(804) NOT NULL DEFAULT '',
  PRIMARY KEY (`categoryid`),
  UNIQUE KEY `parentid` (`parentid`,`tags`),
  UNIQUE KEY `parentid_2` (`parentid`,`position`),
  KEY `backpath` (`backpath`(200))
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- 轉存資料表中的資料 `qa_categories`
--

INSERT INTO `qa_categories` (`categoryid`, `parentid`, `title`, `tags`, `content`, `qcount`, `position`, `backpath`) VALUES
(1, NULL, 'SE3A - Advance Software Engineering', 'se3a-advance-software-engineering', '', 0, 1, 'se3a-advance-software-engineering'),
(2, NULL, 'Android', 'android', 'Android', 1, 2, 'android');

-- --------------------------------------------------------

--
-- 表的結構 `qa_categorymetas`
--

CREATE TABLE IF NOT EXISTS `qa_categorymetas` (
  `categoryid` int(10) unsigned NOT NULL,
  `title` varchar(40) NOT NULL,
  `content` varchar(8000) NOT NULL,
  PRIMARY KEY (`categoryid`,`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `qa_contentwords`
--

CREATE TABLE IF NOT EXISTS `qa_contentwords` (
  `postid` int(10) unsigned NOT NULL,
  `wordid` int(10) unsigned NOT NULL,
  `count` tinyint(3) unsigned NOT NULL,
  `type` enum('Q','A','C','NOTE') NOT NULL,
  `questionid` int(10) unsigned NOT NULL,
  KEY `postid` (`postid`),
  KEY `wordid` (`wordid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 轉存資料表中的資料 `qa_contentwords`
--

INSERT INTO `qa_contentwords` (`postid`, `wordid`, `count`, `type`, `questionid`) VALUES
(3, 13, 1, 'Q', 3),
(3, 14, 1, 'Q', 3),
(3, 15, 1, 'Q', 3),
(3, 7, 2, 'Q', 3),
(3, 8, 1, 'Q', 3),
(3, 9, 1, 'Q', 3),
(3, 10, 1, 'Q', 3),
(3, 11, 1, 'Q', 3),
(3, 16, 1, 'Q', 3),
(3, 17, 1, 'Q', 3),
(3, 6, 1, 'Q', 3),
(3, 18, 1, 'Q', 3),
(3, 19, 1, 'Q', 3);

-- --------------------------------------------------------

--
-- 表的結構 `qa_cookies`
--

CREATE TABLE IF NOT EXISTS `qa_cookies` (
  `cookieid` bigint(20) unsigned NOT NULL,
  `created` datetime NOT NULL,
  `createip` int(10) unsigned NOT NULL,
  `written` datetime DEFAULT NULL,
  `writeip` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`cookieid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `qa_iplimits`
--

CREATE TABLE IF NOT EXISTS `qa_iplimits` (
  `ip` int(10) unsigned NOT NULL,
  `action` char(1) CHARACTER SET ascii NOT NULL,
  `period` int(10) unsigned NOT NULL,
  `count` smallint(5) unsigned NOT NULL,
  UNIQUE KEY `ip` (`ip`,`action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 轉存資料表中的資料 `qa_iplimits`
--

INSERT INTO `qa_iplimits` (`ip`, `action`, `period`, `count`) VALUES
(2130706433, 'L', 371321, 7),
(2130706433, 'Q', 371321, 1),
(3232235880, 'L', 371177, 19),
(3232235880, 'Q', 371168, 1),
(3232235880, 'R', 371168, 1),
(3232235923, 'L', 371321, 2);

-- --------------------------------------------------------

--
-- 表的結構 `qa_messages`
--

CREATE TABLE IF NOT EXISTS `qa_messages` (
  `messageid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fromuserid` int(10) unsigned NOT NULL,
  `touserid` int(10) unsigned NOT NULL,
  `content` varchar(8000) NOT NULL,
  `format` varchar(20) CHARACTER SET ascii NOT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`messageid`),
  KEY `fromuserid` (`fromuserid`,`touserid`,`created`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的結構 `qa_options`
--

CREATE TABLE IF NOT EXISTS `qa_options` (
  `title` varchar(40) NOT NULL,
  `content` varchar(8000) NOT NULL,
  PRIMARY KEY (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 轉存資料表中的資料 `qa_options`
--

INSERT INTO `qa_options` (`title`, `content`) VALUES
('adsense_publisher_id', ''),
('allow_change_usernames', '0'),
('allow_close_questions', '1'),
('allow_login_email_only', '0'),
('allow_multi_answers', '1'),
('allow_no_category', '0'),
('allow_no_sub_category', '0'),
('allow_private_messages', '0'),
('allow_self_answer', '1'),
('allow_view_q_bots', '1'),
('avatar_allow_gravatar', '0'),
('avatar_allow_upload', '0'),
('avatar_default_blobid', ''),
('avatar_default_height', ''),
('avatar_default_show', '0'),
('avatar_default_width', ''),
('avatar_profile_size', '200'),
('avatar_q_list_size', '0'),
('avatar_q_page_a_size', '40'),
('avatar_q_page_c_size', '20'),
('avatar_q_page_q_size', '50'),
('avatar_store_size', '400'),
('avatar_users_size', '30'),
('block_bad_words', ''),
('block_ips_write', ''),
('cache_acount', ''),
('cache_ccount', ''),
('cache_qcount', '1'),
('cache_tagcount', '2'),
('cache_unaqcount', '1'),
('cache_unselqcount', '1'),
('cache_unupaqcount', '1'),
('cache_userpointscount', '3'),
('captcha_module', 'reCAPTCHA'),
('captcha_on_anon_post', '0'),
('captcha_on_feedback', '1'),
('captcha_on_register', '0'),
('captcha_on_reset_password', '0'),
('captcha_on_unconfirmed', '0'),
('columns_tags', '3'),
('columns_users', '2'),
('comment_on_as', '1'),
('comment_on_qs', '0'),
('confirm_user_emails', '0'),
('confirm_user_required', '0'),
('custom_answer', ''),
('custom_ask', ''),
('custom_comment', ''),
('custom_footer', ''),
('custom_header', ''),
('custom_home_content', ''),
('custom_home_heading', 'Learnable Question Base'),
('custom_in_head', ''),
('custom_register', ''),
('custom_sidebar', 'Welcome to Q&amp;A, where you can ask questions and receive answers from other members of the community.'),
('custom_sidepanel', ''),
('custom_welcome', ''),
('db_version', '47'),
('do_ask_check_qs', '0'),
('do_close_on_select', '0'),
('do_complete_tags', '1'),
('do_count_q_views', '1'),
('do_example_tags', '1'),
('editor_for_as', ''),
('editor_for_cs', ''),
('editor_for_qs', ''),
('email_privacy', 'Privacy: Your email address will not be shared or sold to third parties.'),
('event_logger_directory', ''),
('event_logger_hide_header', ''),
('event_logger_to_database', ''),
('event_logger_to_files', ''),
('extra_field_active', '0'),
('extra_field_display', '0'),
('extra_field_label', ''),
('extra_field_prompt', ''),
('facebook_app_id', ''),
('facebook_app_secret', ''),
('feedback_email', 'beta@kiuz.coms.hk'),
('feedback_enabled', '0'),
('feed_for_activity', '1'),
('feed_for_hot', '0'),
('feed_for_qa', '1'),
('feed_for_questions', '1'),
('feed_for_search', '0'),
('feed_for_tag_qs', '0'),
('feed_for_unanswered', '1'),
('feed_full_text', '1'),
('feed_number_items', '50'),
('feed_per_category', '1'),
('flagging_hide_after', '5'),
('flagging_notify_every', '2'),
('flagging_notify_first', '1'),
('flagging_of_posts', '1'),
('follow_on_as', '1'),
('from_email', 'no-reply@192.168.1.104'),
('home_description', ''),
('hot_weight_answers', '100'),
('hot_weight_a_age', '100'),
('hot_weight_q_age', '100'),
('hot_weight_views', '100'),
('hot_weight_votes', '100'),
('links_in_new_window', ''),
('logo_height', ''),
('logo_show', '0'),
('logo_url', ''),
('logo_width', ''),
('mailing_body', '\n\n\n--\nQ&A\nhttp://192.168.1.104/fypv2/question2answer/'),
('mailing_enabled', ''),
('mailing_from_email', 'no-reply@192.168.1.104'),
('mailing_from_name', 'Q&A'),
('mailing_last_userid', ''),
('mailing_per_minute', '500'),
('mailing_subject', 'A message from Q&A'),
('match_ask_check_qs', '3'),
('match_example_tags', '3'),
('max_copy_user_updates', '10'),
('max_len_q_title', '120'),
('max_num_q_tags', '5'),
('max_rate_ip_as', '50'),
('max_rate_ip_cs', '40'),
('max_rate_ip_flags', '10'),
('max_rate_ip_logins', '20'),
('max_rate_ip_qs', '20'),
('max_rate_ip_registers', '5'),
('max_rate_ip_uploads', '20'),
('max_rate_ip_votes', '600'),
('max_rate_user_as', '25'),
('max_rate_user_cs', '20'),
('max_rate_user_flags', '5'),
('max_rate_user_qs', '10'),
('max_rate_user_uploads', '10'),
('max_rate_user_votes', '300'),
('max_store_user_updates', '50'),
('min_len_a_content', '12'),
('min_len_c_content', '12'),
('min_len_q_content', '0'),
('min_len_q_title', '12'),
('min_num_q_tags', '0'),
('moderate_anon_post', '0'),
('moderate_by_points', '0'),
('moderate_notify_admin', '1'),
('moderate_points_limit', '150'),
('moderate_unconfirmed', '0'),
('mouseover_content_max_len', '480'),
('mouseover_content_on', ''),
('nav_activity', '0'),
('nav_ask', '1'),
('nav_categories', '0'),
('nav_home', ''),
('nav_hot', '0'),
('nav_qa_is_home', '0'),
('nav_qa_not_home', '1'),
('nav_questions', '1'),
('nav_tags', '1'),
('nav_unanswered', '1'),
('nav_users', '0'),
('neat_urls', '5'),
('notice_visitor', ''),
('notice_welcome', ''),
('notify_admin_q_post', '0'),
('notify_users_default', '1'),
('pages_prev_next', '3'),
('page_size_activity', '20'),
('page_size_ask_check_qs', '5'),
('page_size_ask_tags', '5'),
('page_size_home', '20'),
('page_size_hot_qs', '20'),
('page_size_qs', '20'),
('page_size_q_as', '10'),
('page_size_search', '10'),
('page_size_tags', '30'),
('page_size_una_qs', '20'),
('page_size_users', '20'),
('page_size_user_posts', '20'),
('permit_anon_view_ips', '70'),
('permit_anon_view_ips_points', ''),
('permit_close_q', '70'),
('permit_close_q_points', ''),
('permit_delete_hidden', '40'),
('permit_delete_hidden_points', ''),
('permit_edit_a', '100'),
('permit_edit_a_points', ''),
('permit_edit_c', '70'),
('permit_edit_c_points', ''),
('permit_edit_q', '70'),
('permit_edit_q_points', ''),
('permit_flag', '110'),
('permit_flag_points', ''),
('permit_hide_show', '70'),
('permit_hide_show_points', ''),
('permit_moderate', '100'),
('permit_moderate_points', ''),
('permit_post_a', '150'),
('permit_post_a_points', ''),
('permit_post_c', '120'),
('permit_post_c_points', ''),
('permit_post_q', '120'),
('permit_post_q_points', ''),
('permit_retag_cat', '70'),
('permit_retag_cat_points', ''),
('permit_select_a', '100'),
('permit_select_a_points', ''),
('permit_view_q_page', '150'),
('permit_vote_a', '120'),
('permit_vote_a_points', ''),
('permit_vote_down', '120'),
('permit_vote_down_points', ''),
('permit_vote_q', '120'),
('permit_vote_q_points', ''),
('points_a_selected', '30'),
('points_a_voted_max_gain', '20'),
('points_a_voted_max_loss', '5'),
('points_base', '100'),
('points_multiple', '10'),
('points_per_a_voted', ''),
('points_per_a_voted_down', '2'),
('points_per_a_voted_up', '2'),
('points_per_q_voted', ''),
('points_per_q_voted_down', '1'),
('points_per_q_voted_up', '1'),
('points_post_a', '4'),
('points_post_q', '2'),
('points_q_voted_max_gain', '10'),
('points_q_voted_max_loss', '3'),
('points_select_a', '3'),
('points_to_titles', ''),
('points_vote_down_a', '1'),
('points_vote_down_q', '1'),
('points_vote_on_a', ''),
('points_vote_on_q', ''),
('points_vote_up_a', '1'),
('points_vote_up_q', '1'),
('q_urls_remove_accents', ''),
('q_urls_title_length', '50'),
('recaptcha_private_key', ''),
('recaptcha_public_key', ''),
('search_module', ''),
('show_a_c_links', '1'),
('show_a_form_immediate', 'if_no_as'),
('show_custom_answer', '0'),
('show_custom_ask', '0'),
('show_custom_comment', '0'),
('show_custom_footer', '0'),
('show_custom_header', '0'),
('show_custom_home', '0'),
('show_custom_in_head', '0'),
('show_custom_register', '0'),
('show_custom_sidebar', '0'),
('show_custom_sidepanel', '0'),
('show_custom_welcome', '0'),
('show_c_reply_buttons', '1'),
('show_fewer_cs_count', '5'),
('show_fewer_cs_from', '10'),
('show_full_date_days', '7'),
('show_home_description', '0'),
('show_message_history', '1'),
('show_notice_visitor', '0'),
('show_notice_welcome', '0'),
('show_selected_first', '1'),
('show_url_links', '1'),
('show_user_points', '1'),
('show_user_titles', '1'),
('show_view_counts', ''),
('show_when_created', '1'),
('site_language', 'en-GB'),
('site_maintenance', '0'),
('site_theme', 'NoahY-q2a-google-mobile-theme-5d8de9f'),
('site_theme_mobile', 'NoahY-q2a-google-mobile-theme-5d8de9f'),
('site_title', 'LearnAble QA'),
('site_url', 'http://192.168.1.104/learnable/question2answer/index.php/'),
('smtp_active', '0'),
('smtp_address', ''),
('smtp_authenticate', '0'),
('smtp_password', ''),
('smtp_port', '25'),
('smtp_secure', ''),
('smtp_username', ''),
('sort_answers_by', 'created'),
('suspend_register_users', '0'),
('tags_or_categories', 'tc'),
('tag_cloud_count_tags', '100'),
('tag_cloud_font_size', '24'),
('tag_cloud_size_popular', '1'),
('tag_separator_comma', ''),
('votes_separated', ''),
('voting_on_as', '1'),
('voting_on_qs', '1'),
('voting_on_q_page_only', ''),
('wysiwyg_editor_upload_all', ''),
('wysiwyg_editor_upload_images', ''),
('wysiwyg_editor_upload_max_size', '1048576'),
('xml_sitemap_show_categories', '1'),
('xml_sitemap_show_category_qs', '1'),
('xml_sitemap_show_questions', '1'),
('xml_sitemap_show_tag_qs', '1'),
('xml_sitemap_show_users', '1');

-- --------------------------------------------------------

--
-- 表的結構 `qa_pages`
--

CREATE TABLE IF NOT EXISTS `qa_pages` (
  `pageid` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(80) NOT NULL,
  `nav` char(1) CHARACTER SET ascii NOT NULL,
  `position` smallint(5) unsigned NOT NULL,
  `flags` tinyint(3) unsigned NOT NULL,
  `permit` tinyint(3) unsigned DEFAULT NULL,
  `tags` varchar(200) NOT NULL,
  `heading` varchar(800) DEFAULT NULL,
  `content` mediumtext,
  PRIMARY KEY (`pageid`),
  UNIQUE KEY `tags` (`tags`),
  UNIQUE KEY `position` (`position`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的結構 `qa_postmetas`
--

CREATE TABLE IF NOT EXISTS `qa_postmetas` (
  `postid` int(10) unsigned NOT NULL,
  `title` varchar(40) NOT NULL,
  `content` varchar(8000) NOT NULL,
  PRIMARY KEY (`postid`,`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `qa_posts`
--

CREATE TABLE IF NOT EXISTS `qa_posts` (
  `postid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` enum('Q','A','C','Q_HIDDEN','A_HIDDEN','C_HIDDEN','Q_QUEUED','A_QUEUED','C_QUEUED','NOTE') NOT NULL,
  `parentid` int(10) unsigned DEFAULT NULL,
  `categoryid` int(10) unsigned DEFAULT NULL,
  `catidpath1` int(10) unsigned DEFAULT NULL,
  `catidpath2` int(10) unsigned DEFAULT NULL,
  `catidpath3` int(10) unsigned DEFAULT NULL,
  `acount` smallint(5) unsigned NOT NULL DEFAULT '0',
  `amaxvote` smallint(5) unsigned NOT NULL DEFAULT '0',
  `selchildid` int(10) unsigned DEFAULT NULL,
  `closedbyid` int(10) unsigned DEFAULT NULL,
  `userid` int(10) unsigned DEFAULT NULL,
  `cookieid` bigint(20) unsigned DEFAULT NULL,
  `createip` int(10) unsigned DEFAULT NULL,
  `lastuserid` int(10) unsigned DEFAULT NULL,
  `lastip` int(10) unsigned DEFAULT NULL,
  `upvotes` smallint(5) unsigned NOT NULL DEFAULT '0',
  `downvotes` smallint(5) unsigned NOT NULL DEFAULT '0',
  `netvotes` smallint(6) NOT NULL DEFAULT '0',
  `lastviewip` int(10) unsigned DEFAULT NULL,
  `views` int(10) unsigned NOT NULL DEFAULT '0',
  `hotness` float DEFAULT NULL,
  `flagcount` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `format` varchar(20) CHARACTER SET ascii NOT NULL DEFAULT '',
  `created` datetime NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updatetype` char(1) CHARACTER SET ascii DEFAULT NULL,
  `title` varchar(800) DEFAULT NULL,
  `content` varchar(8000) DEFAULT NULL,
  `tags` varchar(800) DEFAULT NULL,
  `notify` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`postid`),
  KEY `type` (`type`,`created`),
  KEY `type_2` (`type`,`acount`,`created`),
  KEY `type_4` (`type`,`netvotes`,`created`),
  KEY `type_5` (`type`,`views`,`created`),
  KEY `type_6` (`type`,`hotness`),
  KEY `type_7` (`type`,`amaxvote`,`created`),
  KEY `parentid` (`parentid`,`type`),
  KEY `userid` (`userid`,`type`,`created`),
  KEY `selchildid` (`selchildid`,`type`,`created`),
  KEY `closedbyid` (`closedbyid`),
  KEY `catidpath1` (`catidpath1`,`type`,`created`),
  KEY `catidpath2` (`catidpath2`,`type`,`created`),
  KEY `catidpath3` (`catidpath3`,`type`,`created`),
  KEY `categoryid` (`categoryid`,`type`,`created`),
  KEY `createip` (`createip`,`created`),
  KEY `updated` (`updated`,`type`),
  KEY `flagcount` (`flagcount`,`created`,`type`),
  KEY `catidpath1_2` (`catidpath1`,`updated`,`type`),
  KEY `catidpath2_2` (`catidpath2`,`updated`,`type`),
  KEY `catidpath3_2` (`catidpath3`,`updated`,`type`),
  KEY `categoryid_2` (`categoryid`,`updated`,`type`),
  KEY `lastuserid` (`lastuserid`,`updated`,`type`),
  KEY `lastip` (`lastip`,`updated`,`type`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- 轉存資料表中的資料 `qa_posts`
--

INSERT INTO `qa_posts` (`postid`, `type`, `parentid`, `categoryid`, `catidpath1`, `catidpath2`, `catidpath3`, `acount`, `amaxvote`, `selchildid`, `closedbyid`, `userid`, `cookieid`, `createip`, `lastuserid`, `lastip`, `upvotes`, `downvotes`, `netvotes`, `lastviewip`, `views`, `hotness`, `flagcount`, `format`, `created`, `updated`, `updatetype`, `title`, `content`, `tags`, `notify`) VALUES
(1, 'Q_HIDDEN', NULL, 1, 1, NULL, NULL, 0, 0, NULL, 2, 1, NULL, 3232235880, 1, 2130706433, 0, 0, 0, 2130706433, 3, 14787800000, 0, '', '2012-05-05 16:55:42', '2012-05-12 01:21:43', 'H', 'TestTestTestTestTestTest', 'Test', '', NULL),
(2, 'NOTE', 1, 1, 1, NULL, NULL, 0, 0, NULL, NULL, 1, NULL, 2130706433, NULL, NULL, 0, 0, 0, NULL, 0, NULL, 0, '', '2012-05-12 01:16:28', NULL, NULL, NULL, 'For Testing Only', NULL, NULL),
(3, 'Q', NULL, 2, 2, NULL, NULL, 0, 0, NULL, NULL, 1, NULL, 2130706433, NULL, NULL, 0, 0, 0, 2130706433, 1, 14896700000, 0, '', '2012-05-12 01:21:22', NULL, NULL, 'How to add a listview in xml?', 'I would like to add a listview in UI layout?\nHow to do that?', 'android,listview', NULL);

-- --------------------------------------------------------

--
-- 表的結構 `qa_posttags`
--

CREATE TABLE IF NOT EXISTS `qa_posttags` (
  `postid` int(10) unsigned NOT NULL,
  `wordid` int(10) unsigned NOT NULL,
  `postcreated` datetime NOT NULL,
  KEY `postid` (`postid`),
  KEY `wordid` (`wordid`,`postcreated`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 轉存資料表中的資料 `qa_posttags`
--

INSERT INTO `qa_posttags` (`postid`, `wordid`, `postcreated`) VALUES
(3, 10, '2012-05-12 01:21:22'),
(3, 20, '2012-05-12 01:21:22');

-- --------------------------------------------------------

--
-- 表的結構 `qa_sharedevents`
--

CREATE TABLE IF NOT EXISTS `qa_sharedevents` (
  `entitytype` char(1) CHARACTER SET ascii NOT NULL,
  `entityid` int(10) unsigned NOT NULL,
  `questionid` int(10) unsigned NOT NULL,
  `lastpostid` int(10) unsigned NOT NULL,
  `updatetype` char(1) CHARACTER SET ascii DEFAULT NULL,
  `lastuserid` int(10) unsigned DEFAULT NULL,
  `updated` datetime NOT NULL,
  KEY `entitytype` (`entitytype`,`entityid`,`updated`),
  KEY `questionid` (`questionid`,`entitytype`,`entityid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 轉存資料表中的資料 `qa_sharedevents`
--

INSERT INTO `qa_sharedevents` (`entitytype`, `entityid`, `questionid`, `lastpostid`, `updatetype`, `lastuserid`, `updated`) VALUES
('Q', 1, 1, 1, NULL, 1, '2012-05-05 16:55:43'),
('U', 1, 1, 1, NULL, 1, '2012-05-05 16:55:43'),
('C', 1, 1, 1, NULL, 1, '2012-05-05 16:55:43'),
('Q', 1, 1, 1, 'C', 1, '2012-05-12 01:16:28'),
('U', 1, 1, 1, 'C', 1, '2012-05-12 01:16:28'),
('Q', 3, 3, 3, NULL, 1, '2012-05-12 01:21:22'),
('U', 1, 3, 3, NULL, 1, '2012-05-12 01:21:22'),
('T', 20, 3, 3, NULL, 1, '2012-05-12 01:21:22'),
('T', 10, 3, 3, NULL, 1, '2012-05-12 01:21:22'),
('C', 2, 3, 3, NULL, 1, '2012-05-12 01:21:22');

-- --------------------------------------------------------

--
-- 表的結構 `qa_tagmetas`
--

CREATE TABLE IF NOT EXISTS `qa_tagmetas` (
  `tag` varchar(80) NOT NULL,
  `title` varchar(40) NOT NULL,
  `content` varchar(8000) NOT NULL,
  PRIMARY KEY (`tag`,`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `qa_tagwords`
--

CREATE TABLE IF NOT EXISTS `qa_tagwords` (
  `postid` int(10) unsigned NOT NULL,
  `wordid` int(10) unsigned NOT NULL,
  KEY `postid` (`postid`),
  KEY `wordid` (`wordid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 轉存資料表中的資料 `qa_tagwords`
--

INSERT INTO `qa_tagwords` (`postid`, `wordid`) VALUES
(3, 20),
(3, 10);

-- --------------------------------------------------------

--
-- 表的結構 `qa_titlewords`
--

CREATE TABLE IF NOT EXISTS `qa_titlewords` (
  `postid` int(10) unsigned NOT NULL,
  `wordid` int(10) unsigned NOT NULL,
  KEY `postid` (`postid`),
  KEY `wordid` (`wordid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 轉存資料表中的資料 `qa_titlewords`
--

INSERT INTO `qa_titlewords` (`postid`, `wordid`) VALUES
(3, 6),
(3, 7),
(3, 8),
(3, 9),
(3, 10),
(3, 11),
(3, 12);

-- --------------------------------------------------------

--
-- 表的結構 `qa_userevents`
--

CREATE TABLE IF NOT EXISTS `qa_userevents` (
  `userid` int(10) unsigned NOT NULL,
  `entitytype` char(1) CHARACTER SET ascii NOT NULL,
  `entityid` int(10) unsigned NOT NULL,
  `questionid` int(10) unsigned NOT NULL,
  `lastpostid` int(10) unsigned NOT NULL,
  `updatetype` char(1) CHARACTER SET ascii DEFAULT NULL,
  `lastuserid` int(10) unsigned DEFAULT NULL,
  `updated` datetime NOT NULL,
  KEY `userid` (`userid`,`updated`),
  KEY `questionid` (`questionid`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 轉存資料表中的資料 `qa_userevents`
--

INSERT INTO `qa_userevents` (`userid`, `entitytype`, `entityid`, `questionid`, `lastpostid`, `updatetype`, `lastuserid`, `updated`) VALUES
(1, 'Q', 1, 1, 1, NULL, 1, '2012-05-05 16:55:43'),
(1, 'Q', 1, 1, 1, 'C', 1, '2012-05-12 01:16:28');

-- --------------------------------------------------------

--
-- 表的結構 `qa_userfavorites`
--

CREATE TABLE IF NOT EXISTS `qa_userfavorites` (
  `userid` int(10) unsigned NOT NULL,
  `entitytype` char(1) CHARACTER SET ascii NOT NULL,
  `entityid` int(10) unsigned NOT NULL,
  `nouserevents` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`userid`,`entitytype`,`entityid`),
  KEY `userid` (`userid`,`nouserevents`),
  KEY `entitytype` (`entitytype`,`entityid`,`nouserevents`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 轉存資料表中的資料 `qa_userfavorites`
--

INSERT INTO `qa_userfavorites` (`userid`, `entitytype`, `entityid`, `nouserevents`) VALUES
(1, 'Q', 1, 0);

-- --------------------------------------------------------

--
-- 表的結構 `qa_userfields`
--

CREATE TABLE IF NOT EXISTS `qa_userfields` (
  `fieldid` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(40) NOT NULL,
  `content` varchar(40) DEFAULT NULL,
  `position` smallint(5) unsigned NOT NULL,
  `flags` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`fieldid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- 轉存資料表中的資料 `qa_userfields`
--

INSERT INTO `qa_userfields` (`fieldid`, `title`, `content`, `position`, `flags`) VALUES
(1, 'name', NULL, 1, 0),
(2, 'location', NULL, 2, 0),
(3, 'website', NULL, 3, 2),
(4, 'about', NULL, 4, 1);

-- --------------------------------------------------------

--
-- 表的結構 `qa_userlimits`
--

CREATE TABLE IF NOT EXISTS `qa_userlimits` (
  `userid` int(10) unsigned NOT NULL,
  `action` char(1) CHARACTER SET ascii NOT NULL,
  `period` int(10) unsigned NOT NULL,
  `count` smallint(5) unsigned NOT NULL,
  UNIQUE KEY `userid` (`userid`,`action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 轉存資料表中的資料 `qa_userlimits`
--

INSERT INTO `qa_userlimits` (`userid`, `action`, `period`, `count`) VALUES
(1, 'Q', 371321, 1);

-- --------------------------------------------------------

--
-- 表的結構 `qa_userlogins`
--

CREATE TABLE IF NOT EXISTS `qa_userlogins` (
  `userid` int(10) unsigned NOT NULL,
  `source` varchar(16) CHARACTER SET ascii NOT NULL,
  `identifier` varbinary(1024) NOT NULL,
  `identifiermd5` binary(16) NOT NULL,
  KEY `source` (`source`,`identifiermd5`),
  KEY `userid` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `qa_usermetas`
--

CREATE TABLE IF NOT EXISTS `qa_usermetas` (
  `userid` int(10) unsigned NOT NULL,
  `title` varchar(40) NOT NULL,
  `content` varchar(8000) NOT NULL,
  PRIMARY KEY (`userid`,`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `qa_usernotices`
--

CREATE TABLE IF NOT EXISTS `qa_usernotices` (
  `noticeid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL,
  `content` varchar(8000) NOT NULL,
  `format` varchar(20) CHARACTER SET ascii NOT NULL,
  `tags` varchar(200) DEFAULT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`noticeid`),
  KEY `userid` (`userid`,`created`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的結構 `qa_userpoints`
--

CREATE TABLE IF NOT EXISTS `qa_userpoints` (
  `userid` int(10) unsigned NOT NULL,
  `points` int(11) NOT NULL DEFAULT '0',
  `qposts` mediumint(9) NOT NULL DEFAULT '0',
  `aposts` mediumint(9) NOT NULL DEFAULT '0',
  `cposts` mediumint(9) NOT NULL DEFAULT '0',
  `aselects` mediumint(9) NOT NULL DEFAULT '0',
  `aselecteds` mediumint(9) NOT NULL DEFAULT '0',
  `qupvotes` mediumint(9) NOT NULL DEFAULT '0',
  `qdownvotes` mediumint(9) NOT NULL DEFAULT '0',
  `aupvotes` mediumint(9) NOT NULL DEFAULT '0',
  `adownvotes` mediumint(9) NOT NULL DEFAULT '0',
  `qvoteds` int(11) NOT NULL DEFAULT '0',
  `avoteds` int(11) NOT NULL DEFAULT '0',
  `upvoteds` int(11) NOT NULL DEFAULT '0',
  `downvoteds` int(11) NOT NULL DEFAULT '0',
  `bonus` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userid`),
  KEY `points` (`points`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 轉存資料表中的資料 `qa_userpoints`
--

INSERT INTO `qa_userpoints` (`userid`, `points`, `qposts`, `aposts`, `cposts`, `aselects`, `aselecteds`, `qupvotes`, `qdownvotes`, `aupvotes`, `adownvotes`, `qvoteds`, `avoteds`, `upvoteds`, `downvoteds`, `bonus`) VALUES
(1, 120, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(2, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(3, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- 表的結構 `qa_userprofile`
--

CREATE TABLE IF NOT EXISTS `qa_userprofile` (
  `userid` int(10) unsigned NOT NULL,
  `title` varchar(40) NOT NULL,
  `content` varchar(8000) NOT NULL,
  UNIQUE KEY `userid` (`userid`,`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `qa_users`
--

CREATE TABLE IF NOT EXISTS `qa_users` (
  `userid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `created` datetime NOT NULL,
  `createip` int(10) unsigned NOT NULL,
  `email` varchar(80) NOT NULL,
  `handle` varchar(20) NOT NULL,
  `avatarblobid` bigint(20) unsigned DEFAULT NULL,
  `avatarwidth` smallint(5) unsigned DEFAULT NULL,
  `avatarheight` smallint(5) unsigned DEFAULT NULL,
  `passsalt` binary(16) DEFAULT NULL,
  `passcheck` binary(20) DEFAULT NULL,
  `level` tinyint(3) unsigned NOT NULL,
  `loggedin` datetime NOT NULL,
  `loginip` int(10) unsigned NOT NULL,
  `written` datetime DEFAULT NULL,
  `writeip` int(10) unsigned DEFAULT NULL,
  `emailcode` char(8) CHARACTER SET ascii NOT NULL DEFAULT '',
  `sessioncode` char(8) CHARACTER SET ascii NOT NULL DEFAULT '',
  `sessionsource` varchar(16) CHARACTER SET ascii DEFAULT '',
  `flags` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`userid`),
  KEY `email` (`email`),
  KEY `handle` (`handle`),
  KEY `level` (`level`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- 轉存資料表中的資料 `qa_users`
--

INSERT INTO `qa_users` (`userid`, `created`, `createip`, `email`, `handle`, `avatarblobid`, `avatarwidth`, `avatarheight`, `passsalt`, `passcheck`, `level`, `loggedin`, `loginip`, `written`, `writeip`, `emailcode`, `sessioncode`, `sessionsource`, `flags`) VALUES
(1, '2012-05-05 16:32:45', 3232235880, 'beta@kiuz.coms.hk', 'admin', NULL, NULL, NULL, 'mbaon6xa5dqnzenz', '�Hi���Ѯ�^��\r�#J�', 120, '2012-05-12 01:30:45', 3232235923, '2012-05-12 01:21:43', 2130706433, '', 'nkd6hger', NULL, 0),
(3, '2012-05-05 16:51:39', 3232235880, 'tester@tester.com', 'tester', NULL, NULL, NULL, 'x3hc060nptqaqrco', '9�>��kފ�(�X;ju��', 0, '2012-05-12 01:13:08', 2130706433, '2012-05-06 01:27:10', 3232235880, '', 'j8a34ufw', NULL, 0);

-- --------------------------------------------------------

--
-- 表的結構 `qa_uservotes`
--

CREATE TABLE IF NOT EXISTS `qa_uservotes` (
  `postid` int(10) unsigned NOT NULL,
  `userid` int(10) unsigned NOT NULL,
  `vote` tinyint(4) NOT NULL,
  `flag` tinyint(4) NOT NULL,
  UNIQUE KEY `userid` (`userid`,`postid`),
  KEY `postid` (`postid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `qa_widgets`
--

CREATE TABLE IF NOT EXISTS `qa_widgets` (
  `widgetid` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `place` char(2) CHARACTER SET ascii NOT NULL,
  `position` smallint(5) unsigned NOT NULL,
  `tags` varchar(800) CHARACTER SET ascii NOT NULL,
  `title` varchar(80) NOT NULL,
  PRIMARY KEY (`widgetid`),
  UNIQUE KEY `position` (`position`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的結構 `qa_words`
--

CREATE TABLE IF NOT EXISTS `qa_words` (
  `wordid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `word` varchar(80) NOT NULL,
  `titlecount` int(10) unsigned NOT NULL DEFAULT '0',
  `contentcount` int(10) unsigned NOT NULL DEFAULT '0',
  `tagwordcount` int(10) unsigned NOT NULL DEFAULT '0',
  `tagcount` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`wordid`),
  KEY `word` (`word`),
  KEY `tagcount` (`tagcount`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=21 ;

--
-- 轉存資料表中的資料 `qa_words`
--

INSERT INTO `qa_words` (`wordid`, `word`, `titlecount`, `contentcount`, `tagwordcount`, `tagcount`) VALUES
(1, 'testtesttesttesttesttest', 0, 0, 0, 0),
(2, 'test', 0, 0, 0, 0),
(3, 'for', 0, 0, 0, 0),
(4, 'testing', 0, 0, 0, 0),
(5, 'only', 0, 0, 0, 0),
(6, 'how', 1, 1, 0, 0),
(7, 'to', 1, 1, 0, 0),
(8, 'add', 1, 1, 0, 0),
(9, 'a', 1, 1, 0, 0),
(10, 'listview', 1, 1, 1, 1),
(11, 'in', 1, 1, 0, 0),
(12, 'xml', 1, 0, 0, 0),
(13, 'i', 0, 1, 0, 0),
(14, 'would', 0, 1, 0, 0),
(15, 'like', 0, 1, 0, 0),
(16, 'ui', 0, 1, 0, 0),
(17, 'layout', 0, 1, 0, 0),
(18, 'do', 0, 1, 0, 0),
(19, 'that', 0, 1, 0, 0),
(20, 'android', 0, 0, 1, 1);

--
-- 匯出資料表的 Constraints
--

--
-- 資料表的 Constraints `qa_categorymetas`
--
ALTER TABLE `qa_categorymetas`
  ADD CONSTRAINT `qa_categorymetas_ibfk_1` FOREIGN KEY (`categoryid`) REFERENCES `qa_categories` (`categoryid`) ON DELETE CASCADE;

--
-- 資料表的 Constraints `qa_contentwords`
--
ALTER TABLE `qa_contentwords`
  ADD CONSTRAINT `qa_contentwords_ibfk_1` FOREIGN KEY (`postid`) REFERENCES `qa_posts` (`postid`) ON DELETE CASCADE,
  ADD CONSTRAINT `qa_contentwords_ibfk_2` FOREIGN KEY (`wordid`) REFERENCES `qa_words` (`wordid`);

--
-- 資料表的 Constraints `qa_postmetas`
--
ALTER TABLE `qa_postmetas`
  ADD CONSTRAINT `qa_postmetas_ibfk_1` FOREIGN KEY (`postid`) REFERENCES `qa_posts` (`postid`) ON DELETE CASCADE;

--
-- 資料表的 Constraints `qa_posts`
--
ALTER TABLE `qa_posts`
  ADD CONSTRAINT `qa_posts_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `qa_users` (`userid`) ON DELETE SET NULL,
  ADD CONSTRAINT `qa_posts_ibfk_2` FOREIGN KEY (`parentid`) REFERENCES `qa_posts` (`postid`),
  ADD CONSTRAINT `qa_posts_ibfk_3` FOREIGN KEY (`categoryid`) REFERENCES `qa_categories` (`categoryid`) ON DELETE SET NULL,
  ADD CONSTRAINT `qa_posts_ibfk_4` FOREIGN KEY (`closedbyid`) REFERENCES `qa_posts` (`postid`);

--
-- 資料表的 Constraints `qa_posttags`
--
ALTER TABLE `qa_posttags`
  ADD CONSTRAINT `qa_posttags_ibfk_1` FOREIGN KEY (`postid`) REFERENCES `qa_posts` (`postid`) ON DELETE CASCADE,
  ADD CONSTRAINT `qa_posttags_ibfk_2` FOREIGN KEY (`wordid`) REFERENCES `qa_words` (`wordid`);

--
-- 資料表的 Constraints `qa_tagwords`
--
ALTER TABLE `qa_tagwords`
  ADD CONSTRAINT `qa_tagwords_ibfk_1` FOREIGN KEY (`postid`) REFERENCES `qa_posts` (`postid`) ON DELETE CASCADE,
  ADD CONSTRAINT `qa_tagwords_ibfk_2` FOREIGN KEY (`wordid`) REFERENCES `qa_words` (`wordid`);

--
-- 資料表的 Constraints `qa_titlewords`
--
ALTER TABLE `qa_titlewords`
  ADD CONSTRAINT `qa_titlewords_ibfk_1` FOREIGN KEY (`postid`) REFERENCES `qa_posts` (`postid`) ON DELETE CASCADE,
  ADD CONSTRAINT `qa_titlewords_ibfk_2` FOREIGN KEY (`wordid`) REFERENCES `qa_words` (`wordid`);

--
-- 資料表的 Constraints `qa_userevents`
--
ALTER TABLE `qa_userevents`
  ADD CONSTRAINT `qa_userevents_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `qa_users` (`userid`) ON DELETE CASCADE;

--
-- 資料表的 Constraints `qa_userfavorites`
--
ALTER TABLE `qa_userfavorites`
  ADD CONSTRAINT `qa_userfavorites_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `qa_users` (`userid`) ON DELETE CASCADE;

--
-- 資料表的 Constraints `qa_userlimits`
--
ALTER TABLE `qa_userlimits`
  ADD CONSTRAINT `qa_userlimits_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `qa_users` (`userid`) ON DELETE CASCADE;

--
-- 資料表的 Constraints `qa_userlogins`
--
ALTER TABLE `qa_userlogins`
  ADD CONSTRAINT `qa_userlogins_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `qa_users` (`userid`) ON DELETE CASCADE;

--
-- 資料表的 Constraints `qa_usermetas`
--
ALTER TABLE `qa_usermetas`
  ADD CONSTRAINT `qa_usermetas_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `qa_users` (`userid`) ON DELETE CASCADE;

--
-- 資料表的 Constraints `qa_usernotices`
--
ALTER TABLE `qa_usernotices`
  ADD CONSTRAINT `qa_usernotices_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `qa_users` (`userid`) ON DELETE CASCADE;

--
-- 資料表的 Constraints `qa_userprofile`
--
ALTER TABLE `qa_userprofile`
  ADD CONSTRAINT `qa_userprofile_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `qa_users` (`userid`) ON DELETE CASCADE;

--
-- 資料表的 Constraints `qa_uservotes`
--
ALTER TABLE `qa_uservotes`
  ADD CONSTRAINT `qa_uservotes_ibfk_1` FOREIGN KEY (`postid`) REFERENCES `qa_posts` (`postid`) ON DELETE CASCADE,
  ADD CONSTRAINT `qa_uservotes_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `qa_users` (`userid`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
