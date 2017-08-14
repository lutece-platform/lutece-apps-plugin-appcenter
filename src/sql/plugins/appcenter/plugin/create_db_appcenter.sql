
--
-- Structure for table appcenter_application
--

DROP TABLE IF EXISTS appcenter_application;
CREATE TABLE appcenter_application (
id_application int(6) NOT NULL,
name varchar(50) default '' NOT NULL,
description varchar(255) default '',
application_data long varchar,
PRIMARY KEY (id_application)
);

--
-- Structure for table appcenter_user_application
--

DROP TABLE IF EXISTS appcenter_user_application;
CREATE TABLE appcenter_user_application (
id_user_application int(6) NOT NULL,
user_id varchar(50) default '' NOT NULL,
user_role int(11) default '0' NOT NULL,
PRIMARY KEY (id_user_application)
);
