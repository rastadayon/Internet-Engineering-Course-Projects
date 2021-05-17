package ir.ac.ut.ie.Bolbolestan07.utils.HTTPRequestHandler;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HTTPRequestHandler {
    public static String getRequest(String url) throws Exception{
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            String result = "";
            if (entity != null)
                result = EntityUtils.toString(entity);
            return result;
        }
    }

    public static void postRequest(String url) throws Exception{
        //HttpClient httpClient = HttpClientBuilder.create().bulid();
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        //httpClient.execute(request);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            String result = "";
            if (entity != null)
                result = EntityUtils.toString(entity);
            return result;
        }
    }

}
