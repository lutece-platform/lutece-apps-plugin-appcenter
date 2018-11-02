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
INSERT INTO 'appcenter_profile' ('id_profile','code','label') VALUES (1,'MOA','Chef de projet applicatif');
INSERT INTO 'appcenter_profile' ('id_profile','code','label') VALUES (2,'MOE_DELEG','MOE déléguée (Prestataire)');
INSERT INTO 'appcenter_profile' ('id_profile','code','label') VALUES (3,'MOE','MOE (BILD)');
INSERT INTO 'appcenter_profile' ('id_profile','code','label') VALUES (4,'MOA_DELEG','MOA déléguée');

INSERT INTO 'appcenter_resource' ('id_resource','code','label','resource_type') VALUES (1,'APP_APP','Application','APP');
INSERT INTO 'appcenter_resource' ('id_resource','code','label','resource_type') VALUES (2,'ENV_REC','Environnement de recette','ENV');
INSERT INTO 'appcenter_resource' ('id_resource','code','label','resource_type') VALUES (3,'ENV_PROD','Environnement de production','ENV');
INSERT INTO 'appcenter_resource' ('id_resource','code','label','resource_type') VALUES (4,'ENV_FOR','Environnement de formation','ENV');
INSERT INTO 'appcenter_resource' ('id_resource','code','label','resource_type') VALUES (5,'ENV_INT','Environnement d''integration','ENV');
INSERT INTO 'appcenter_resource' ('id_resource','code','label','resource_type') VALUES (6,'ENV_PRE','Environnement de pré-recette','ENV');
INSERT INTO 'appcenter_resource' ('id_resource','code','label','resource_type') VALUES (7,'ENV_SIM','Environnement de simulation','ENV');
INSERT INTO 'appcenter_resource' ('id_resource','code','label','resource_type') VALUES (8,'ENV_DEV','Environnement de developpement','ENV');

INSERT INTO 'appcenter_action_role' ('id_action','code','label','resource_type') VALUES (1,'DEPLOY_SITE_TRUNK','Déploiement d''un site (version de developpement)','ENV');
INSERT INTO 'appcenter_action_role' ('id_action','code','label','resource_type') VALUES (2,'DEPLOY_SITE_LAST_TAG','Déploiement d''un site (version publiée la plus récente)','ENV');
INSERT INTO 'appcenter_action_role' ('id_action','code','label','resource_type') VALUES (3,'DEPLOY_SITE_CREATE_TAG','Déploiement d''un site (version de developpement, avec publication de version)','ENV');
INSERT INTO 'appcenter_action_role' ('id_action','code','label','resource_type') VALUES (4,'DEPLOY_SCRIPT','Déploiement d''un script','ENV');
INSERT INTO 'appcenter_action_role' ('id_action','code','label','resource_type') VALUES (5,'APPLICATION_RO','Lecture des informations applicatives','APP');
INSERT INTO 'appcenter_action_role' ('id_action','code','label','resource_type') VALUES (6,'APPLICATION_RW','Gestion des informations applicatives','APP');
INSERT INTO 'appcenter_action_role' ('id_action','code','label','resource_type') VALUES (7,'USER_DELEGATION','Délégation de droits de gestion application','APP');
INSERT INTO 'appcenter_action_role' ('id_action','code','label','resource_type') VALUES (8,'DEMAND_RO','Lecture des demandes applicatives','APP');
INSERT INTO 'appcenter_action_role' ('id_action','code','label','resource_type') VALUES (9,'DEMAND_CR','Création de demandes applicatives','APP');

INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('APPLICATION_RO','MOA','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('APPLICATION_RW','MOA','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEMAND_CR','MOA','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEMAND_RO','MOA','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SCRIPT','MOA','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SITE_CREATE_TAG','MOA','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SITE_LAST_TAG','MOA','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SITE_TRUNK','MOA','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('USER_DELEGATION','MOA','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('APPLICATION_RO','MOA_DELEG','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEMAND_RO','MOA_DELEG','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SCRIPT','MOA_DELEG','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SITE_CREATE_TAG','MOA_DELEG','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SITE_LAST_TAG','MOA_DELEG','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SITE_TRUNK','MOA_DELEG','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('APPLICATION_RO','MOE','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('APPLICATION_RW','MOE','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEMAND_CR','MOE','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEMAND_RO','MOE','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SCRIPT','MOE','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SITE_CREATE_TAG','MOE','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SITE_LAST_TAG','MOE','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SITE_TRUNK','MOE','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('USER_DELEGATION','MOE','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('APPLICATION_RO','MOE_DELEG','*');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SCRIPT','MOE_DELEG','ENV_DEV');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SCRIPT','MOE_DELEG','ENV_REC');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SITE_CREATE_TAG','MOE_DELEG','ENV_REC');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SITE_LAST_TAG','MOE_DELEG','ENV_REC');
INSERT INTO 'appcenter_action_role_profile' ('code_action_role','code_profile','code_resource') VALUES ('DEPLOY_SITE_TRUNK','MOE_DELEG','ENV_REC');