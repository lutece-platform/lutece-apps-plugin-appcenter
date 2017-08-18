INSERT INTO workflow_workflow VALUES 
(100,'Workflow des demandes d\'accès au site SVN','Workflow des demandes d\'accès au site SVN','2017-08-16 12:38:32',1,'all'),
(200,'Workflow des demandes d\'agents OpenAM','Workflow des demandes d\'agents OpenAM','2017-08-16 12:38:32',1,'all'),
(300,'Workflow des demandes de paramétrages MonCompte','Workflow des demandes de paramétrage Mon Compte','2017-08-16 12:38:32',1,'all'),
(400,'Workflow des demandes de paramétrages NotifyGRU','Workflow des demandes de paramétrages NotifyGRU','2017-08-16 12:38:32',1,'all');


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
(402,'Close','Demande close',400,0,0,NULL,3);

INSERT INTO workflow_action VALUES 
(100,'Creation de la demande d\'accès au SVN','Creation de la demande d\'accès au SVN',100,100,101,3,1,0,1,0),
(200,'Creation de la demande d\'agent OpenAM','Creation de la demande d\'agent OpenAM',200,200,201,3,1,0,1,0),
(300,'Creation de la demande de paramétrage MonCompte','Creation de la demande de paramétrage MonCompte',300,300,301,3,1,0,1,0),
(400,'Creation de la demande de paramétrage NotifyGRU','Creation de la demande de paramétrage NotifyGRU',400,400,401,3,1,0,1,0),

(101,'Traiter la demande d\'accès au SVN','Traiter la demande d\'accès au SVN',100,101,102,3,0,0,2,0),
(201,'Traiter la demande d\'agent OpenAM','Traiter la demande d\'agent OpenAM',200,201,202,3,0,0,2,0),
(301,'Traiter la demande de paramétrage MonCompte','Traiter la demande de paramétrage MonCompte',300,301,302,3,0,0,2,0),
(401,'Traiter la demande de paramétrage NotifyGRU','Traiter la demande de paramétrage NotifyGRU',400,401,402,3,0,0,2,0);

INSERT INTO workflow_task VALUES
(1,'taskOpenam',201,1);

INSERT INTO workflow_resource_workflow VALUES 
(1,'DEMANDCENTER_DEMAND_SOURCE',101,100,-1,0);
INSERT INTO workflow_resource_history VALUES 
(1,1,'DEMANDCENTER_DEMAND_SOURCE',100,100,'2017-08-17 12:24:13','auto');
