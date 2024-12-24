-- db/migration/V2__create_authority_and_user_authority_tables.sql

-- Créer la table authority
CREATE TABLE airbnb_clone.authority (
    name VARCHAR(50) PRIMARY KEY NOT NULL
);

-- Créer la table user_authority pour lier les utilisateurs et les rôles
CREATE TABLE airbnb_clone.user_authority (
                                             user_id BIGINT NOT NULL,
                                             authority_name VARCHAR(50) NOT NULL,
                                             PRIMARY KEY (user_id, authority_name)
);

-- Ajouter des contraintes de clé étrangère
ALTER TABLE airbnb_clone.user_authority
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES airbnb_clone.airbnb_user(id),
    ADD CONSTRAINT fk_authority_name FOREIGN KEY (authority_name) REFERENCES airbnb_clone.authority(name);
