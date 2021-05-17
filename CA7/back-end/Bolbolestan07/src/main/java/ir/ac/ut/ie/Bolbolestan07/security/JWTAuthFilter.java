package ir.ac.ut.ie.Bolbolestan07.security;

//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.sql.SQLException;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import io.jsonwebtoken.*;
//import ir.ac.ut.ie.Bolbolestan07.exceptions.BadCharactersException;
//import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Student.Student;
//import ir.ac.ut.ie.Bolbolestan07.repository.BolbolestanRepository;
//import ir.ac.ut.ie.Bolbolestan07.utils.Utils;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//public class JWTAuthFilter extends OncePerRequestFilter {
//
//    private static final String HEADER = "Authorization";
//    private static final String PREFIX = "Bearer ";
//    private static final String SECRET = "bolbolestan";
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        try {
//            if (checkJWTToken(request, response)) {
//                Claims claims = validateToken(request);
//                if(Utils.hasIllegalChars(claims.getSubject())){
//                    throw new BadCharactersException();
//                }
//                if (claims.get("authorities") != null) {
//                    Student student = BolbolestanRepository.getInstance().getStudent(claims.getSubject());
//                    setUpSpringAuthentication(claims);
//                } else {
//                    SecurityContextHolder.clearContext();
//                }
//            } else {
//                SecurityContextHolder.clearContext();
//            }
//            chain.doFilter(request, response);
//        } catch (UnsupportedJwtException | MalformedJwtException | BadCharactersException e) {
//            System.out.println(e.getMessage());
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
//        } /*catch (SQLException e) {
//            SecurityContextHolder.clearContext();
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
//        }*/
//    }
//
//    public static String getJWTToken(String username) {
//        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
//                .commaSeparatedStringToAuthorityList("ROLE_USER");
//
//        String token = Jwts
//                .builder()
//                .setId("softtekJWT")
//                .setSubject(username)
//                .claim("authorities",
//                        grantedAuthorities.stream()
//                                .map(GrantedAuthority::getAuthority)
//                                .collect(Collectors.toList()))
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 3000))
//                .signWith(SignatureAlgorithm.HS512,
//                        SECRET.getBytes()).compact();
//
//        return "Bearer " + token;
//    }
//
//    private Claims validateToken(HttpServletRequest request) {
//        String jwtToken = getToken(request);
//        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
//    }
//
//    private static String getToken(HttpServletRequest request) {
//        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
//        return jwtToken;
//    }
//
//    /**
//     * Authentication method in Spring flow
//     *
//     * @param claims
//     */
//    private void setUpSpringAuthentication(Claims claims) {
//        @SuppressWarnings("unchecked")
////                todo check user email for authorization
//        List<String> authorities = (List<String>) claims.get("authorities");
//
//        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
//                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//    }
//
//    private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
//        String authenticationHeader = request.getHeader(HEADER);
//        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
//            return false;
//        return true;
//    }
//}


import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Student.StudentManager;
import ir.ac.ut.ie.Bolbolestan07.repository.Student.StudentMapper;
import ir.ac.ut.ie.Bolbolestan07.utils.JWTUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class JWTAuthFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI();
        String method = request.getMethod();

        if((url.equals("/user") && method.equals("POST")) || url.equals("/auth/login") || url.equals("auth/signup")) //????
            chain.doFilter(request, response);
        else {
            String token = request.getHeader("Authorization");
            if(token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("You have not authorized yet!");
            }
            else {
                String username = JWTUtils.verifyJWT(token);
                if(username == null) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().println("The JWT token is invalidated!");
                }
                else {
                    try {
                        Student student = new StudentMapper().find(username);
                        request.setAttribute("student", student.getEmail());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    chain.doFilter(request, response);
                }
            }
        }
    }
}
