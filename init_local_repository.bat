@echo off
title inti_local_repository
echo INIT STARTING......
call mvn install:install-file -Dfile=.\lib\aspose-cells-8.5.2.jar -DgroupId=com.aspose -DartifactId=cells -Dversion=8.5.2 -Dpackaging=jar
