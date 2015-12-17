#!/bin/bash


LOGDIR=deploy_logs

server_running() {
	echo "check for $1 at port $2"
	TEST=$(netcat -vz $1 $2 2>&1 )
	if [[ $TEST == *"succeeded!"* ]]
	then
		return 0
	else 
		return 1
	fi
}


if [ ! -d "$LOGDIR" ]; then
	echo "creating log folder -> $LOGDIR"
  mkdir $LOGDIR
fi



# Mise a jours des dépots git
echo "pull the git repository on vm3  "
	ssh vm3 "cd /home/im2ag/ECOM-M2PGI/ && git pull"
echo "pull the git repository on vm5  "
	ssh vm5 "cd /home/im2ag/ECOM-M2PGI/ && git pull" 

# Lancement de widlfy
if server_running 152.77.78.8 8080;
then 
	echo "wilfly (or different service)  is already running"
else
	echo "running wildlfy .." 
	ssh -f vm3 '(./wildfly-9.0.2.Final/bin/standalone.sh)' &> $LOGDIR/deploy-wildfly.log 
fi

echo "clean and installing project"
#ssh vm3 'cd /home/im2ag/ECOM-M2PGI/ecom && mvn clean'
#ssh vm3 'cd /home/im2ag/ECOM-M2PGI/ecom && mvn install'

echo "deploy project"
#ssh vm3 'cd /home/im2ag/ECOM-M2PGI/ecom && mvn wildfly:deploy'

echo "running elastic search"
if server_running 152.77.78.9 9300;
then 
	echo "Elsatic search is already running"
else 
	echo "Running elastic search"
	ssh -f vm4 '(./elasticsearch-2.0.0/bin/elasticsearch)' &> $LOGDIR/deploy-es.log 
fi


echo "running nodejs"
if server_running 152.77.78.10 3000
then 
	echo "node js is running on port 3000"
else 
		ssh -f vm5 '(export NVM_DIR="/home/im2ag/.nvm"
		[ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"  # This loads nvm &&
		nvm use 5.2.0 && cd /home/im2ag/ECOM-M2PGI/nodejs && node main.js)' &> $LOGDIR/deploy-node.log
fi
