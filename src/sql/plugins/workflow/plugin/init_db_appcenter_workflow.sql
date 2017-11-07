INSERT INTO workflow_workflow VALUES 
(100,'Workflow des demandes d\'accès au site SVN','Workflow des demandes d\'accès au site SVN','2017-08-16 12:38:32',1,'all'),
(200,'Workflow des demandes d\'agents OpenAM','Workflow des demandes d\'agents OpenAM','2017-08-16 12:38:32',1,'all'),
(300,'Workflow des demandes de paramétrages MonCompte','Workflow des demandes de paramétrage Mon Compte','2017-08-16 12:38:32',1,'all'),
(400,'Workflow des demandes de paramétrages NotifyGRU','Workflow des demandes de paramétrages NotifyGRU','2017-08-16 12:38:32',1,'all'),
(500,'Workflow des demandes de configuration IdentityStore','Workflow des demandes de paramétrages IdentityStore','2017-08-16 12:38:32',1,'all'),
(600,'Workflow des demandes d\'ajout d\'application FastDeploy','Workflow des demandes d\'ajout d\'application FastDeploy','2017-08-16 12:38:32',1,'all'),
(700,'Workflow des demandes d''intégration continue','Workflow des demandes d''intégration continue','2017-11-07 12:38:32',1,'all');


INSERT INTO workflow_state VALUES 
(100,'Initialisée','Demande initialisée',100,1,0,NULL,1),
(101,'En cours','Demande en cours',100,0,0,NULL,2),
(102,'Close','Demande close',100,0,0,NULL,3),

(200,'Initialisée','Demande initialisée',200,1,0,NULL,1),
(201,'En cours','Demande en cours',200,0,0,NULL,2),
(202,'Close','Demande close',200,0,0,NULL,3),

(300,'Initialisée','Demande initialisée',300,1,0,NULL,1),
(301,'En cours','Demande en cours',300,0,0,NULL,2),
(302,'Close','Demande close',300,0,0,NULL,3),

(400,'Initialisée','Demande initialisée',400,1,0,NULL,1),
(401,'En cours','Demande en cours',400,0,0,NULL,2),
(402,'Close','Demande close',400,0,0,NULL,3),

(500,'Initialisée','Demande initialisée',500,1,0,NULL,1),
(501,'En cours','Demande en cours',500,0,0,NULL,2),
(502,'Close','Demande close',500,0,0,NULL,3),

(600,'Initialisée','Demande initialisée',600,1,0,NULL,1),
(601,'En cours','Demande en cours',600,0,0,NULL,2),
(602,'Close','Demande close',600,0,0,NULL,3),

(700,'Initialisée','Demande initialisée',700,1,0,NULL,1),
(701,'En cours','Demande en cours',700,0,0,NULL,2),
(702,'Close','Demande close',700,0,0,NULL,3);

INSERT INTO workflow_action VALUES 
(100,'Creation de la demande d\'accès au SVN','Creation de la demande d\'accès au SVN',100,100,101,3,1,0,1,0),
(200,'Creation de la demande d\'agent OpenAM','Creation de la demande d\'agent OpenAM',200,200,201,3,1,0,1,0),
(300,'Creation de la demande de paramétrage MonCompte','Creation de la demande de paramétrage MonCompte',300,300,301,3,1,0,1,0),
(400,'Creation de la demande de paramétrage NotifyGRU','Creation de la demande de paramétrage NotifyGRU',400,400,401,3,1,0,1,0),
(500,'Creation de la demande de paramétrage Identitystore','Creation de la demande de paramétrage Identitystore',500,500,501,3,1,0,1,0),
(600,'Creation de la demande d\'application FastDeploy','Creation de la demande d\'application FastDeploy',600,600,601,3,1,0,1,0),
(700,'Creation de la demande d''intégration continue','Creation de la demande d''intégration continue',700,700,701,3,1,0,1,0),

(101,'Traiter la demande d\'accès au SVN','Traiter la demande d\'accès au SVN',100,101,102,3,0,0,2,0),
(201,'Traiter la demande d\'agent OpenAM','Traiter la demande d\'agent OpenAM',200,201,202,3,0,0,2,0),
(301,'Traiter la demande de paramétrage MonCompte','Traiter la demande de paramétrage MonCompte',300,301,302,3,0,0,2,0),
(401,'Traiter la demande de paramétrage NotifyGRU','Traiter la demande de paramétrage NotifyGRU',400,401,402,3,0,0,2,0),
(501,'Traiter la demande de paramétrage IdentityStore','Traiter la demande de paramétrage IdentityStore',500,501,502,3,0,0,2,0),
(601,'Traiter la demande de creation d\'une application FastDeploy','Traiter la demande de creation d\'une application FastDeploy',600,601,602,3,0,0,2,0),
(701,'Traiter la demande de paramétrage d''intégration continue','Traiter la demande de paramétrage d''intégration continue',700,701,702,3,0,0,2,0);


INSERT INTO workflow_task VALUES
(1,'taskSources',101,1),
(2,'taskOpenam',201,1),
(3,'taskMonCompteSettings',301,1),
(4,'taskCustomDemandStatus',100,2),
(5,'taskCustomDemandStatus',200,2),
(6,'taskCustomDemandStatus',300,2),
(8,'taskCustomDemandStatus',101,2),
(9,'taskCustomDemandStatus',201,2),
(10,'taskCustomDemandStatus',301,2),
(11,'taskNotificationGru',401,1),
(12,'taskCustomDemandStatus',400,2),
(13,'taskCustomDemandStatus',401,2),
(14,'taskCustomDemandStatus',500,1),
(15,'taskCustomDemandStatus',501,2),
(16,'taskIdentitystore',501,1),
(17,'taskCustomDemandStatus',600,1);


INSERT INTO appcenter_task_custom_demand_status_config VALUES
(4,'Demande d\'autorisation d\'accès SVN en cours de traitement'),
(5,'Demande de création d\'agent OpenAM en cours de traitement'),
(6,'Demande de paramètrage MonCompte en cours de traitement'),
(8,'Les autorisations d\'accès SVN ont été effectuées'),
(9,'L\'agent OpenAM demandé a été créé'),
(10,'Le paramétrage MonCompte souhaité a été effectué'),
(12,'Demande de configuration de la notification GRU en cours de traitement'),
(13,'La configuration de la notification GRU a été effecuée');
(14,'La demande de configuration de votre application dans le référentiel d\'identité est en cours de traitement'),
(15,'La demande de configuration de votre application dans le référentiel d\'identité a bien été traitée');


INSERT INTO workflow_resource_workflow VALUES 
(1,'DEMANDCENTER_DEMAND_SOURCE',101,100,-1,0);
INSERT INTO workflow_resource_history VALUES 
(1,1,'DEMANDCENTER_DEMAND_SOURCE',100,100,'2017-08-17 12:24:13','auto');
