# 打包、部署、启动、使用
## 一、打包
### （一）打包方式
打包方式有两种：
- IntelliJ IDEA工具。
- 命令行执行maven打包命令，需遵循约定的打包顺序。

#### - IntelliJ IDEA
1. Maven工具栏中选择应用模块；
2. 展开模块下的Lifecycle；
3. 点击执行命令操作，如clean、package、intall等。

#### - 命令行
1. 在各个模块源码根目录执行命令；
2. 两命令选一使用
```
# 不执行但编译测试用例类，生成相应的class文件至target/test-classes下
mvn clean package -DskipTests
# 不执行也不编译测试用例类
mvn clean package -Dmaven.test.skip=true（推荐）

注：
-DskipTests、-Dmaven.test.skip=true表示希望不执行测试用例（仅限非正式发布时）
```

### （二）打包顺序
[如果打包时执行测试检测工作，务必遵循以下顺序]

给出了模块打包顺序以及通过maven命令打包时的命令，打包结果在各自模块的target目录下***.jar。

1. 打包gis-register-center模块
2. 打包gis-api-gateway模块
3. 安装gis-service-common模块
4. 打包gis-service-core模块
5. 打包gis-service-show模块
```
cd gis-register-center
mvn clean package -Dmaven.test.skip=true

cd gis-api-gateway
mvn clean package -Dmaven.test.skip=true

cd gis-service-common
mvn clean install -Dmaven.test.skip=true

cd gis-service-core
mvn clean package -Dmaven.test.skip=true

cd gis-service-show
mvn clean package -Dmaven.test.skip=true
```
注意：third-party-supermap模块会打成jar包放到gis-service-core模块引用。


## 二、部署启动
Linux上可以通过nohup或者supervisor（推荐）进行启动。

项目源码使用模块编程，client端注册到了eureka服务端，所以启动项目应该也要启动eureka的服务端。

### 1. 启动gis-register-center
#### 【部署目录组织】
```
gis-register-center文件夹，含以下文件
    gis-register-center-0.0.1-SNAPSHOT.jar
    application-standalone.yml
    application-eureka1.yml
    application-eureka2.yml
    start-gis-register-center-standalone.sh
```

#### 【单实例部署（推荐）】
**1）** 启动

启动命令中加入spring.profiles.active参数，指定使用的配置。
```
java -jar gis-register-center-0.0.1-SNAPSHOT.jar --spring.profiles.active=standalone --server.port=8761
或者
nohup java -jar gis-register-center-0.0.1-SNAPSHOT.jar --spring.profiles.active=standalone --server.port=8761 > console-gis-register-center.file 2>&1 &
```
或者直接执行启动脚本
```
start-gis-register-center-standalone.sh
```

**2）** 检测
```
浏览器访问
http://IP:8761/ 查看实际注册的服务及其状态信息、注册中心本身细节的页面
    - 状态为UP
    
如上，即为单实例注册中心启动成功
```

#### 【多实例高可用部署】 

运行两个Eureka实例，来保证高可靠和高可用。
可参考https://blog.csdn.net/gingerredjade/article/details/100515563。

**1）** 配置hosts，增加如下配置项
```
127.0.0.1 eureka1
127.0.0.1 eureka2
```

**2）** 启动

启动命令中加入spring.profiles.active参数，指定使用的配置。
```
java -jar gis-register-center-0.0.1-SNAPSHOT.jar --spring.profiles.active=eureka1 --server.port=8761
java -jar gis-register-center-0.0.1-SNAPSHOT.jar --spring.profiles.active=eureka2 --server.port=8762
或者
nohup java -jar gis-register-center-0.0.1-SNAPSHOT.jar --spring.profiles.active=eureka1 --server.port=8761 > console-gis-register-center.file 2>&1 &
nohup java -jar gis-register-center-0.0.1-SNAPSHOT.jar --spring.profiles.active=eureka2 --server.port=8762 > console-gis-register-center.file 2>&1 &

等待几分钟，两个控制台不再报connection refuse，出现Started Eureka Server，进行注册中心检测步骤。
```
或者直接执行启动脚本
```
start-gis-register-center-cluster.sh
```

**2）** 检测
```
浏览器访问
http://eureka1:8761/
    - 状态为UP
    - 有两个gis-register-center注册实例，分别是8761、8762端口
    - DS Replicas处显示副本eureka2
    - registered-replicas、available-replicas对应值为http://eureka2:8762/eureka/
http://eureka2:8762/
    - 状态为UP
    - 有两个gis-register-center注册实例，分别是8761、8762端口
    - DS Replicas处显示副本eureka1
    - registered-replicas、available-replicas对应值为http://eureka1:8761/eureka/
如上，即为两节点高可用注册中心启动成功
```

