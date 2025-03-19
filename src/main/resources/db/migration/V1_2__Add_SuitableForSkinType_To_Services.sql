-- Add suitable_for_skin_type column to services table
ALTER TABLE services ADD COLUMN suitable_for_skin_type VARCHAR(20);

-- Set default value for existing records
UPDATE services SET suitable_for_skin_type = 'NORMAL' WHERE suitable_for_skin_type IS NULL; 