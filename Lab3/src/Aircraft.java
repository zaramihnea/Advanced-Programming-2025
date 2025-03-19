public abstract class Aircraft implements Comparable<Aircraft> {
    private String model;
    private String callSign; // tail number or name
    private double wingspan; // in meters

    public Aircraft(String model, String callSign, double wingspan) {
        this.model = model;
        this.callSign = callSign;
        this.wingspan = wingspan;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public double getWingspan() {
        return wingspan;
    }

    public void setWingspan(double wingspan) {
        this.wingspan = wingspan;
    }

    @Override
    public int compareTo(Aircraft other) {
        return this.callSign.compareTo(other.callSign);
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "model='" + model + '\'' +
                ", callSign='" + callSign + '\'' +
                ", wingspan=" + wingspan +
                '}';
    }
}