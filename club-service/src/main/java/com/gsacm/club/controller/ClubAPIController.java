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

package com.gsacm.club.controller;


import com.gsacm.club.services.ClubService;
import com.gsacm.helpers.dto.ClubDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.gsacm.helpers.helpers.RequestRouts.API_CLUB_ROUT;

/**
 * Le contrôleur API de type Club.
 */
@Slf4j
@AllArgsConstructor
@RequestMapping(API_CLUB_ROUT)
@RestController
@Tag(name = "API du clube", description = "Terminaux pour la gestion des clubs")
public class ClubAPIController {

    /**
     * Le service Clube.
     */
    private final ClubService clubService;

    /**
     * Ajouter un nouveau clube.
     *
     * @param file le fichier téléchargé.
     * @return l'entité de réponse
     */
    @Operation(summary = "Ajouter un nouveau clube", description = "Ajouter un nouveau clube au système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clube ajouté avec succès"),
            @ApiResponse(responseCode = "400", description = "Format de demande invalide")
    })
    @PostMapping(value = "add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ClubDTO> newClub(
            @ModelAttribute ClubDTO dto,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        log.info("Clube {} enregistré avec succès", dto);
        ClubDTO createdClub = clubService.newClub(dto, file);
        return ResponseEntity.ok(createdClub);
    }


    /**
     * Rechercher un clube par entité de réponse d'identifiant.
     *
     * @param id l'identifiant du clube
     * @return l'entité de réponse
     */
    @Operation(summary = "Trouver un clube par ID", description = "Trouver un clube par ID à partir du système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clube trouvé avec succès"),
            @ApiResponse(responseCode = "400", description = "Format de demande invalide")
    })
    @GetMapping("{clubId}")
    public ResponseEntity<ClubDTO> findClubByID(@PathVariable("clubId") Long id) {
        ClubDTO clubByID = clubService.findClubByID(id);
        return ResponseEntity.ok(clubByID);
    }

    /**
     * Rechercher un club par nom d'entité de réponse.
     *
     * @param name le nom du clube
     * @return l'entité de réponse
     */
    @Operation(summary = "Trouver un clube par Nom", description = "Trouver un clube par Nom à partir du système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clube trouvé avec succès"),
            @ApiResponse(responseCode = "400", description = "Format de demande invalide")
    })
    @GetMapping("name/{clubName}")
    public ResponseEntity<ClubDTO> findClubByName(@PathVariable("clubName") String name) {
        ClubDTO clubByID = clubService.findClubByName(name);
        return ResponseEntity.ok(clubByID);
    }

    /**
     * Mettre à jour le club par entité de réponse d'identifiant.
     *
     * @param dto le dto
     * @param id  l'identifiant du clube
     * @return l'entité de réponse
     */
    @Operation(summary = "Mettre à jour le clube par ID", description = "Mettre à jour le club par ID dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clube est mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Format de demande invalide")
    })
    @PutMapping("{clubId}")
    ResponseEntity<ClubDTO> updateClubByID(@RequestBody ClubDTO dto, @PathVariable("clubId") Long id) {
        ClubDTO updatedClub = clubService.updateClubByID(dto, id);
        return ResponseEntity.ok(updatedClub);
    }


    /**
     * Trouver toutes les entités de réponse des clubes.
     *
     * @return l'entité de réponse
     */
    @Operation(summary = "Liste des clubes", description = "Obtenir la liste des clubs du système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clubes récupérés avec succès"),
            @ApiResponse(responseCode = "400", description = "Format de demande invalide")
    })
    @GetMapping("all")
    public ResponseEntity<List<ClubDTO>> findAllClubs() {
        List<ClubDTO> allClubs = clubService.findAllClubs();
        return ResponseEntity.ok(allClubs);
    }

    /**
     * Supprimer le club par entité de réponse d'identifiant.
     *
     * @param id l'identifiant cu clube
     * @return l'entité de réponse
     */
    @Operation(summary = "Supprimer le club par ID", description = "Supprimer le club par ID du système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clube supprimé avec succès"),
            @ApiResponse(responseCode = "400", description = "Format de demande invalide")
    })
    @PatchMapping("{clubId}")
    public ResponseEntity<ClubDTO> deleteClubByID(@PathVariable("clubId") Long id) {
        ClubDTO clubByID = clubService.deleteClubByID(id);
        return ResponseEntity.ok(clubByID);
    }
}