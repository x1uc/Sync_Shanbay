package top.x1uc.ShanBay;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.x1uc.Decoder;
import top.x1uc.Entity.*;
import top.x1uc.Mapper.WordMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShanBayCollection {
    @Value("${shanbay.collectionUrl}")
    private String collectionUrl;
    @Value("${shanbay.getWordUrl}")
    public String getWordUrl;
    @Value("${shanbay.cookie}")
    private String cookie;

    private final WordMapper wordMapper;


    public void addWordToShanBay(YouDaoData youDaoData) {
        System.out.println("==========\t扇贝同步中\t========");
        List<YouDaoWordItem> itemList = youDaoData.getItemList();
        List<String> wordIds = RequestWordIds(itemList);
        // trans encrypt code to wordId
        List<ShanBayWordPair> shanBayWordPairList = Decoding(wordIds);
        // add words to shanBay notebook
        Collection(shanBayWordPairList);
        System.out.println("==========\t扇贝同步完成\t========");
    }

    /**
     * @param itemList
     * @return {@link List }<{@link String }>
     * trans word to wordId(encoding) of shanBay
     */
    public List<String> RequestWordIds(List<YouDaoWordItem> itemList) {
        HttpClient httpClient = new HttpClient();
        List<String> result = new ArrayList<>();
        itemList.forEach(youDaoWordItem -> {
            try {
                Thread.sleep(500);
                // create a getMethod
                GetMethod getMethod = getGetMethod(youDaoWordItem);
                httpClient.executeMethod(getMethod);
                // trans res to obj
                String data = new String(getMethod.getResponseBody());
                // the word exist in youdao, but it not exists in shanbay
                // especially for phrases
                // So we need to judge, or we'll get an error when we decode
                // if a word is not existing in shanbay , it will return the json below.
                // we needed append a '\n' after return data , I suspect the reason is related to Fastjson.
                log.info("add word {} to Sbay", youDaoWordItem.getWord());
                if (!data.equals("{\"errors\":{},\"msg\":\"not found vocabulary\"}\n")) {
                    ShanBayGetWordData wordData = JSONObject.parseObject(data, ShanBayGetWordData.class);
                    // add the return list 
                    result.add(wordData.getData());
                } else {
                    log.error("this word :{} is not exist", youDaoWordItem.getWord());
                }
                // releaseConnection
                getMethod.releaseConnection();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return result;
    }


    /**
     * @param wordsData
     * @return {@link List }<{@link ShanBayWordPair }>
     * we need to decode the wordsData because of wordsData from return by shanBay is encoded;
     */
    public List<ShanBayWordPair> Decoding(List<String> wordsData) {
        HttpClient httpClient = new HttpClient();
        List<ShanBayWordPair> result = new ArrayList<>();
        // item is encoding data 
        wordsData.forEach(item -> {
            try {
                String shanBayWord = Decoder.decode(item);
                ShanBayWordPair shanBayWordPair = JSON.parseObject(shanBayWord, ShanBayWordPair.class);
                result.add(shanBayWordPair);
            } catch (JSONException e) {
                log.error("json parse is error");
            }
        });
        return result;
    }


    /**
     * @param wordPairs add word to shanBay Collection Book
     */
    public void Collection(List<ShanBayWordPair> wordPairs) {
        HttpClient httpClient = new HttpClient();
        wordPairs.forEach(item -> {
            try {
                PostMethod postMethod = getPostRequest(item.getId());
                httpClient.executeMethod(postMethod);
                if (postMethod.getStatusCode() == 200) {
                    // beautiful output 
                    String word = item.getWord();
                    int padding = Math.max(0, 20 - word.length());
                    String formattedString = String.format("======%s%" + padding + "s已同步========", word, "");
                    System.out.println(formattedString);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Word {
        public String word;
    }

    private GetMethod getGetMethod(YouDaoWordItem youDaoWordItem) {
        String word = youDaoWordItem.getWord();
        StringBuilder switchSpace = new StringBuilder();
        // url can't exist space , we need to trans space to "%20"
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ' ') {
                switchSpace.append("%20");
            } else {
                switchSpace.append(word.charAt(i));
            }
        }
        GetMethod getMethod = new GetMethod(getWordUrl + switchSpace);
        getMethod.addRequestHeader("Cookie", cookie);
        return getMethod;
    }

    private RequestEntity getRequestEntity(String item) throws UnsupportedEncodingException {
        Word word = new Word();
        word.word = item;
        String input = JSON.toJSONString(word);
        RequestEntity requestEntity = new StringRequestEntity(input, "application/json", "UTF-8");
        return requestEntity;
    }

    private PostMethod getPostRequest(String item) throws UnsupportedEncodingException {
        PostMethod postMethod = new PostMethod(collectionUrl);
        postMethod.setRequestHeader("cookie", cookie);
        ShanBayPostWord word = new ShanBayPostWord(item);
        String shanBayWord = JSON.toJSONString(word);
        RequestEntity requestEntity = new StringRequestEntity(shanBayWord, "application/json", "UTF-8");
        postMethod.setRequestEntity(requestEntity);
        return postMethod;
    }

}
