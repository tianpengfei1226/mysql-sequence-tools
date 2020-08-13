分布式ID生成器 

使用方式
1,clone 该项目，
2,mysql-sequence-tools-autoconfiguration install 
3,mysql-sequence-tools-starter install
4,执行建表sql,并添加相应的序列配置。
示例配置如下：

| name     | current_value | increment | tatal | threshold |
| -------- | ------------- | --------- | ----- | --------- |
| tpf-test | 0             | 1         | 20000 | 500       |

