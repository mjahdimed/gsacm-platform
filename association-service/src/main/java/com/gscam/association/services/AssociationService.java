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

package com.gscam.association.services;


import com.gsacm.helpers.dto.AssociationDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * L'interface du service Association.
 */
public interface AssociationService {

    /**
     * Nouvelle association association dto.
     *
     * @param dto  le dto
     * @param file le fichier téléchargé
     * @return l'associations  dto
     * :: Insérer un nouvelle association ::
     */
    AssociationDTO addAssociation(AssociationDTO dto, MultipartFile file);

    /**
     * Mettre à jour le club par id clube dto.
     *
     * @param dto     le dto
     * @param assocId l'identifiant de l'association
     * @return l'associations  dto
     * :: Mettre à jour l'associations  par ID ::
     */
    AssociationDTO updateAssociationByID(AssociationDTO dto, Long assocId, MultipartFile file);

    /**
     * Trouver une association  par identifiant association dto.
     *
     * @param assocId l'identifiant de l'association
     * @return le dto de l'association
     * :: Trouver une association e par ID ::
     */
    AssociationDTO findAssociationByID(Long assocId);

    /**
     * Trouver une association  par nom clube dto.
     *
     * @param assocName le nom de l'association
     * @return le dto de l'association
     * :: Trouver une association  par Nom ::
     */

    AssociationDTO findAssociationByName(String assocName);

    /**
     * Trouver la liste de tous les clubes.
     *
     * @return la liste
     * :: Obtenir tous les associations ::
     */
    List<AssociationDTO> findAllAssociations();

    /**
     * Supprimer le club par id association dto.
     *
     * @param assocId l'identifiant de l'association
     * @return le dto de l'association
     * :: Supprimer l'associations  par ID ::
     */
    AssociationDTO deleteAssociationByID(Long assocId);
}
