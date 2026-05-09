package se.kth.iv1350.repairelectricbike.integration;

import java.util.Objects;

/**
 * Contains information about a bike.
 */
public final class BikeDTO {
    private final String brand;
    private final String model;
    private final String serialNo;

    /**
     * Creates a new instance representing a bike.
     *
     * @param brand    The bike's brand.
     * @param model    The bike's model.
     * @param serialNo The bike's serial number.
     */
    public BikeDTO(String brand, String model, String serialNo) {
        this.brand = brand;
        this.model = model;
        this.serialNo = serialNo;
    }

    @Override
    public String toString() {
        return "brand: " + brand + ", " +
                "model: " + model + ", " +
                "serialNo: " + serialNo;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof BikeDTO other))
            return false;

        return Objects.equals(brand, other.brand) &&
               Objects.equals(model, other.model) &&
               Objects.equals(serialNo, other.serialNo);
    }
    

    /**
     * Get the bike's brand.
     *
     * @return the bike's brand.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Get the bike's model.
     *
     * @return the bike's model.
     */
    public String getModel() {
        return model;
    }

    /**
     * Get the serial no.
     *
     * @return the serial no.
     */
    public String getSerialNo() {
        return serialNo;
    }
}
 
