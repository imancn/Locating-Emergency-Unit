public class EmergencyUnit {

    private Coordinates coordinates;

    private int fitness;
    private Incidents incidents;

    public EmergencyUnit(Coordinates coordinates, Incidents incidents) {
        this.coordinates = coordinates;
        this.incidents = incidents;
        fitness = calculateFitness(incidents);
    }

    public int getFitness() {
        return this.fitness;
    }

    private int calculateFitness(Incidents incidents) {
        int fitness = 0;
        for(Coordinates incidentCoordinates : incidents.getIncidentPoints()) {
            fitness += incidentCoordinates.distanceFrom(coordinates);
        }
        return fitness;
    }

    public EmergencyUnit crossOver(EmergencyUnit partner) {
        final double CROSSOVER_RATE = 0.5;
        return new EmergencyUnit(
                new Coordinates(
                        Math.random() <= CROSSOVER_RATE ? this.coordinates.getLatitude() : partner.coordinates.getLatitude(),
                        Math.random() <= CROSSOVER_RATE ? this.coordinates.getLongitude() : partner.coordinates.getLongitude()
                ),
                incidents
        );
    }

    public EmergencyUnit mutate() {
        final double MUTATION_RATE = 0.05;
        return new EmergencyUnit(
                new Coordinates(
                        Math.random() <= MUTATION_RATE ? incidents.getRandomLatitude() : this.coordinates.getLatitude(),
                        Math.random() <= MUTATION_RATE ? incidents.getRandomLongitude() : this.coordinates.getLongitude()
                ),
                incidents
        );
    }

    @Override
    public String toString() {
        return "Latitude : " + this.coordinates.getLatitude() + " | Longitude : " + this.coordinates.getLongitude();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
