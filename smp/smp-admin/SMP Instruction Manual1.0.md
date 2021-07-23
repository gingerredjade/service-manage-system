# 服务管理系统1.0(ServiceManagePlatform)

1.0版本不含权限管理功能。

# 使用手册

## 1. 环境简介

服务管理系统（smp-admin）依赖以下软件：

```bash
- JDK：1.8+
- 浏览器：Chrome76+或者Firefox66+
- 数据库：DM7(2018+) / mysql-8.0.17+

- 操作系统：Windows需win7 64+
```



## 2. 项目软件包组织结构

├── dbfile   										 		  #数据库脚本

├── docs   										 			#说明文档

├── smp-admin   										 #软件包目录

  ├── README.md										#说明文件

  ├── smp-admin-0.0.1-SNAPSHOT.jar    #软件包
  ├── show/visual										 #可视化

  	  └── smp-show	  						       #管理系统UI+服务列表UI（New）

 ├── application.yml     							  #配置文件
 ├── application-business.yml   			   #配置文件

 ├── application-dm.yml         	 			  #DM数据库配置文件
 ├── application-mysql.yml    	 			  #MySQL数据库配置文件

 └── start-smp-admin.sh			 			  #启动脚本

使用方法参见smp-admin其中的README.md说明文档。

## 3. 项目部署与访使用

### 3.1 前置部署

#### 3.1.1 JDK

1. 服务器安装JDK环境

2. 服务器配置Java环境变量

   ```bash
   [Windows]
   (1) JAVA_HOME：JDK安装路径，注意定位到jdk版本那一层路径
   (2) PATH：%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;或者直接%JAVA_HOME%\bin;
   (3) CLASSPATH:.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;
   
   [Linux]
   CentOS中卸载系统自带的OpenJDK，安装Sun的JDK
   	rpm -qa|grep jdk		检测系统是否安装
   	yum -y remove java 复制的jdk文件名
   	rpm -ivh --prefix=/home/jhy/softwares /home/jhy/tools/jdk-8u65-linux-x64.rpm
   	
   	修改环境变量/etc/profile,在其中导入JAVA_HOME,导入PATH
   	export JAVA_HOME=/home/mmss/jdk1.8.0_65
   	export PATH=$PATH:$JAVA_HOME/bin
   ```

#### 3.1.2 DM7 / MySQL8.0.17

​	DM7略。

​	MySQL8.0.17参见文档《MySQL-8.0.17 install(WIN&LINUX).pdf》。

​	**【注意】**

​	如果**安装在Windows操作系统**，在**安装MySQL 8.0 Server前**，须**预安装  .Net4.5 和 VCRUNTIME140_1.dll组件 **。

- .Net4.5软件见：microsoftnetframework.zip。

- VCRUNTIME140_1.dll组件见：VCRUNTIME140_1.dll组件.zip。

### 3.2 服务管理系统部署步骤

1. 请确保前置部署已正确完成，即远程服务器环境（数据库和JDK1.8）已经安装好。
   
     1） JDK1.8版本；
     

​       2） 若使用DM数据库，DM数据库需DM7（2018年版本+）；

​       3） 若使用MySQL数据库，MySQL数据库需使用mysql-8.0.17+版本。



2. 创建数据库。
   
     【MySQL数据库】

     **注意**：安装数据库时，使用用户名：root；密码：gisnci；使用其他亦可，如若使用其他，需在后面的管理系统里修改MySQL数据库连接信息即可。

     ​	方法一：使用Navicat可视化创建数据库。
     
     ```
     连接到安装好的数据库；
     右键点击“新建数据库”；
     数据库名为：fwgl；
     字符集选择：utf8；
排序规则选择：utf8_general_ci。
     ```

     ​	方法二：使用命令创建数据库。
     
     ```
     mysql -u root -p  			登录mysql；
     CREATE DATABASE fwgl DEFAULT CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;		
     							创建数据库；
     
     show databases;  	 		查看服务器上当前存在的数据库；
     use fwgl;					访问指定数据库；
     drop database fwgl;			删除数据库；（此处为说明，仅在需删数据库时使用）
     
     show tables;				查看含有的表；
     flush privileges;			刷新；
     ```
     
     
     【DM数据库】
     
     ​	DM管理工具中，点击“用户”-“管理用户”，右键“管理用户”的“新建用户”。
     
     - 用户名：FWGL；
     - 密码：gis12345678；
     - 所属角色：DBA勾选授予、转授；PUBLIC勾选授予。
     
     ​	然后点击“确定”。
     
     ​         

3. 导入数据库基础数据。

     ```bash
     [若是DM数据库]：
     dbfile/dm/smp_ddl.sql
     dbfile/dm/smp_sys_dml.sql
     执行上述文件中的插入数据部分SQL即可。
     
     [若是MySQL数据库]：
     dbfile/mysql/smp_ddl.sql
     dbfile/mysql/smp_sys_dml.sql
     dbfile/mysql/smp_bus_dml.sql
     ```

     无可视化界面操作，MySQL数据库可使用命令导入数据。

     ```
     mysql -u root -p  			登录mysql；
     use fwgl;					访问指定数据库；
     source D:/.../smp_ddl.sql
     source D:/.../smp_sys_dml.sql
     source D:/.../smp_bus_dml.sql
     ```

     

