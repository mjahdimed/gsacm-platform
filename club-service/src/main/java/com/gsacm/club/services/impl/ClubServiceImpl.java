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


import com.gsacm.club.dao.IClubDAO;
import com.gsacm.club.models.Club;
import com.gsacm.club.services.ClubService;
import com.gsacm.club.utils.ClubDTOConverter;
import com.gsacm.club.utils.FileStorageProperties;
import com.gsacm.club.validators.ClubValidator;
import com.gsacm.helpers.dto.ClubDTO;
import com.gsacm.helpers.exceptions.EntityNotFoundException;
import com.gsacm.helpers.exceptions.ErrorCodes;
import com.gsacm.helpers.exceptions.FileStorageException;
import com.gsacm.helpers.exceptions.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;




/**
 * Le service Implementation de type Clube.
 */
@Service
@Slf4j
public class ClubServiceImpl implements ClubService {

    /**
     * Le Clube dao.
     */
    private final IClubDAO iClubDAO;

    private final Path fileStorageLocation;
    private final Path fileDefautlLocation;

    @Autowired
    public ClubServiceImpl(IClubDAO iClubDAO, FileStorageProperties fileStorageProperties) {
        this.iClubDAO = iClubDAO;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.fileDefautlLocation = Paths.get(fileStorageProperties.getDefaultDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new FileStorageException("Could not create the directory to upload", ex);
        }
    }


