import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Incidents {
    private List<Coordinates> incidentPoints;

    private Coordinates maxPoint;
    private Coordinates minPoint;

    public Incidents(String fileName) throws IOException, InvalidFormatException {
        incidentPoints = new ArrayList<>();
        minPoint = new Coordinates(999.0, 999.0);
        maxPoint = new Coordinates(-999.0, -999.0);

        XSSFWorkbook excel = new XSSFWorkbook(new File(fileName));
        XSSFSheet sheet = excel.getSheetAt(0);

        for (int i = 1; i < sheet.getLastRowNum(); i++){
            try {
                Coordinates coordinates = new Coordinates(
                        Double.valueOf(sheet.getRow(i).getCell(14).getRawValue()),
                        Double.valueOf(sheet.getRow(i).getCell(15).getRawValue())
                );
                incidentPoints.add(coordinates);

                if (coordinates.getLatitude() < minPoint.getLatitude())
                    minPoint.setLatitude(coordinates.getLatitude());
                if (coordinates.getLongitude() < minPoint.getLongitude())
                    minPoint.setLongitude(coordinates.getLongitude());

                if (coordinates.getLatitude() > maxPoint.getLatitude())
                    maxPoint.setLatitude(coordinates.getLatitude());
                if (coordinates.getLongitude() > maxPoint.getLongitude())
                    maxPoint.setLongitude(coordinates.getLongitude());
            } catch (Exception ex){
                System.out.println("Cannot read data of row" + i);
            }
        }
        excel.close();
    }

    public List<Coordinates> getIncidentPoints() {
        return incidentPoints;
    }

    public Coordinates getMaxPoint() {
        return maxPoint;
    }

    public Coordinates getMinPoint() {
        return minPoint;
    }

    public Coordinates getRandomPoint(){
        Random rand = new Random();
        double latDif = maxPoint.getLatitude() - minPoint.getLatitude();
        double lonDif = maxPoint.getLongitude() - minPoint.getLongitude();
        return new Coordinates(
                (rand.nextDouble() * latDif) + minPoint.getLatitude(),
                (rand.nextDouble() * lonDif) + minPoint.getLongitude()
        );
    }

    public List<Coordinates> getRandomPoints(int n){
        List<Coordinates> points = new ArrayList<>();
        Random rand = new Random();
        double latDif = maxPoint.getLatitude() - minPoint.getLatitude();
        double lonDif = maxPoint.getLongitude() - minPoint.getLongitude();
        for (int i = 0; i < n; i++){
            points.add(new Coordinates(
                    (rand.nextDouble() * latDif) + minPoint.getLatitude(),
                    (rand.nextDouble() * lonDif) + minPoint.getLongitude()
            ));
        }
        return points;
    }

    public Double getRandomLatitude(){
        Random rand = new Random();
        double latDif = maxPoint.getLatitude() - minPoint.getLatitude();
        return (rand.nextDouble() * latDif) + minPoint.getLatitude();
    }

    public Double getRandomLongitude(){
        Random rand = new Random();
        double lonDif = maxPoint.getLongitude() - minPoint.getLongitude();
        return (rand.nextDouble() * lonDif) + minPoint.getLongitude();
    }
}