4. 解压前端代码包并修改需要连接的后台配置

    - 软件包中的**前端可视化部内容需要现解压**，坐标**smp-admin/show/visual/smp-show-XXXX.zip**；

    - 修改**smp-admin/show/visual/smp-show/api.js中的api.js属性值**，IP_POST中指定连接的后端的服务的IP地址及端口。

      【注】IP、端口应与服务运行所在机器一致，应**使用实际IP**，不能用localhost、127.0.0.1。

    

5. 端口默认使用8998。如若修改需修改前后端配置，以保持前后端服务IP及端口一致。

    ```bash
    [后端端口修改位置]：
    1、smp-admin/application.yml文件中的server.port属性。
    
    [前端端口修改位置]：
    1、smp-admin/show/visual/smp-show/api.js中的api.js属性值。
    IP_POST中指定连接的后端的服务的IP地址及端口。
    ```

    

6. 修改数据库连接。

    ```bash
    [若连接DM数据库]：
    smp-admin/application-dm.yml
    修改上述DM数据库配置文件中的DM数据库连接信息：
    spring.datasource.url
    spring.datasource.username
    spring.datasource.password
    
    [若连接MySQL数据库]：
    smp-admin/application-mysql.yml
    修改上述MySQL数据库配置文件中的MySQL数据库连接信息：
    spring.datasource.url
    spring.datasource.username
    spring.datasource.password
    ```

7. 配置拇指图存储路径。（当前可忽略）

    ```
    smp-admin/application-business.yml
    修改preread.upload-path。
    注意：存储路径须以'\''（Windows）或者'/'（Linux）结束。
    ```

    

8. 启动服务。

    ```bash
    在smp-admin/目录下，执行
    java -jar smp-admin-1.0.0.jar
    或者执行
    start-smp-admin.sh
    ```

    启动无误后，进行后续步骤。

9. 服务列表使用方式。

     ```bash
     [服务管理系统]
     	http://IP:PORT/visual/smp-show/index.html
     
     [服务管理系统接口API]
         http://IP:PORT/swagger-ui.html查看服务端API接口
     
     [服务管理系统服务目录信息]
     	登录服务系统后点击“服务管理”-“服务浏览”-“服务目录”
     ```

     

### 3.3 服务管理系统访问使用

#### 3.3.1 服务管理系统

​	http://192.168.56.222:8998/visual/smp-show/index.html

#### 3.3.2 服务管理系统API接口

​	http://192.168.56.222:8998/swagger-ui.html



### 3.4 服务接口调用说明

​	接口含义及使用说明详见【服务管理系统API接口】。

调用时，需设置请求头：

```
'Content-Type': 'application/json;charset=UTF-8'
```



#### 3.4.1 服务主题管理

##### 3.4.1.1 获取全部已注册服务主题列表

	GET /subject/listAll

##### 3.4.1.2 分页获取已添加服务主题列表

支持指定查询过滤参数，过滤参数名可选项包括：name（主题名称）。

```
POST /subject/list
[参数模型]：
{
  "pageNum": 0,
  "pageSize": 0,
  "params": [
    {
      "name": "string",
      "value": "string"
    }
  ]
}
```

调用示例（Vue）：

- data()

```Vue
pageRequest: {pageNum: 1, pageSize: 10}
```

- methods

```Vue
	  // 获取分页数据
	  findPage: function (data) {
        if (data !== null) {
          this.pageRequest = data.pageRequest;
        }
        this.pageRequest.params = [{name: 'name', value: this.filters.name}]
        //this.$axios.post('http://127.0.0.1:8998/subject/list', this.pageRequest, {timeout: 1000 * 60 * 2}).then((res) => {
        this.$api.svcSubject.findSvcSubjectPage(this.pageRequest).then((res) => {
          this.pageResult = res.data.data;
          //console.log(res.data.data)
        }).then(data!=null?data.callback:'')
      }
```

- axios api

```Vue
// 分页查询服务主题
export const findSvcSubjectPage = (data) => {
  return axios({
    url: API_URL + '/subject/list',
    method: 'post',
    data
  })
}
```

##### 3.4.1.3 过滤查询服务主题列表

支持指定查询过滤参数，过滤参数名可选项包括：name（主题名称）。

```
POST /subject/filter
[参数模型]：
{
  "params": [
    {
      "name": "string",
      "value": "string"
    }
  ]
}
```

调用参数构造示例：

```
{
  "params": [
    {
      "name": "name",
      "value": "地"
    }
  ]
}
```



##### 3.4.1.4 其他请见接口API



#### 3.4.2 服务类型管理

