package com.wode.bangertong.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Async;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpsUtil {
    /**
     * 实例化HttpClient
     *
     * @param maxTotal
     * @param maxPerRoute
     * @param socketTimeout
     * @param connectTimeout
     * @param connectionRequestTimeout
     * @return
     */
    public static HttpClient createHttpClient(int maxTotal, int maxPerRoute, int socketTimeout, int connectTimeout,
                                              int connectionRequestTimeout) {
        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(maxPerRoute);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
                .setDefaultRequestConfig(defaultRequestConfig).build();
        return httpClient;
    }

    /**
     * 发送post请求
     *
     * @param httpClient httpClient
     * @param url        请求地址
     * @param params     请求参数
     * @return
     * @throws Exception 发送请求异常
     */
    public static String sendPost(HttpClient httpClient, String url, Map<String, String> params) throws Exception {
        String encoding = Consts.UTF_8.name();
        String resp = "";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        if (params != null && params.size() > 0) {
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> itr = params.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, String> entry = itr.next();
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formParams, encoding);
            httpPost.setEntity(postEntity);
        }
        CloseableHttpResponse response = null;
        try {
            response = (CloseableHttpResponse) httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status != 200) {
                String msg = String.format("接口返回状态码异常, status=%d, reponse=", status, EntityUtils.toString(response.getEntity(), encoding));
                System.out.println(msg);
            }
            resp = EntityUtils.toString(response.getEntity(), encoding);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    // log
                    e.printStackTrace();
                }
            }
        }
        return resp;
    }
    public static String doGet(String URL) {
        HttpURLConnection conn = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            //创建远程url连接对象
            java.net.URL url = new URL(URL);
            //通过远程url连接对象打开一个连接，强转成HTTPURLConnection类
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);
            conn.setRequestProperty("Accept", "application/json");

            //发送请求
            conn.connect();
            //通过conn取得输入流，并使用Reader读取
            if (200 == conn.getResponseCode()) {
                is = conn.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                    System.out.println(line);
                }
            } else {
                System.out.println("ResponseCode is an error code:" + conn.getResponseCode());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            conn.disconnect();
        }
        return result.toString();
    }
    public static String doPost(String url, String json) throws Exception {
        String responseText = null;
        CloseableHttpClient closeableHttpClient = createHttpsClient();
        HttpPost method = new HttpPost(url);
        StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        method.setEntity(entity);
        HttpResponse httpResponse = closeableHttpClient.execute(method);
        HttpEntity httpEntity2 = httpResponse.getEntity();
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result = EntityUtils.toString(httpEntity2);
            responseText = result;
        }
        closeableHttpClient.close();
        return responseText;
    }
    public static CloseableHttpClient createHttpsClient() throws Exception {
        X509TrustManager x509mgr = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{x509mgr}, new java.security.SecureRandom());
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }
    public static final JSONObject httpsPost(String url, JSONObject obj) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, JSONException {
        JSONObject res = null;
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream in = Reader.class.getResourceAsStream("/server.keystore");
        keyStore.load(in, "123456".toCharArray());

        SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy()).build();
        SSLConnectionSocketFactory sslf = new SSLConnectionSocketFactory(sslcontext);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslf).build();
        try {
            HttpPost post = new HttpPost(url);
            StringEntity s = new StringEntity(obj.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                res = JSON.parseObject(EntityUtils.toString(entity, Charset.forName("UTF-8")));
                EntityUtils.consume(entity);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return res;
    }

    public static final JSONObject httpPost(String url, String json) throws JSONException, ClientProtocolException, IOException {
        JSONObject res = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost(url);
//            StringEntity s = new StringEntity(obj.toString());
            StringEntity s = new StringEntity(json, "utf-8");// 解决中文乱码问题

//            s.setContentEncoding("UTF-8");
            s.setContentType("multipart/form-data;");
            post.setEntity(s);
            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                res = JSON.parseObject(EntityUtils.toString(entity, Charset.forName("UTF-8")));
                EntityUtils.consume(entity);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return res;
    }

    public static final JSONObject getHttpResponse(String url) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, JSONException {
        JSONObject res = new JSONObject();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(get);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String str = EntityUtils.toString(entity, Charset.forName("UTF-8"));
                    if (str.matches("[0-9]+")) {
                        res.put("code", str);
                    } else {
                        res = JSON.parseObject(str);
                    }
                    EntityUtils.consume(entity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return res;
    }

    public static final JSONObject getHttpResponse(String url, String token) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException,
            JSONException {
        JSONObject res = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet get = new HttpGet(url);
            get.setHeader("Authorization", token);
            CloseableHttpResponse response = httpclient.execute(get);
            try {
                HttpEntity entity = response.getEntity();
                res = JSON.parseObject(EntityUtils.toString(entity, Charset.forName("UTF-8")));
                EntityUtils.consume(entity);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return res;
    }

    /**
     *
     *
     * @param url
     * @param obj
     * @return
     * @throws JSONException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static final JSONObject httpPostPushCreate(String url, JSONObject obj) throws JSONException, ClientProtocolException, IOException {
        JSONObject res = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost(url);
            post.addHeader("Authorization", "af769d4cf557d09ed029ee00bdd178a4");
            StringEntity s = new StringEntity(obj.toString(), "UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                res = JSON.parseObject(EntityUtils.toString(entity, Charset.forName("UTF-8")));
                EntityUtils.consume(entity);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return res;
    }


    @Async
    public JSONObject httpPostAsync(String url, JSONObject obj) throws JSONException, ClientProtocolException, IOException {
        JSONObject res = null;
        CloseableHttpClient httpclient = new DefaultHttpClient();
        try {
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);//设置请求超时时间 10s
            httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 200);//设置请求超时时间 10s
            HttpPost post = new HttpPost(url);
            StringEntity s = new StringEntity(obj.toString(), "UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                res = JSON.parseObject(EntityUtils.toString(entity, Charset.forName("UTF-8")));
                EntityUtils.consume(entity);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception e) {
        } finally {
            httpclient.close();
        }
        return res;
    }

}
