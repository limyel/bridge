# bridge

> 安得五彩虹，驾天作长桥

一个简单的基于 netty 实现的 TCP 请求转发代理程序（可用于内网穿透）。

写它的原因一方面是我有内网穿透的需求，另一方面是刚学了 netty，想做个简单的东西来练练手。写代码的过程中，我明显感觉到 netty 对 Java NIO 的封装有多爽，简直是 **NIO for Human**。

## 使用

将服务端客户端打包成 jar 包（bridge-server.jar、bridge-client.jar）之后，就可以使用了。

### 服务端

服务端的配置文件默认放在 jar 包当前目录下，名字是 `server.properties`，所以直接启动服务端的话 `bridge-server.jar` 会读取 `./server.properties` 文件的配置。如果当前目录下没有配置文件，程序提供了默认的启动配置，默认及可修改配置如下：

```properties
port=9876
```

所以直接启动服务端时：

```bash
java -jar bridge-server.jar
```

会在本地监听 9876 端口。

如果需要指定配置文件的地址，直接加上运行参数即可：

```bash
java -jar bridge-server.jar /home/limyel/config.properties
```

### 客户端

客户端的配置文件机制和服务端差不多，默认读取 jar 包当前目录下的 `client.properties`。客户端程序可配置选项如下：

```properties
# 服务端IP地址
server-host=xxx.xxx.xxx.xxx
# 服务端端口
server-port=xxxx

# 代理列表，remote——服务端对外打通的端口，local——本地代理的地址:端口
proxy={remote:xxxxx, local:xxx.xxx.xxx.xxx:xxxx}, {remote:yyyyy, local:yyy.yyy.yyy.yyy:yyyy}
```