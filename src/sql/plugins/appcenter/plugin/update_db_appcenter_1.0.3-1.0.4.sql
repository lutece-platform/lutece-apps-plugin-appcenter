-----------------------
-- JIRA APPCENTER-84 --
-----------------------

-- Table appcenter_user_application_role
ALTER TABLE appcenter_user_application_role
MODIFY id_role varchar(50) COLLATE utf8_unicode_ci NOT NULL;

UPDATE appcenter_user_application_role SET id_role = 'appcenter_admin' WHERE id_role = '0';
UPDATE appcenter_user_application_role SET id_role = 'appcenter_projet_manager' WHERE id_role = '1';
UPDATE appcenter_user_application_role SET id_role = 'appcenter_project_manager_deleg' WHERE id_role = '2';
UPDATE appcenter_user_application_role SET id_role = 'appcenter_project_developer' WHERE id_role = '3';

-- Table appcenter_permission_role
ALTER TABLE appcenter_permission_role
MODIFY id_role varchar(50) COLLATE utf8_unicode_ci NOT NULL;

UPDATE appcenter_permission_role SET id_role = 'appcenter_admin' WHERE id_role = '0';
UPDATE appcenter_permission_role SET id_role = 'appcenter_projet_manager' WHERE id_role = '1';
UPDATE appcenter_permission_role SET id_role = 'appcenter_project_manager_deleg' WHERE id_role = '2';
UPDATE appcenter_permission_role SET id_role = 'appcenter_project_developer' WHERE id_role = '3';

INSERT INTO core_admin_role_resource ( role_key, resource_type, resource_id, permission)
SELECT id_role, "APPCENTER", code_resource, code_permission
FROM appcenter_permission_role;

-- DROP TABLE appcenter_role
DROP TABLE appcenter_role;

-- DROP TABLE appcenter_permission_role
DROP TABLE appcenter_permission_role;