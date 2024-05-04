package com.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParser;


import javax.crypto.SecretKey;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

public class JWTAuthFilter implements Filter {

   
    
    private static final String SECRET_KEY = "asfadsfgadkgfsdkgflsdjmgkdfsngkldfngmkdlfngdklfgndfkngdgkldfnnld";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Obteniendo la URL de la solicitud
        String url = request.getRequestURI();      

     // Excluir las URLs específicas
     if (url.equals("/ProyectoTecnicatura3.0/login.xhtml") || url.equals("/ProyectoTecnicatura3.0/rest/todos")|| url.equals("/ProyectoTecnicatura3.0/rest/todosHistorico")|| url.equals("/ProyectoTecnicatura3.0/rest/agregar")|| url.equals("/ProyectoTecnicatura3.0/rest/agregarHistorico")|| url.equals("/ProyectoTecnicatura3.0/rest/agregarAlimentacion")) {
         filterChain.doFilter(request, response);
         return;
     }
        
        // Excluir las solicitudes a archivos CSS en el directorio /layout/
        if (url.startsWith("/ProyectoTecnicatura3.0/layout/") && url.endsWith(".css")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Obtener el token JWT de la sesión del usuario
        HttpSession session = request.getSession(false);
        String jwtToken = null;
        if (session != null) {
            jwtToken = (String) session.getAttribute("jwtToken");
        }

        // Agregar el token JWT a los encabezados de la solicitud
        if (jwtToken != null) {
            request.setAttribute("jwtToken", jwtToken); 
        }
        
        // Verificar si hay un token JWT presente en la solicitud
        if (jwtToken != null) {
            // Verificar si el token incluye el prefijo "Bearer"
            if (!jwtToken.startsWith("Bearer ")) {
                // Agregar el prefijo "Bearer" al token JWT
                jwtToken = "Bearer " + jwtToken;
            }
        
        try {
        	// Extraer el token JWT sin el prefijo "Bearer"
            String token = jwtToken.substring(7);

            // Validar el token JWT
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Continuar con la cadena de filtros si la validación es exitosa
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // Si hay algún error al validar el token JWT, devolver un error 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, "Acceso no autorizado: Token JWT inválido");
        }
    } else {
        // Si no se proporciona un token JWT en la solicitud, devolver un error 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, "Acceso no autorizado: Token JWT no proporcionado");
    }
    }

    @Override
    public void destroy() {
    }
}

