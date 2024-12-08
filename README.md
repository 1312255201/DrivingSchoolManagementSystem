# 哈尔滨理工大学软件22-7班小组大作业
## 驾校管理系统
使用纯JSP实现，6人小组

数据库内需要配置email信息


配置请参考这篇博客，感谢大佬的无私奉献
<br>
https://blog.csdn.net/Lo_CoCo_vE/article/details/131400289

开发使用JDK 21

Tomcat：10.1.28

需要修改Tomcat的log路径可写，或用管理员运行项目

需要对tomcat进行配置

tomcat\conf\servers.xml

 <Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="2000000"
               redirectPort="8443"
               maxPostSize="-1"
               maxParameterCount="1000"
               URIEncoding="UTF-8"
               />
添加对maxPostSize （最大文件上传大小）和 connectionTimeout（超时时间）

否则在上传证件照和请假证明时会出现post获取内容全部为null的情况
