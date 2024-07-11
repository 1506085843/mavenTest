@echo off
title inti_local_repository
echo INIT STARTING......
call mvn install:install-file -Dfile=.\lib\aspose-cells-8.5.2.jar -DgroupId=com.aspose -DartifactId=cells -Dversion=8.5.2 -Dpackaging=jar
call mvn install:install-file -Dfile=.\lib\aspose-pdf-21.11.jar -DgroupId=com.aspose -DartifactId=aspose-pdf -Dversion=21.11  -Dpackaging=jar