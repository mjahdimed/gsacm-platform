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

package com.gsacm.club.utils;


import com.gsacm.clients.club.ClubDTO;
import com.gsacm.club.models.Address;
import com.gsacm.club.models.Club;
import com.gsacm.club.models.Info;

public class ClubDTOConverter {
    /**
     * From entity club dto.
     *
     * @param club the club
     * @return the club dto
     */
    public static ClubDTO fromEntity(Club club) {
        if (club == null) {
            return null;
        }

        return ClubDTO.builder()
                .id(club.getId())
                .name(club.getName())
                .logoUrl(club.getLogoUrl())
                .description(club.getDescription())
                .email(club.getInfo().getEmail())
                .numFix(club.getInfo().getNumFix())
                .numFax(club.getInfo().getNumFax())
                .gsm(club.getInfo().getGsm())
                .siteWeb(club.getInfo().getSiteWeb())
                .address1(club.getAddress().getAddress1())
                .address2(club.getAddress().getAddress2())
                .ville(club.getAddress().getVille())
                .codepostale(club.getAddress().getCodepostale())
                .pays(club.getAddress().getPays())
                .creationDate(club.getCreationDate())
                .lastModifiedDate(club.getLastModifiedDate())
                .deletedDate(club.getDeletedDate())
                .status(club.isStatus())
                .build();
    }

    /**
     * To entity club.
     *
     * @param clubDTO the club dto
     * @return the club
     */
    public static Club toEntity(ClubDTO clubDTO) {
        if (clubDTO == null) {
            return null;
        }

        Club club = new Club();
        club.setId(clubDTO.getId());
        club.setName(clubDTO.getName());
        club.setLogoUrl(clubDTO.getLogoUrl());
        club.setDescription(clubDTO.getDescription());
        club.setStatus(clubDTO.isStatus());

        // Set properties from embedded classes
        Info info = new Info();
        info.setEmail(clubDTO.getEmail());
        info.setNumFix(clubDTO.getNumFix());
        info.setNumFax(clubDTO.getNumFax());
        info.setGsm(clubDTO.getGsm());
        info.setSiteWeb(clubDTO.getSiteWeb());
        club.setInfo(info);

        Address address = new Address();
        address.setAddress1(clubDTO.getAddress1());
        address.setAddress2(clubDTO.getAddress2());
        address.setVille(clubDTO.getVille());
        address.setCodepostale(clubDTO.getCodepostale());
        address.setPays(clubDTO.getPays());
        club.setAddress(address);

        club.setCreationDate(clubDTO.getCreationDate());
        club.setLastModifiedDate(clubDTO.getLastModifiedDate());
        club.setDeletedDate(clubDTO.getDeletedDate());

        return club;
    }
}
