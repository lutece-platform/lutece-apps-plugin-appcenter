INSERT INTO appcenter_category_demand_type ( id, label, question , is_depending_of_environment, n_order ) VALUES
    (1,'Applications','Quels types de demande souhaitez pouvoir effectuer pour votre application ?',0,1),
    (2,'Livraison d''environnement','Livraison d''environnement',1,2),
    (3,'GRU','Votre application a t-elle besoin d''être rattachée à la GRU ?',1,3),
    (4,'Guichet Professionnel','Votre application a t-elle besoin d''être rattachée au Guichet Professionnel ?',1,4),
    (5,'WSSO','Votre application a t-elle besoin d''être rattachée au WSSO ?',1,5);

/*
Authorizations management example
*/
INSERT INTO `appcenter_role` (`id_role`,`code`,`label`) VALUES (1,'project_manager','Chef de projet');
INSERT INTO `appcenter_role` (`id_role`,`code`,`label`) VALUES (2,'project_manager_deleg','Chef de projet délégué');
INSERT INTO `appcenter_role` (`id_role`,`code`,`label`) VALUES (3,'project_developer','Développeur');

INSERT INTO appcenter_user VALUES ("john.doe@nowhere.com","");