# 服务管理系统2.0(ServiceManagePlatform)

2.0在1.0的基础上增加了权限控制功能，部署模式发生了变化，支持两种部署方式。

# 一、使用手册

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

  ├── smp-admin-0.0.1-SNAPSHOT.jar     #软件包
  ├── show/visual										 #废弃使用

 ├── application.yml     							  #配置文件
 ├── application-business.yml   			   #配置文件

 ├── application-dm.yml         	 			  #DM数据库配置文件
 ├── application-mysql.yml    	 			  #MySQL数据库配置文件

 ├── SMP Instruction Manual2.0.md  	 #部署&使用说明

 └── start-smp-admin.sh			 			  #后端启动脚本（Linux）

├──  smp-visual		 			  					#前端可视化文件

  └── smp-show	  						       #管理系统UI+服务列表UI（New）

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

#### 3.2.1 前置部署（JDK、数据库、数据）

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

     ```
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

#### 3.2.2 软件部署（两种方式二选一）

软件采用前后端分离的开发模式，部署支持前后端统一部署、前后端分离部署，选择其一即可。其中，分离部署形式需要安装Nginx的支持。

##### 3.2.2.1 前后端统一部署

1. 解压软件包package-XXXX.zip。

2. 端口默认使用8998。如若修改需修改前后端配置，以保持前后端服务IP及端口一致。

   ```
   [后端端口修改位置]：
   1、smp-admin/application.yml文件中的server.port属性。
   ```

3. 修改数据库连接。

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

4. 启动服务。

   ```
   在smp-admin/目录下，执行
   java -jar smp-admin-1.0.0.jar
   或者执行
   start-smp-admin.sh（Linux系统下）
   ```

   启动无误后，进行后续步骤。

5. 服务访问。

   ```
   [服务管理系统]
   	http://IP:PORT/smp-show/index.html
   
   [服务管理系统接口API]
       http://IP:PORT/swagger-ui.html查看服务端API接口
   
   [服务管理系统服务目录信息]
   	登录服务系统后点击“服务管理”-“服务浏览”-“服务目录”
   ```

   

##### 3.2.2.2 前后端分离部署（Linux上不推荐）

【后端部署】

1. 除了**软件包package-20210426.zip**，还需要有如下文件：

    - **nginx**。
        - nginx-1.10.3.zip（Windows版本）或者
        - nginx-1.13.3-linux.tar.gz（Linux版本，需要自己安装）。
    - **nginx-smp.conf**（Nginx配置文件）。

2. 解压软件包package-20210426.zip。

3. 端口默认使用8998。如若修改需修改前后端配置，以保持前后端服务IP及端口一致。

    ```
    [后端端口修改位置]：
    1、smp-admin/application.yml文件中的server.port属性。
    ```

4. 修改数据库连接。

     ```
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

5. 启动后端服务。

     ```
     在smp-admin/目录下，执行
     java -jar smp-admin-1.0.0.jar
     或者执行
     start-smp-admin.sh（Linux系统下）
     ```

     启动无误后，进行后续步骤。

6. 服务访问。

     ```
     [服务管理系统接口API]
         http://IP:PORT/swagger-ui.html查看服务端API接口
     ```

     

【前端部署】

1. 安装Nginx。

2. 将**nginx-smp.conf**放置于**nginx安装目录/conf/下**，修改如下配置项。
    转发的后端server对应的IP和端口为软件后端部署机器IP及端口。

    ```
    upstream smpshow.org {
    	server 127.0.0.1:8998;
    }
    ```

    root对应的值配置成前端可视化静态文件的路径，前端可视化静态文件路径在**软件包\smp-visual\smp-show**。

    ```
    location ~ .*\.(js|css|ico|png|jpg|eot|svg|ttf|woff|html) {
    	    # 所有静态文件直接读取硬盘，不用转发了
            root D:\\smp-visual-nginx\\package-20210426\\smp-visual\\smp-show\\;
            # 缓存30天
    	    expires 30d; 
    }
    ```

    

