package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class GoogleAPI {

/*
//   public static void main(String[] args) throws IOException, URISyntaxException {
//        String text = "Hello world!";
//        System.out.println("Translated text: " + translateEnToVi(text));
//   }
 */

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String translate(String langFrom, String langTo, String text) throws IOException, URISyntaxException {
        String urlStr = "https://translate.google.com/translate_a/t?client=gtrans" +
                "&sl=" + langFrom +
                "&tl=" + langTo +
                "&hl=" + langTo +
                "&tk=" + generateNewToken() +
                "&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8);
        URI uri = new URI(urlStr);
        URL url = uri.toURL();
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String res = response.toString().trim().replace("\"", "");
        return res.trim().replace("\"", "");
    }

    private static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String translateEnToVi(String text) throws IOException, URISyntaxException {
        return translate("en", "vi", text);
    }

    public static String translateViToEn(String text) throws IOException, URISyntaxException {
        return translate("vi", "en", text);
    }
}
