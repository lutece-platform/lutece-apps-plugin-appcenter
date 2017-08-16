
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

--
-- Structure for table appcenter_demand
--

DROP TABLE IF EXISTS appcenter_demand;
CREATE TABLE appcenter_demand  (
id_demand int(6) AUTO_INCREMENT,
statustext long varchar,
demandtype long varchar NOT NULL,
idapplication int(11) default '0' NOT NULL,
environmentprefix varchar(50) default '',
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