3. 启动nginx


    【Nginx操作命令小结】
    
    Linux操作系统
    
    ```
    1.查看nginx版本号：nginx -v
    2.启动：sbin/nginx或者sbin/nginx -c nginx-smp.conf
    		ps aux|grep nginx	通过进程查看命令查看nginx是否成功启动
    		ps -ef|grep nginx 查询设置的工作衍生进程数		
    		默认使用80端口，在浏览器输入http://nginx服务器IP，如果出现“Welcome to nginx”字样说明启动成功
    3.重启：sbin/nginx -s reload(每次修改了配置环境只需重启即可)
    4.关闭：sbin/nginx -s stop
    5.杀死：killall -9 nginx
    6.卸载Nginx:yum remove nginx	
    ```
    
    WIndows操作系统
    
    ```
    1.查看nginx版本号：nginx -v
    2.启动：nginx.exe -c conf\nginx-smp.conf
    3.重启：nginx.exe -s reload
    4.关闭：nginx.exe -s stop
    ```


​    

4. 访问使用。

    	[服务管理系统]
        		直接访问 http://IP/index.html（请求的端口是80）
        		注意：此IP为Nginx部署所在IP地址。这个请求并没有发给后台的jar上，实际上发给了nginx，由于nginx中进行了配置。静态文件直接去找配置的本地路径，它就会直接去本地中找。后台请求nginx会帮助你转发到你配置的后台服务端。
        		
        	[服务管理系统服务目录信息]
        		登录服务系统后点击“服务管理”-“服务浏览”-“服务目录”

  

### 3.3 服务接口文档等外部网页菜单设置

接口文档采用内嵌外部网页形式实现，网页URL需要根据部署环境更新。

更新方式如下：

1. 软件成功部署。
2. admin账户登录系统后，点击“权限管理”-“菜单管理”，编辑“开放平台”-“接口文档”，更新菜单路由为实际环境URL提交。




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



## 4. Swagger测试页面说明

通过SpringBoot框架编写的后台接口，一般可以通过Swagger UI页面进行接口测试。

由于引入Spring Security安全框架，接口受到保护，需要携带合法的token令牌（一般是登录成功之后由后台返回）才能正常访问，但是swagger本身的接口测试页面默认并没有提供传送token参数的地方，因此提供了定制功能，Swagger UI页面进行接口测试的时候需要提供有效的token方可正确执行。

因此，在Swagger测试页面测试接口时，需要先将登录接口返回的token复制到对应参数，swagger在发送请求的时候会把token放入请求头。

如果没有提供token会报如下错误：

```
{
  "timestamp": 1617854833310,
  "status": 403,
  "error": "Forbidden",
  "message": "Access Denied",
  "path": "/menu/findMenuTree"
}
```

这个错误就是Spring Security针对缺少访问权限的错误反馈信息，说明我们的接口已经受到安全框架的保护。



### 4.1  获取token

访问http://IP:PORT//swagger-ui.html页面；

展开【登录管理】接口操作集，找到其中的【/login】POST方法登录接口，点击loginBean右侧的“示例”标签，在左侧参数处填写正确的account、password参数值，captcha参数值为空。

点击【试一下】按钮进行登录测试。

查看登录成功之后返回的登录认证信息，其中包含JWT生成的token令牌。后续访问需要携带此token作为凭证进行接口访问。

### 4.2  使用token测试其他接口

复制上一步生成的token信息（“token”属性的长字符串），再找到其他的接口，把复制的token 信息粘贴到token令牌参数输入框内，再执行接口。

注意！

**虽然是同一用户携带同一令牌，但只有拥有权限的接口才能访问，没有权限的接口不允许访问。**

应**使用具备指定接口的访问权限的账户登录，获取其对应的合法token**，方能使用该token对指定接口进行测试，否则访问相关接口会返回403错误，接口访问被拒绝！



## 5. 页面权限控制（自记录）

