
See http://code.google.com/p/nepaliconverter/


INSTALLATION

unzip the file
run 'run.bat'





MAKING A RELEASE
ant clean
ant jar
zip -9r ../ConversionTools4-2009-02-11.zip run.bat dist res README* -x .svn -x res/.svn
