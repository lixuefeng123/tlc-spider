#!/bin/bash

source ${basedir}/conf/tlc-spider.conf
nohup java ${java_args} ${jvm_args} ${main_class} >>${log_path}/${log_name}.log 2>&1 &
echo $!>${pid}