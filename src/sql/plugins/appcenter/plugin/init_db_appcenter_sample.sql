
INSERT INTO appcenter_application ( id_application,code , name , description , application_data )
VALUES (1,'W72','Budget Participatif','Budget Participatif de la Ville de Paris 2017','{"sources":{"siteRepository":"http://dev.lutece.paris.fr/svn/sites/paris.fr/site-budget-participatif/","sourcesDatas":[]}}');


INSERT INTO `appcenter_demand` VALUES (1,NULL,'sources','sources',1,'{"siteRepository":"http://dev.lutece.paris.fr/svn/sites/paris.fr/site-budget-participatif/","userName":"ACL","email":"alexandre.close@paris.fr"}','2017-03-01 12:16:14',0);

--
-- Add identitystore attributes
--
INSERT INTO appcenter_attribute ( id_attribute, key_name, label, description ) VALUES
    (1, 'gender', 'Genre', 'Code 0=Non Precise 1=Mme 2=Mr Fait partie du format pivot FranceConnect'),
    (2, 'preferred_username', 'Nom usuel','Champ optionel des IDP FranceConnect'),
    (3, 'family_name', 'Nom de famille de naissance', 'Fait partie du format pivot FranceConnect'),
    (4, 'first_name', 'Prénom', 'Prénom usuel'),
    (5, 'birthdate', 'Date de naissance', 'Fait partie du format pivot FranceConnect'),
    (6, 'birthplace', 'Lieu de naissance', 'Code INSEE de la commune ou vide pour l''étranger. Fait partie du format pivot FranceConnect'),
    (7, 'birthcountry', 'Pays de naissance', 'Code INSEE du pays de naissance. Fait partie du format pivot FranceConnect'),
    (8, 'address', 'Adresse', 'Champ d''adresse : adresse'),
    (9, 'address_detail','Complément d''adresse','Champ d''adresse : complément d''adresse'),
    (10, 'address_postal_code','Code postal','Champ d''adresse : code postal'),
    (11, 'address_city','Ville','Champ d''adresse : ville'),	
    (12, 'email', 'Email', 'Champ optionel des IDP FranceConnect'),
    (13, 'mobile_phone', 'Téléphone portable', 'Réservé pour l''envoi de SMS'),
    (14, 'fixed_phone', 'Téléphone fixe', ''),
    (15, 'preferred_contact_mode', 'Moyen de contact préféré', ''),	
    (16, 'login', 'Adresse de connexion', ''),
    (17, 'address_2', 'Adresse de facturation', 'Champ d''adresse de facturation: adresse'),
    (18, 'address_2_detail','Complément d''adresse de facturation','Champ d''adresse de facturation: complément d''adresse'),
    (19, 'address_2_postal_code','Code postal adresse de facturation','Champ d''adresse de facturation: code postal'),
    (20, 'address_2_city','Ville adresse de facturation','Champ d''adresse de facturation: ville'),
    (21, 'accept_news','Accepte les informations de la Mairie de Paris','valeurs possibles true ou false'),
    (22, 'accept_survey','Accepte les enquetes de satisfaction','valeurs possibles true ou false'),
    (23, 'given_name', 'Prénoms', 'Fait partie du format pivot FranceConnect');

INSERT INTO appcenter_category_demand_type ( id, label, is_depending_of_environment, n_order ) VALUES
    (1,'Applications',0,1),
    (1,'GRU',1,2),
    (1,'Guichet Professionnel',1,3);

INSERT INTO appcenter_demand_type ( id, id_demand_type, label,, id_category_demand_type, n_order ) VALUES
    (1,'sources','Gestion des sources',1,1),
    (2,'jobs','Intégration continue',1,2),
    (3,'fastdeploy','FastDeploy',1,3),
    (4,'openam','Authentification Front Office',2,4),
    (5,'moncomptesettings','Liens Mon Compte',2,5),
    (6,'notificationgru','Notifications GRU',2,6),
    (7,'identitystore','Configuration du référentiel d''identité',2,7),
    (8,'guichetpro','Guichet professionnel',3,8);
