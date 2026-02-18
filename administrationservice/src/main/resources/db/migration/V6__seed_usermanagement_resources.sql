-- ============================================================================
-- V6: User Management Page Resources
-- ============================================================================
-- Registers every editable component on UserManagement.tsx as a protected
-- resource in the ABAC system: the page itself, tabs, input fields, and buttons.
--
-- Resource hierarchy:
--   User Management Page (page)
--     ├── User Tab (tab)
--     │     ├── userName (field)
--     │     ├── firstName (field)
--     │     ├── middleName (field)
--     │     ├── lastName (field)
--     │     ├── email (field)
--     │     ├── password (field)
--     │     ├── userStatus (field)
--     │     └── organization (field)
--     ├── Roles Tab (tab)
--     │     └── roleCheckbox (field)
--     ├── Settings Tab (tab)
--     │     ├── hiringDate (field)
--     │     ├── lastDate (field)
--     │     └── workerType (field)
--     ├── Groups Tab (tab)
--     │     ├── addToGroup (button)
--     │     └── removeFromGroup (button)
--     ├── Add User Button (button)
--     ├── Refresh Button (button)
--     ├── Edit Button (button)
--     ├── Delete Button (button)
--     ├── Save Button (button)
--     └── Cancel Button (button)
-- ============================================================================


-- ============================================================================
-- 1. ADDITIONAL RESOURCE TYPES
-- ============================================================================
-- V4 already seeds: page, api, document, database, report, service, module
-- We add: tab, field, button
INSERT INTO resource_types (type_name, description) VALUES
    ('tab', 'UI tab or section within a page'),
    ('field', 'Input field or form control'),
    ('button', 'Action button or control');


-- ============================================================================
-- 2. RESOURCES
-- ============================================================================
-- We use resource_uri as a logical path for frontend matching.
-- resource_type_id references: 1=page, and the new types added above.
-- We look up the new resource_type_ids dynamically.
-- ============================================================================

-- Page-level resource
INSERT INTO resources (resource_type_id, resource_name, resource_uri, is_active)
SELECT rt.resource_type_id, 'User Management Page', '/usermanagement/users', TRUE
FROM resource_types rt WHERE rt.type_name = 'page';

-- ============================================================================
-- TABS
-- ============================================================================
INSERT INTO resources (resource_type_id, resource_name, resource_uri, is_active)
SELECT rt.resource_type_id, vals.rname, vals.ruri, TRUE
FROM resource_types rt
CROSS JOIN (VALUES
    ('User Management - User Tab',     '/usermanagement/users/tab/user'),
    ('User Management - Roles Tab',    '/usermanagement/users/tab/roles'),
    ('User Management - Settings Tab', '/usermanagement/users/tab/settings'),
    ('User Management - Groups Tab',   '/usermanagement/users/tab/groups')
) AS vals(rname, ruri)
WHERE rt.type_name = 'tab';

-- ============================================================================
-- FIELDS - User Tab
-- ============================================================================
INSERT INTO resources (resource_type_id, resource_name, resource_uri, is_active)
SELECT rt.resource_type_id, vals.rname, vals.ruri, TRUE
FROM resource_types rt
CROSS JOIN (VALUES
    ('User Management - Username Field',     '/usermanagement/users/field/userName'),
    ('User Management - First Name Field',   '/usermanagement/users/field/firstName'),
    ('User Management - Middle Name Field',  '/usermanagement/users/field/middleName'),
    ('User Management - Last Name Field',    '/usermanagement/users/field/lastName'),
    ('User Management - Email Field',        '/usermanagement/users/field/email'),
    ('User Management - Password Field',     '/usermanagement/users/field/password'),
    ('User Management - Status Field',       '/usermanagement/users/field/userStatus'),
    ('User Management - Organization Field', '/usermanagement/users/field/organization')
) AS vals(rname, ruri)
WHERE rt.type_name = 'field';

