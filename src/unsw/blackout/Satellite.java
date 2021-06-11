package unsw.blackout;

public class Satellite {
    // General fields
    private double height;
    private String id;
    private double position;
    private String[] possibleConnections;
    private double velocity;

    // Constructor with no arguments
    public Satellite() {
        this.height = 10000;
        this.id = "Satellite";
        this.position = 10;
    }

    /**
     * 
     * @param height
     * @param id
     * @param position
     */
    public Satellite(double height, String id, double position) {
        this.height = height;
        this.id = id;
        this.position = position;
    }

    // SETTERS

    /**
     * 
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * 
     * @param position
     */
    public void setPosition(double position) {
        this.position = position;
    }

    /**
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @param velocity
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * 
     * @param possibleConnections
     */
    public void setPossibleConnections(String[] possibleConnections) {
        this.possibleConnections = possibleConnections;
    }

    // GETTERS

    /**
     * 
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * 
     * @return position
     */
    public double getPosition() {
        return position;
    }

    /**
     * 
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @return velocity
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * 
     * @return possible connections (String[])
     */
    public String[] getPossibleConnections() {
        return possibleConnections;
    }
}


