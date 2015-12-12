export JAVA_HOME=$HOME/jdk1.8.0_65
echo "java home $JAVA_HOME"
export JBOSS_HOME=$HOME/wildfly-9.0.2.Final


cp /home/im2ag/.jenkins/jobs/ecom-m2pgi/workspace/ecom/scripts/wildfly/standalone-test.xml $JBOSS_HOME/standalone/configuration/standalone.xml
cp /home/im2ag/.jenkins/jobs/ecom-m2pgi/workspace/ecom/scripts/wildfly/application.properties $JBOSS_HOME/standalone/configuration/

cp /home/im2ag/.jenkins/jobs/ecom-m2pgi/workspace/ecom/scripts/ecom-config/arquilliand.xml ecom/ecom-ejb/src/test/resources/

mvn clean test -Parq-wildfly-managed
