#!/usr/bin/env bash

if [ $# != 1 ]
then
  echo "Script requires 1 argument (module name)"
  echo "Usage: $0 module_name"
  exit 1
fi

echo "Deploy module $1"

echo "Build project"

gradle "$1":clean --warning-mode=all
if [ $? != 0 ]
then
  echo "Clean failed! Exit..."
  exit 1
fi

gradle "$1":build --warning-mode=all
if [ $? != 0 ]
then
  echo "Build failed! Exit..."
  exit 1
fi

#--------------------------------------------------

echo "Copy files"

ssh -i key.pem ubuntu@3.248.170.197 << EOF

mkdir /home/ubuntu/"$1"

EOF

scp -i key.pem "$1"/build/libs/"$1".jar ubuntu@3.248.170.197:/home/ubuntu/"$1"/
if [ $? != 0 ]
then
  echo "Copy failed! Exit..."
  exit 1
fi

#--------------------------------------------------

echo "Restart application"

ssh -i key.pem ubuntu@3.248.170.197 << EOF

cd /home/ubuntu/"$1"
pgrep -f "$1"
if [ $? != 0 ]
then
  echo "No process with name "$1" found"
else
  echo "Kill running process"
  kill -9 $(pgrep -f "$1")
fi
rm *log*
nohup java -jar -XX:+PrintGCDetails -Xloggc:gc.log -Xms128m -Xmx256m "$1".jar > "$1".log &

EOF

echo 'Success'