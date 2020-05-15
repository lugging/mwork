### flywaydb 数据库版本维护工具

[官方文档](https://flywaydb.org/documentation)

* Prefix: V for versioned (configurable), U for undo (configurable) and R for repeatable migrations (configurable)
* Version: Version with dots or underscores separate as many parts as you like (Not for repeatable migrations)
* Separator: __ (two underscores) (configurable)
* Suffix: .sql (configurable)


### HikariCP重要参数配置
[文章](!https://www.cnblogs.com/fireround/p/11701369.html)
> 重要参数
* maximum-pool-size
* minimum-idle
* pool-name
* idle-timeout
* max-lifetime
* connection-timeout

初始化过程和连接创建逻辑
当HikariCP初始化时候，会将添加连接到池中，直到达到minimum-idle的数量，此时保持这个状态。当有新的连接池请求时，HikariCP会返回一个connection的代理。当connection都处于使用状态时，若此时有新的连接池请求，HikariCP就会继续新建connection直到达到maximun-pool-size。

> maximum-pool-size
  池中最大连接数（包括空闲和正在使用的连接）。默认值是10，这个一般预估应用的最大连接数，后期根据监测得到一个最大值的一个平均值。要知道，最大连接并不是越多越好，一个connection会占用系统的带宽和存储。但是 当连接池没有空闲连接并且已经到达最大值，新来的连接池请求（HikariPool#getConnection）会被阻塞直到connectionTimeout（毫秒），超时后便抛出SQLException。

> minimum-idle
  池中最小空闲连接数量。默认值10，小于池中最大连接数，一般根据系统大部分情况下的数据库连接情况取一个平均值。Hikari会尽可能、尽快地将空闲连接数维持在这个数量上。如果为了获得最佳性能和对峰值需求的响应能力，我们也不妨让他和最大连接数保持一致，使得HikariCP成为一个固定大小的数据库连接池。

> pool-name
  连接池的名字。一般会出现在日志和JMX控制台中。默认值：auto-genenrated。建议取一个合适的名字，便于监控。

> idle-timeout （毫秒）
  空闲时间。仅在minimum-idle小于maximum-poop-size的时候才会起作用。默认值10分钟。根据应用实际情况做调整，对于一些间歇性流量达到峰值的应用，一般需要考虑设置的比间歇时间更大，防止创建数据库连接拖慢了应用速度。

> max-lifetime
  连接池中连接的最大生命周期。当连接一致处于闲置状态时，数据库可能会主动断开连接。为了防止大量的同一时间处于空闲连接因为数据库方的闲置超时策略断开连接（可以理解为连接雪崩），一般将这个值设置的比数据库的“闲置超时时间”小几秒，以便这些连接断开后，HikariCP能迅速的创建新一轮的连接。

> connection-timeout （毫秒）
  连接超时时间。默认值为30s，可以接收的最小超时时间为250ms。但是连接池请求也可以自定义超时时间（com.zaxxer.hikari.pool.HikariPool#getConnection(long)）。

> initialization-fail-timeout
  如果池无法成功初始化连接，则此属性控制池是否将 fail fast 1

> validationTimeout （毫秒）
  连接将被测试活动的最大时间量 5000 如果小于250毫秒，则会被重置回5秒 

### 暴露Actuator Endpoints
[Endpoints](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready-endpoints)

> http
* management.endpoints.web.exposure.include=*
* management.endpoints.web.exposure.exclude=env,beans

> jms
* management.endpoints.jmx.exposure.include=health,info

### 附录: spy.properties详细说明
```
#指定应用的日志拦截模块,默认为com.p6spy.engine.spy.P6SpyFactory 
#modulelist=com.p6spy.engine.spy.P6SpyFactory,com.p6spy.engine.logging.P6LogFactory,com.p6spy.engine.outage.P6OutageFactory

# 真实JDBC driver , 多个以 逗号 分割 默认为空
#driverlist=

# 是否自动刷新 默认 flase
#autoflush=false

# 配置SimpleDateFormat日期格式 默认为空
#dateformat=

# 打印堆栈跟踪信息 默认flase
#stacktrace=false

# 如果 stacktrace=true，则可以指定具体的类名来进行过滤。
#stacktraceclass=

# 监测属性配置文件是否进行重新加载
#reloadproperties=false

# 属性配置文件重新加载的时间间隔，单位:秒 默认60s
#reloadpropertiesinterval=60

# 指定 Log 的 appender，取值：
#appender=com.p6spy.engine.spy.appender.Slf4JLogger
#appender=com.p6spy.engine.spy.appender.StdoutLogger
#appender=com.p6spy.engine.spy.appender.FileLogger

# 指定 Log 的文件名 默认 spy.log
#logfile=spy.log

# 指定是否每次是增加 Log，设置为 false 则每次都会先进行清空 默认true
#append=true

# 指定日志输出样式  默认为com.p6spy.engine.spy.appender.SingleLineFormat , 单行输出 不格式化语句
#logMessageFormat=com.p6spy.engine.spy.appender.SingleLineFormat
# 也可以采用  com.p6spy.engine.spy.appender.CustomLineFormat 来自定义输出样式, 默认值是%(currentTime)|%(executionTime)|%(category)|connection%(connectionId)|%(sqlSingleLine)
# 可用的变量为:
#   %(connectionId)            connection id
#   %(currentTime)             当前时间
#   %(executionTime)           执行耗时
#   %(category)                执行分组
#   %(effectiveSql)            提交的SQL 换行
#   %(effectiveSqlSingleLine)  提交的SQL 不换行显示
#   %(sql)                     执行的真实SQL语句，已替换占位
#   %(sqlSingleLine)           执行的真实SQL语句，已替换占位 不换行显示
#customLogMessageFormat=%(currentTime)|%(executionTime)|%(category)|connection%(connectionId)|%(sqlSingleLine)

# date类型字段记录日志时使用的日期格式 默认dd-MMM-yy
#databaseDialectDateFormat=dd-MMM-yy

# boolean类型字段记录日志时使用的日期格式 默认boolean 可选值numeric
#databaseDialectBooleanFormat=boolean

# 是否通过jmx暴露属性 默认true
#jmx=true

# 如果jmx设置为true 指定通过jmx暴露属性时的前缀 默认为空
# com.p6spy(.<jmxPrefix>)?:name=<optionsClassName>
#jmxPrefix=

# 是否显示纳秒 默认false
#useNanoTime=false

# 实际数据源 JNDI
#realdatasource=/RealMySqlDS
# 实际数据源 datasource class
#realdatasourceclass=com.mysql.jdbc.jdbc2.optional.MysqlDataSource

# 实际数据源所携带的配置参数 以 k=v 方式指定 以 分号 分割
#realdatasourceproperties=port;3306,serverName;myhost,databaseName;jbossdb,foo;bar

# jndi数据源配置 
# 设置 JNDI 数据源的 NamingContextFactory。 
#jndicontextfactory=org.jnp.interfaces.NamingContextFactory
# 设置 JNDI 数据源的提供者的 URL。 
#jndicontextproviderurl=localhost:1099
# 设置 JNDI 数据源的一些定制信息，以分号分隔。 
#jndicontextcustom=java.naming.factory.url.pkgs;org.jboss.naming:org.jnp.interfaces

# 是否开启日志过滤 默认false， 这项配置是否生效前提是配置了 include/exclude/sqlexpression
#filter=false

# 过滤 Log 时所包含的表名列表，以逗号分隔 默认为空
#include=
# 过滤 Log 时所排除的表名列表，以逗号分隔 默认为空
#exclude=

# 过滤 Log 时的 SQL 正则表达式名称  默认为空
#sqlexpression=

#显示指定过滤 Log 时排队的分类列表，取值: error, info, batch, debug, statement,
#commit, rollback, result and resultset are valid values
# (默认 info,debug,result,resultset,batch)
#excludecategories=info,debug,result,resultset,batch

# 是否过滤二进制字段
# (default is false)
#excludebinary=false

# P6Log 模块执行时间设置，整数值 (以毫秒为单位)，只有当超过这个时间才进行记录 Log。 默认为0
#executionThreshold=

# P6Outage 模块是否记录较长时间运行的语句 默认false
# outagedetection=true|false
# P6Outage 模块执行时间设置，整数值 （以秒为单位)），只有当超过这个时间才进行记录 Log。 默认30s
# outagedetectioninterval=integer time (seconds)
```

### 拦截器（Interceptor）和过滤器（Filter）的执行顺序
 过滤前-拦截前-Action处理-拦截后-过滤后
interceptor 的执行顺序大致为：
* 请求到达 DispatcherServlet
* DispatcherServlet 发送至 Interceptor ，执行 preHandle
* 请求达到 Controller
* 请求结束后，postHandle 执行

Spring 中主要通过 HandlerInterceptor 接口来实现请求的拦截，实现 HandlerInterceptor 接口需要实现下面三个方法：
* preHandle() – 在handler执行之前，返回 boolean 值，true 表示继续执行，false 为停止执行并返回。
* postHandle() – 在handler执行之后, 可以在返回之前对返回的结果进行修改
* afterCompletion() – 在请求完全结束后调用，可以用来统计请求耗时等等


### 业务执行顺序

controller -> flow -> service -> dao

> controller 
接收http请求 不做业务逻辑处理
> flow 
业务流程逻辑处理
> service
原子服务处理
>dao
数据库访问层，不做业务逻辑处理，可使用本地缓存或者分布式缓存

### FLOW 执行顺序 继承子AbstractFlow
preHandler -> validation -> process -> afterHandler
异常时执行: exceptionHandler


### 流控处理
[sentinel](https://github.com/alibaba/spring-cloud-alibaba/wiki/Sentinel)