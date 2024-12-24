-- Création de la séquence pour l'ID des listings
CREATE SEQUENCE airbnb_clone.listing_generator
    START WITH 1
    INCREMENT BY 1;

-- Création de la table listing
CREATE TABLE listing (
                         id BIGINT PRIMARY KEY NOT NULL,
                         public_id UUID NOT NULL UNIQUE,
                         title VARCHAR(256) NOT NULL,
                         description VARCHAR(256) NOT NULL,
                         guests INT NOT NULL,
                         bedrooms INT NOT NULL,
                         beds INT NOT NULL,
                         bathrooms INT NOT NULL,
                         price INT NOT NULL,
                         category VARCHAR(256) NOT NULL,
                         location VARCHAR(256) NOT NULL,
                         landlord_public_id UUID NOT NULL,
                         created_date TIMESTAMP,
                         last_modified_date TIMESTAMP
);

-- Ajout de la contrainte de clé étrangère sur landlord_public_id
ALTER TABLE listing
    ADD CONSTRAINT fk_landlord_id FOREIGN KEY (landlord_public_id) REFERENCES airbnb_clone.airbnb_user (public_id) ON DELETE CASCADE;
