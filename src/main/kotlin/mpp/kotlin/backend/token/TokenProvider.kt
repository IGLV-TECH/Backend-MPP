import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.*

class TokenProvider() {

    private val key: ByteArray = generateSecureSecretKey()

//    private fun generateSecureSecretKey(): ByteArray {
//        val secureRandom = SecureRandom()
//        println("prim")
//        println(secureRandom)
//        val keyBytes = ByteArray(64)
//        secureRandom.nextBytes(keyBytes)
//        println("ceva")
//        println(keyBytes.contentToString())
//        //DA, GENEREAZA CHEIE DIF PT FIECARE CONTROLLER FMM
//        return keyBytes
//    }

    private fun generateSecureSecretKey(): ByteArray {
        return byteArrayOf(
            -22, -108, -41, 2, -117, -93, 125, -36, 82, -101, 57, 51, -25, 50, -43, -24,
            -60, 59, 125, 83, -64, -34, 42, 52, -68, -7, -53, 110, 38, 113, -9, 7, 51,
            -90, 115, -43, -101, -59, -23, 80, -73, 38, -115, 62, 30, -42, -46, 31, 123,
            -90, -31, -88, -16, -33, 74, 64, -17, -70, 111, -20, -5, 91, 44, 100
        )
    }

    fun generateToken(email: String, role: String): String {
        val now = Date()
        val expiryDate = Date(now.time + 3600000)
        val claims: Claims = Jwts.claims().setSubject(email)
        claims["role"]=role

        return Jwts.builder()
            .setClaims(claims)
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

    fun getRoleFromToken(token: String): String? {
        val claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key)).build().parseClaimsJws(token).body
        return claims["role"] as? String
    }
}