    /**
     * Nouveau club clube dto.
     *
     * @param dto  le dto
     * @param file le fichier téléchargé
     * @return le Clube dto
     */
    @Override
    public ClubDTO addClub(ClubDTO dto, MultipartFile file) {
        // Validate the fields
        List<String> errors = ClubValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Club invalide: {}", dto);
            throw new InvalidEntityException("Club invalide", ErrorCodes.INVALID_INPUT, errors);
        }
        String logoUrl;
        if (file != null && !file.isEmpty() && file.getOriginalFilename() != null) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                logoUrl = targetLocation.toString(); // Use the file path as the logo URL
            } catch (IOException ex) {
                throw new FileStorageException("Impossible de stocker le fichier " + fileName + ". Veuillez réessayer!", ex);
            }
        } else {
            logoUrl = this.fileDefautlLocation.toString() + File.separator + "no-image.png"; // Use the fileDefaultLocation as the default logo URL
        }
        // Convert ClubDTO to Club
        Club club = ClubDTOConverter.toEntity(dto);
        // SetlogoUrl
        club.setLogoUrl(logoUrl);
        // Set the creation date
        club.setCreationDate(LocalDateTime.now());
        // Set the status to "Active"
        club.setStatus("Active");
        // Save the club
        Club savedClub = iClubDAO.save(club);
        return ClubDTOConverter.fromEntity(savedClub);
    }


    /**
     * Mettre à jour le club par id clube dto.
     *
     * @param dto    le dto
     * @param clubId l'identifiant du clube
     * @return le Clube dto
     */
    @Override
    public ClubDTO updateClubByID(ClubDTO dto, Long clubId, MultipartFile file) {
        // Verify if clubId is not empty
        if (clubId == null) {
            log.error("L'identifiant du club est nul");
            throw new InvalidEntityException("L'identifiant du club est nul", ErrorCodes.INVALID_INPUT);
        }

        // Validate the fields
        List<String> errors = ClubValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Le club n'est pas valide: {}", dto);
            throw new InvalidEntityException("Le club n'est pas valide", ErrorCodes.INVALID_INPUT, errors);
        }

        Club existingClub = iClubDAO.findByIdAndStatus(clubId, "Active")
                .orElseThrow(() -> {
                    log.error("Club actif avec ID {} introuvable", clubId);
                    return new EntityNotFoundException("Club actif avec ID: " + clubId + " introuvable",
                            ErrorCodes.RESOURCE_NOT_FOUND);
                });

        // Check if a file is provided for logo update
        if (file != null && !file.isEmpty() && file.getOriginalFilename() != null) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                String logoUrl = targetLocation.toString();
                existingClub.setLogoUrl(logoUrl);
            } catch (IOException ex) {
                throw new FileStorageException("Impossible de stocker le fichier " + fileName + ". Veuillez réessayer!", ex);
            }
        }

        // Check if logoUrl is empty, set the default value if it is
        if (Objects.isNull(existingClub.getLogoUrl()) || existingClub.getLogoUrl().isEmpty()) {
            String defaultLogoUrl = this.fileDefautlLocation.toString() + File.separator + "no-image.png"; // Use the fileDefaultLocation as the default logo URL;
            existingClub.setLogoUrl(defaultLogoUrl);
        }
        // Update the fields of the existing club with the values from the DTO
        existingClub.setName(dto.getName());
        existingClub.setDescription(dto.getDescription());
        existingClub.setLastModifiedDate(LocalDateTime.now());

        // Save the updated club
        Club updatedClub = iClubDAO.save(existingClub);

        return ClubDTOConverter.fromEntity(updatedClub);
    }
    /**
     * Trouver un club par identifiant club dto.
     *
     * @param clubId l'identifiant du clube
     * @return le dto du clube
     */
    @Override
    public ClubDTO findClubByID(Long clubId) {
        // Vérifier si clubId n'est pas vide
        if (clubId == null) {
            log.error("L'identifiant du clube est nul");
            throw new InvalidEntityException("L'identifiant du clube est nul", ErrorCodes.INVALID_INPUT);
        }

        // Récupérer le clube
        return iClubDAO.findById(clubId)
                .filter(club -> club.getStatus().equals("Active")) // Filter for active clubs
                .map(ClubDTOConverter::fromEntity)
                .orElseThrow(() -> {
                    log.error("Clube avec l'identifiant donné est {} introuvable", clubId);
                    return new EntityNotFoundException("Clube avec l'identifiant: " + clubId + " n'a pas été trouvé",
                            ErrorCodes.RESOURCE_NOT_FOUND);
                });
    }

    /**
     * Trouver un club par nom clube dto.
     *
     * @param clubName le nom du clube
     * @return le dto du clube
     */
    @Override
    public ClubDTO findClubByName(String clubName) {
        // Vérifiez si Nom clube n'est pas vide
        if (clubName == null) {
            log.error("Le nom du clube est nul");
            throw new InvalidEntityException("Le nom du clube est nul", ErrorCodes.INVALID_INPUT);
        }
        // Récupérer le club par nom
        return iClubDAO.findByName(clubName)
                .filter(club -> club.getStatus().equals("Active")) // Filtrer les clubs actifs
                .map(ClubDTOConverter::fromEntity)
                .orElseThrow(() -> {
                    log.error("Le club avec le nom donné est {} introuvable", clubName);
                    return new EntityNotFoundException("Le club avec le nom donné: " + clubName + " n'a pas été trouvé",
                            ErrorCodes.RESOURCE_NOT_FOUND);
                });
    }

    /**
     * Trouver la liste de tous les clubes.
     *
     * @return la liste
     */
    @Override
    public List<ClubDTO> findAllClubs() {
        List<Club> clubs = new ArrayList<>(iClubDAO.findAllByStatus("Active"));
        if (clubs.isEmpty()) {
            log.info("Aucun clube trouvé");
        } else {
            log.info("clubes {} trouvés", clubs.size());
        }

        return clubs.stream()
                .map(ClubDTOConverter::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Supprimer le club par id club dto.
     *
     * @param clubId l'identifiant du clube
     * @return le dto du clube
     */
    @Override
    public ClubDTO deleteClubByID(Long clubId) {
        // Vérifiez si clubId n'est pas vide
        if (clubId == null) {
            log.error("L'identifiant du club est nul");
            throw new InvalidEntityException("L'identifiant du club est nul", ErrorCodes.INVALID_INPUT);
        }

        // Vérifier si le club existe par ID
        Club existingClub = iClubDAO.findByIdAndStatus(clubId, "Active")
                .orElseThrow(() -> {
                    log.error("Club avec l'identifiant donné {} introuvable", clubId);
                    return new EntityNotFoundException("Club avec l'identifiant: " + clubId + " n'a pas été trouvé",
                            ErrorCodes.RESOURCE_NOT_FOUND);
                });

        // Définir la date de suppression
        existingClub.setDeletedDate(LocalDateTime.now());
        // Mettre à jour le statut du club existant
        existingClub.setStatus("Inactive");
        // Mettre à jour le club
        Club deletedClub = iClubDAO.save(existingClub);
        return ClubDTOConverter.fromEntity(deletedClub);
    }

}
