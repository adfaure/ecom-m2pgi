#!/bin/bash

ES="vm4"
NODE="vm5"
WILDFLY="vm3"

echo "turning off elastic search"
ES_PID=$(ssh vm4 lsof -i:9300 -t)
if [ -z "$ES_PID" ];
then 
	echo "	Elastic search is not running"
else 
	echo "	killing ES with pid : $ES_PID"
	ssh $ES kill -9 $ES_PID
fi


echo "turning of nodejs" 
NODE_PID=$(ssh $NODE lsof -i:3000 -t)
if [ -z "$NODE_PID" ];
then 
	echo "	Node JS is not running"
else 
	echo "	killing nodejs a with pid : $NODE_PID"
	ssh $NODE kill -9 $NODE_PID
fi

echo "turninga of wildlfy" 
WF_PID=$(ssh $WILDFLY lsof -i:8080 -t)
if [ -z "$WF_PID" ];
then 
	echo "	widfly JS is not running"
else 
	echo "	killing wildlfy a with pid : $WF_PID"
	ssh $WILDFLY kill -9 $WF_PID
fi
