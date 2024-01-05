package br.com.alura.forum.config

import br.com.alura.forum.model.Role
import br.com.alura.forum.service.UsuarioService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey


@Component
class JWTUtil (
    @Value("\${jwt.secret}")
    private var secret: String,
    private val usuarioService: UsuarioService
) {

    private val expiration: Long = 60000
    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(username: String, authorities: List<Role>): String? {
        return Jwts.builder()
                .subject(username)
                .claim("role", authorities)
                .expiration(Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    fun isValid(jwt: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun getAuthentication(jwt: String?): Authentication {
        val username = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).body.subject
        val user = usuarioService.loadUserByUsername(username);
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }

}