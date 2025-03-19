public class Freighter extends Aircraft implements CargoCapable {
    private double maxPayload; // in tons

    public Freighter(String model, String callSign, double wingspan, double maxPayload) {
        super(model, callSign, wingspan);
        this.maxPayload = maxPayload;
    }

    @Override
    public double getMaxPayload() {
        return maxPayload;
    }

    @Override
    public void setMaxPayload(double maxPayload) {
        this.maxPayload = maxPayload;
    }

    @Override
    public String toString() {
        return "Freighter{" +
                "model='" + getModel() + '\'' +
                ", callSign='" + getCallSign() + '\'' +
                ", wingspan=" + getWingspan() +
                ", maxPayload=" + maxPayload +
                '}';
    }
}