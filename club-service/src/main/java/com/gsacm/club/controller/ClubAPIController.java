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


import com.gsacm.clients.club.ClubDTO;
import com.gsacm.club.services.ClubService;
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

import static com.gsacm.clients.helpers.RequestRouts.API_CLUB_ROUT;

/**
 * The type Club api controller.
 */
@Slf4j
@AllArgsConstructor
@RequestMapping(API_CLUB_ROUT)
@RestController
@Tag(name = "Club API", description = "Endpoints for managing clubs")
public class ClubAPIController {

    /**
     * The Club service.
     */
    private final ClubService clubService;

    @Operation(summary = "Add new club", description = "Add a new club to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Club added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format")
    })
    @PostMapping(value = "add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ClubDTO> newClub(
            @ModelAttribute ClubDTO dto,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        log.info("Club {} saved successfully", dto);
        ClubDTO createdClub = clubService.newClub(dto, file);
        return ResponseEntity.ok(createdClub);
    }


    /**
     * Find club by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @Operation(summary = "Find club by ID", description = "Find club by ID from system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Club found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format")
    })
    @GetMapping("{clubId}")
    public ResponseEntity<ClubDTO> findClubByID(@PathVariable("clubId") Long id) {
        ClubDTO clubByID = clubService.findClubByID(id);
        return ResponseEntity.ok(clubByID);
    }

    /**
     * Find club by name response entity.
     *
     * @param name the name
     * @return the response entity
     */
    @Operation(summary = "Find club by Name", description = "Find club by ID from system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Club found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format")
    })
    @GetMapping("name/{clubName}")
    public ResponseEntity<ClubDTO> findClubByName(@PathVariable("clubName") String name) {
        ClubDTO clubByID = clubService.findClubByName(name);
        return ResponseEntity.ok(clubByID);
    }

    /**
     * Update club by id response entity.
     *
     * @param dto the dto
     * @param id  the id
     * @return the response entity
     */
    @Operation(summary = "Update club by ID", description = "Update club by ID to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Club updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format")
    })
    @PutMapping("{clubId}")
    ResponseEntity<ClubDTO> updateClubByID(@RequestBody ClubDTO dto, @PathVariable("clubId") Long id) {
        ClubDTO updatedClub = clubService.updateClubByID(dto, id);
        return ResponseEntity.ok(updatedClub);
    }


    /**
     * Find all clubs response entity.
     *
     * @return the response entity
     */
    @Operation(summary = "List Of Clubs", description = "Get List Of Clubs from system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clubs fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format")
    })
    @GetMapping("all")
    public ResponseEntity<List<ClubDTO>> findAllClubs() {
        List<ClubDTO> allClubs = clubService.findAllClubs();
        return ResponseEntity.ok(allClubs);
    }

    /**
     * Delete club by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @Operation(summary = "Delete club by ID", description = "Delete club by ID from system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Club deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format")
    })
    @PatchMapping("{clubId}")
    public ResponseEntity<ClubDTO> deleteClubByID(@PathVariable("clubId") Long id) {
        ClubDTO clubByID = clubService.deleteClubByID(id);
        return ResponseEntity.ok(clubByID);
    }
}