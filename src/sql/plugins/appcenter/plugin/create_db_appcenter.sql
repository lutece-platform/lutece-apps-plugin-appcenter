
--
-- Structure for table appcenter_demand_type
--

DROP TABLE IF EXISTS appcenter_demand_type;
CREATE TABLE appcenter_demand_type (
id int AUTO_INCREMENT,
id_demand_type varchar(255) default '' NOT NULL,
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
application_data long varchar,
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
-- Structure for table appcenter_user_application
--

DROP TABLE IF EXISTS appcenter_user_application;
CREATE TABLE appcenter_user_application (
id_application int(6) NOT NULL,
user_id varchar(50) default '' NOT NULL,
user_role int(11) default '0' NOT NULL,
PRIMARY KEY ( id_application , user_id )
);

--
-- Structure for table appcenter_demand
--

DROP TABLE IF EXISTS appcenter_demand;
CREATE TABLE appcenter_demand  (
id_demand int(6) AUTO_INCREMENT,
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
-- Structure for table appcenter_attribute
--

DROP TABLE IF EXISTS appcenter_attribute;
CREATE TABLE appcenter_attribute (
id_attribute int AUTO_INCREMENT,
key_name varchar(255) default '' NOT NULL,
label varchar(255) default '' NOT NULL,
description varchar(255) default '',
PRIMARY KEY (id_attribute)
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
