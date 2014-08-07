mysql-test
==========

dependencies: jdk-1.7

    mvn package
    nohup /usr/lib/jvm/java-1.7.0/bin/java -jar mysql-test-1.0-SNAPSHOT.jar jdbc:mysql://host/schema user password > /dev/null &
