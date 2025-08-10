import { decode } from './decode.js';
import { get_word_data, collection_word } from './shanbay.js';
import { get_coolection_words } from './youdao.js';

const current_sync_word_number = 100;
const youdao_cookie = 'OUTFOX_SEARCH_USER_ID_NCOO=54886629.91094269; OUTFOX_SEARCH_USER_ID=-743285457@114.219.71.136; NTES_YD_SESS=uGYaEWYOUn0scBNytOhFm7sdVIdvzFcnEL47G_bRG90QZ2qhZLps8NdJ7wck_PJn3bFr0vG.g.kRO.MqidRrmlgjO9TM5LgwyK7XZKE8Flir5AZYK5F58ATKBn1WS00kZrK_rE38lQwusEwmIYXpQm9tlV8uFm_pYdAcolObX8KCNnAG_K83kWyEX5lttuY6rSiNTMVrBoGQfZWl4l0YsaNfN_kzTlhgUZWkQexMpN1Vi; NTES_YD_PASSPORT=o8XeyRzqOyYBqzTIS2.ag9W77y_ORL6hFjOHbgCcI_dIuWdmu57zsxeC1hoF94CkyJHjNLp2n2F7inOZNrVAo.FXKnp1ELf7fXPR.7qBBoy2dimTYWFivquvUAmrD8u9AgwrWiCzCh7nJQGBy98JXQVIqxoc0sRNYmFD0pDgqDhHR5GMMAskLDN6XqW123e3f.3vpgCesV3726ZkDAYeXe_y9MW8US8Ig; S_INFO=1754836783|0|0&60##|18298157412; P_INFO=18298157412|1754836783|1|dict_logon|00&99|null&null&null#jis&320500#10#0|&0||18298157412; DICT_SESS=v2|4RK6m26x6VkEhLzYOM6B0wFnfJKOLll0qyOMgynHeZ0UEkflM64wK0OEP4Jyn4PFRJK6LYERf6S0gukMTZhLpF0Of0HgLOMk50; DICT_PERS=v2|urs-phone-web||DICT||web||-1||1754836784212||114.219.71.136||urs-phoneyd.28f0732ce0684cd2b@163.com||JFkLU5hM6BRzf0LwBh4UW0QukMJ4PMPK0PLnMU5O4PLRPK6LJyOMYfRYMnfwyhLQu0PBnfU5RMYGRzERfeShHlG0; DICT_LOGIN=3||1754836784216; DICT_UT=urs-phoneyd.28f0732ce0684cd2b@163.com; _uetsid=a9db1be075f711f0ba435f46a720bac5; _uetvid=a9db4ee075f711f0a03d57eca2864d79';
const shanbay_cookie = 'sajssdk_2015_cross_new_user=1; auth_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjM4NjYxNDEzLCJleHAiOjE3NjI2MTIyMDIsImV4cF92MiI6MTc2MjYxMjIwMiwiZGV2aWNlIjoiIiwidXNlcm5hbWUiOiJQaG9uZV9hOTkxM2Q0NDk1MDRjNDZlIiwiaXNfc3RhZmYiOjAsInNlc3Npb25faWQiOiI4MDIwMmM3MDc1ZjYxMWYwOTZhNGUyNmVjNjQ3ZmM4OCJ9.7bKLLDPldvlXnUWDOVUyfyKKeGf7koDusHQH_QkngkA; csrftoken=ab9c5e1a464fb1bcf985f328d6ba8fdb; tfstk=gEREbd0Ec-F6ztrsOhCz0SopVAfdM_oXrQs5rUYlRMj3O2ByQUxnJTM-pbvwqhBCxWgdUh-W2BsCRU_kbhK9AeT5typ9cEVBOvC7a6CRZmijGjgpJ_Clg6EwKyCGzZUnNgbhI6QW_xNmpjTpJPYKKnJIGzzSXkshq3XhIlbfj7bkqgfi7ab7x8jHqF0NXwq3-WVosObVu_jkq_0wSGQlZwxlZV8GXacOiZQsCMXnSlAsjxwNY9SD-GP2XCQH7JLCbyze_dWFm7SaZ7AN-UugnSPiZivvjdvH3yV1F1Iwfw--MWChUpxFS_lzs6-p-QXwNcZFWpxesTRsyvQ9KFRPHQ38dNpMkBByBbP5NTb2v6Anfk5ySB6vhOEEfg9X5BXwmcNw2TJHYNfc4_ERSnTWwpruU9bO7igZ7RhmtDJHFWa8e8BgtNSjunw8e9bO7igZ78eRIrbNcq-f.; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22eyxfle%22%2C%22%24device_id%22%3A%2219894636a191c5a-02722222b66b478-1e462c6e-2871189-19894636a1a1967%22%2C%22props%22%3A%7B%22%24latest_referrer%22%3A%22%22%2C%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%7D%2C%22first_id%22%3A%2219894636a191c5a-02722222b66b478-1e462c6e-2871189-19894636a1a1967%22%7D';

const shanbay_getword_url = 'https://apiv3.shanbay.com/wordsapp/words/vocab?word=';
const shanbay_collection_url = 'https://apiv3.shanbay.com/wordscollection/words';
const youdao_get_coolection_url = `https://dict.youdao.com/wordbook/webapi/v2/word/list?limit=${current_sync_word_number}&offset=0&sort=time&lanTo=&lanFrom=`;

const sleep = (ms) => new Promise(resolve => setTimeout(resolve, ms));

const main = async () => {
    try {
        const words = await get_coolection_words(youdao_cookie, youdao_get_coolection_url);

        for (let word of words) {
            const data = await get_word_data(shanbay_cookie, shanbay_getword_url, word);
            if (!data) {
                continue;
            }
            const word_info = decode(data);
            await collection_word(shanbay_cookie, shanbay_collection_url, word_info.id);
            console.log(`已收藏单词: ${word_info.word}`);
            await sleep(1000); // 防止请求过快
        }
    } catch (error) {
        console.error('Error in main function:', error);
    }
};

main();
