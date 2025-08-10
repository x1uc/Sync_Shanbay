const get_coolection_words = async (cookie, url) => {
    const res = await fetch(url, {
        headers: {
            'Cookie': cookie,
            'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36',
            'Accept': 'application/json, text/javascript, */*; q=0.01',
            'Accept-Language': 'zh-CN,zh;q=0.9'
        },
        method: 'GET'
    });
    const data = await res.json();
    if (data.code !== 0) {
        throw new Error(`Error fetching collection: ${data}`);
    }
    return data.data.itemList.map(item => item.word);
};

export { get_coolection_words };