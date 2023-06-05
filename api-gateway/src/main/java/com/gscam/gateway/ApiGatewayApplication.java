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

package com.gscam.gateway;


import com.gscam.gateway.utils.LogFolderInitializer;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

/**
 * Le type Application de gateway API.
 */
@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "API Gateway", version = "1.0", description = "Documentation API Gateway v1.0"))
public class ApiGatewayApplication {

    /**
     * Le LOGGER constant.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiGatewayApplication.class);

    /**
     * Le point d'entrée de l'application.
     *
     * @param args les arguments d'entrée
     */
    public static void main(String[] args) {
        LOGGER.info("Démarrage de l'application API Gateway");
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    /**
     * Localisateur des url.
     *
     * @param builder le constructeur
     * @return le localisateur d'url
     */
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Les URL du service clube
                .route(r -> r.path("/club-service/v3/api-docs").and().method(HttpMethod.GET).uri("lb://club-service"))
                .route(r -> r.path("/api/v1/clubs/add").and().method(HttpMethod.POST).uri("lb://club-service"))
                .route(r -> r.path("/api/v1/clubs/all").and().method(HttpMethod.POST).uri("lb://club-service"))
                .route(r -> r.path("/api/v1/clubs/{clubId}").and().method(HttpMethod.GET).uri("lb://club-service"))
                .route(r -> r.path("/api/v1/clubs/name/{clubName}").and().method(HttpMethod.GET).uri("lb://club-service"))
                .route(r -> r.path("/api/v1/clubs/{clubId}").and().method(HttpMethod.PUT).uri("lb://club-service"))
                .route(r -> r.path("/api/v1/clubs/{clubId}").and().method(HttpMethod.PATCH).uri("lb://club-service"))

                // Les URL du service association
                .route(r -> r.path("/association-service/v3/api-docs").and().method(HttpMethod.GET).uri("lb://association-service"))
                .route(r -> r.path("/api/v1/associations/add").and().method(HttpMethod.POST).uri("lb://association-service"))
                .route(r -> r.path("/api/v1/associations/all").and().method(HttpMethod.POST).uri("lb://association-service"))
                .route(r -> r.path("/api/v1/associations/{assocId}").and().method(HttpMethod.GET).uri("lb://association-service"))
                .route(r -> r.path("/api/v1/associations/name/{assocName}").and().method(HttpMethod.GET).uri("lb://association-service"))
                .route(r -> r.path("/api/v1/associations/{assocId}").and().method(HttpMethod.PUT).uri("lb://association-service"))
                .route(r -> r.path("/api/v1/associations/{assocId}").and().method(HttpMethod.PATCH).uri("lb://association-service"))
                .build();
    }

    /**
     * Executeur la ligne de command.
     *
     * @param routeLocator le localisateur d'url
     * @return la ligne de command.
     */
    @Bean
    public CommandLineRunner commandLineRunner(RouteLocator routeLocator) {
        return args -> {
            // Imprimer des les url réels
            routeLocator.getRoutes().subscribe(route -> LOGGER.info("Effective Route: {}", route));
        };
    }

    /**
     * Initialiseur de dossier de journaux.
     *
     * @return l'initialiseur de dossier de journal
     */
    @Bean
    public LogFolderInitializer logFolderInitializer() {
        return new LogFolderInitializer();
    }
}
