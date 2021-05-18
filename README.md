# CopyManagerDemo (for Greenplum)

This demo is running a Greenplum copy command using JDBC libraries for Postgresql </br>

The copy command allows to batch the information retrieved from the database and put it in a file directly

## How to run

The demo takes this configuration parameters, to be specified in a configuration file properties.ini(where the .jar will reside)

```
jdbcString=jdbc:postgresql://192.168.12.145:5432/dashboard
username=gpadmin
password=
database=dashboard
query=(SELECT * from USERS)
delim=,
filename=/Users/dpalaia/export.csv
```

query is the query to run in copy mode
delim is the column separator you want to use for the copy command. </br>
filename is the output file </br>

At the end you will have an output like this: </br>


```
dpalaia@dpalaia-a02 CopyManagerTest % cat /Users/dpalaia/export.csv
asdfasdf,awerwerwer,asdfasdfasdf
asdfasdf,awerwerwer,asdfasdfasdf
asdfasdf,awerwerwer,asdfasdfasdf
```

Once the configuration file properties.ini is filled, You run the jar in /target like:<br>

java -jar demo-0.0.1-SNAPSHOT.jar

## Copy command code

The copy command code is quite straightforward to use and is using the Postgresql CopyManager package

```
CopyManager cm = ((PGConnection) connection).getCopyAPI();
FileWriter fileWriter = new FileWriter(filename);
String copycommand = "copy " +  query + " to stdout with delimiter '"+delim+"'" + ";";
System.out.println(copycommand);
cm.copyOut(copycommand, fileWriter);

fileWriter.close();
```

## How to compile

This demo is compiled with maven, To compile the .jar is enough to mvn install on the root path </br>

mvn install  </br>

It will download all the dependencies if needed.
