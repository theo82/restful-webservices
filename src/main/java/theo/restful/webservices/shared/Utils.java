package theo.restful.webservices.shared;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import theo.restful.webservices.security.SecurityConstants;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEDGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuxvxyz";

    /**
     * A method that will be used to check is a token has expired or not.
     *
     * @param token
     * @return
     */
    public static boolean hasTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.getTokenSecret())
                .parseClaimsJws(token).getBody();

        Date tokenExpirationDate = claims.getExpiration();
        Date todayDate = new Date();

        return tokenExpirationDate.before(todayDate);
    }

    public String generateUserId(int length){
        return generateRandomString(length);
    }


    public String generateAddressId(int length) {
        return generateRandomString(length);
    }
    private String generateRandomString(int length){

        StringBuilder returnValue = new StringBuilder(length);

        for(int i=0; i < length; i++){
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);

    }
}
