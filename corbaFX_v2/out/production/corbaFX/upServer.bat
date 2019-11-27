cd C:\Users\L\IdeaProjects\corbaFX_v2\src

javac *.java NotePadApp/*.java

start orbd -ORBInitialPort 1050

start java -classpath . Server -ORBInitialPort 1050 -ORBInitialHost localhost