import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.*
import java.security.SecureRandom

class TokenProvider() {

    private val key: ByteArray = generateSecureSecretKey()

    private fun generateSecureSecretKey(): ByteArray {
        val secureRandom = SecureRandom()
        val keyBytes = ByteArray(64)
        secureRandom.nextBytes(keyBytes)
        return keyBytes
    }

    fun generateToken(email: String): String {
        val now = Date()
        val expiryDate = Date(now.time + 3600000)

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(Keys.hmacShaKeyFor(key), SignatureAlgorithm.HS512)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key)).build().parseClaimsJws(token)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun getEmailFromToken(token: String): String? {
        val claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key)).build().parseClaimsJws(token).body
        return claims.subject
    }
}
