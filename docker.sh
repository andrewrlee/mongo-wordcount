#!/bin/bash

USAGE="Usage : $0 [start|stop]";

if [ $# -lt 1 ]
then
  echo $USAGE
  exit
fi

function start {
  sudo docker run --name mongodb_01      -d -p 27017:27017                               plasma147/mongodb --noprealloc --smallfiles
}

function stop {
  sudo docker stop  mongodb_01 
  sudo docker rm    mongodb_01
}


case "$1" in

"start") start 
    ;;

"stop")  stop
    ;;

*) echo $USAGE
   ;;
esac
