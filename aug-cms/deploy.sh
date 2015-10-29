#!/bin/bash

cd `dirname $0`
echo "Deploying AUG-CMS Server"

mvn clean
mvn package
mvn tomcat7:undeploy
mvn tomcat7:deploy

