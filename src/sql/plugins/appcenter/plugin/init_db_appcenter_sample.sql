
INSERT INTO appcenter_application ( id_application,code , name , description , application_data )
VALUES (1,'W72','Budget Participatif','Budget Participatif de la Ville de Paris 2017','{"sources":{"siteRepository":"http://dev.lutece.paris.fr/svn/sites/paris.fr/site-budget-participatif/","sourcesDatas":[]}}');


INSERT INTO `appcenter_demand` VALUES (1,NULL,'sources','sources',1,'{"siteRepository":"http://dev.lutece.paris.fr/svn/sites/paris.fr/site-budget-participatif/","userName":"ACL","email":"alexandre.close@paris.fr"}','2017-03-01 12:16:14');

--
-- Add identitystore attributes
--
INSERT INTO appcenter_attribute ( id_attribute, key_name, label, description ) VALUES
(1, 'last_name', 'Nom de famille', 'Nom de famille'),
(2, 'first_name', 'Prénom', 'Prénom');
