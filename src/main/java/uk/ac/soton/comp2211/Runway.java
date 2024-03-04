package uk.ac.soton.comp2211;

class Runway {
    String id;
    double lengthInMeters;

    public Runway(String id, double lengthInMeters) {
        this.id = id;
        this.lengthInMeters = lengthInMeters;
    }

    public double getLengthInMeters() {
        return lengthInMeters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLengthInMeters(double lengthInMeters) {
        this.lengthInMeters = lengthInMeters;
    }
}

class Obstacle {
    String name;
    double heightInMeters;

    public Obstacle(String name, double heightInMeters) {
        this.name = name;
        this.heightInMeters = heightInMeters;
    }

    public double getHeightInMeters() {
        return heightInMeters;
    }

    public String getName() {
        return name;
    }

    public void setHeightInMeters(double heightInMeters) {
        this.heightInMeters = heightInMeters;
    }

    public void setName(String name) {
        this.name = name;
    }
}
