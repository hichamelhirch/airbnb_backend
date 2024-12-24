package com.hicham.airbnb_clone_back.infrastructure.config;

import com.hicham.airbnb_clone_back.user.Domain.Authority;
import com.hicham.airbnb_clone_back.user.Domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class to handle security-related operations, such as mapping OAuth2 attributes to a user object,
 * extracting roles from claims, and verifying user authorities.
 */
public class SecurityUtils {

    // Constants representing specific roles in the system
    public static final String ROLE_TENANT = "ROLE_TENANT"; // Role for tenants
    public static final String ROLE_LANDLORD = "ROLE_LANDLORD"; // Role for landlords

    // Custom namespace used for storing roles in JWT claims
    public static final String CLAIMS_NAMESPACE = "https://www.hichamelhirch.vercel.app/roles";

    /**
     * Maps OAuth2 attributes from the authentication token to a custom User object.
     *
     * @param attributes Map of attributes retrieved from the OAuth2 provider (e.g., Auth0).
     * @return A {@link User} object populated with information extracted from the attributes.
     */
    public static User mapOauth2AttributesToUser(Map<String, Object> attributes) {
        User user = new User();

        // Extract unique user identifier (sub)
        String sub = String.valueOf(attributes.get("sub"));

        // Extract username if available
        String username = null;
        if (attributes.get("preferred_username") != null) {
            username = ((String) attributes.get("preferred_username")).toLowerCase();
        }

        // Set user's first name (using given_name or nickname as fallback)
        if (attributes.get("given_name") != null) {
            user.setFirstName(((String) attributes.get("given_name")));
        } else if (attributes.get("nickname") != null) {
            user.setFirstName(((String) attributes.get("nickname")));
        }

        // Set user's last name
        if (attributes.get("family_name") != null) {
            user.setLastName(((String) attributes.get("family_name")));
        }

        // Set user's email address (fallbacks included for custom scenarios)
        if (attributes.get("email") != null) {
            user.setEmail(((String) attributes.get("email")));
        } else if (sub.contains("|") && (username != null && username.contains("@"))) {
            user.setEmail(username);
        } else {
            user.setEmail(sub);
        }

        // Set user's profile picture URL if available
        if (attributes.get("picture") != null) {
            user.setImageUrl(((String) attributes.get("picture")));
        }

        // Extract roles from the custom claims namespace
        if (attributes.get(CLAIMS_NAMESPACE) != null) {
            List<String> authoritiesRaw = (List<String>) attributes.get(CLAIMS_NAMESPACE);

            // Map raw role strings to Authority objects
            Set<Authority> authorities = authoritiesRaw.stream()
                    .map(authority -> {
                        Authority auth = new Authority();
                        auth.setName(authority);
                        return auth;
                    }).collect(Collectors.toSet());

            // Set the authorities in the user object
            user.setAuthorities(authorities);
        }

        return user;
    }

    /**
     * Extracts authorities (roles) from JWT claims and maps them to Spring Security's {@link SimpleGrantedAuthority}.
     *
     * @param claims JWT claims containing the roles.
     * @return A list of {@link SimpleGrantedAuthority} objects representing user roles.
     */
    public static List<SimpleGrantedAuthority> extractAuthorityFromClaims(Map<String, Object> claims) {
        return mapRolesToGrantedAuthorities(getRolesFromClaims(claims));
    }

    /**
     * Retrieves roles from JWT claims using the predefined custom namespace.
     *
     * @param claims JWT claims.
     * @return A collection of roles extracted from the claims.
     */
   /* private static Collection<String> getRolesFromClaims(Map<String, Object> claims) {
        return (List<String>) claims.get(CLAIMS_NAMESPACE);
    }*/
    private static Collection<String> getRolesFromClaims(Map<String, Object> claims) {
        if (claims.containsKey(CLAIMS_NAMESPACE)) {
            return (List<String>) claims.get(CLAIMS_NAMESPACE);
        }
        return Collections.emptyList(); // Retourne une liste vide si la clé n'existe pas
    }

    /**
     * Maps a collection of role strings to a list of {@link SimpleGrantedAuthority} objects.
     *
     * @param roles Collection of role strings.
     * @return A list of {@link SimpleGrantedAuthority} objects.
     */
 /*   private static List<SimpleGrantedAuthority> mapRolesToGrantedAuthorities(Collection<String> roles) {
        return roles.stream()
                .filter(role -> role.startsWith("ROLE_")) // Filter roles that start with "ROLE_"
                .map(SimpleGrantedAuthority::new) // Map each role to a SimpleGrantedAuthority
                .toList();
    }
*/

    private static List<SimpleGrantedAuthority> mapRolesToGrantedAuthorities(Collection<String> roles) {
        if (roles == null) {
            return Collections.emptyList(); // Si roles est nul, retourne une liste vide
        }
        return roles.stream()
                .filter(role -> role.startsWith("ROLE_"))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    /**
     * Checks if the currently authenticated user has any of the specified authorities.
     *
     * @param authorities Array of authority strings to check.
     * @return True if the user has at least one of the specified authorities, false otherwise.
     */
    public static boolean hasCurrentUserAnyOfAuthorities(String... authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verify if any of the authorities match the user's granted authorities
        return (authentication != null && getAuthorities(authentication)
                .anyMatch(authority -> Arrays.asList(authorities).contains(authority)));
    }

    /**
     * Extracts granted authorities (roles) from the authentication object.
     * Handles both JwtAuthenticationToken and standard authentication objects.
     *
     * @param authentication Authentication object from Spring Security.
     * @return A stream of authority strings.
     */
    private static Stream<String> getAuthorities(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities =
                authentication instanceof JwtAuthenticationToken jwtAuthenticationToken ?
                        // Extract authorities from JWT claims
                        extractAuthorityFromClaims(jwtAuthenticationToken.getToken().getClaims()) :
                        // Otherwise, use directly the authentication's authorities
                        authentication.getAuthorities();

        // Convert authorities to a stream of strings
        return authorities.stream().map(GrantedAuthority::getAuthority);
    }
}
