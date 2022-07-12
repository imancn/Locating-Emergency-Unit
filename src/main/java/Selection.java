import java.util.Comparator;
import java.util.Random;

public class Selection {
	
	public static EmergencyUnit tournamentSelection(Population population) {
		final int MAX = population.getPopulation().size();
		final double TOURNAMENT_SIZE = 3;
		population.getPopulation().sort(Comparator.comparingInt(EmergencyUnit::getFitness));
		Random rand = new Random();
		EmergencyUnit bestFitness = population.getEmergencyUnit(rand.nextInt(MAX));
		for(int i = 0; i < TOURNAMENT_SIZE; i++){
			EmergencyUnit contFitness = population.getEmergencyUnit(rand.nextInt(MAX));
			if(contFitness.getFitness() < bestFitness.getFitness()) {
				bestFitness = contFitness;
			}
		}
		return bestFitness;
	}
}
