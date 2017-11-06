
--
-- Data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'APPCENTER_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('APPCENTER_MANAGEMENT','appcenter.adminFeature.ManageAppCenter.name',1,'jsp/admin/plugins/appcenter/ManageApplications.jsp','appcenter.adminFeature.ManageAppCenter.description',0,'appcenter',NULL,NULL,NULL,4);


--
-- Data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'APPCENTER_MANAGEMENT';
INSERT INTO core_user_right (id_right,id_user) VALUES ('APPCENTER_MANAGEMENT',1);

UPDATE core_admin_user SET password_max_valid_date = NULL;
