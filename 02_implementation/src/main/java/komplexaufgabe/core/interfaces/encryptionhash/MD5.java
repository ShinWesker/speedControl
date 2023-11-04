package komplexaufgabe.core.interfaces.encryptionhash;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 implements IEncryptionHash {
    @Override
    public String encrypt(String plainMessage) {
        return new DigestUtils("MD5").digestAsHex(plainMessage);
    }
}
