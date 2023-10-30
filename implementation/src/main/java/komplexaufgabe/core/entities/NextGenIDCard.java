package komplexaufgabe.core.entities;

import komplexaufgabe.core.interfaces.encryptionhash.IEncryptionHash;
import komplexaufgabe.core.interfaces.encryptionhash.MD5;
import komplexaufgabe.core.interfaces.encryptionhash.SHA256;


public class NextGenIDCard extends IDCard {
    private final IEncryptionHash fingerprintEncryption;
    private final NextGenMagneticStrip nextGenMagneticStrip;
    public NextGenIDCard(){
        super(new SHA256());
        fingerprintEncryption = new MD5();
        nextGenMagneticStrip = new NextGenMagneticStrip();
    }

    @Override
    public void store(int pin) {
        nextGenMagneticStrip.setPin(encryptionHash.encrypt(String.valueOf(pin)));
    }

    @Override
    public boolean isAuthorized(int pin) {
        return encryptionHash.encrypt(String.valueOf(pin)).equals(nextGenMagneticStrip.getPin());
    }


    public void store(String fingerprint) {
        nextGenMagneticStrip.setPin(fingerprintEncryption.encrypt(String.valueOf(fingerprint)));
    }

    public boolean isAuthorized(String fingerprint) {
        return fingerprintEncryption.encrypt(String.valueOf(fingerprint)).equals(nextGenMagneticStrip.getPin());
    }
}