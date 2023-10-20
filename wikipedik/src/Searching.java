import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Scanner;

public class Searching {
    public Searching(){}
    private List<WebPage> getSearch(String response){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(response, JsonElement.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("query");
        JsonArray js = jsonObject.getAsJsonArray("search");
        List<WebPage> webPages = new ArrayList<>();

        for(int i =0; i<js.size();i++){
            webPages.add(gson.fromJson(js.get(i), WebPage.class));
        }
        return webPages;
    }
    public URL Search(String str){
        try{
            String baseURL = "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=";
            String encodeStr = URLEncoder.encode(str, StandardCharsets.UTF_8.name());
            //String encodeStr = URLEncoder.encode(search, "UTF-8");
            String urlStr = baseURL + encodeStr;
            URL url = new URL(urlStr);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            int response = conn.getResponseCode();
            if(response == HttpsURLConnection.HTTP_OK){
                StringBuilder sb = new StringBuilder();
                String line;

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while((line = reader.readLine()) != null){
                    sb.append(line);
                }

                //parsing
                List <WebPage> wikiWebPages = getSearch(sb.toString());


                if(wikiWebPages.isEmpty()) throw new NullPointerException("Ничего не найдено:(");
                for(int i = 0; i< wikiWebPages.size(); i++){
                    System.out.println(i+1 + ") " + wikiWebPages.get(i).getTitle());
                }

                System.out.print("Выберите номер подходящей статьи: ");
                Scanner in = new Scanner(System.in);
                int num = Integer.parseInt(in.nextLine());
                in.close();
                return new URL("https://ru.wikipedia.org/w/index.php?curid=" + wikiWebPages.get(num-1).getPageId());
            }
            else{
                System.out.println("Ошибка запроса " + response);
            }

        } catch(UnsupportedEncodingException ex){
            ex.printStackTrace();
        } catch (NumberFormatException a) {
            System.out.println("Необходимо ввести цифру...");
        } catch (IndexOutOfBoundsException b) {
            System.out.println("Вы ввели некорректный номер...");
        } catch (NullPointerException c){
            System.out.println(c.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