-- ============================================================================
-- FIELDS - Roles Tab
-- ============================================================================
INSERT INTO resources (resource_type_id, resource_name, resource_uri, is_active)
SELECT rt.resource_type_id, 'User Management - Role Checkbox', '/usermanagement/users/field/roleCheckbox', TRUE
FROM resource_types rt WHERE rt.type_name = 'field';

-- ============================================================================
-- FIELDS - Settings Tab
-- ============================================================================
INSERT INTO resources (resource_type_id, resource_name, resource_uri, is_active)
SELECT rt.resource_type_id, vals.rname, vals.ruri, TRUE
FROM resource_types rt
CROSS JOIN (VALUES
    ('User Management - Hiring Date Field',    '/usermanagement/users/field/hiringDate'),
    ('User Management - Last Date Field',      '/usermanagement/users/field/lastDate'),
    ('User Management - Worker Type Field',    '/usermanagement/users/field/workerType')
) AS vals(rname, ruri)
WHERE rt.type_name = 'field';

-- ============================================================================
-- BUTTONS
-- ============================================================================
INSERT INTO resources (resource_type_id, resource_name, resource_uri, is_active)
SELECT rt.resource_type_id, vals.rname, vals.ruri, TRUE
FROM resource_types rt
CROSS JOIN (VALUES
    ('User Management - Add User Button',          '/usermanagement/users/button/addUser'),
    ('User Management - Refresh Button',           '/usermanagement/users/button/refresh'),
    ('User Management - Edit Button',              '/usermanagement/users/button/edit'),
    ('User Management - Delete Button',            '/usermanagement/users/button/delete'),
    ('User Management - Save Button',              '/usermanagement/users/button/save'),
    ('User Management - Cancel Button',            '/usermanagement/users/button/cancel'),
    ('User Management - Add To Group Button',      '/usermanagement/users/button/addToGroup'),
    ('User Management - Remove From Group Button', '/usermanagement/users/button/removeFromGroup')
) AS vals(rname, ruri)
WHERE rt.type_name = 'button';


-- ============================================================================
-- 3. RESOURCE HIERARCHY
-- ============================================================================
-- Link child resources to the User Management Page parent.
-- ============================================================================
INSERT INTO resource_hierarchies (parent_resource_id, child_resource_id)
SELECT parent.resource_id, child.resource_id
FROM resources parent, resources child
WHERE parent.resource_uri = '/usermanagement/users'
  AND child.resource_uri LIKE '/usermanagement/users/%';


-- ============================================================================
-- 4. RESOURCE ATTRIBUTES
-- ============================================================================
-- Tag all User Management resources with module = 'usermanagement'
-- First ensure the 'module' attribute definition exists
INSERT INTO attribute_definitions (attribute_name, attribute_category_id, data_type_id, description, is_required)
SELECT 'module', 2, 1, 'The application module this resource belongs to', FALSE
WHERE NOT EXISTS (
    SELECT 1 FROM attribute_definitions WHERE attribute_name = 'module'
);

INSERT INTO resource_attributes (resource_id, attribute_id, attribute_value)
SELECT r.resource_id, ad.attribute_id, 'usermanagement'
FROM resources r
CROSS JOIN attribute_definitions ad
WHERE r.resource_uri LIKE '/usermanagement/users%'
  AND ad.attribute_name = 'module';


-- ============================================================================
-- VERIFICATION QUERIES (run manually):
-- ============================================================================
-- SELECT r.resource_id, rt.type_name, r.resource_name, r.resource_uri
-- FROM resources r
-- JOIN resource_types rt ON r.resource_type_id = rt.resource_type_id
-- WHERE r.resource_uri LIKE '/usermanagement/users%'
-- ORDER BY rt.type_name, r.resource_name;
--
-- SELECT rh.hierarchy_id, p.resource_name AS parent, c.resource_name AS child
-- FROM resource_hierarchies rh
-- JOIN resources p ON rh.parent_resource_id = p.resource_id
-- JOIN resources c ON rh.child_resource_id = c.resource_id
-- WHERE p.resource_uri = '/usermanagement/users';
-- ============================================================================
