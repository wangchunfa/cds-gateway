# 一、功能说明  
首云统一网关服务，基于spring cloud zuul开发，提供如下功能：
- 限流
- 前置请求日志记录，并设置Trace ID
- 后置返回日志记录
- 统一错误处理
- 整合了Hystrix监控

# 二、使用说明
## 健康检查接口
    浏览器访问  
    http://localhost:9100/actuator/health  
    返回UP，说明服务正常
    {"status":"UP"}

## 部署说明
- 需要在配置中心初始化配置信息，在配置中心数据库执行SQL文件：gateway-ini.sql

## 动态刷新配置
如果需要增加应用到网关，或者需要修改已加入网关的应用参数，不需要重启网关服务。需要执行网关的刷新配置接口，如果是集群部署，
需要每个集群节点都要刷新配置。刷新接口如下：

    http://{网关IP}:{网关端口}/actuator/refresh
    
## 监控网关API调用情况
在浏览器访问如下URL
http://{网关IP}:{网关端口}/hystrix  
在Hystrix Dashboard文本框输入(注：是整个URL)：   
http://{网关IP}:{网关端口}/actuator/hystrix.stream 