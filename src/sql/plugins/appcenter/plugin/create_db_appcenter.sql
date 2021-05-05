
--
-- Structure for table appcenter_demand_type
--

DROP TABLE IF EXISTS appcenter_demand_type;
CREATE TABLE appcenter_demand_type (
id int AUTO_INCREMENT,
id_demand_type varchar(255) default '' NOT NULL,
id_workflow int default -1,
java_class long varchar NOT NULL,
label long varchar NOT NULL,
description long varchar,
question long varchar,
id_category_demand_type int default '0',
n_order int default 0,
PRIMARY KEY (id)
);

--
-- Structure for table appcenter_category_demand_type
--

DROP TABLE IF EXISTS appcenter_category_demand_type;
CREATE TABLE appcenter_category_demand_type (
id int AUTO_INCREMENT,
label long varchar,
question long varchar,
is_depending_of_environment SMALLINT,
n_order int default 0,
PRIMARY KEY (id)
);


--
-- Structure for table appcenter_application
--

DROP TABLE IF EXISTS appcenter_application;
CREATE TABLE appcenter_application (
id_application int(6) NOT NULL,
code varchar(50) default NULL,
name varchar(50) default '' NOT NULL,
description varchar(255) default '',
id_organization_manager int default 0,
application_data long varchar,
id_file int(6),
is_active` smallint(1) NOT NULL default 1,
PRIMARY KEY (id_application)
);

--
-- Structure for table appcenter_
--
DROP TABLE IF EXISTS appcenter_application_environment;
CREATE TABLE appcenter_application_environment (
id_application_environment int(6) AUTO_INCREMENT,
id_application int(6),
environment_code varchar(255) default '' NOT NULL,
PRIMARY KEY (id_application_environment)
);

--
-- Structure for table appcenter_demand
--

DROP TABLE IF EXISTS appcenter_demand;
CREATE TABLE appcenter_demand  (
id_demand int(6) AUTO_INCREMENT,
id_user_front varchar(255) NOT NULL,
status_text long varchar,
id_demand_type varchar(255) NOT NULL,
demand_type long varchar NOT NULL,
id_application int(11) default '0' NOT NULL,
demand_content TEXT,
creation_date datetime NOT NULL,
is_closed SMALLINT NOT NULL DEFAULT '0',
environment varchar(255) NOT NULL,
PRIMARY KEY (id_demand) 
);

--
-- Structure for table appcenter_workflow_resource_history_demand
--

DROP TABLE IF EXISTS appcenter_workflow_resource_history_demand;
CREATE TABLE appcenter_workflow_resource_history_demand (
id_history int(6) NOT NULL,
id_user_front int(11) default '0',
PRIMARY KEY ( id_history )
);

--
-- Structure for table appcenter_workflow_resource_history_demand
--

DROP TABLE IF EXISTS appcenter_task_custom_demand_status_config;
CREATE TABLE appcenter_task_custom_demand_status_config (
id_task int(6) NOT NULL,
custom_demand_status varchar(1000) default '' NOT NULL,
PRIMARY KEY ( id_task )
);

--
-- Structure for table appcenter_task_notify_config
--

DROP TABLE IF EXISTS appcenter_task_notify_config;
CREATE TABLE appcenter_task_notify_config (
id_task int(6) NOT NULL,
recipients varchar(10000) NULL,
notification_type varchar(255) NOT NULL DEFAULT '',
id_mailing_list int(6) NOT NULL DEFAULT -1,
subject varchar(1000) NOT NULL DEFAULT '',
message long varchar,
sender_name varchar(255) NOT NULL DEFAULT '',
PRIMARY KEY ( id_task )
);

DROP TABLE IF EXISTS appcenter_documentation;
CREATE TABLE appcenter_documentation (
id_documentation int AUTO_INCREMENT,
id_demand_type int default '0',
label long varchar,
url long varchar,
category varchar(255) default '',
PRIMARY KEY (id_documentation)
);

--
-- Structure for table appcenter_user_application_role
--

DROP TABLE IF EXISTS appcenter_user_application_role;
CREATE TABLE appcenter_user_application_role (
id_role int default '-1' NOT NULL,
id_application int default '-1' NOT NULL,
id_user varchar(255) default '' NOT NULL,
PRIMARY KEY (id_user, id_role, id_application)
);

--
-- Structure for table appcenter_permission_role
--

DROP TABLE IF EXISTS appcenter_permission_role;
CREATE TABLE appcenter_permission_role (
code_permission varchar(50) NOT NULL,
id_role int default '0' NOT NULL,
code_resource varchar(50) default '' NOT NULL,
PRIMARY KEY (id_role, code_permission, code_resource)
);

--
-- Structure for table appcenter_role
--

DROP TABLE IF EXISTS appcenter_role;
CREATE TABLE appcenter_role (
id_role int AUTO_INCREMENT,
code varchar(50) NOT NULL,
label varchar(255) default '' NOT NULL,
PRIMARY KEY (id_role),
  UNIQUE KEY `IDX_ROLE` (`code`)
);

--
-- Structure for table appcenter_user
--

DROP TABLE IF EXISTS appcenter_user;
CREATE TABLE appcenter_user (
id_user varchar(255) default '',
user_infos varchar(5000) default '{}',
PRIMARY KEY (id_user)
);

--
-- Structure for table appcenter_organization
--

DROP TABLE IF EXISTS appcenter_organization;
CREATE TABLE appcenter_organization (
id_organization int AUTO_INCREMENT,
name varchar(50) default '',
PRIMARY KEY (id_organization)
);

--
-- Structure for table appcenter_organization_manager
--

DROP TABLE IF EXISTS appcenter_organization_manager;
CREATE TABLE appcenter_organization_manager (
id_organization_manager int AUTO_INCREMENT,
id_organization int default '0',
first_name varchar(50) default '',
family_name varchar(255) default '',
mail varchar(255) default '',
PRIMARY KEY (id_organization_manager)
);

--
-- Structure for table appcenter_demand_validation
--

DROP TABLE IF EXISTS appcenter_demand_validation;
CREATE TABLE appcenter_demand_validation (
id int AUTO_INCREMENT,
id_demand int default '0' NOT NULL,
id_task int default '0' NOT NULL,
status int default '0' NOT NULL,
id_user varchar(255) default '',
PRIMARY KEY (id)
);

--
-- Structure for table workflow_prerequisite_validation_cf
--

DROP TABLE IF EXISTS workflow_prerequisite_validation_cf;
CREATE TABLE workflow_prerequisite_validation_cf (
id_prerequisite int NOT NULL,
id_task int NOT NULL,
status int NOT NULL,
PRIMARY KEY (id_prerequisite)
);

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
