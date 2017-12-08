
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

INSERT INTO appcenter_category_demand_type ( id, label, question , is_depending_of_environment, n_order ) VALUES
    (1,'Applications','Quels types de demande souhaitez pouvoir effectuer pour votre application ?',0,1),
    (2,'Autres demandes','Autres demandes',1,5),
    (3,'GRU','Votre application a t-elle besoin d''être rattachée à la GRU ?',1,2),
    (4,'Guichet Professionnel','Votre application a t-elle besoin d''être rattachée au Guichet Professionnel ?',1,3),
    (5,'WSSO','Votre application a t-elle besoin d''être rattachée au WSSO ?',1,4);

INSERT INTO appcenter_demand_type ( id, id_demand_type, id_workflow, java_class , label, description, question, id_category_demand_type, n_order ) VALUES
    (1,'appcode',900,'fr.paris.lutece.plugins.appcenter.modules.appcode.business.AppCodeDemand','Code Applicatif','Le code applicatif est un code qui permet d''identifier de manière unique votre application.','Votre application a t-elle besoin d''un code applicatif ?',1,1),
    (2,'sources',100,'fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesDemand','Gestion des sources','Ce type de demande permet de déclarer les référentiels de site et de composants souhaités de votre application. Un référentiel de sources est un espace collaboratif contenant le code source de votre application. Cette demande vous permet également de gérer les accès à ces référentiels.','Avez-vous besoin de créer des réferentiels de sources (SVN, GitHub… ), ou ajouter des droits sur des référentiels existants ?',1,2),
    (3,'jobs',700,'fr.paris.lutece.plugins.appcenter.modules.jobs.business.JobDemand','Intégration continue','Ce type de demande est destiné à la création de tâches d''intégration continue. Une tâche d''intégration continue permet d''automatiser la construction de votre application. Pour les applications Lutèce, il y a deux types de tâches : l''une destinée à la documentation (tâche Doc&QA) et l''autre à la génération des binaires des composants de l''application (tâche Deploy).','Souhaitez-vous que les composants techniques (plugin, module...) propres à votre application soient ajoutés à l''outil d''intégration continue ? (Jenkins)',1,3),
    (4,'fastdeployapplication',600,'fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.business.FastDeployApplicationDemand','FastDeploy','Fast Deploy est un outil de DevOps permettant déployer votre application en un clic. FastDeploy pilote les déploiements en s''appuyant sur les APIs fournies par le BIAD (cloudmgr). Il récupére automatiquement les sources de votre application sur le référentiel de site de votre application.','Souhaitez-vous que votre application utilise l''outil de déploiement simplifié Fast Deploy ?',1,4),
    (5,'environment',-1,'','Demande d''environnement physique','Ce type de demande est destiné à la création d''environnement physique (ajout de VM)','Avez vous besoin de faire une demande de livraison d''environnement physique ?',2,5),
    (6,'openam',200,'fr.paris.lutece.plugins.appcenter.modules.openam.business.OpenamDemand','Authentification Compte Parisien','L''authentification du Compte Parisien (MonCompte) se base sur un serveur SSO utilisant la technologie OpenAM.','Votre application propose-t-elle une authentification "MonCompte" ? (OpenAM) ?',3,6),
    (7,'moncomptesettings',300,'fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingDemand','Liens Mon Compte','Ce type de demande permet de définir un lien depuis MonCompte vers votre service numérique. Ce lien peut être un favori et/ou un lien de retour depuis la page de profil de MonCompte vers votre service.','Souhaitez-vous définir des liens depuis MonCompte permettant de rejoindre plus facilement votre application (Favori) ?',3,7),
    (8,'notificationgru',400,'fr.paris.lutece.plugins.appcenter.modules.notificationgru.business.NotificationGruDemand','Notifications vers la GRU','Ce type de demande permet d''obtenir la configuration nécessaire afin que votre application puisse émettre des notifications vers la GRU (Mails, vue 360, MonCompte). Ces notifications sont visibles par les agents dans le back office de la GRU, et par les usagers dans leur tableau de bord MonCompte.','Votre application doit-elle envoyer des notifications vers la GRU (vue 360, tableau de bord des usagers, mails aux usagers) ?',3,8),
    (9,'identitystore',500,'fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreDemand','Référentiel d''identité','Ce type de demande permet d''établir un contrat entre votre application et le référentiel d''identité de la GRU. Il s''agit ici de déterminer quels sont les droits de votre application sur les attributs d''identité des usagers. ( lecture, écriture, certification ). Lors du développement de votre application, vous pouvez utiliser l''une des trois méthodes ci-dessous pour récupérer des attributs d''identité ( classées par simplicité d''utilisation )','Votre application doit-elle récupérer des attributs de l''usager qui se connecte ?',3,9),
    (10,'guichetpro',800,'fr.paris.lutece.plugins.appcenter.modules.guichetpro.business.GuichetProDemand','Notifications vers le guichet professionnel','Ce type de demande permet d''obtenir la configuration nécessaire afin d''émettre des notifications vers le Guichet Professionnel. Ces notifications sont visibles par les usagers dans leur tableau de bord du Guichet Professionnel.','Votre application a t-elle besoin d''émettre des notifications vers le Guichet Professionnel ?',4,10),
    (11,'wsso',-1,'','WSSO','Ce type de demande permet de bénéficier de l''authentification WSSO','L''authentification de votre site doit-elle être confiée au WSSO ?',5,11);

INSERT INTO appcenter_documentation( id_documentation, id_demand_type, label, url, category ) VALUES
    (1,4,'Mode d''emploi de l''application Fast Deploy','http://fr.lutece.paris.fr/fr/wiki/fastdeploy.html','project_manager'),
    (2,4,'FAQ Fast Deploy','http://fr.lutece.paris.fr/fr/wiki/faq-fastdeploy.html','project_manager'),
    (3,6,'Présentation de l''architecture de l''authentification','http://fr.lutece.paris.fr/fr/wiki/gru-authentication.html','project_manager'),
    (4,6,'Aide à la configuration de l''authentification','http://fr.lutece.paris.fr/fr/wiki/configluteceagentopenam.html','integ'),
    (5,7,'Personnalisation des liens vers MonCompte','http://fr.lutece.paris.fr/fr/wiki/gru-proposecertificationinservices.html','dev'),
    (6,8,'Comment envoyer une notification GRU ?','http://fr.lutece.paris.fr/fr/wiki/gru-notifygru.html','dev'),
    (7,9,'Récupération des attributs d''identité à partir du LuteceUser','http://fr.lutece.paris.fr/fr/wiki/gru-loadattributesafterauthentication.html','integ'),
    (8,9,'Récupération des attributs d''identité à partir de la library-identitystore','http://fr.lutece.paris.fr/fr/wiki/gru-library-identitystore.html','dev'),
    (9,9,'Récupération des attributs d''identité à partir en utilisant l''API du référentiel d''identité','http://fr.lutece.paris.fr/fr/wiki/gru-appeldirect-identitystore.html','dev'),
    (10,11,'Comment faire une demande WSSO sur SATIS ?','http://intraparis.dsti.mdp/DSTI/document?id=10845&id_attribute=127','project_manager');

INSERT INTO appcenter_demand_type_category ( id_demand_type_category, name, description ) VALUES
    (1,'Autorisation d\'occupation du domaine public','Autorisation d\'occupation du domaine public'),
    (2,'Autres demandes','Autres demandes'),
    (3,'Cinéma','Cinéma'),
    (4,'Logement','Logement'),
    (5,'Urbanisme','Urbanisme'),
    (6,'Voirie','Voirie');
