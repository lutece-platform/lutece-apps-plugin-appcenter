INSERT INTO workflow_workflow VALUES 
(100,'Workflow des demandes d\'accès au site SVN','Workflow des demandes d\'accès au site SVN','2017-08-16 12:38:32',0,'all');


INSERT INTO workflow_state VALUES 
(1,'Initialisée','demande initialisée',1,1,0,NULL,1),
(2,'En cours','Demande en cours',1,0,0,NULL,2),
(3,'Close','Demande close',1,0,0,NULL,3);


INSERT INTO workflow_action VALUES (1,'Creation de la demande d\'accès au SVN','Creation de la demande d\'accès au SVN',1,1,2,3,1,0,1,0);
