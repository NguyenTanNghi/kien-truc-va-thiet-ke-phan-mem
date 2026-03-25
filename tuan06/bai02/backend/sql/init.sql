-- Create database for User Partition System
CREATE DATABASE IF NOT EXISTS userdb;

-- Use the database
USE userdb;

-- Database will be auto-created by Hibernate with ddl-auto: update
-- The following tables will be created automatically:
-- 1. user_male (id, name, email, gender, created_at)
-- 2. user_female (id, name, email, gender, created_at)
-- 3. user_core (id, name, email, gender)
-- 4. user_profile (id, avatar, bio)
-- Optional: Check if tables exist
SHOW TABLES;

-- Optional: View table structure
-- DESCRIBE user_male;
-- DESCRIBE user_female;
-- DESCRIBE user_core;
-- DESCRIBE user_profile;