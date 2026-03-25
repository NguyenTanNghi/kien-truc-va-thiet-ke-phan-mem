-- Sample queries to test the partitioning system
-- Insert sample data (optional - use the API instead)
-- This is just to show you the table structure
-- View all male users
SELECT
    *
FROM
    user_male;

-- View all female users
SELECT
    *
FROM
    user_female;

-- View core user data
SELECT
    *
FROM
    user_core;

-- View profile data
SELECT
    *
FROM
    user_profile;

-- Join core and profile data (vertical partitioning)
SELECT
    c.id,
    c.name,
    c.email,
    c.gender,
    p.avatar,
    p.bio
FROM
    user_core c
    LEFT JOIN user_profile p ON c.id = p.id;

-- Count users by gender (horizontal partitioning)
SELECT
    'male' as gender,
    COUNT(*) as count
FROM
    user_male
UNION ALL
SELECT
    'female' as gender,
    COUNT(*) as count
FROM
    user_female;

-- Get all users from both partitions
SELECT
    id,
    name,
    email,
    gender,
    created_at
FROM
    user_male
UNION ALL
SELECT
    id,
    name,
    email,
    gender,
    created_at
FROM
    user_female
ORDER BY
    id;