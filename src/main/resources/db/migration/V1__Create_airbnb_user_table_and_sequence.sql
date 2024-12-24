
-- Créer la séquence pour la génération d'ID des utilisateurs
CREATE SEQUENCE airbnb_clone.user_generator
    START WITH 1
    INCREMENT BY 1;

-- Créer la table airbnb_user
CREATE TABLE airbnb_clone.airbnb_user (
                                          id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('airbnb_clone.user_generator'),
                                          public_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
                                          first_name VARCHAR(50),
                                          last_name VARCHAR(50),
                                          email VARCHAR(255) UNIQUE,
                                          image_url VARCHAR(256),
                                          created_date TIMESTAMP,
                                          last_modified_date TIMESTAMP
);
