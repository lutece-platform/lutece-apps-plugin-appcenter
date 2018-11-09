INSERT INTO appcenter_category_demand_type ( id, label, question , is_depending_of_environment, n_order ) VALUES
    (1,'Applications','Quels types de demande souhaitez pouvoir effectuer pour votre application ?',0,1),
    (2,'Livraison d''environnement','Livraison d''environnement',1,2),
    (3,'GRU','Votre application a t-elle besoin d''être rattachée à la GRU ?',1,3),
    (4,'Guichet Professionnel','Votre application a t-elle besoin d''être rattachée au Guichet Professionnel ?',1,4),
    (5,'WSSO','Votre application a t-elle besoin d''être rattachée au WSSO ?',1,5),
    (6,'Support','',0,6);

/*
Authorizations management example
*/
INSERT INTO appcenter_role (id_role,code,label) VALUES (1,'app_owner','Propriétaire de l''application');
INSERT INTO appcenter_role (id_role,code,label) VALUES (2,'project_manager','Chef de projet (Mairie) ');
INSERT INTO appcenter_role (id_role,code,label) VALUES (3,'moa_deleg','Assistant chef de projet (Prestataire)');
INSERT INTO appcenter_role (id_role,code,label) VALUES (4,'moe_deleg','Développeur (Prestataire)');

/*
Permissions for the role owner
*/
INSERT INTO appcenter_permission_role ( code_permission, id_role, code_resource) VALUES ( 'PERMISSION_MODIFY_APPLICATION','1','APP');
INSERT INTO appcenter_permission_role ( code_permission, id_role, code_resource) VALUES ( 'PERMISSION_VIEW_APPLICATION','1','APP');
INSERT INTO appcenter_permission_role ( code_permission, id_role, code_resource) VALUES ( 'PERMISSION_VIEW_DEMANDS','1','APP');
INSERT INTO appcenter_permission_role ( code_permission, id_role, code_resource) VALUES ( 'PERMISSION_ADD_USERS','1','APP');
INSERT INTO appcenter_permission_role ( code_permission, id_role, code_resource) VALUES ( 'PERMISSION_REMOVE_USER','1','APP');