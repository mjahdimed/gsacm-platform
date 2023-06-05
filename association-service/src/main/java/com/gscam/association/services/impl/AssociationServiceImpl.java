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

package com.gscam.association.services.impl;

import com.gsacm.helpers.dto.AssociationDTO;
import com.gsacm.helpers.exceptions.EntityNotFoundException;
import com.gsacm.helpers.exceptions.ErrorCodes;
import com.gsacm.helpers.exceptions.FileStorageException;
import com.gsacm.helpers.exceptions.InvalidEntityException;
import com.gscam.association.dao.IAssociationDAO;
import com.gscam.association.models.Association;
import com.gscam.association.services.AssociationService;
import com.gscam.association.utils.AssociationDTOConverter;
import com.gscam.association.utils.FileStorageProperties;
import com.gscam.association.validators.AssociationValidator;
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
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class AssociationServiceImpl implements AssociationService {


    /**
     * L'Association dao.
     */
    private final IAssociationDAO iAssociationDAO;

    private final Path fileStorageLocation;
    private final Path fileDefautlLocation;

    @Autowired
    public AssociationServiceImpl(IAssociationDAO iAssociationDAO, FileStorageProperties fileStorageProperties) {
        this.iAssociationDAO = iAssociationDAO;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.fileDefautlLocation = Paths.get(fileStorageProperties.getDefaultDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new FileStorageException("Impossible de créer le répertoire à télécharger", ex);
        }
    }

    /**
     * Nouvelle association association dto.
     *
     * @param dto  le dto
     * @param file le fichier téléchargé
     * @return l'associations  dto
     * :: Insérer un nouvelle association ::
     */
    @Override
    public AssociationDTO addAssociation(AssociationDTO dto, MultipartFile file) {
        // Validate the fields
        List<String> errors = AssociationValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("association invalide: {}", dto);
            throw new InvalidEntityException("association invalide", ErrorCodes.INVALID_INPUT, errors);
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
        // Convert AssociationDTO to association
        Association association = AssociationDTOConverter.toEntity(dto);
        // SetlogoUrl
        association.setLogoUrl(logoUrl);
        // Set the creation date
        association.setCreationDate(LocalDateTime.now());
        // Set the status to "Active"
        association.setStatus("Active");
        // Save the association
        Association savedAssociation = iAssociationDAO.save(association);
        return AssociationDTOConverter.fromEntity(savedAssociation);
    }

    /**
     * Mettre à jour le association par id association dto.
     *
     * @param dto     le dto
     * @param assocId l'identifiant de l'association
     * @return l'associations  dto
     * :: Mettre à jour l'association  par ID ::
     */
    @Override
    public AssociationDTO updateAssociationByID(AssociationDTO dto, Long assocId, MultipartFile file) {
        // Verify if assocId is not empty
        if (assocId == null) {
            log.error("L'identifiant de l'association est nul");
            throw new InvalidEntityException("L'identifiant de l'association est nul", ErrorCodes.INVALID_INPUT);
        }

        // Validate the fields
        List<String> errors = AssociationValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("l'Association n'est pas valide: {}", dto);
            throw new InvalidEntityException("l'Association n'est pas valide", ErrorCodes.INVALID_INPUT, errors);
        }

        Association existingAssociation = iAssociationDAO.findByIdAndStatus(assocId, "Active")
                .orElseThrow(() -> {
                    log.error("l'Association Actif avec ID {} introuvable", assocId);
                    return new EntityNotFoundException("l'Association Actif avec ID: " + assocId + " introuvable",
                            ErrorCodes.RESOURCE_NOT_FOUND);
                });

        // Check if a file is provided for logo update
        if (file != null && !file.isEmpty() && file.getOriginalFilename() != null) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                String logoUrl = targetLocation.toString();
                existingAssociation.setLogoUrl(logoUrl);
            } catch (IOException ex) {
                throw new FileStorageException("Impossible de stocker le fichier " + fileName + ". Veuillez réessayer!", ex);
            }
        }

        // Check if logoUrl is empty, set the default value if it is
        if (Objects.isNull(existingAssociation.getLogoUrl()) || existingAssociation.getLogoUrl().isEmpty()) {
            String defaultLogoUrl = this.fileDefautlLocation.toString() + File.separator + "no-image.png"; // Use the fileDefaultLocation as the default logo URL;
            existingAssociation.setLogoUrl(defaultLogoUrl);
        }
        // Update the fields of the existing association with the values from the DTO
        existingAssociation.setName(dto.getName());
        existingAssociation.setDescription(dto.getDescription());
        existingAssociation.setLastModifiedDate(LocalDateTime.now());

        // Save the updated association
        Association updatedAssociation = iAssociationDAO.save(existingAssociation);

        return AssociationDTOConverter.fromEntity(updatedAssociation);
    }


    /**
     * Trouver une association  par identifiant association dto.
     *
     * @param assocId l'identifiant de l'association
     * @return le dto de l'association
     * :: Trouver une association e par ID ::
     */
    @Override
    public AssociationDTO findAssociationByID(Long assocId) {
        if (assocId == null) {
            throw new InvalidEntityException("L'identifiant de l' association est nul", ErrorCodes.INVALID_INPUT);
        }

        Optional<Association> optionalAssociation = iAssociationDAO.findByIdAndStatus(assocId, "Active");
        Association association = optionalAssociation.orElseThrow(() -> new EntityNotFoundException(
                "l'Association avec l'identifiant: " + assocId + " n'a pas été trouvé", ErrorCodes.RESOURCE_NOT_FOUND));

        return AssociationDTOConverter.fromEntity(association);
    }

    @Override
    public AssociationDTO findAssociationByName(String assocName) {
        // Vérifiez si Nom de l' association n'est pas vide
        if (assocName == null) {
            log.error("Le nom de l' association est nul");
            throw new InvalidEntityException("Le nom de l' association est nul", ErrorCodes.INVALID_INPUT);
        }
        // Récupérer L' association par nom
        return iAssociationDAO.findByName(assocName)
                .filter(association -> association.getStatus().equals("Active")) // Filtrer les clubs actifs
                .map(AssociationDTOConverter::fromEntity)
                .orElseThrow(() -> {
                    log.error("L' association avec le nom donné est {} introuvable", assocName);
                    return new EntityNotFoundException("L' association avec le nom donné: " + assocName + " n'a pas été trouvé",
                            ErrorCodes.RESOURCE_NOT_FOUND);
                });
    }


    /**
     * Trouver la liste de tous les Associations.
     *
     * @return la liste
     * :: Obtenir tous les associations ::
     */
    @Override
    public List<AssociationDTO> findAllAssociations() {
        List<Association> associations = new ArrayList<>(iAssociationDAO.findAllByStatus("Active"));
        if (associations.isEmpty()) {
            log.info("Aucune association trouvé");
        } else {
            log.info("Associations {} trouvés", associations.size());
        }

        return associations.stream()
                .map(AssociationDTOConverter::fromEntity)
                .collect(Collectors.toList());
    }


    /**
     * Supprimer le club par id association dto.
     *
     * @param assocId l'identifiant de l'association
     * @return le dto de l'association
     * :: Supprimer l'associations  par ID ::
     */

    @Override
    public AssociationDTO deleteAssociationByID(Long assocId) {
        // Vérifiez si assocId n'est pas vide
        if (assocId == null) {
            log.error("L'identifiant de l'association est nul");
            throw new InvalidEntityException("L'identifiant de l'association est nul", ErrorCodes.INVALID_INPUT);
        }

        // Vérifier si le Association existe par ID
        Association existingAssociation = iAssociationDAO.findByIdAndStatus(assocId, "Active")
                .orElseThrow(() -> {
                    log.error("Association avec l'identifiant donné {} introuvable", assocId);
                    return new EntityNotFoundException("Association avec l'identifiant: " + assocId + " n'a pas été trouvé",
                            ErrorCodes.RESOURCE_NOT_FOUND);
                });

        // Définir la date de suppression
        existingAssociation.setDeletedDate(LocalDateTime.now());
        // Mettre à jour le statut du Association existant
        existingAssociation.setStatus("Inactive");
        // Mettre à jour l'Association
        Association deletedAssociation = iAssociationDAO.save(existingAssociation);
        return AssociationDTOConverter.fromEntity(deletedAssociation);
    }


}
