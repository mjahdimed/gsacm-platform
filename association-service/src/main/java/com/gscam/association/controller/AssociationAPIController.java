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

package com.gscam.association.controller;


/**
 * Le contrôleur API de type Club.
 */
//@Slf4j
//@CrossOrigin
//@AllArgsConstructor
//@RequestMapping(API_ASSOCIATION_ROUT)
//@RestController
//@Tag(name = "API de l'association", description = "Terminaux pour la gestion des clubs")
public class AssociationAPIController {
//
//    /**
//     * Le service Clube.
//     */
//    private final AssociationService associationService;
//
//    /**
//     * Ajouter un nouvelle Association.
//     *
//     * @param file le fichier téléchargé.
//     * @return l'entité de réponse
//     */
//    @Operation(summary = "Ajouter un nouveau Association", description = "Ajouter une nouvelle Association au système")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Association ajouté avec succès"),
//            @ApiResponse(responseCode = "400", description = "Format de demande invalide")
//    })
//    @PostMapping(value = "add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<AssociationDTO> addAssociation(
//            @ModelAttribute AssociationDTO dto,
//            @RequestParam(value = "file", required = false) MultipartFile file
//    ) {
//        try {
//            if (file != null && file.getSize() > 1048576) {
//                throw new FileSizeLimitExceededException("Le fichier de terrain dépasse sa taille maximale autorisée.", file.getSize(), 1048576);
//            }
//
//            log.info("Association {} enregistré avec succès", dto);
//            AssociationDTO createdAssociation = associationService.addAssociation(dto, file);
//            return ResponseEntity.ok(createdAssociation);
//        } catch (FileSizeLimitExceededException e) {
//            log.error("Erreur lors de l'ajout du Association: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        } catch (Exception e) {
//            log.error("Une erreur s'est produite lors de l'ajout du Association: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    /**
//     * Rechercher une association par entité de réponse d'identifiant.
//     *
//     * @param id l'identifiant du Association
//     * @return l'entité de réponse
//     */
//    @Operation(summary = "Trouver un Association par ID", description = "Trouver un Association par ID à partir du système")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Association trouvé avec succès"),
//            @ApiResponse(responseCode = "404", description = "Association non trouvé")
//   })
//    @GetMapping("{assocId}")
//    public ResponseEntity<AssociationDTO> findAssociationByID(@PathVariable("assocId") Long id) {
//        try {
//            AssociationDTO AssociationByID = associationService.findAssociationByID(id);
//            log.info("Clube {} trouvé avec succès", AssociationByID);
//            return ResponseEntity.ok(AssociationByID);
//        } catch (EntityNotFoundException e) {
//            log.info("Aucune Association trouvé avec ID {}", id);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//
//    /**
//     * Rechercher une association par nom d'entité de réponse.
//     *
//     * @param name le nom de l'association
//     * @return l'entité de réponse
//     */
//    @Operation(summary = "Trouver une association par Nom", description = "Trouver une association par Nom à partir du système")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Clube trouvé avec succès"),
//            @ApiResponse(responseCode = "404", description = "Clube non trouvé")
//    })
//    @GetMapping("name/{assocName}")
//    public ResponseEntity<AssociationDTO> findAssociationByName(@PathVariable("assocName") String name) {
//        try {
//        AssociationDTO clubNyName = associationService.findAssociationByName(name);
//            log.info("Association {} trouvé avec succès", clubNyName);
//        return ResponseEntity.ok(clubNyName);
//        }catch(EntityNotFoundException e){
//            log.info("Aucune Association trouvé avec e Nom {}", name);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//
//    /**
//     * Mettre à jour le club par entité de réponse d'identifiant.
//     *
//     * @param dto le dto
//     * @param id  l'identifiant de l'association
//     * @return l'entité de réponse
//     */
//    @Operation(summary = "Mettre à jour le club par ID", description = "Mettre à jour le club par ID dans le système")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Club est mis à jour avec succès"),
//            @ApiResponse(responseCode = "404", description = "Club non trouvé")
//    })
//    @PutMapping(value = "{assocId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<AssociationDTO> updateClubByID(
//            @ModelAttribute AssociationDTO dto,
//            @PathVariable("assocId") Long id,
//            @RequestParam(value = "file", required = false) MultipartFile file) {
//        try {
//            AssociationDTO updatedClub = associationService.updateAssociationByID(dto, id, file);
//            log.info("Club {} mis à jour avec succès", updatedClub);
//            return ResponseEntity.ok(updatedClub);
//        } catch (EntityNotFoundException e) {
//            log.error("Aucun Club trouvé avec ID {}", id);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } catch (Exception e) {
//            log.error("Une erreur s'est produite lors de la mise à jour du club avec ID {}: {}", id, e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//
//    /**
//     * Trouver toutes les entités de réponse des Associations.
//     *
//     * @return l'entité de réponse
//     */
//    @Operation(summary = "Liste des Associations", description = "Obtenir la liste des clubs du système")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Associations récupérés avec succès"),
//            @ApiResponse(responseCode = "404", description = "Aucune Association n'a été trouvé dans  la liste.")
//    })
//    @GetMapping(value = "all")
//    public ResponseEntity<List<AssociationDTO>> findAllClubs() {
//        try {
//            List<AssociationDTO> allAssociations = associationService.findAllAssociations();
//            log.info("la liste des clubs: {} est récupérés avec succès",allAssociations);
//            return ResponseEntity.ok(allAssociations);
//        } catch (Exception e) {
//            log.error("Une erreur s'est produite lors de la récupération de la liste des clubs: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    /**
//     * Supprimer le club par entité de réponse d'identifiant.
//     *
//     * @param id l'identifiant cu Association
//     * @return l'entité de réponse
//     */
//    @Operation(summary = "Supprimer le club par ID", description = "Supprimer le club par ID du système")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Association supprimé avec succès"),
//            @ApiResponse(responseCode = "404", description = "Association non trouvé"),
//            @ApiResponse(responseCode = "400", description = "Format de demande invalide")
//    })
//    @PatchMapping("{assocId}")
//    public ResponseEntity<AssociationDTO> deleteClubByID(@PathVariable("assocId") Long id) {
//        try {
//            AssociationDTO associationByID = associationService.deleteAssociationByID(id);
//            log.info("Association {} supprimé avec succès", associationByID);
//            return ResponseEntity.ok(associationByID);
//        } catch (EntityNotFoundException e) {
//            log.error("Aucune Association trouvé avec ID {}", id);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } catch (Exception e) {
//            log.error("Une erreur s'est produite lors de la suppression du club: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}