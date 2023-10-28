package komplexaufgabe.entities.human.officer.idcard;

import komplexaufgabe.interfaces.encryptionhash.IEncryptionHash;

public abstract class IDCard {
    protected final IEncryptionHash encryptionHash;

    protected IDCard(IEncryptionHash pEncryptionHash){
        encryptionHash = pEncryptionHash;
    }

    public abstract void savePIN(int pin);
}
