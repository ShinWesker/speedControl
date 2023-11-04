package komplexaufgabe.core.interfaces.encryption;

public interface IEncryption {
    String encrypt(String plainMessage);

    String decrypt(String cryptedMessage);
}
