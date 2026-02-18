-- ============================================================================
-- V5: Role Attribute Definition and Role Values
-- ============================================================================
-- Defines the "role" attribute and seeds 8 role values as subject attributes.
-- These roles correspond to organizational roles in the OSWFM system.
-- ============================================================================

-- ============================================================================
-- 1. ATTRIBUTE DEFINITION: role
-- ============================================================================
-- attribute_category_id = 1 (subject), data_type_id = 1 (string)
INSERT INTO attribute_definitions (attribute_name, attribute_category_id, data_type_id, description, is_required)
VALUES ('role', 1, 1, 'The organizational role assigned to the user', FALSE);

-- ============================================================================
-- 2. SUBJECT ATTRIBUTE VALUES: one per role
-- ============================================================================
-- These are reusable attribute values (not tied to any specific user).
-- Users receive them via direct assignment (user_subject_attributes)
-- or group membership (group_subject_attributes).
-- ============================================================================

-- Get the attribute_id for 'role' dynamically
INSERT INTO subject_attributes (attribute_id, attribute_value)
SELECT ad.attribute_id, role_value
FROM attribute_definitions ad
CROSS JOIN (VALUES
    ('HR Admin'),
    ('Employee'),
    ('Emergency Contact Management (ECM) Administrator'),
    ('Telework Managing Officer'),
    ('Project Manager'),
    ('Timekeeper'),
    ('Telework Coordinator'),
    ('Supervisor')
) AS roles(role_value)
WHERE ad.attribute_name = 'role';

-- ============================================================================
-- VERIFICATION QUERY (run manually to verify):
-- ============================================================================
-- SELECT sa.subject_attr_id, ad.attribute_name, sa.attribute_value
-- FROM subject_attributes sa
-- JOIN attribute_definitions ad ON sa.attribute_id = ad.attribute_id
-- WHERE ad.attribute_name = 'role'
-- ORDER BY sa.subject_attr_id;
-- ============================================================================