### 2. 启动gis-api-gateway
**0）** 部署目录组织
```
gis-api-gateway，含以下文件
    gis-api-gateway-0.0.1-SNAPSHOT.jar
    bootstrap.yml
    start-gis-api-gateway.sh
```

**1）** 配置查看
 ```
 [bootstrap.yml]
    eureka.client.service-url.defaultZone
    authority.access.enabled
 ```


**2）** 启动
 ```
 java -jar gis-api-gateway-0.0.1-SNAPSHOT.jar
 或者
 nohup java -jar gis-api-gateway-0.0.1-SNAPSHOT.jar > console-gis-api-gateway.file 2>&1 &
 ```
 或者直接执行启动脚本
 ```
 start-gis-api-gateway.sh
 ```
 
**3）** 检测
 - 访问http://localhost:8761/
 - 检查是否有gis-api-gateway注册实例，且是否为UP状态
 - 点击实例对应的Status后面的链接可查看应用的配置说明，访问的即是http://ip:port/actuator/info。
     

### 3. 启动gis-config-server(不使用)
### 4. 启动gis-service-core
**0）** 部署目录组织
```
gis-service-core，含以下文件
    gis-service-core-1.0-SNAPSHOT.jar
    application.yml
    application-gisapp.yml
    start-gis-service-core.sh
```

**1）** 配置查看
 ```
 [application.yml]
    eureka.client.service-url.defaultZone
    3rd.upload-path（配置成 “<jar包同级目录>/3rslibs” ，并在启动前将已有的三方服务模块放置该目录以备启动时加载）
 
 [application-gisapp.yml]
 检查、配置每类服务的
    - author
    - server-prefix
    - server-handler-class
 ```

**2）** 启动
 ```
 java -jar gis-service-core-1.0-SNAPSHOT.jar
 或者
 nohup java -jar gis-service-core-1.0-SNAPSHOT.jar > console-gis-api-gateway.file 2>&1 &
 ```
  或者直接执行启动脚本
  ```
  start-gis-service-core.sh
  ```
 
**3）** 检测
 - 访问http://IP:8761/
 - 检查是否有gis-service-core注册实例，且是否为UP状态
 - 点击实例对应的Status后面的链接可查看应用的配置说明，访问的即是http://ip:port/actuator/info。

### 5. 启动gis-service-show
**0）** 部署目录组织
```
gis-service-show，含以下文件
    gis-service-show-0.0.1-SNAPSHOT.jar
    application.yml
    application-mysql.yml
    start-gis-service-show.sh
    show/visual/工程所有静态资源
    
其中，所有静态资源即“开发工程中src/main/resources/static目录下的静态文件”。
可动态修改其中的静态文件，不重启服务即可生效。
``` 
 
**1）** 配置查看
 ```
 [application.yml]
    eureka.client.service-url.defaultZone
    preread.upload-path
    preread.layer-upload-path
 
 
 [application-mysql.yml]
    spring.datasource.url
    spring.datasource.username
    spring.datasource.password
 ```
   
**2）** 启动
 ```
 java -jar gis-service-show-0.0.1-SNAPSHOT.jar
 或者
 nohup java -jar gis-service-show-0.0.1-SNAPSHOT.jar > console-gis-service-show.file 2>&1 &
 ```
 或者直接执行启动脚本
 ```
 start-gis-service-show.sh
 ```
 
**3）** 检测
 - 访问http://eureka1:8761/或者http://eureka2:8762/任一
 - 检查是否有gis-service-show注册实例，且是否为UP状态
 - 点击实例对应的Status后面的链接可查看应用的配置说明，访问的即是http://ip:port/actuator/info。



## 三、健康状态检测
不要通过API网关路由这些端点：指标、映射、运行情况、日志等。

我们的基础设施应拒绝任何来自外部直接访问生产环境的微服务的请求。

给客户端发送执行健康状态检测的命令：
```
检查服务是否启动
- 使用Postman发送GET请求：http://localhost:8080/actuator/health 
- 使用终端发送GET请求：curl -X GET http://localhost:8080/actuator/health 
```

## 四、其他说明
网关和服务注册中心之间的交互需要一些时间才能生效。如果在启动所有微服务后的第一分钟内使用应用，则可能会从网关获得一些服务器错误（HTTP状态代码500）。

服务注册中心需要更多的时间来完成它的工作。


