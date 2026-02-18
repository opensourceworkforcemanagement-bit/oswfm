-- ============================================================================
-- V7: ABAC Policies for User Management Page
-- ============================================================================
-- Creates permit and deny policies for each role against User Management
-- resources defined in V6.
--
-- PERMISSION MATRIX:
-- ┌──────────────────────────────┬────────┬────────┬────────┬────────┬────────┐
-- │ Role                         │  Read  │ Create │ Update │ Delete │  Note  │
-- ├──────────────────────────────┼────────┼────────┼────────┼────────┼────────┤
-- │ HR Admin                     │   ✓    │   ✓    │   ✓    │   ✓    │ Full   │
-- │ Supervisor                   │   ✓    │   ✗    │   ✗    │   ✗    │ R/O    │
-- │ Project Manager              │   ✓    │   ✗    │   ✗    │   ✗    │ R/O    │
-- │ Employee                     │   ✓    │   ✗    │   ✗    │   ✗    │ R/O    │
-- │ ECM Administrator            │   ✓    │   ✗    │   ✗    │   ✗    │ R/O    │
-- │ Timekeeper                   │   ✓    │   ✗    │   ✗    │   ✗    │ R/O    │
-- │ Telework Managing Officer    │   ✓    │   ✗    │   ✗    │   ✗    │ R/O    │
-- │ Telework Coordinator         │   ✓    │   ✗    │   ✗    │   ✗    │ R/O    │
-- └──────────────────────────────┴────────┴────────┴────────┴────────┴────────┘
--
-- Implementation approach:
--   - One PERMIT policy per role for allowed operations on the page resource
--   - One DENY policy per role for denied operations
--   - Policy rules match on subject attribute role = '<role_value>'
--   - Policies target the User Management Page resource (and all children
--     inherit via hierarchy)
-- ============================================================================


-- ============================================================================
-- HELPER: Get the attribute_id for 'role'
-- ============================================================================
-- We reference attribute_definitions.attribute_name = 'role' in policy rules.


-- ============================================================================
-- 1. HR Admin - FULL ACCESS (read, create, update, delete)
-- ============================================================================

-- PERMIT policy
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - HR Admin Full Access',
    'Grants HR Admin full access to all User Management resources',
    1,   -- permit
    100,
    TRUE
);

-- Policy rule: role equals 'HR Admin'
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is HR Admin', ad.attribute_id, 'equals', 'HR Admin', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - HR Admin Full Access'
  AND ad.attribute_name = 'role';

-- Target: User Management Page (all children inherit)
INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - HR Admin Full Access'
  AND r.resource_uri = '/usermanagement/users';

-- Target operations: read, create, update, delete
INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - HR Admin Full Access'
  AND o.operation_name IN ('read', 'create', 'update', 'delete');


-- ============================================================================
-- 2. Supervisor - READ ONLY
-- ============================================================================

-- PERMIT read
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - Supervisor Read Access',
    'Grants Supervisor read-only access to User Management',
    1, 50, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is Supervisor', ad.attribute_id, 'equals', 'Supervisor', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - Supervisor Read Access' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - Supervisor Read Access' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - Supervisor Read Access' AND o.operation_name = 'read';

-- DENY create, update, delete
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - Supervisor Deny Write',
    'Denies Supervisor create/update/delete on User Management',
    2, 60, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is Supervisor', ad.attribute_id, 'equals', 'Supervisor', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - Supervisor Deny Write' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - Supervisor Deny Write' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - Supervisor Deny Write' AND o.operation_name IN ('create', 'update', 'delete');


-- ============================================================================
-- 3. Employee - READ ONLY
-- ============================================================================

INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - Employee Read Access',
    'Grants Employee read-only access to User Management',
    1, 50, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is Employee', ad.attribute_id, 'equals', 'Employee', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - Employee Read Access' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - Employee Read Access' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - Employee Read Access' AND o.operation_name = 'read';

-- DENY write
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - Employee Deny Write',
    'Denies Employee create/update/delete on User Management',
    2, 60, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is Employee', ad.attribute_id, 'equals', 'Employee', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - Employee Deny Write' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - Employee Deny Write' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - Employee Deny Write' AND o.operation_name IN ('create', 'update', 'delete');


-- ============================================================================
-- 4. ECM Administrator - READ ONLY
-- ============================================================================

INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - ECM Admin Read Access',
    'Grants ECM Administrator read-only access to User Management',
    1, 50, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is ECM Admin', ad.attribute_id, 'equals', 'Emergency Contact Management (ECM) Administrator', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - ECM Admin Read Access' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - ECM Admin Read Access' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - ECM Admin Read Access' AND o.operation_name = 'read';

-- DENY write
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - ECM Admin Deny Write',
    'Denies ECM Administrator create/update/delete on User Management',
    2, 60, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is ECM Admin', ad.attribute_id, 'equals', 'Emergency Contact Management (ECM) Administrator', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - ECM Admin Deny Write' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - ECM Admin Deny Write' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - ECM Admin Deny Write' AND o.operation_name IN ('create', 'update', 'delete');


-- ============================================================================
-- 5. Telework Managing Officer - READ ONLY
-- ============================================================================

INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - Telework MO Read Access',
    'Grants Telework Managing Officer read-only access to User Management',
    1, 50, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is Telework MO', ad.attribute_id, 'equals', 'Telework Managing Officer', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - Telework MO Read Access' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - Telework MO Read Access' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - Telework MO Read Access' AND o.operation_name = 'read';

