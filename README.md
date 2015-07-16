钛理财蜘蛛爬虫
===================================

技术架构
-----------------------------------
spring/quartz/httpcomponent/htmlcleaner/xpath/manve/git

### 1.抓取
设置quartz执行频率，启动该项目作为后台进程持续运行

### 2.IP代理
不断抓取代理网站最新的可用ip，通过多线程抓取，结束后测试ip可用性

### 3.打包
执行maven package -P prod后，生成tar.gz，内部包含三个文件夹:
> lib: 所有jar包，包括依赖包和本身包
> conf: 配置文件，包含spring/quartz/系统变量
> bin: 启动脚本/结束脚本