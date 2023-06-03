/*
 *   Copyright
 *   © 2023.Spring Microservices Gestion du Sport Médical. Tous les droits sont réservés.
 *   Énoncé/Déclaration des droits Sous réserve des dispositions du présent avis,
 *   ce site Web et l'ensemble de son contenu, de ses informations ou de son matériel sont protégés par le droit d'auteur de Spring Microservices Medical Sport Management,
 *   ainsi que de ses concédants de licence. En conséquence, votre utilisation de notre site Web ou de ses services ne constitue pas une licence d'utilisation
 *   du droit d'auteur sur notre site Web.
 *   Sauf dans la mesure permise par la loi sur le droit d'auteur applicable ou Spring Microservices Medical Sport Management, toute forme d'utilisation,
 *   de reproduction ou de redistribution d'une partie de l'ensemble du contenu, des informations ou du matériel de ce site Web sous quelque forme que ce soit est interdite.
 *   Vous ne pouvez pas, sauf autorisation préalable et consentement écrit exprès de Spring Microservices Medical Sport Management, copier, télécharger,
 *   imprimer, extraire, exploiter, adapter, éditer, modifier, republier, reproduire, rediffuser, dupliquer,
 *   distribuer ou afficher publiquement tout contenu, information ou élément de ce site Web à des fins non personnelles ou commerciales,
 *   à l'exception de toute autre utilisation autorisée par la loi sur le droit d'auteur applicable. Vous ne pouvez pas non plus transmettre,
 *   héberger ou stocker un tel contenu, information ou matériel sous quelque forme ou par quelque moyen que ce soit, y compris, mais sans s'y limiter, la photocopie,
 *   l'enregistrement ou sous toute forme imprimée, électronique ou numérique, ou sur tout autre site Web.
 *   Cependant, vous êtes autorisé à copier le contenu,
 *   les informations ou le matériel de ce site Web pour un usage personnel, un usage éducatif de tiers individuels,
 *   un usage gouvernemental ou toute autre utilisation autorisée par la loi sur le droit d'auteur applicable tout en reconnaissant
 *   cet auteur Spring Microservices Medical Sport Management comme source d'un tel contenu,
 *   information ou matériel.  Sous réserve de nos termes et conditions,
 *   vous n'êtes pas autorisé à publier des informations ou à ajouter du contenu protégé par des droits d'auteur par des tiers.
 *   Conformément au Digital Millennium Copyright Act (DMCA),
 *   Spring Microservices Medical Sport Management répondra aux avis de retrait ou aux rapports d'abus par les titulaires
 *   de droits d'auteur pour supprimer tout contenu abusif ou contrefait sur ce site Web.
 *   Spring Microservices Medical Sport Management ne sera pas responsable de la qualité, de l'exactitude,
 *   de l'exhaustivité ou de la pertinence du contenu, des informations ou du matériel de ce site Web.
 *
 */

-- V1__create_club_table.sql

-- Creates the club_tbl table
CREATE TABLE IF NOT EXISTS club_tbl
(
    id                 BIGINT NOT NULL,
    creation_date      TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_date       TIMESTAMP WITHOUT TIME ZONE,
    status             VARCHAR(255),
    name               VARCHAR(255),
    logo_url           VARCHAR(255),
    description        VARCHAR(255),
    email              VARCHAR(255),
    num_fix            VARCHAR(255),
    num_fax            VARCHAR(255),
    gsm                VARCHAR(255),
    site_web           VARCHAR(255),
    address1           VARCHAR(255),
    address2           VARCHAR(255),
    ville              VARCHAR(255),
    code_postale       VARCHAR(255),
    pays               VARCHAR(255),
    CONSTRAINT pk_club_tbl PRIMARY KEY (id)
);

-- Creates the club_seq sequence if it doesn't already exist
CREATE SEQUENCE IF NOT EXISTS club_seq START WITH 1 INCREMENT BY 1;

-- Add a meaningful comment here describing the purpose of the migration
COMMENT ON TABLE club_tbl IS 'Table for storing information about clubs.';