-- DENY write
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - Telework MO Deny Write',
    'Denies Telework Managing Officer create/update/delete on User Management',
    2, 60, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is Telework MO', ad.attribute_id, 'equals', 'Telework Managing Officer', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - Telework MO Deny Write' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - Telework MO Deny Write' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - Telework MO Deny Write' AND o.operation_name IN ('create', 'update', 'delete');


-- ============================================================================
-- 6. Project Manager - READ ONLY
-- ============================================================================

INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - Project Manager Read Access',
    'Grants Project Manager read-only access to User Management',
    1, 50, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is Project Manager', ad.attribute_id, 'equals', 'Project Manager', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - Project Manager Read Access' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - Project Manager Read Access' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - Project Manager Read Access' AND o.operation_name = 'read';

-- DENY write
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - Project Manager Deny Write',
    'Denies Project Manager create/update/delete on User Management',
    2, 60, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is Project Manager', ad.attribute_id, 'equals', 'Project Manager', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - Project Manager Deny Write' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - Project Manager Deny Write' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - Project Manager Deny Write' AND o.operation_name IN ('create', 'update', 'delete');


-- ============================================================================
-- 7. Timekeeper - READ ONLY
-- ============================================================================

INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - Timekeeper Read Access',
    'Grants Timekeeper read-only access to User Management',
    1, 50, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is Timekeeper', ad.attribute_id, 'equals', 'Timekeeper', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - Timekeeper Read Access' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - Timekeeper Read Access' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - Timekeeper Read Access' AND o.operation_name = 'read';

-- DENY write
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - Timekeeper Deny Write',
    'Denies Timekeeper create/update/delete on User Management',
    2, 60, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is Timekeeper', ad.attribute_id, 'equals', 'Timekeeper', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - Timekeeper Deny Write' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - Timekeeper Deny Write' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - Timekeeper Deny Write' AND o.operation_name IN ('create', 'update', 'delete');


-- ============================================================================
-- 8. Telework Coordinator - READ ONLY
-- ============================================================================

INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - Telework Coord Read Access',
    'Grants Telework Coordinator read-only access to User Management',
    1, 50, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is Telework Coordinator', ad.attribute_id, 'equals', 'Telework Coordinator', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - Telework Coord Read Access' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - Telework Coord Read Access' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - Telework Coord Read Access' AND o.operation_name = 'read';

-- DENY write
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active)
VALUES (
    'UM - Telework Coord Deny Write',
    'Denies Telework Coordinator create/update/delete on User Management',
    2, 60, TRUE
);

INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order)
SELECT p.policy_id, 'Role is Telework Coordinator', ad.attribute_id, 'equals', 'Telework Coordinator', 'AND', 1
FROM policies p, attribute_definitions ad
WHERE p.policy_name = 'UM - Telework Coord Deny Write' AND ad.attribute_name = 'role';

INSERT INTO policy_resource_targets (policy_id, resource_id)
SELECT p.policy_id, r.resource_id
FROM policies p, resources r
WHERE p.policy_name = 'UM - Telework Coord Deny Write' AND r.resource_uri = '/usermanagement/users';

INSERT INTO policy_operation_targets (policy_id, operation_id)
SELECT p.policy_id, o.operation_id
FROM policies p, operations o
WHERE p.policy_name = 'UM - Telework Coord Deny Write' AND o.operation_name IN ('create', 'update', 'delete');


-- ============================================================================
-- 9. OBLIGATIONS - Log denied write operations
-- ============================================================================
INSERT INTO policy_obligations (policy_id, obligation_type_id, obligation_params, is_mandatory)
SELECT p.policy_id, ot.obligation_type_id,
       ('{"message": "Write operation denied for ' ||
        REPLACE(p.policy_name, 'UM - ', '') ||
        '", "severity": "info"}')::jsonb,
       TRUE
FROM policies p
CROSS JOIN obligation_types ot
WHERE p.policy_name LIKE 'UM - % Deny Write'
  AND ot.type_name = 'log_access';


-- ============================================================================
-- SUMMARY
-- ============================================================================
-- Total policies created: 16
--   - 8 PERMIT policies (one per role, read access)
--   - 7 DENY policies (one per non-admin role, write access)
--   - 1 PERMIT policy for HR Admin (full CRUD)
--
-- HR Admin: read + create + update + delete (PERMIT, priority 100)
-- All others: read only (PERMIT priority 50, DENY priority 60)
--
-- The deny policies have higher priority (60) than the read permits (50)
-- but lower than HR Admin's full access (100), ensuring:
--   1. HR Admin always has full access
--   2. Other roles can only read
--   3. Deny policies explicitly block write operations
-- ============================================================================

-- ============================================================================
-- VERIFICATION QUERY:
-- ============================================================================
-- SELECT p.policy_name, pt.type_name AS policy_type, p.priority,
--        string_agg(DISTINCT o.operation_name, ', ') AS operations,
--        string_agg(DISTINCT r.resource_name, ', ') AS resources
-- FROM policies p
-- JOIN policy_types pt ON p.policy_type_id = pt.policy_type_id
-- LEFT JOIN policy_operation_targets pot ON p.policy_id = pot.policy_id
-- LEFT JOIN operations o ON pot.operation_id = o.operation_id
-- LEFT JOIN policy_resource_targets prt ON p.policy_id = prt.policy_id
-- LEFT JOIN resources r ON prt.resource_id = r.resource_id
-- WHERE p.policy_name LIKE 'UM -%'
-- GROUP BY p.policy_name, pt.type_name, p.priority
-- ORDER BY p.priority DESC, p.policy_name;
-- ============================================================================
