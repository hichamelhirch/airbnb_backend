-- V1.1__Update_file_column_type.sql
-- Migration Flyway pour mettre à jour le type de la colonne `file` de `bytea` vers `oid`

ALTER TABLE airbnb_clone.listing_picture
    ALTER COLUMN file SET DATA TYPE oid;
