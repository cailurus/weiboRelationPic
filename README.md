# 微博人物关系图

欢迎fork~

## 简介
### 2013-03-26
使用d3.js绘图
使用javaweb最基本的servlet+jsp tomcat7.0 (用7.0就应为他支持注解)
用Jsoup 于模拟登陆，(因为sina只支持restful的 非数字 域名，ouath登录后redirect不到localhost) 
故插入登录的cookie，并模拟提交以获得return的authorize code


## 如何运行
1.  在[sina开发](http://open.weibo.com/) 注册 专业版应用，顺便看看[文档](http://open.weibo.com/wiki/API%E6%96%87%E6%A1%A3_V2)
将client_ID，client_SERCRET，redirect_URI，baseURL，填入下载的sdk里的config.properties

2.  获取登录时的cookie 并黏贴进
