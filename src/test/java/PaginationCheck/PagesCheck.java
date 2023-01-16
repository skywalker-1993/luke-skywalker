package PaginationCheck;

import com.intuit.karate.Http;
import lombok.Data;
import org.apache.http.HttpException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Data
public class PagesCheck {

  private static final String usersEndpoint = "https://reqres.in/api/users";
  public int totalUsers = 0;
  protected int totalPages = 0;
  protected Map<Integer, JSONObject> responseBody = new HashMap<>();

  private static int getPageTotalUsers(JSONObject obj) {
    JSONArray data = (JSONArray) obj.get("data");
    return data.length();
  }

  protected static JSONArray getPageUsers(JSONObject obj) {
    return (JSONArray) obj.get("data");
  }

  private static int getTotalUsers(JSONObject obj) {
    return (int) obj.get("total");
  }

  private static int getTotalPages(JSONObject obj) {
    return (int) obj.get("total_pages");
  }

  private static String getUserPage(int page) throws HttpException {
    com.intuit.karate.http.Response response = Http.to(usersEndpoint + "?page=" + page).get();
    if (response.getStatus() != 200) throw new HttpException("Users List was NOT retrieved!");
    return response.getBodyAsString();
  }

  protected void addParsedPage(int page) throws HttpException {
    JSONObject obj = new JSONObject(getUserPage(page));
    this.responseBody.put(page, obj);
    if (0 == this.totalPages && 0 == this.totalUsers) {
      this.totalPages = getTotalPages(obj);
      this.totalUsers = getTotalUsers(obj);
    }
  }

  public static PagesCheck getPageCheckInstance() {
    return new PagesCheck();
  }

  public static int getTotalUserCount(PagesCheck pageCheck) throws HttpException {
    pageCheck.addParsedPage(1);
    int countUsers = getPageTotalUsers(pageCheck.responseBody.get(1));
    for (int pageIndex = 2; pageIndex < pageCheck.totalPages + 1; pageIndex++) {
      pageCheck.addParsedPage(pageIndex);
      countUsers += getPageTotalUsers(pageCheck.responseBody.get(pageIndex));
    }
    return countUsers;
  }

}
