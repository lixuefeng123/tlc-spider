#!/bin/bash

source conf/tlc-spider.conf
/bin/kill -9 `cat ${pid}`
current_date=`date "+%Y-%m-%d %H:%M:%S"`
echo "${current_date} shutdown tlc-spider by shutdown.sh" >>${log_path}/${log_name}.log