后台权限管理系统，当然少不了权限控制。至于权限控制，前端方面就是指对页面资源的访问和操作控制。

前端资源权限主要分为两个部分，即导航菜单的查看权限和页面增、删、改操作按钮的操作权限。

### 5.1 权限控制方案

#### 5.1.1 菜单类型

代表页面资源的类型。

#### 5.1.2 权限标识

权限标识是对页面资源进行权限控制的唯一标识，主要是增、删、改、查的权限控制。

权限标识主要包含四种，以用户管理为例，权限标识包括

sys:user:add	新增

sys:user:edit	编辑

sys:user:delete	删除

sys:user:view	查看

后台接口根据用户权限加载用户菜单数据返回给前端，前端导航菜单显示用户菜单数据，并在管理页面根据用户操作权限标识设置页面操作按钮的可见性或可用状态，我们这里采用无操作权限则页面操作按钮为不可用状态的方式。

### 5.2 导航菜单实现思路

用户登录系统，用户登录成功之后跳转到首页。

根据用户加载导航菜单。

```
在路由导航守卫路由时加载用户导航菜单并存储到store。加载过程如下，返回结果排除按钮类型：

user→user_role→role→role_menu→menu。
```

导航栏读取菜单树：导航栏页面到store读取导航菜单树并进行展示。

### 5.3 页面按钮实现思路

用户登录系统，用户登录成功之后跳转到首页。

加载权限标识。

```
在路由导航守卫路由时加载用户权限标识集合并保存到store备用。加载过程如下，返回结果是用户权限标识的集合：

user→user_role→role→role_menu→menu。
```

页面按钮控制：

页面操作按钮提供perms属性绑定权限标识，使用disable属性绑定权限判断方法的返回值，权限判断方法hasPerms(perms)通过查找上一步保存的用户权限标识集合是否包含perms来包含说明用户拥有此相关权限，否则设置当前操作按钮为不可用状态。



## 6.嵌套外部网页（自记录）

### 6.1 需求背景

有时候我们需要内嵌外部网页，可以通过点击导航菜单，然后将主内容栏加载外部网页的内容进行显示，如查看服务端提供的SQL监控页面、接口文档等。

这时就要求我们的导航菜单能够解析外部嵌套网页的URL，并根据URL路由到相应的嵌套组件。

### 6.2 实现原理

1. 给菜单URL添加外部嵌套网页格式，除内部渲染的页面URL外，外部网页统一直接以http[s]完整路径开头。
2. 路由导航守卫在动态加载路由时，检测到如果是外部嵌套网页，就绑定到IFrame嵌套组件，最后使用IFrame组件来渲染嵌套页面。
3. 菜单点击跳转的时候，根据路由类型生成不同的路由路径，载入特定的页面内容渲染到步骤2绑定的特定组件上。

### 6.3 代码实现

#### 6.3.1 确定菜单URL

服务接口页面，其实显示的就是服务提供的Swagger接口页面。访问地址是http://localhost:8080/swagger-ui.html 即完整的HTTP地址格式。

我们将Swagger接口的菜单URL配置为http://localhost:8080/swagger-ui.html ，localhost和8080跟随服务设定。届时路由解析时检测到以http开头的就是外部网页，然后绑定到IFrame嵌套页面组件上进行渲染。

#### 6.3.2 创建嵌套组件

在views/pages下创建iframe目录并在其下新建一个IFrame.vue嵌套组件。

IFrame组件在渲染时，读取store的iframeUrl以加载要渲染的内容（通过设置src）。

