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

2.  获取登录时的cookie 并黏贴进的cookies map的SUE，SUP，SUS里。
3.  cookie的获取方法，我是用chrome(chrome其实是个类xnux的多用户环境)，新建个用户(设置-用户)，并在新用户里输入redirurl。
4.  redirurl的获取，Servlet.Entrance 的第40行 下断点，调试时获取，并将url输进3里，出现一个sina微博的登录授权界面。输入1里的帐号密码。
5.  chrome里当前user里的设置-高级-内容设置-cookie 有个weibo.com的网站，里面有3个key val，黏贴进2
6.  对Servlet.Entrance 的第50行 下断点，调试时 观察 doc内容是否为你 1里注册时的redirurl的网页源代码，if true  congratulation
7.  Enjoy it , fix it

以上为无服务器，无域名，无云时单机模拟的用法，

输入 localhost:8080/weiboRelationPic/Entrance  然后输入uid

![pic](http://i1308.photobucket.com/albums/s607/caorong/syz_zps8be3efd6.png)