##### 3.4.2.1 获取全部已注册服务类型列表

	GET /type/listAll

##### 3.4.2.2 分页获取已添加服务类型列表

支持指定查询过滤参数，过滤参数名可选项包括：

- name（类型名称），
- subjectName（类型所属的主题名称）。

```
POST /type/list
[参数模型]：
{
  "pageNum": 0,
  "pageSize": 0,
  "params": [
    {
      "name": "string",
      "value": "string"
    }
  ]
}
```

调用示例（Vue）：

- data()

```Vue
pageRequest: {pageNum: 1, pageSize: 10}
```

- methods

```Vue
	  // 获取分页数据（name是类型名称;subjectName是类型所属的主题名称）
      findPage: function (data) {
        if (data !== null) {
          this.pageRequest = data.pageRequest;
        }
        this.pageRequest.params = [
          {name: 'name', value: this.filters.name},
          {name: 'subjectName', value: this.filters.subjectName}
        ]
        this.$api.svcType.findSvcTypePage(this.pageRequest).then((res) => {
          this.pageResult = res.data.data;
          //console.log(res.data.data)
        }).then(data!=null?data.callback:'')
      }
```

- axios api

```vue
// 分页查询服务类型
export const findSvcTypePage = (data) => {
  return axios({
    url: API_URL + '/type/list',
    method: 'post',
    data
  })
}
```

##### 3.4.2.3 过滤查询服务类型列表

支持指定查询过滤参数，过滤参数名可选项包括：

- name（类型名称），
- subjectName（类型所属的主题名称）。

```
POST /type/filter
[参数模型]：
{
  "params": [
    {
      "name": "string",
      "value": "string"
    }
  ]
}
```

调用参数构造示例：

```
{
  "params": [
    {
      "name": "subjectName",
      "value": "气象"
    },
{
      "name": "name",
      "value": "分析"
    }
  ]
}
```



##### 3.4.2.4 其他请见接口API



#### 3.4.3 服务信息管理

##### 3.4.3.1 查询全部已注册服务

```vue
GET /service/listAll
```

##### 3.4.3.2 分页查询服务信息

支持指定查询过滤参数，过滤参数名可选项包括：

- terminal（所属终端类型）[编号形式]
- deptName（服务所属机构名称），
- svcSubjectName（服务所属主题名称），
- svcTypeName（服务所属类型名称），
- name（服务名称），
- alias（服务别名），
- version（版本）。
- svcKeyword（关键字），
- auditState（服务审核状态）[编号形式]，
- releaseState（服务发布状态）[编号形式]

注：其中的编号形式对应关系见【服务管理系统-字典管理】

```vue
POST /service/list
[参数模型]：
{
  "pageNum": 0,
  "pageSize": 0,
  "params": [
    {
      "name": "string",
      "value": "string"
    }
  ]
}
```

调用示例（Vue）：

- data()

```Vue
pageRequest: {pageNum: 1, pageSize: 10}
```

- methods

```vue
	  // 获取已注册服务分页数据
      findPage: function (data) {
        if (data !== null) {
          this.pageRequest = data.pageRequest;
        }
        this.pageRequest.params = [
          {name: 'name', value: this.filters.name},
          {name: 'alias', value: this.filters.alias},
          {name: 'svcSubjectName', value: this.filters.svcSubjectName},
          {name: 'svcTypeName', value: this.filters.svcTypeName},
          {name: 'svcKeyword', value: this.filters.svcKeyword},
          {name: 'deptName', value: this.filters.deptName},
          {name: 'version', value: this.filters.version}
        ]
        this.$api.svc.findSvcPage(this.pageRequest).then((res) => {
          this.pageResult = res.data.data;
          //console.log(res.data.data)
        }).then(data!=null?data.callback:'')
      }
```

- axios api

```vue
// 分页查询服务信息
export const findSvcPage = (data) => {
  return axios({
    url: API_URL + '/service/list',
    method: 'post',
    data
  })
}
```



##### 3.4.3.3 过滤查询服务信息

该接口与【分页查询服务信息】接口的不同之处在于，该接口没有分页功能。

支持指定查询过滤参数，过滤参数名可选项包括：

- terminal（所属终端类型）[编号形式]
- deptName（服务所属机构名称），
- svcSubjectName（服务所属主题名称），
- svcTypeName（服务所属类型名称），
- name（服务名称），
- alias（服务别名），
- version（版本）。
- svcKeyword（关键字），
- auditState（服务审核状态）[编号形式]，
- releaseState（服务发布状态）[编号形式]

注：其中的编号形式对应关系见【服务管理系统-字典管理】

```vue
POST /service/filter
[参数模型]：
{
  "params": [
    {
      "name": "string",
      "value": "string"
    }
  ]
}
```

调用参数构造示例：

```
{
  "params": [
    {
      "name": "deptName",
      "value": "电子科技"
    },
    {
      "name": "name",
      "value": "气象"
    }
  ]
}
```



##### 3.4.3.4 其他请见接口API