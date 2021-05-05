INSERT INTO `appcenter_role` (`id_role`,`code`,`label`) VALUES (0,'admin','Admin');

ALTER TABLE appcenter_application
ADD COLUMN `is_active` smallint(1) NOT NULL DEFAULT '1';
