To actually run a batch you have to execute the following command in the oasp4j directory once:

  mvn clean install

It might be OK if not all projects can be build successfully.

Afterwards, go to the oasp4j/samples directory and execute (must be executed once after each modification):

  mvn clean package

Then you can invoke the batch with

  billExport.bat or productImport.bat

When the batch finishes successfully, you will see something like this in the console log:

2016-03-14 14:29:09.955  INFO 5524 --- [main] i.o.m.b.c.b.SpringBootBatchCommandLine   : Batch Status: COMPLETED
2016-03-14 14:29:09.955  INFO 5524 --- [main] i.o.m.b.c.b.SpringBootBatchCommandLine   : Return Code: 0
