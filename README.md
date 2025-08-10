# 有道-扇贝单词同步工具

该工具的主要功能是一次性同步所有有道单词的生词到扇贝单词生词本

## 配置选项

在 [`main.js`](main.js) 中可以修改以下配置：

- `current_sync_word_number`: 每次同步的单词数量（默认100个，请根据你实际情况及inxing修改）
- `sleep(1000)`: 请求间隔时间（默认1000毫秒）

## 功能特性

- 自动获取有道词典收藏的单词列表
- 在扇贝单词中查找对应单词
- 自动收藏到扇贝单词词库中
- 支持批量同步，避免请求频率过高

## 使用方法

### 1. 环境准备

确保已安装 Node.js (版本 >= 14.0)

### 2. 获取 Cookie

#### 获取有道词典 Cookie：
1. 打开浏览器，访问 [有道词典网页版](https://dict.youdao.com/)
2. 登录你的账号
3. 按 F12 打开开发者工具
4. 在 Network 标签页中刷新页面
5. 找到任意一个请求，复制 Cookie 值

#### 获取扇贝单词 Cookie：
1. 打开浏览器，访问 [扇贝单词网页版](https://web.shanbay.com/)
2. 登录你的账号
3. 按 F12 打开开发者工具
4. 在 Network 标签页中刷新页面
5. 找到任意一个请求，复制 Cookie 值

### 3. 配置 Cookie

编辑 [`main.js`](main.js) 文件，替换以下变量：

```javascript
const youdao_cookie = '你的有道词典Cookie';
const shanbay_cookie = '你的扇贝单词Cookie';
```

### 4. 运行同步

```bash
node main.js
```


## 工作原理

1. 通过 [`get_coolection_words`](youdao.js) 函数获取有道词典收藏的单词列表
2. 使用 [`get_word_data`](shanbay.js) 函数在扇贝词库中查找对应单词
3. 通过 [`decode`](decode.js) 函数解析扇贝返回的加密数据
4. 调用 [`collection_word`](shanbay.js) 函数将单词添加到扇贝收藏夹

## 注意事项

- 该脚本的主要功能是一次性同步所有有道单词的生词到扇贝单词生词本
- 请确保 Cookie 有效，如果同步失败可能需要重新获取。扇贝单词的Cookie的有效期为一周，每周需要进行替换
- 扇贝单词的单词本有自己的规则，每日收藏的单词会纳入今日生词，被当作一个新的生词来进行复习，所以同步是请控制同步单词数，保证复习状态。
- 程序会自动跳过扇贝词库中不存在的单词
- 建议不要设置过短的请求间隔，避免被限制访问


## 文件说明

- [`main.js`](main.js): 主程序入口
- [`youdao.js`](youdao.js): 有道词典API相关功能
- [`shanbay.js`](shanbay.js): 扇贝单词API相关功能  
- [`decode.js`](decode.js): 扇贝数据解密功能
- [`package.json`](package.json): 项目配置文件