```Javascript
views/IFrame/IFrame.vue

<template>
  <div class="iframe-container">
    <iframe :src="src" scrolling="auto" frameborder="0" class="frame" :onload="onloaded()">
    </iframe>
  </div>
</template>

<script>
export default {
  data() {
    return {
      src: "",
      loading: null
    }
  },
  methods: {
    // 获取路径
    resetSrc: function(url) {
      this.src = url
      this.load()
    },
    load: function() {
      this.loading = this.$loading({
        lock: true,
        text: "loading...",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.5)",
        // fullscreen: false,
        target: document.querySelector("#kt_content ")
      })
    },
    onloaded: function() {
      if(this.loading) {
        this.loading.close()
      }
    }
  },
  mounted() {
    this.resetSrc(this.$store.state.iframe.iframeUrl);
  },
  watch: {
    $route: {
      handler: function(val, oldVal) {
        // 如果是跳转到嵌套页面，切换iframe的url
        this.resetSrc(this.$store.state.iframe.iframeUrl);
      }
    }
  }
}
</script>

<style lang="scss">
.iframe-container {
  position: absolute;
  top: 0px;
  left: 0px;
  right: 0px;;
  bottom: 0px;
  .frame {
    width: 100%;
    height: 100%;
  }
}
</style>
```



#### 6.3.3 绑定嵌套组件

在导航守卫动态加载路由的时候，解析URL，如果是嵌套页面，就绑定到IFrame组件。

main.js的相关的内容如下：

```Javascript
	  // 创建路由配置
      const route = {
        path: menuList[i].url,
        component: null,
        name: menuList[i].name,
        meta: {
          icon: menuList[i].icon,
          index: menuList[i].id
        }
      }
      let path = getIFramePath(menuList[i].url)
      if (path) {
        // 如果是嵌套页面, 通过iframe展示
        route['path'] = path
        route['component'] = resolve => require([`@/views/pages/iframe/IFrame`], resolve)
        // 存储嵌套页面路由路径和访问URL
        let url = getIFrameUrl(menuList[i].url)
        let iFrameUrl = {'path': path, 'url': url}
        store.commit('addIFrameUrl', iFrameUrl)
      } else {
```

在每次路由时，把路由路径保存到store，如果是IFrame嵌套页面，IFrame就会在渲染时到store中读取iframeUrl以确定渲染的内容。

```Javascript
main.js

/**
 * 处理IFrame嵌套页面
 */
function handleIFrameUrl(path) {
  // 嵌套页面，保存iframeUrl到store，供IFrame组件读取展示
  let url = path
  let length = store.state.iframe.iframeUrls.length
  for(let i=0; i<length; i++) {
    let iframe = store.state.iframe.iframeUrls[i]
    if(path != null && path.endsWith(iframe.path)) {
      url = iframe.url
      store.commit('setIFrameUrl', url)
      break
    }
  }
}
```

在store/modules下新建iframe.js文件，存储IFrame状态。

```Javascript
store/modules/iframe.js

export default {
  state: {
    iframeUrl: [], // 当前嵌套页面路由路径
    iframeUrls: [] // 所有嵌套页面路由路径访问URL
  },
  getters: {
  },
  mutations: {
    setIFrameUrl(state, iframeUrl) { // 设置iframeUrl
      state.iframeUrl = iframeUrl
    },
    addIFrameUrl(state, iframeUrl) { // iframeUrls
      state.iframeUrls.push(iframeUrl)
    }
  },
  actions: {
  }
}
```

iframe.js是一个工具类，主要对嵌套URL进行处理。

```Javascript
utils/iframe.js

/**
 * 嵌套页面IFrame模块
 */

import { baseUrl } from '@/utils/global'

/**
 * 嵌套页面URL地址
 * @param {*} url
 */
export function getIFramePath (url) {
  let iframeUrl = ''
  if(/^iframe:.*/.test(url)) {
    iframeUrl = url.replace('iframe:', '')
  } else if(/^http[s]?:\/\/.*/.test(url)) {
    iframeUrl = url.replace('http://', '')
    if(iframeUrl.indexOf(":") != -1) {
      iframeUrl = iframeUrl.substring(iframeUrl.lastIndexOf(":") + 1)
    }
  }
  return iframeUrl
}

/**
 * 嵌套页面路由路径
 * @param {*} url
 */
export function getIFrameUrl (url) {
  let iframeUrl = ''
  if(/^iframe:.*/.test(url)) {
    iframeUrl = baseUrl + url.replace('iframe:', '')
  } else if(/^http[s]?:\/\/.*/.test(url)) {
    iframeUrl = url
  }
  return iframeUrl
}
```



