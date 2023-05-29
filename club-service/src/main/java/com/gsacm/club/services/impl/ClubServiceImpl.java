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

package com.gsacm.club.services.impl;


import com.gsacm.clients.club.ClubDTO;
import com.gsacm.club.dao.IClubDAO;
import com.gsacm.club.exceptions.EntityNotFoundException;
import com.gsacm.club.exceptions.ErrorCodes;
import com.gsacm.club.exceptions.InvalidEntityException;
import com.gsacm.club.models.Club;
import com.gsacm.club.services.ClubService;
import com.gsacm.club.utils.ClubDTOConverter;
import com.gsacm.club.validators.ClubValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The type Club service.
 */
@Service
@Slf4j
@AllArgsConstructor
public class ClubServiceImpl implements ClubService {

    // Import Club Repository
    private final IClubDAO iClubDAO;

    public ClubDTO newClub(ClubDTO dto) {
        // Validate Fields
        List<String> errors = ClubValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Club is not valid: {}", dto);
            throw new InvalidEntityException("Club is not valid", ErrorCodes.INVALID_INPUT, errors);
        }

        Club club = ClubDTOConverter.toEntity(dto);
        // Set Creation date
        club.setCreationDate(LocalDateTime.now());
        // Set Status to "Active"
        club.setStatus("Active");

        // Save Club
        Club savedClub = iClubDAO.save(club);
        return ClubDTOConverter.fromEntity(savedClub);
    }

    @Override
    public ClubDTO updateClubByID(ClubDTO dto, Long clubId) {
        // Check if clubId is not empty
        if (clubId == null) {
            log.error("Club ID is null");
            throw new InvalidEntityException("Club ID is null", ErrorCodes.INVALID_INPUT);
        }

        // Validate Fields
        List<String> errors = ClubValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Club is not valid: {}", dto);
            throw new InvalidEntityException("Club is not valid", ErrorCodes.INVALID_INPUT, errors);
        }

        Club existingClub = iClubDAO.findByIdAndStatus(clubId, "Active")
                .orElseThrow(() -> {
                    log.error("Active club with ID {} not found", clubId);
                    return new EntityNotFoundException("Active club with ID: " + clubId + " not found",
                            ErrorCodes.RESOURCE_NOT_FOUND);
                });

        // Update the fields of the existing club with the values from the DTO
        existingClub.setName(dto.getName());
        existingClub.setDescription(dto.getDescription());
        existingClub.setLastModifiedDate(LocalDateTime.now());

        Club updatedClub = iClubDAO.save(existingClub);

        return ClubDTOConverter.fromEntity(updatedClub);
    }

    @Override
    public ClubDTO findClubByID(Long clubId) {
        // Check if clubId is not empty
        if (clubId == null) {
            log.error("Club ID is null");
            throw new InvalidEntityException("Club ID is null", ErrorCodes.INVALID_INPUT);
        }
        // Retrieve the club
        return iClubDAO.findById(clubId)
                .filter(club -> club.getStatus().equals("Active")) // Filter for active clubs
                .map(ClubDTOConverter::fromEntity)
                .orElseThrow(() -> {
                    log.error("Club with given ID {} not found", clubId);
                    return new EntityNotFoundException("Club with given ID: " + clubId + " not found",
                            ErrorCodes.RESOURCE_NOT_FOUND);
                });
    }

    @Override
    public ClubDTO findClubByName(String clubName) {
        // Check if clubName is not empty
        if (clubName == null) {
            log.error("Club Name is null");
            throw new InvalidEntityException("Club Name is null", ErrorCodes.INVALID_INPUT);
        }
        // Retrieve the club by Name
        return iClubDAO.findByName(clubName)
                .filter(club -> club.getStatus().equals("Active")) // Filter for active clubs
                .map(ClubDTOConverter::fromEntity)
                .orElseThrow(() -> {
                    log.error("Club with given Name {} not found", clubName);
                    return new EntityNotFoundException("Club with given Name: " + clubName + " not found",
                            ErrorCodes.RESOURCE_NOT_FOUND);
                });
    }

    @Override
    public List<ClubDTO> findAllClubs() {
        List<Club> clubs = new ArrayList<>(iClubDAO.findAllByStatus("Active"));
        if (clubs.isEmpty()) {
            log.info("No active clubs found");
        } else {
            log.info("Found {} active clubs", clubs.size());
        }

        return clubs.stream()
                .map(ClubDTOConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ClubDTO deleteClubByID(Long clubId) {
        // Check if clubId is not empty
        if (clubId == null) {
            log.error("Club ID is null");
            throw new InvalidEntityException("Club ID is null", ErrorCodes.INVALID_INPUT);
        }

        // Check if Club Exist by ID
        Club existingClub = iClubDAO.findByIdAndStatus(clubId, "Active")
                .orElseThrow(() -> {
                    log.error("Club with given ID {} not found", clubId);
                    return new EntityNotFoundException("Club with given ID: " + clubId + " not found",
                            ErrorCodes.RESOURCE_NOT_FOUND);
                });

        // Set Delete date
        existingClub.setDeletedDate(LocalDateTime.now());
        // Update the status of the existing club
        existingClub.setStatus("Inactive");
        // Update Club
        Club deletedClub = iClubDAO.save(existingClub);
        return ClubDTOConverter.fromEntity(deletedClub);
    }
}
