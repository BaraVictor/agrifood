package com.italy.agrifood.search;

import com.italy.agrifood.entity.Customer;
import com.italy.agrifood.service.CustomerService;
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
public class CustomerSearch {

    private static final Logger logger = Logger.getLogger(CustomerSearch.class.getName());

    private static String ACCESS_TOKEN;
    private static final String[] keywords = {"olio", "formaggio", "vino", "prodotti artigianali", "frutta", "conserve", "legumi", "spezie"};

    @Autowired
    private CustomerService customerService;

    public void startSearch() {
        loadAccessToken();
        for (String keyword : keywords) {
            searchCustomers(keyword);
        }
    }

    private void loadAccessToken() {
        // Load access token from configuration or secure source
        // ACCESS_TOKEN = "your_access_token";
    }

    private void searchCustomers(String keyword) {
        String url;
        try {
            url = "https://graph.facebook.com/search?q=" + URLEncoder.encode(keyword, "UTF-8") +
                    "&type=user&fields=id,name,email&access_token=" + ACCESS_TOKEN;

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
                JSONObject user = data.getJSONObject(i);
                String id = user.optString("id", "ID not available");
                String name = user.optString("name", "Name not available");
                String email = user.optString("email", "Email not available");

                // Assuming some default placeholder if the phone data isn't available
                String phone = "Phone not available";

                // Create a new Customer object and save it to the database
                Customer customer = new Customer();
                customer.setCustomerId(id); // Assuming id can be used as customerId
                customer.setName(name);
                customer.setEmail(email);
                customer.setPhoneNumber(phone);

                customerService.saveCustomer(customer);
            }
        } else {
            logger.info("No data found for keyword: " + keyword);
        }
    }
}
