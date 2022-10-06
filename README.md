= Red Hat Fuse (Camel) MQTT Simulator 

== Running project locally 

    mvn spring-boot:run

== Deploy on openshift 

• Log in to your OpenShift cluster:

$ oc login -u developer -p developer

• Create a new OpenShift project :

$ oc new-project MY_PROJECT_NAME
• Import fuse image  <br />
$ oc import-image fuse7/fuse-java-openshift-jdk11-rhel8 --from=registry.redhat.io/fuse7/fuse-java-openshift-jdk11-rhel8 --confirm  <br />
• Use jkube for deployment  <br />
$ mvn clean -DskipTests oc:deploy -Popenshift -Djkube.generator.fromMode=istag -Djkube.generator.from=MY_PROJECT_NAME/fuse-java-openshift-jdk11-rhel8

