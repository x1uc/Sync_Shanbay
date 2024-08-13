package top.x1uc.YouDao;

import com.alibaba.fastjson2.JSON;
import top.x1uc.Entity.YouDaoData;
import top.x1uc.Entity.YouDaoListResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CollectionWordList {
    @Value("${youdao.cookie}")
    private String youdaoCookie;
    @Value("${youdao.collectionUrl}")
    private String collectionUrl;
    @Value("${user-agent}")
    private String user_agent;

    private final static HttpClient httpClient = new HttpClient();

    public YouDaoData getCollectionWordList() {
        // set get contain
        GetMethod getMethod = new GetMethod(collectionUrl);
        getMethod.addRequestHeader("Cookie", youdaoCookie);
        getMethod.addRequestHeader("User-Agent", user_agent);
        // send request 
        try {
            httpClient.executeMethod(getMethod);
        } catch (IOException e) {
            // todo print log 
            throw new RuntimeException(e);
        }
        // trans responseBody to Obj
        try {
            String s = new String(getMethod.getResponseBody(), "UTF-8");
            YouDaoListResponse youDaoListResponse = JSON.parseObject(s, YouDaoListResponse.class);
            if (youDaoListResponse.getCode() == 401) {
                log.error("Cookie失效");
                throw new RuntimeException();
            }
            getMethod.releaseConnection();
            return youDaoListResponse.getData();
        } catch (IOException e) {
            // todo print log 
            throw new RuntimeException(e);
        }
    }
}
