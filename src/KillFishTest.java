import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class KillFishTest {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        final String XRequestedWith = "XMLHttpRequest";
        final String actionLostUserPass = "user.lost.pass";

        String phone = "+79xxxxxxx";
        String Xcsrftoken = "";


        try {
            Xcsrftoken = Jsoup.connect("https://killfish.ru/my.html").execute().parse().body().getElementsByAttributeValue("data-key", "token").attr("data-value");
            System.out.println(Xcsrftoken);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Connection.Response resp2 = Jsoup.connect("https://killfish.ru/ajax.php").ignoreContentType(true).header("X-csrf-token", Xcsrftoken)
                    .header("X-Requested-With", XRequestedWith).data("action", actionLostUserPass).data("phone", phone)
                    .method(Connection.Method.POST).execute();

            JSONObject jsonObject = new JSONObject(resp2.body());

            System.out.println("ok: " + jsonObject.getBoolean("ok") + ",\n\r"
                    + "err: " + (jsonObject.has("err") ? jsonObject.getString("err") : "No") + ",\n\r"
                    + "auth: " + jsonObject.getBoolean("auth"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
