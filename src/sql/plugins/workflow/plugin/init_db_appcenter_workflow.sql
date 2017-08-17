INSERT INTO workflow_workflow VALUES 
(100,'Workflow des demandes d\'accès au site SVN','Workflow des demandes d\'accès au site SVN','2017-08-16 12:38:32',0,'all');


INSERT INTO workflow_state VALUES 
(100,'Initialisée','demande initialisée',100,1,0,NULL,1),
(101,'En cours','Demande en cours',100,0,0,NULL,2),
(102,'Close','Demande close',100,0,0,NULL,3);


INSERT INTO workflow_action VALUES (100,'Creation de la demande d\'accès au SVN','Creation de la demande d\'accès au SVN',100,100,101,3,1,0,1,0);
