package RequestBody;

import static RequestBody.GenericRequest.getRequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Data
@AllArgsConstructor
public class CarRequest {

  private String brandName;
  private String model;
  private int numberDoors;
  private boolean sportsCar;

  private static final String[] FIVE_DOORS = {"Mercedes Class A", "Mercedes Class C", "BMW Series 2", "Golf Volkswagen"};
  private static final String[] THREE_DOORS = {"Mercedes Class B", "BMW Series 1", "Volkswagen Passat"};
  private static final String[] TWO_DOORS = {"BMW i8", "Volkswagen GTI Roadster"};

  private static final String[] SPORTS_CARS = {"Mercedes Class C", "BMW i8", "Volkswagen GTI Roadster"};

  private static final Map<String, String> CAR_MODELS = new HashMap<>() {{
    //Note: Information here is simplified, except for the models and brands, to avoid extensive research.
    put("Class A", "Mercedes"); //5, false
    put("Class B", "Mercedes"); //3, false
    put("Class C", "Mercedes"); //5, true
    put("Series 1", "BMW"); //3, false
    put("Series 2", "BMW"); //5, false
    put("i8", "BMW"); //2, true
    put("Passat", "Volkswagen"); //3 false
    put("GTI Roadster", "Volkswagen"); //2, true
    put("Golf", "Volkswagen"); //5, false
  }};

  private static int getNumberOfDoors(String brand, String model){
    String brandModel = brand + " " + model;
    if (Arrays.asList(TWO_DOORS).contains(brandModel)) {
      return 2;
    } else if (Arrays.asList(THREE_DOORS).contains(brandModel)) {
      return 3;
    } else {
      return 5;
    }
  }

  private static boolean isItASportsCar(String brand, String model) {
    String brandModel = brand + " " + model;
    return Arrays.asList(SPORTS_CARS).contains(brandModel);
  }

  private static String[] getRandomModelBrand() {
    Random generator = new Random();
    Object[] keys = CAR_MODELS.keySet().toArray();
    String randomKey = keys[generator.nextInt(keys.length)].toString();
    return new String[] {CAR_MODELS.get(randomKey), randomKey};
  }

  public static CarRequest getRandomCarObject() {
    String[] brandModel = getRandomModelBrand();
    int numberDoors = getNumberOfDoors(brandModel[0], brandModel[1]);
    boolean sportsCar = isItASportsCar(brandModel[0], brandModel[1]);
    return getCarObject(brandModel[0], brandModel[1], numberDoors, sportsCar);
  }

  public static CarRequest getCarObject(String brandName, String model, int numberDoors, boolean sportsCar) {
    return new CarRequest(brandName, model, numberDoors, sportsCar);
  }

  public static String getCarBody(CarRequest car) throws JsonProcessingException {
    return getRequestBody(car);
  }

  public static void main(String[] args) throws JsonProcessingException {
    System.out.println(getCarBody(getRandomCarObject()));
  }

}
