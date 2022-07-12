import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException, InvalidFormatException {

		final String fileName = "src/main/resources/fire_indicents.xlsx";
		Incidents incidents = new Incidents(fileName);

		XSSFWorkbook excel = new XSSFWorkbook();
		XSSFSheet sheet = excel.createSheet("result");
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Generation");
		row.createCell(1).setCellValue("Latitude");
		row.createCell(2).setCellValue("Longitude");
		row.createCell(3).setCellValue("Cost(KM)");

		Population population = new Population(30, incidents);
		EmergencyUnit best = population.getPopulation().get(0);

		int generation = 1;
		while (generation <= 500) {
			population.setPopulation(population.evolvePopulation(population));

			row = sheet.createRow(generation);
			row.createCell(0).setCellValue(generation);
			row.createCell(1).setCellValue(population.getFittest().getCoordinates().getLatitude());
			row.createCell(2).setCellValue(population.getFittest().getCoordinates().getLongitude());
			row.createCell(3).setCellValue(population.getFittest().getFitness());

			if (best.getFitness() > population.getFittest().getFitness())
				best = population.getFittest();
			generation++;
		}

		File file = new File("src/outputs/output.xlsx");
		if (!file.createNewFile()){
			file.delete();
			file.createNewFile();
		}
		OutputStream os = new FileOutputStream(file);
		os.write(extractBytes(excel));
	}

	private static byte[] extractBytes(XSSFWorkbook wb) throws IOException {
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		wb.write(byteOutput);
		byte[] bytes = byteOutput.toByteArray();
		byteOutput.flush();
		byteOutput.close();
		wb.close();
		return bytes;
	}

}
