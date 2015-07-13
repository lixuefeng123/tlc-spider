#!/bin/bash

source tlc-spider.ini
nohup java ${java_args} ${jvm_args} ${main_class} >>${log_path}/${log_name}.log 2>&1 &
echo $!>${pid}