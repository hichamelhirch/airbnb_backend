-- Création de la séquence pour l'ID des bookings
CREATE SEQUENCE airbnb_clone.booking_generator
    START WITH 1
    INCREMENT BY 1;

-- Création de la table booking
CREATE TABLE booking (
                         id BIGINT PRIMARY KEY NOT NULL,
                         start_date TIMESTAMP WITH TIME ZONE NOT NULL,
                         end_date TIMESTAMP WITH TIME ZONE NOT NULL,
                         total_price INT NOT NULL,
                         nb_of_travelers INT NOT NULL,
                         public_id UUID NOT NULL UNIQUE,
                         fk_listing UUID NOT NULL,
                         fk_tenant UUID NOT NULL,
                         created_date TIMESTAMP,
                         last_modified_date TIMESTAMP
);

-- Ajout de la contrainte de clé étrangère pour fk_listing
ALTER TABLE booking
    ADD CONSTRAINT fk_listing_id FOREIGN KEY (fk_listing) REFERENCES airbnb_clone.listing (public_id) ON DELETE CASCADE;

-- Ajout de la contrainte de clé étrangère pour fk_tenant
ALTER TABLE booking
    ADD CONSTRAINT fk_tenant_id FOREIGN KEY (fk_tenant) REFERENCES airbnb_clone.airbnb_user (public_id) ON DELETE CASCADE;
