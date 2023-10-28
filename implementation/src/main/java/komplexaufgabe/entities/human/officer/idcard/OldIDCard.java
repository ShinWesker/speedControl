package komplexaufgabe.entities.human.officer.idcard;


import komplexaufgabe.entities.human.officer.idcard.magneticstrip.MagneticStrip;
import komplexaufgabe.interfaces.encryptionhash.SHA256;

public class OldIDCard extends IDCard {
    private final MagneticStrip oldMagneticStrip;

    public OldIDCard(){
        super(new SHA256());
        oldMagneticStrip = new MagneticStrip();
    }

    @Override
    public void savePIN(int pin) {
        oldMagneticStrip.setPin(encryptionHash.encrypt(String.valueOf(pin)));
    }
}

