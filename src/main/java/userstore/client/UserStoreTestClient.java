package userstore.client;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonGeneratorImpl;
import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.core.util.IOUtils;
import org.springframework.boot.jackson.JsonObjectSerializer;
import userstore.dto.AuthenticationData;
import userstore.dto.UserDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class UserStoreTestClient {

    private static final String URL = "http://localhost:8100";

    private static CloseableHttpClient client;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("user", "dupa"));
        client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();

       // insertUser(new UserDto(null, "Admin", null, null));
       // registerUser("Admin", "dupa2");
        authenticateUser("Admin", "dupa2");
        getUser(1L);
        getUsers();

    }

    private static void insertUser(UserDto userDto) throws IOException {
        System.out.println("user: " + userDto);

        String userJson = objectMapper.writeValueAsString(userDto);
        System.out.println("userJson: " + userJson);

        post(URL+"/user", userJson);
    }

    private static void getUser(long id) throws IOException {
        String responseBody = get(String.format(URL + "/user?id=%d", id));
        System.out.println("User from get: "+responseBody);
    }

    private static void getUsers() throws IOException {
        String responseBody = get(URL + "/user");
        System.out.println("Users from getAll: "+responseBody);
    }

    private static void registerUser(String user, String pass) throws IOException {
        AuthenticationData authenticationData = new AuthenticationData(user, pass);
        System.out.println("user: " + authenticationData);

        String authJson = objectMapper.writeValueAsString(authenticationData);
        System.out.println("authJson: " + authJson);

        String res = post(URL+"/authenticate", authJson);

        System.out.println("User registered: "+res);
    }

    private static void authenticateUser(String user, String pass) throws IOException {
        AuthenticationData authenticationData = new AuthenticationData(user, pass);
        System.out.println("user: " + authenticationData);

        String res = get(String.format(URL+"/authenticate?username=%s&password=%s", user, pass));

        System.out.println("User registered: "+res);
    }

    private static String post(String url, String body) throws IOException {
        var httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(body);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        try(CloseableHttpResponse response = client.execute(httpPost)){
            String responseBody = IOUtils.toString(new InputStreamReader(response.getEntity().getContent()));
            System.out.println("Response: "+response.getStatusLine());
            return responseBody;
        }
    }

    private static String get(String url) throws IOException {
        try(CloseableHttpResponse response = client.execute(new HttpGet(url))){
            String responseBody = IOUtils.toString(new InputStreamReader(response.getEntity().getContent()));
            System.out.println("User from get: "+responseBody);
            return responseBody;
        }
    }
}