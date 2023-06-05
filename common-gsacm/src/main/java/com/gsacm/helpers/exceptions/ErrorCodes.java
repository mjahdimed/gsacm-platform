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

package com.gsacm.helpers.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Les codes d'erreur enum.
 */
public enum ErrorCodes {
    /**
     * L'entrée invalide.
     */
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "InvalidInput", "L'entrée fournie n'est pas valide."),
    /**
     * Le champ Manquant.
     */
    MISSING_FIELD(HttpStatus.BAD_REQUEST, "MissingField", "Un champ obligatoire est manquant."),
    /**
     * L'erreur de base de données.
     */
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DatabaseError", "Une erreur s'est produite lors de l'accès à la base de données."),
    /**
     * Codes d'erreur d'erreur d'authentification.
     */
    AUTHENTICATION_ERROR(HttpStatus.UNAUTHORIZED, "AuthenticationError", "Authentification échouée."),
    /**
     * Codes d'erreur d'erreur d'authentification.
     */
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "PermissionDenied", "Vous n'êtes pas autorisé à effectuer cette opération."),
    /**
     * La Ressource est introuvable.
     */
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "ResourceNotFound", "La ressource demandée n'a pas été trouvée.");

    /**
     * Le statut HTTP.
     */
    private final HttpStatus httpStatus;
    /**
     * Le code Erreur.
     */
    private final String code;
    /**
     * Le Message.
     */
    private final String message;

    /**
     * Instancie un nouveau code d'erreur.
     *
     * @param httpStatus Le statut HTTP.
     * @param code       Le code Erreur.
     * @param message    Le Message.
     */
    ErrorCodes(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    /**
     * Obtient le statut http.
     *
     * @return le statut http
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * Obtient le code errur.
     *
     * @return le code errur
     */
    public String getCode() {
        return code;
    }

    /**
     * Reçoit un message.
     *
     * @return un message
     */
    public String getMessage() {
        return message;
    }
}


