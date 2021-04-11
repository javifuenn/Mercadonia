package util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class Encriptador {
    /*
     * Este método de encriptación de contraseñas se basa en "Base64"
     * Trabajo con la contraseña real 'contra', la contraseña codificada 'contrabd' y la clave de encriptación 'salt'
     */
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error al crear la contraseña: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
    // Generador de la contraseña cifrada
    public static String crearContraSegura(String password, String salt) {
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    public static boolean verificarContra(String providedPassword,
                                          String securedPassword, String salt)
    {
        boolean coinciden = false;

        // Genero la contraseña de nuevo con la misma clave
        String newSecurePassword = crearContraSegura(providedPassword, salt);
        coinciden = newSecurePassword.equalsIgnoreCase(securedPassword);

        return coinciden;
    }

    public static String protegerContraseña(String pass) {
        String myPassword = pass;

        // Genera la clave de encriptación
        String salt = getSalt(30);

        // Crea la contraseña segura
        String mySecurePassword = crearContraSegura(myPassword, salt);

        // Pas la contraseña y la clave de desencriptación para guardarlo en la BD
        String contrayclave = (mySecurePassword + " " + salt);
        return contrayclave;
    }

    public static boolean verifyProvidedPassword(String pass, String passBD, String saltBD) {
        // Contraseña a validar
        String providedPassword = pass;

        // Contraseña encriptada de la base de datos
        String securePassword = passBD;

        // Clave de desencriptación almacenada en la base de datos
        String salt = saltBD;
        boolean correcto = verificarContra(providedPassword, securePassword, salt);

        return correcto;
    }



}
