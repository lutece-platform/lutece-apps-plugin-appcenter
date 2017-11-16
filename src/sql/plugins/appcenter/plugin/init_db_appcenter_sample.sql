
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
    (2,'GRU',1,2),
    (3,'Guichet Professionnel',1,3);

INSERT INTO appcenter_demand_type ( id, id_demand_type, label, description, id_category_demand_type, n_order ) VALUES
    (1,'appcode','Code Applicatif','Le code applicatif est un code sous lequel votre application est enregistrée auprès du BIAD.',1,1),
    (2,'sources','Gestion des sources','Ce type de demande permet de déclarer les référentiels de site et de composants utilisés ou souhaités pour votre application. Il permet également de demander des droits supplémentaires concernant les utilisateurs de ces référentiels.',1,2),
    (3,'jobs','Intégration continue','Ce type de demande permet de demander la création d''un job Jenkins. Un job Jenkins est un ensemble de traitements automatisés permettant de reconstruire un site ou un composant périodiquement ou après modificiation.',1,3),
    (4,'fastdeployapplication','FastDeploy','Fast Deploy est une application permettant de faciliter les déploiements des applications. Il utilise un référentiel de sources, et un environnement, pour déployer l''application sur les machines du BIAD',1,4),
    (5,'openam','Authentification Front Office','OpenAM est un mode d''authentification qui est notamment utilisé pour authentifier les utilisateurs de la Ville de Paris côté front; dans le cadre de la GRU.',2,5),
    (6,'moncomptesettings','Liens Mon Compte','Ce type de demande permet de définir un lien depuis MonCompte vers votre service numérique. Ce lien peut être un favori et/ou un lien de retour depuis la page de profil de MonCompte vers votre service.',2,6),
    (7,'notificationgru','Notifications GRU','Ce type de demande permet d''obtenir la configuration nécessaire afin d''émettre des notifications (Mails, vue 360, MonCompte). L''avantage de ces notifications est qu''elles sont traçées dans la vue 360 et dans MonCompte, ce qui permet un meilleur suivi des demandes des Parisiens',2,7),
    (8,'identitystore','Configuration du référentiel d''identité','Ce type de demande permet d''obtenir les configurations nécessaires auprès du référentiel d''identité de la GRU. Il s''agit ici de déterminer quels sont les droits de votre application sur les attributs des usagers',2,8),
    (9,'guichetpro','Guichet professionnel','Ce type de demande permet d''obtenir les configurations nécessaires afin d''émettre des notifications vers le Guichet Professionnel',3,9);

INSERT INTO appcenter_documentation( id_documentation, id_demand_type, label, url, category ) VALUES
    (1,4,'Mode d''emploi de l''application Fast Deploy','http://fr.lutece.paris.fr/fr/wiki/fastdeploy.html','project_manager'),
    (2,4,'FAQ Fast Deploy','http://fr.lutece.paris.fr/fr/wiki/faq-fastdeploy.html','project_manager'),
    (3,5,'Présentation de l''authentification Front apportée par la GRU','http://fr.lutece.paris.fr/fr/wiki/gru-authentication.html','project_manager'),
    (4,5,'Configuration de l''authentification Front apportée par la GRU','http://fr.lutece.paris.fr/fr/wiki/configluteceagentopenam.html','integ'),
    (5,6,'Personnalisation des liens vers MonCompte','http://fr.lutece.paris.fr/fr/wiki/gru-proposecertificationinservices.html','dev'),
    (6,7,'Comment envoyer une notification GRU ?','http://fr.lutece.paris.fr/fr/wiki/gru-notifygru.html','dev'),
    (7,8,'Comment récupérer des attributs utilisateurs ? Première méthode','http://fr.lutece.paris.fr/fr/wiki/gru-loadattributesafterauthentication.html','integ'),
    (8,8,'Comment récupérer des attributs utilisateurs ? Deuxième méthode','http://fr.lutece.paris.fr/fr/wiki/gru-library-identitystore.html','dev'),
    (9,8,'Comment récupérer des attributs utilisateurs ? Troisième méthode','http://fr.lutece.paris.fr/fr/wiki/gru-appeldirect-identitystore.html','dev');
