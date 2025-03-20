-- This is a placeholder SQL statement to prevent the script from being empty
SELECT 1;

-- You can add initial data here if needed
-- For example:
-- INSERT INTO treatment (id, status, created_at) VALUES ('init-treatment', 'PENDING', NOW()); 

-- Add an admin user (password is 'admin')
INSERT INTO users (id, username, password, email, firstName, lastName, role, active, createdAt, updatedAt) 
VALUES ('admin-user-id', 'admin', '$2a$10$6Q527Bv9ao.fA6.4Gj6pju/AWRk5HlRtdF9hYWMh7S5J5quNqJ9wy', 'admin@beautifulcare.com', 'Admin', 'User', 'ADMIN', 1, NOW(), NOW());

-- Add service categories
INSERT INTO service_category (id, name, description, created_at, updated_at)
VALUES ('cat1', 'Facial Treatments', 'All facial treatment services', NOW(), NOW());

INSERT INTO service_category (id, name, description, created_at, updated_at)
VALUES ('cat2', 'Body Treatments', 'All body treatment services', NOW(), NOW());

-- Add sample services
INSERT INTO service_entity (id, name, description, price, duration, category_id)
VALUES ('serv1', 'Basic Facial', 'A refreshing facial treatment for all skin types', 150.00, 60, 'cat1');

INSERT INTO service_entity (id, name, description, price, duration, category_id)
VALUES ('serv2', 'Deep Cleansing Facial', 'Deep pore cleansing facial with extraction', 200.00, 90, 'cat1');

INSERT INTO service_entity (id, name, description, price, duration, category_id)
VALUES ('serv3', 'Body Scrub', 'Full body scrub treatment with natural ingredients', 250.00, 120, 'cat2'); 