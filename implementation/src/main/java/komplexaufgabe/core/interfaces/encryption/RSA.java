package komplexaufgabe.core.interfaces.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA implements IEncryption {
    public void buildKeys() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            FileOutputStream privateKeyFOS = new FileOutputStream("private.key");
            privateKeyFOS.write(privateKey.getEncoded());

            FileOutputStream publicKeyFOS = new FileOutputStream("public.key");
            publicKeyFOS.write(publicKey.getEncoded());

            System.out.println("private key : " + privateKey.getFormat());
            System.out.println("public key  : " + publicKey.getFormat());
        } catch (NoSuchAlgorithmException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private PrivateKey loadPrivateKey() {
        try {
            File privateKeyFile = new File("private.key");
            byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
            KeyFactory privateKeyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            return privateKeyFactory.generatePrivate(privateKeySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println(e);
        }

        return null;
    }

    private PublicKey loadPublicKey() {
        try {
            File publicKeyFile = new File("public.key");
            byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
            KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            return publicKeyFactory.generatePublic(publicKeySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public String encrypt(String plainMessage) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, loadPublicKey());
            byte[] bytes = cipher.doFinal(plainMessage.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(bytes));
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException |
                 NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public String decrypt(String encryptedMessage) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, loadPrivateKey());
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            return new String(bytes);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException |
                 NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
