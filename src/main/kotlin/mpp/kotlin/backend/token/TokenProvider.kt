import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.security.SecureRandom
import java.util.*

object TokenProvider {

    private val key: ByteArray = generateSecureSecretKey()

    private val invalidTokens: MutableSet<String> = mutableSetOf()

    private fun generateSecureSecretKey(): ByteArray {
        val secureRandom = SecureRandom()
        val keyBytes = ByteArray(64)
        secureRandom.nextBytes(keyBytes)
        return keyBytes
    }

    fun generateToken(email: String, role: String): String {
        val now = Date()
        val expiryDate = Date(now.time + 3600000)
        val claims: Claims = Jwts.claims().setSubject(email)
        claims["role"] = role

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expiryDate)
            .signWith(Keys.hmacShaKeyFor(key), SignatureAlgorithm.HS512).compact()
    }

    fun validateToken(token: String): Boolean {
        return !invalidTokens.contains(token) && try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key)).build().parseClaimsJws(token)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun getRoleFromToken(token: String): String? {
        val claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key)).build().parseClaimsJws(token).body
        return claims["role"] as? String
    }

    fun getEmailFromToken(token: String): String? {
        val claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key)).build().parseClaimsJws(token).body
        return claims.subject
    }

    fun invalidateToken(token: String) {
        invalidTokens.add(token)
    }
}
