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

package com.gsacm.club;


import com.gsacm.club.utils.LogFolderInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.sql.*;

@SpringBootApplication
@EnableDiscoveryClient
public class ClubServiceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClubServiceApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Démarrage de l'application Club Service");

        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
        String dbPassword = "admin";
        String dbUsername = "admin";

        Connection connection = null;
        Statement statement = null;
        try {
            System.out.println("Création de la base de données si elle n'existe pas...");
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            statement = connection.createStatement();

            // Check if the database already exists
            ResultSet resultSet = connection.getMetaData().getCatalogs();
            boolean databaseExists = false;
            while (resultSet.next()) {
                String databaseName = resultSet.getString(1);
                if (databaseName.equalsIgnoreCase("club_db")) {
                    databaseExists = true;
                    break;
                }
            }
            resultSet.close();

            if (!databaseExists) {
                statement.executeUpdate("CREATE DATABASE club_db");
                System.out.println("Base de données créée.");
            } else {
                System.out.println("La base de données existe déjà. Ignorer la création.");
            }

            // Grant privileges to the user on the newly created database
            Connection clubDbConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/club_db", dbUsername, dbPassword);
            Statement clubDbStatement = clubDbConnection.createStatement();
            clubDbStatement.executeUpdate("GRANT ALL PRIVILEGES ON DATABASE club_db TO " + dbUsername);
            System.out.println("Privilèges accordés sur club_db.");

            clubDbStatement.close();
            clubDbConnection.close();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    System.out.println("Déclaration fermée.");
                }
                if (connection != null) {
                    System.out.println("Connexion fermée.");
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        SpringApplication.run(ClubServiceApplication.class, args);
    }


    @Bean
    public LogFolderInitializer logFolderInitializer() {
        return new LogFolderInitializer();
    }
}
