package lab2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String baseURL = "http://www.neracoos.org/erddap/tabledap/E05_aanderaa_all.json?station,mooring_site_desc,water_depth,time,current_speed,current_speed_qc,current_direction,current_direction_qc,current_u,current_u_qc,current_v,current_v_qc,temperature,temperature_qc,conductivity,conductivity_qc,salinity,salinity_qc,sigma_t,sigma_t_qc,time_created,time_modified,longitude,latitude,depth&time%3E=2015-08-25T15:00:00Z&time%3C=2016-12-05T14:00:00Z";

    private static File getJson() throws IOException {
        URLConnection urlCon = null;

        File file = null;
        try
        {
            urlCon = new URL(baseURL).openConnection();

            Scanner scanner = new Scanner(urlCon.getInputStream());
            file = new File("jsonFile.json");
            if(file.length() != 0)
                return file;
            FileWriter fileWriter = new FileWriter(file);
            while(scanner.hasNextLine())
                fileWriter.write(scanner.nextLine());
            fileWriter.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        return file;
    }

    private static List<SmthFromPhysics> parse(File file)
    {
        List<SmthFromPhysics> jsonList = new LinkedList<>();

        try {

            Reader reader = new BufferedReader(new FileReader(file));
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
            JSONObject main = (JSONObject) jsonObject.get("table");
            JSONArray rows = (JSONArray) main.get("rows");
            for (int i = 0; i < rows.size(); i++) {
                JSONArray temp = (JSONArray) rows.get(i);
                SmthFromPhysics datchickState = new SmthFromPhysics(temp);
                jsonList.add(datchickState);
            }

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        catch (ParseException pEx)
        {
            pEx.printStackTrace();
        }

        return jsonList;
    }

    private static void min_max_avg(List<SmthFromPhysics> states) {
        double minCurrentSpeed, maxCurrentSpeed, avgCurrentSpeed;
        double minTemperature, maxTemperature, avgTemperature;
        double minSalinity, maxSalinity, avgSalinity;
        int minSpeedIndex = 0, minTemperatureIndex = 0,minSalinityIndex =0,
                maxSpeedIndex=0, maxTemperatureIndex = 0, maxSalinityIndex=0;

        minCurrentSpeed = states.get(0).getCurrent_speed();
        maxCurrentSpeed = minCurrentSpeed;
        avgCurrentSpeed = minCurrentSpeed;

        minTemperature = states.get(0).getTemperature();
        maxTemperature = minTemperature;
        avgTemperature = minTemperature;

        minSalinity = states.get(0).getSalinity();
        maxSalinity = minSalinity;
        avgSalinity = minSalinity;

        for (int i = 1; i <states.size() ; i++) {
            double speedTemp = states.get(i).getCurrent_speed();
            double temperatureTemp = states.get(i).getTemperature();
            double salinityTemp = states.get(i).getSalinity();
            if (states.get(i).getCurrent_speed_qc() ==0) {
                if (speedTemp < minCurrentSpeed) {
                    minCurrentSpeed = speedTemp;
                    minSpeedIndex = i;
                }
                if (speedTemp > maxCurrentSpeed) {
                    maxCurrentSpeed = speedTemp;
                    maxSpeedIndex = i;
                }
                avgCurrentSpeed += speedTemp;
            }
            if(states.get(i).getTemperature_qc() == 0) {
                if (temperatureTemp < minTemperature) {
                    minTemperature = temperatureTemp;
                    minTemperatureIndex = i;
                }
                if (temperatureTemp > maxTemperature) {
                    maxTemperature = temperatureTemp;
                    maxTemperatureIndex = i;
                }
                avgTemperature += temperatureTemp;
            }
            if(states.get(i).getSalinity_qc() ==0) {
                if (salinityTemp < minSalinity) {
                    minSalinity = salinityTemp;
                    minSalinityIndex = i;
                }
                if (salinityTemp > maxSalinity) {
                    maxSalinity = salinityTemp;
                    maxSalinityIndex = i;
                }
                avgSalinity += salinityTemp;
            }
        }

        System.out.println("Max current speed: "+ maxCurrentSpeed);
        System.out.println("Min current speed: "+ minCurrentSpeed);
        System.out.println("Average current speed: "+ avgCurrentSpeed/states.size());

        System.out.println("Max temperature: "+ maxTemperature);
        System.out.println("Min temperature: "+ minTemperature);
        System.out.println("Average temperature: "+ avgTemperature/states.size());

        System.out.println("Max salinity: "+ maxSalinity);
        System.out.println("Min salinity: "+ minSalinity);
        System.out.println("Average salinity: "+ avgSalinity/states.size());
    }

    public static void main(String[] args) {
        try {
            File file = getJson();
            List<SmthFromPhysics> result = parse(file);
            min_max_avg(result);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
