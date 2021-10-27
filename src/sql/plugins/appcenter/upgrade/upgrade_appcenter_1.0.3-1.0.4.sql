ALTER TABLE appcenter_application ADD id_file int(6);

--
-- Structure for table appcenter_application_url
--

DROP TABLE IF EXISTS `appcenter_application_url`;
CREATE TABLE `appcenter_application_url` (
	`id_application_url` int(6) AUTO_INCREMENT,
	`id_application` int(6) NOT NULL,
	`type` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
    `url` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
    `environment` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
    `description` mediumtext COLLATE utf8_unicode_ci,
    PRIMARY KEY (`id_application_url`)
);