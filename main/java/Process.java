import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by stuar on 10/6/2018.
 */
public class Process {

    private Map<String, List<String>> connections = new HashMap<>();
    private List<String> traversedCities = new ArrayList<>();
    private String status;
    private String originalFromCity;
    private String originalToCity;

    public String getStatus() {
        return status;
    }

    public void process(String filename, String fromCity, String toCity) {

        originalFromCity = fromCity;
        originalToCity = toCity;

        try (Stream<String> lines = Files.lines(Paths.get(filename), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> processLine(line));
        }
        catch (IOException ioException) {
            status = "File: " + filename + " not found";
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            status = "File: " + filename + " contained an invalid line";
            return;
        }

        status = areConnected(fromCity, toCity) ? "yes" : "no";

    }

    private boolean areConnected(String fromCity, String toCity) {

        if (connections.containsKey(fromCity) && connections.containsKey(toCity)) {

            if (connections.get(fromCity).contains(toCity)) {
                if (toCity.equalsIgnoreCase(originalToCity)) {
                    return true;
                }
            } else {
                for (String city : connections.get(fromCity)) {
                    if (!traversedCities.contains(city)) {
                        traversedCities.add(city);
                        if (areConnected(city, toCity)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private void processLine(String line) {

        String[] parts = line.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException();
        }

        String fromCity = parts[0].trim();
        String toCity = parts[1].trim();

        if (!connections.containsKey(fromCity)) {
            ArrayList<String> toCityList = new ArrayList<>();
            toCityList.add(toCity);
            connections.put(fromCity, toCityList);
        }
        else {
            if (!connections.get(fromCity).contains(toCity)) {
                connections.get(fromCity).add(toCity);
            }
            else {
                throw new IllegalArgumentException();
            }
        }

        if (!connections.containsKey(toCity)) {
            ArrayList<String> fromCityList = new ArrayList<>();
            fromCityList.add(fromCity);
            connections.put(toCity, fromCityList);
        }
        else {
            if (!connections.get(toCity).contains(fromCity)) {
                connections.get(toCity).add(fromCity);
            }
            else {
                throw new IllegalArgumentException();
            }
        }
    }

}

