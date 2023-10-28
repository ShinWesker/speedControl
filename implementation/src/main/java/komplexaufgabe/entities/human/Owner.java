package komplexaufgabe.entities.human;

import komplexaufgabe.entities.car.Car;
import komplexaufgabe.entities.smartphone.SmartPhone;

import java.util.Date;

public class Owner extends Human {
    private final SmartPhone smartPhone;
    private final Car car;

    private Owner(OwnerBuilder ownerBuilder) {
        super(ownerBuilder.name, ownerBuilder.birthdate, ownerBuilder.face);
        smartPhone = ownerBuilder.smartPhone;
        car = ownerBuilder.car;
    }

    public SmartPhone getSmartPhone() {
        return smartPhone;
    }

    public Car getCar() {
        return car;
    }

    public static class OwnerBuilder {
        private final String name;
        private final String face;
        private final Date birthdate;
        private final SmartPhone smartPhone;
        private final Car car;

        public OwnerBuilder(String pName, Date pBirthDate, String pFace, SmartPhone pSmartPhone, Car pCar) {
            name = pName;
            birthdate = pBirthDate;
            face = pFace;
            smartPhone = pSmartPhone;
            car = pCar;
        }

        public Owner build() {
            return new Owner(this);
        }
    }
}


