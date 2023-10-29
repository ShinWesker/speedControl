package komplexaufgabe.core.interfaces.encryptionhash;
import org.apache.commons.codec.digest.DigestUtils;

public class SHA256 implements IEncryptionHash{
    @Override
    public String encrypt(String plainMessage) {
        return new DigestUtils("SHA3-256").digestAsHex(plainMessage);
    }
}
