package com.schoolmanagement.authentication.securite;

import com.schoolmanagement.authentication.entity.Permission;
import com.schoolmanagement.authentication.entity.StatutUtilisateur;
import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class FiltreSecurite extends OncePerRequestFilter {

  private final ServiceJeton serviceJeton;
  private final UtilisateurRepository utilisateurRepository;

  public FiltreSecurite(ServiceJeton serviceJeton, UtilisateurRepository utilisateurRepository) {

    this.serviceJeton = serviceJeton;
    this.utilisateurRepository = utilisateurRepository;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorization = request.getHeader("Authorization");

    // Pas de JWT : on laisse Spring Security décider
    if (authorization == null || !authorization.startsWith("Bearer ")) {

      filterChain.doFilter(request, response);
      return;
    }

    String token = authorization.substring(7);

    try {
      // Vérifier le JWT
      Claims claims = serviceJeton.verifierToken(token);

      // Récupérer l'ID de l'utilisateur
      Long utilisateurId = Long.valueOf(claims.getSubject());

      // Récupérer l'utilisateur en base
      Utilisateur utilisateur =
          utilisateurRepository.findWithPermissionsById(utilisateurId).orElseThrow();

      if (utilisateur.getStatut() != StatutUtilisateur.ACTIF) {
        throw new SecurityException("Compte utilisateur inactif");
      }

      List<SimpleGrantedAuthority> authorities = new ArrayList<>();

      // Ajouter le rôle
      authorities.add(new SimpleGrantedAuthority("ROLE_" + utilisateur.getTypeRole().name()));

      // Ajouter les permissions
      for (Permission permission : utilisateur.getPermissions()) {

        authorities.add(new SimpleGrantedAuthority(permission.getNomPermission()));
      }

      // Créer l'authentification
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(utilisateurId, null, authorities);

      // Enregistrer l'utilisateur connecté
      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (RuntimeException exception) {
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
  }
}
