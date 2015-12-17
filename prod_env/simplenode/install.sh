#!/bin/bash

wildfly=vm3
nodejs=vm5

ssh $wildfly 'rm -r /home/im2ag/ECOM-M2PGI'
ssh $wildfly 'git clone git@github.com:oldDadou/ECOM-M2PGI.git'
ssh $nodejs  'rm -r /home/im2ag/ECOM-M2PGI'
ssh $nodejs 'git clone git@github.com:oldDadou/ECOM-M2PGI.git'

#ssh $wildfly 'wget http://download.jboss.org/wildfly/9.0.2.Final/wildfly-9.0.2.Final.tar.gz -O /tmp/wildfly-9.0.2.Final.tar.gz'
#ssh $wildfly 'cd /home/im2ag/ && tar -xvf /tmp/wildfly-9.0.2.Final.tar.gz'

ssh $wildfly 'cp /home/im2ag/ECOM-M2PGI/ecom/scripts/wildfly/standalone.xml wildfly-9.0.1.Final/standalone/configuration/'
ssh $wildfly 'cp /home/im2ag/ECOM-M2PGI/ecom/scripts/wildfly/application.properties wildfly-9.0.1.Final/standalone/configuration/'
ssh $wildfly 'cd /home/im2ag/ECOM-M2PGI/ecom && mvn install'
ssh $nodejs 'export NVM_DIR="/home/im2ag/.nvm"
                [ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"  &&
                nvm use 5.2.0 && cd /home/im2ag/ECOM-M2PGI/nodejs && npm install' 
