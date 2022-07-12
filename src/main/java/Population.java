import java.util.ArrayList;
import java.util.List;

public class Population {
	
	private ArrayList<EmergencyUnit> population;

	public Population(int initialPopulation, Incidents incidents) {
		population = new ArrayList<>();
		List<Coordinates> points = incidents.getRandomPoints(initialPopulation);
		for(Coordinates coordinates : points) {
			this.population.add(new EmergencyUnit(coordinates, incidents));
		}
	}

	public EmergencyUnit getFittest() {
		EmergencyUnit bestFitness = this.population.get(0);
		for(EmergencyUnit emergencyUnit : this.population) {
			if(bestFitness.getFitness() > emergencyUnit.getFitness()) {
				bestFitness = emergencyUnit;
			}
		}
		return bestFitness;
	}

	public void setPopulation(ArrayList<EmergencyUnit> population) {
		this.population = population;
	}

	public ArrayList<EmergencyUnit> getPopulation() {
		return population;
	}

	public ArrayList<EmergencyUnit> evolvePopulation(Population population) {
		ArrayList<EmergencyUnit> newPopulation = new ArrayList<>();
		for(int i = 0; i < this.population.size(); i++) {
			EmergencyUnit parentA = Selection.tournamentSelection(population);
			EmergencyUnit parentB = Selection.tournamentSelection(population);
			EmergencyUnit child = parentA.crossOver(parentB).mutate();
			newPopulation.add(child);
		}
		return newPopulation;
	}
	
	@Override
	public String toString() {
		String ret = "";
		ret += "Population\n";
		for(EmergencyUnit emergencyUnit : this.population) {
			ret += emergencyUnit.toString() + "\n";
		}
		return ret;
	}

	public EmergencyUnit getEmergencyUnit(int index) {
		return this.population.get(index);
	}
}
