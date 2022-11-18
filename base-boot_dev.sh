#!/usr/bin/env bash

# 开发环境-基础服务

# 定义参数

# 当前日期
NOW_DATE=$(date +%Y%m%d)

# 仓库地址
GIT_URL="http://gogs-git.hztywl.cn/zhousw/base-boot.git"
# 源码路径
CODE_PATH="/home/webdev/code/base-boot"
# 源文件
JAR_PATH="$CODE_PATH/base-api/target/base-api*.jar"
# 部署路径
DEPLOY_PATH="/home/webdev/webapps/saas/base-boot-8767"
# 存储的目标
BUILD_PATH="$DEPLOY_PATH/build/base-api_$(date +%Y%m%d)_$(date +%H%M%S).jar"
# 项目分支
BRANCH="dev"
# 配置文件激活
PROFILES="dev"

# 进程ID
CURRENT_PID="$DEPLOY_PATH/pid"

# JVM运行参数
# run with server mode
JAVA_OPTS="-server"
# jvm memory config
JAVA_OPTS="$JAVA_OPTS -Xms512M -Xmx512M -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ParallelRefProcEnabled"
# GC 日志
JAVA_OPTS="$JAVA_OPTS -XX:ErrorFile=$DEPLOY_PATH/logs/hs_err_pid_%p.log -Xloggc:$DEPLOY_PATH/logs/gc-$NOW_DATE.log"

# when out-of-memory save heap dump
JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$DEPLOY_PATH/temp/."

# use G1 collector with JDK 1.8
JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC -XX:G1HeapRegionSize=16m -XX:G1ReservePercent=25 -XX:InitiatingHeapOccupancyPercent=30"
# other config
JAVA_OPTS="$JAVA_OPTS -Dloader.path=libs -Dspring.profiles.active=${PROFILES}"

# Spring运行参数
SPRING_OPTS="--application.log.level=DEBUG --spring.profiles.active=${PROFILES}"
# 应用程序的上下文路径
SPRING_OPTS="$SPRING_OPTS --server.servlet.context-path=/boot"

# 停止程序等待时间(秒)
SHUTDOWN_WAIT_SECONDS=20

# 日志存放路径
LOG_PATH=out.log

# 进程ID
pid() {
  if [ -f $CURRENT_PID ]; then
    echo $(cat $CURRENT_PID)
  else
    # grep -v grep (去除包含grep的进程行)
    echo $(ps -ef | grep $DEPLOY_PATH | grep -v 'grep\|tail \|tailf ' | tr -s " " | cut -d " " -f2)
  fi
}

# 进程ID检索字符串
pid_grep() {
  echo "$(pid)" | tr " " "|"
}

# 构建项目
build() {
  # 不存在文件夹则递归创建文件夹
  if [ ! -d "$CODE_PATH" ]; then
    mkdir -p $CODE_PATH
    # 从git clone 项目
    git clone $GIT_URL $CODE_PATH
  else
    echo "源码路径文件夹已经存在"
  fi

  # 更新代码，打包&构建项目
  cd $CODE_PATH
  git checkout $BRANCH
  git pull
  mvn clean install -U -P $BRANCH -Dmaven.test.skip=true

  # 拷贝
  mkdir -p $DEPLOY_PATH/build
  cp -rp $JAR_PATH $BUILD_PATH
  # 当前运行的jar，软连接
  ln -snf $BUILD_PATH $DEPLOY_PATH/current.jar
}

# 启动项目
start() {
  pid=$(pid)
  if [ -n "$pid" ]; then
    echo -e "\e[00;31mApplication is already running (pid: $pid)\e[00m"
  else
    echo -e "\e[00;32mStarting application\e[00m"
    cd $DEPLOY_PATH
    # 清空日志
    echo "" >$LOG_PATH
    # 启动项目
    nohup java $JAVA_OPTS -jar $DEPLOY_PATH/current.jar $SPRING_OPTS >>$LOG_PATH 2>&1 &
    # 写入PID
    echo $! >$CURRENT_PID
    # 查看日志
    tailf $LOG_PATH
  fi
  return 0
}

# 停止项目
stop() {
  # 定义在外部，防止停止后获取不到
  pid=$(pid)
  pid_grep=$(pid_grep)
  if [ -n "$pid" ]; then
    echo -e "\e[00;31mStopping application\e[00m"
    # 停止程序
    kill $pid

    let kwait=$SHUTDOWN_WAIT_SECONDS
    count=0
    # echo "$pid"
    # echo "$pid_grep"
    # echo `ps -p "$pid" | egrep -c "$pid_grep" | tr " " "|"`
    # echo `ps -p "$pid" | egrep -c "$(pid_grep)" | tr " " "|"`
    # 等待容器关闭最大时长(条件为真就结束循环)
    until [ $(ps -p "$pid" | egrep -c "$pid_grep") = '0' ] || [ $count -gt $kwait ]; do
      echo -n -e "\e[00;31mWaiting for processes to exit\e[00m\n"
      sleep 1
      let count=$count+1
    done

    # 延时后容器还未停止，强制杀掉进程
    if [ $count -gt $kwait ]; then
      echo -n -e "\n\e[00;31mKilling processes which didn't stop after $kwait seconds\e[00m"
      kill -9 $pid
    fi

    # 移除PID
    if [ -f $CURRENT_PID ]; then
      rm -f $CURRENT_PID
    fi
  else
    echo -e "\e[00;31mApplication is not running\e[00m"
  fi

  return 0
}

case $1 in
build)
  build
  ;;

deploy)
  build
  stop
  start
  ;;

start)
  start
  ;;

restart)
  stop
  start
  ;;

stop)
  stop
  ;;

*)
  echo "only support [ build deploy start restart stop ]"
  ;;
esac
exit 0
