public class Drone extends Aircraft implements CargoCapable {
    private double maxPayload; // in tons
    private double batteryLife; // in hours

    public Drone(String model, String callSign, double wingspan, double maxPayload, double batteryLife) {
        super(model, callSign, wingspan);
        this.maxPayload = maxPayload;
        this.batteryLife = batteryLife;
    }

    @Override
    public double getMaxPayload() {
        return maxPayload;
    }

    @Override
    public void setMaxPayload(double maxPayload) {
        this.maxPayload = maxPayload;
    }

    public double getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(double batteryLife) {
        this.batteryLife = batteryLife;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "model='" + getModel() + '\'' +
                ", callSign='" + getCallSign() + '\'' +
                ", wingspan=" + getWingspan() +
                ", maxPayload=" + maxPayload +
                ", batteryLife=" + batteryLife +
                '}';
    }
}