package komplexaufgabe.entities.human.officer.idcard;

import komplexaufgabe.entities.human.officer.idcard.magneticstrip.NextGenMagneticStrip;
import komplexaufgabe.interfaces.encryptionhash.MD5;


public class NextGenIDCard extends IDCard {
    private final NextGenMagneticStrip nextGenMagneticStrip;
    public NextGenIDCard(){
        super(new MD5());
        nextGenMagneticStrip = new NextGenMagneticStrip();
    }

    @Override
    public void savePIN(int pin) {
        nextGenMagneticStrip.setPin(encryptionHash.encrypt(String.valueOf(pin)));
    }

    public void saveFingerPrint(String fingerPrint){
        nextGenMagneticStrip.setFingerPrint(encryptionHash.encrypt(fingerPrint));
    }
}