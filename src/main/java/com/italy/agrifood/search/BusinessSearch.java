package com.italy.agrifood.search;
import com.italy.agrifood.entity.Business;
import com.italy.agrifood.service.BusinessService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class BusinessSearch {

    private static final Logger logger = Logger.getLogger(BusinessSearch    .class.getName());

    private static String ACCESS_TOKEN;
    private static final String[] keywords = {"olio", "formaggio", "vino", "prodotti artigianali", "frutta", "conserve", "legumi", "spezie"};

    @Autowired
    private BusinessService businessService;

    public void startSearch() {
        loadAccessToken();
        for (String keyword : keywords) {
            searchBusinesses(keyword);
        }
    }

    private void loadAccessToken() {
        // Codul pentru a încărca access token-ul din config.properties
    }

    private void searchBusinesses(String keyword) {
        String url;
        try {
            url = "https://graph.facebook.com/pages/search?q=" + URLEncoder.encode(keyword, "UTF-8") +
                    "&fields=id,name,about&access_token=" + ACCESS_TOKEN;

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                handleResponse(conn, keyword);
            } else {
                logger.log(Level.SEVERE, "Error: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching data for keyword: " + keyword, e);
        }
    }

    private void handleResponse(HttpURLConnection conn, String keyword) throws Exception {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            parseAndSaveResults(content.toString(), keyword);
        }
    }

    private void parseAndSaveResults(String response, String keyword) {
        JSONObject jsonResponse = new JSONObject(response);

        if (jsonResponse.has("data")) {
            JSONArray data = jsonResponse.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject page = data.getJSONObject(i);
                String id = page.optString("id", "ID not available");
                String name = page.optString("name", "Name not available");
                String about = page.optString("about", "Description not available");

                // Construim link-ul către pagina de Facebook
                String pageLink = "https://www.facebook.com/" + id;

                // Creăm un nou obiect Business și îl salvăm în baza de date
                Business business = new Business();
                business.setBusinessId(id);
                business.setName(name);
                business.setDescription(about);
                business.setKeyword(keyword);
                business.setPageLink(pageLink);

                businessService.saveBusiness(business);
            }
        } else {
            logger.info("No data found for keyword: " + keyword);
        }
    }

}