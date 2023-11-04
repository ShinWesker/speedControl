package komplexaufgabe.core.entities;


import komplexaufgabe.core.interfaces.encryptionhash.SHA256;

public class OldIDCard extends IDCard {
    private final MagneticStrip oldMagneticStrip;

    public OldIDCard() {
        super(new SHA256());
        oldMagneticStrip = new MagneticStrip();
    }


    @Override
    public void store(int pin) {
        oldMagneticStrip.setPin(encryptionHash.encrypt(String.valueOf(pin)));
    }

    @Override
    public boolean isAuthorized(int pin) {
        return encryptionHash.encrypt(String.valueOf(pin)).equals(oldMagneticStrip.getPin());
    }
}

