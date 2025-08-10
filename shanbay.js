const get_word_data = async (cookie, url, word) => {
    try {
        const result = await fetch(url + word, {
            headers: {
                'Cookie': cookie,
                'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36',
                'Accept': 'application/json, text/javascript, */*; q=0.01',
                'Accept-Language': 'zh-CN,zh;q=0.9'
            },
            method: 'GET'
        });
        const data = await result.json();
        if (!data.data) {
            console.log(`该单词在扇贝单词词库中不存在: ${word}`);
        }
        return data.data;
    } catch (error) {
        console.log(`获取单词数据失败: ${word}, 继续同步`);
        return null;
    }
}

const collection_word = async (cookie, url, word_id) => {
    try {
        const result = await fetch(url, {
            headers: {
                'Cookie': cookie,
                'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36',
                'Accept': 'application/json',
                'Accept-Language': 'zh-CN,zh;q=0.9',
                'Content-Type': 'application/json'
            },
            method: 'POST',
            body: JSON.stringify({ vocab_id: word_id, business_id: 6 })
        });
    } catch (error) {
        console.error(`收藏单词失败: ${word_id}, 错误信息: ${error}`);
        return null;
    }   
}

export { get_word_data, collection_word };