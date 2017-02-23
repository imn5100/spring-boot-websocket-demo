# spring-boot-webSocket-demo
Simple demo of spring-boot build webSocket server.</br>
使用Spring-boot的webSocket模块一个简单的例子。<br>
chat.html 基于ws的网页端群聊demo。<br>
canvas.html 基于canvas与ws的同步绘图demo,多人同步绘制一幅图。<br>
运行：
```
mvn spring-boot:run
```
或使用maven install后生成websocket-demo-1.0.jar文件<br>
直接使用java运行:
```
java -jar websocket-demo-1.0.jar
```
之后直接用浏览器打开 /src/main/resources/templates/chat.html 或 canvas.html




