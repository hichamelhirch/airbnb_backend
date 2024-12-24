-- Création de la séquence pour l'ID des photos de listing
CREATE SEQUENCE airbnb_clone.listing_picture_generator
    START WITH 1
    INCREMENT BY 1;

-- Création de la table listing_picture
CREATE TABLE listing_picture (
                                 id BIGINT PRIMARY KEY NOT NULL,
                                 listing_fk BIGINT NOT NULL,
                                 file BYTEA NOT NULL,
                                 is_cover BOOLEAN NOT NULL,
                                 file_content_type VARCHAR(255) NOT NULL,
                                 created_date TIMESTAMP,
                                 last_modified_date TIMESTAMP
);

-- Ajout de la contrainte de clé étrangère sur listing_fk
ALTER TABLE listing_picture
    ADD CONSTRAINT fk_listing_id FOREIGN KEY (listing_fk) REFERENCES listing (id) ON DELETE CASCADE;
