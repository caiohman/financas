package caixaApp;

import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class User{
    //Atributos
    private String login;
    private static String password;

    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATIONS = 10000;
    private static final int SALT_SIZE = 32;
    private static final int HASH_SIZE = 512;

    public User( String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    //will receive permission from db
  //  public boolean getPermission() {
  //   
  //  }

    public String getLogin() {
        return this.login;
    }

    public static byte [] hash(byte[] salt) {
        char[] charPassword = password.toCharArray();
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = calculateHash(skf, charPassword, salt);
            return hash;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {

        }
        return null;
    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);

        return salt;
    }

    private static byte[] calculateHash(SecretKeyFactory skf,
     char[] password, byte[] salt) throws InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, HASH_SIZE);

        return skf.generateSecret(spec).getEncoded();
    }

    public static boolean comparePasswords( byte[] originalHash,
       byte[] comparisonHash){
        int diff = originalHash.length ^ comparisonHash.length;
        for (int i = 0; i < originalHash.length && i < comparisonHash.length; i++) {
          diff |= originalHash[i] ^ comparisonHash[i];
        }
        return diff == 0;
    }

}