#### 6.3.4 菜单路由跳转

在菜单路由跳转的时候，判断是否是IFrame路由，如果是就处理成IFrame需要的路由URL进行跳转。

```Javascript
components/MenuTree/index.vue


	handleRoute (menu) {
      // 如果是嵌套页面，转换成iframe的path
      let path = getIFramePath(menu.url)
      if(!path) {
        path = menu.url
      }
      // 通过菜单URL跳转至指定路由
      console.log(3333)
      console.log(menu)
      console.log(path)
      this.$router.push("/" + path)
    }
```



#### 6.3.5 温馨提示

登录系统，点击导航菜单，可以看到在主内容区域加载了接口页面内容。

【注意】

如果页面显示拒绝加载，查看控制台是否是因为X-Frame-Options的设置，因为Spring Security默认是不允许页面被嵌套的，所以X-Frame-Options被默认设置为DENY，这个可以在后台WebSecurityConfig配置类通过

```Java
http.headers().frameOptions().disable();
```

禁用X-Frame-Options设置就可以正常显示了。



# 二、前后端分离的两种部署方式（自记录）

1. 前后端一起部署（前端打包成静态文件后，拷贝到后端项目中，然后部署后端项目）
2. 前后端分离部署，前端使用nginx部署，后端直接运行jar。

## 1、前后端一起部署

1. 前端代码打包生成静态文件目录，smp-show/***。

2. 将前端代码拷贝至后端代码工程文件的resources/public/下。

3. 注意前端不管权限，要在后端配置权限，忽略前端打包的静态文件的权限校验。
   在WebSecurityConfig.java中重写configure(WebSecurity web)接口，如下

   ```Java
   	@Override
   	public void configure(WebSecurity web) throws Exception {
   		//super.configure(web);
   		web.ignoring().antMatchers("/smp-show/**");
   	}
   ```

4. 注意：smp-show/api.js里的API_URL变量的ip可写成localhost，端口要与application.yml中设置的端口保持一致。

5. 打包后端整个工程（含前端打包的静态文件了），生成jar包。

6. 部署、启动jar。

7. 访问

   ```
   http://192.168.55.111:8998/smp-show/index.html
   后台接口文档：http://192.168.55.111:8998/swagger-ui.html
   ```

这种部署方案不存在跨域问题了。跨域是在开发时候使用，真正项目上线之后，打包放在一起了，就不存在跨域了。



## 2、前后端分离部署

1. 安装nginx
2. 拷贝前端打包出来的静态文件目录

### 2.1 Windows上部署

1. 配置nginx配置文件nginx.conf。
   将package-20210423\smp-admin\show\visual\smp-show路径配置到其中的。。。位置。
   
2. 保存文件、退出。

3. 启动nginx。

4. 访问

   ```
   前端：直接访问 http://192.168.55.111/index.html（请求的端口是80）
   	注意：这个请求并没有发给后台的jar上，实际上发给了nginx，由于nginx中进行了配置。
   		- 静态文件直接去找配置的本地路径，它就会直接去本地中找。
   		- 后台请求nginx会帮助你转发到你配置的后台服务端。
   后台接口文档：http://192.168.55.111:8998/swagger-ui.html
   ```

   这种模式，前端、后端虽然在不同的地方部署，但是前后端请求都到nginx，是统一的通过nginx来访问，对于浏览器来说，静态页面也在80上面，请求的接口也是在80上面，所以不用管8998，对浏览器来说就不存在跨域的问题。


### 2.2 Linux上部署

如果访问的时候，提示403错误的话，可能是前端静态文件目录没有访问权限，需要为其赋予权限或者放到可以访问到的目录就可以了。

两种部署方案实际都跟跨域没有关系。开发时候，只需要在nodejs里配置一下请求转发就可以了，上线之后不存在这些问题的。






