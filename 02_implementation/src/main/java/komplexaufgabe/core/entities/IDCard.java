package komplexaufgabe.core.entities;

import komplexaufgabe.core.interfaces.encryptionhash.IEncryptionHash;

public abstract class IDCard {
    protected final IEncryptionHash encryptionHash;

    protected IDCard(IEncryptionHash pEncryptionHash) {
        encryptionHash = pEncryptionHash;
    }

    public abstract void store(int pin);

    public abstract boolean isAuthorized(int pin);
}
