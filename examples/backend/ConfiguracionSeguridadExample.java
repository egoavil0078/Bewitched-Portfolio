package com.kevin.marketplace.configuracion;

import com.kevin.marketplace.autentificacion.jwt.JwtAuthFilter;
import tools.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class ConfiguracionSeguridadExample {

    private final JwtAuthFilter jwtAuthFilter;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .exceptionHandling(errors -> errors

                        .authenticationEntryPoint((request, response, ex) ->
                                SecurityErrorWriter.write(
                                        response,
                                        objectMapper,
                                        401,
                                        "AUTHENTICATION_REQUIRED",
                                        "Debes iniciar sesión"
                                )
                        )

                        .accessDeniedHandler((request, response, ex) ->
                                SecurityErrorWriter.write(
                                        response,
                                        objectMapper,
                                        403,
                                        "ACCESS_DENIED",
                                        "No tienes permisos para realizar esta acción"
                                )
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // AUTENTICACIÓN
                        // =========================
                        .requestMatchers(
                                "/api/autentificacion/**"
                        ).permitAll()


                        // =========================
                        // STRIPE WEBHOOK
                        // Solo POST puede acceder
                        // sin autenticación JWT.
                        // Stripe valida mediante firma.
                        // =========================
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/pagos/stripe/webhook"
                        ).permitAll()


                        // =========================
                        // HEALTH CHECK AWS / ALB
                        // =========================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/actuator/health"
                        ).permitAll()


                        // =========================
                        // CATÁLOGO PÚBLICO
                        // =========================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/productos/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/categorias/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/paises-envio/activos"
                        ).permitAll()


                        // =========================
                        // IMÁGENES / MEDIA PÚBLICA
                        // Necesario para que productos
                        // e imágenes del catálogo puedan
                        // verse sin iniciar sesión.
                        // =========================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/media/**"
                        ).permitAll()


                        // =========================
                        // ASSETS PARA EMAIL
                        // =========================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/email-assets/**"
                        ).permitAll()


                        // =========================
                        // PÁGINAS LEGALES PÚBLICAS
                        // Google Play y usuarios
                        // pueden consultarlas sin JWT.
                        // =========================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/privacidad",
                                "/eliminar-cuenta"
                        ).permitAll()


                        // =========================
                        // USUARIOS
                        // Requiere estar autenticado.
                        // Los endpoints exclusivamente
                        // ADMIN tienen además
                        // @PreAuthorize("hasRole('ADMIN')")
                        // =========================
                        .requestMatchers(
                                "/api/usuarios/**"
                        ).authenticated()


                        // =========================
                        // RESTO DEL BACKEND
                        // Requiere JWT
                        // =========================
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }
}