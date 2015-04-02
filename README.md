dynamic-crud-SQLite
===================

Is an Android Library to created, extract, put, update delete data from an SQLite database.

To use the library it's necessary implements the *ITables* and  *IColumns* interfaces, and extends *AFieldData* or implements *IFieldData*. 

It's designed to use in a survey, where each table is related to a form (an activity).

The *ITables* interfaces is an abstraction of the SQLite table, table has to be an Enum which implements this interfaces. The enum contains info about the columns of this table, the activity related to this table and the group (if data belong to a king of *IGroup*). The Table class have the responsability to build the object (*IFieldData*) used to manage data.

The *IColumns* interface is used as common interface to manage the table fields, every columns is an Enum which values are the type of data and if it's primary key.

The *ATables* allows the users to get some information about a Table or Columns and call the builder of IFieldData from the *ITables* implementation.

The *IFieldData* is an abstraction of the data survey and it's imlplements by the abstract class *AFieldData*, which allow to get set data, using as keys the IColumns value.

**DataSourceFactory** is the class that manage the db. It implements Singleton design pattern.


###Example

The table:

```java
public enum Tabelle implements ITables {

	TEST1(Columns.TableTest1.class, ActivityForm1.class,
	        null), TEST2(
	        Columns.TableTest2.class, ActivityForm2.class,
	        null);
	        
	        
	       ...... 
```


The columns:

```java
public class Columns {

	public enum TableTest1 implements IColumns {

		ID(INTEGER, true), INT1(INTEGER, false), STRING1(TEXT, false), STRING2(
		        TEXT, false);

		private final String value;
		private final boolean isPrymary;

		private TableAnagrafica(String value, boolean b) {
			this.value = value;
			this.isPrymary = b;
		}

		@Override
		public String getType() {
			// TODO Auto-generated method stub
			return value;
		}

		@Override
		public boolean isPrimary() {
			// TODO Auto-generated method stub
			return isPrymary;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return this.name();
		}

		@Override
		public String[] getNames() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public enum TableAppezzamento implements IColumns {

		ID(INTEGER, true), REAL1(REAL, false), STRING3(TEXT, false), STRING4(
		        TEXT, false);;

		private final String value;
		private final boolean isPrymary;

		private TableAppezzamento(String value, boolean b) {
			this.value = value;
			this.isPrymary = b;
		}

		@Override
		public String getType() {
			// TODO Auto-generated method stub
			return value;
		}

		@Override
		public boolean isPrimary() {
			// TODO Auto-generated method stub
			return isPrymary;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return this.name();
		}

		@Override
		public String[] getNames() {
			// TODO Auto-generated method stub
			return null;
		}
	}


```

So next step is create the data object and extract the value from the database:

```java
IApplication app = ((IApplication) getApplication());
DataSourceFactory df = app.getDataBase();
ArrayList<IFieldData> mPlace = df.getAllRows(getTables(), app.getGroup().getWhereClause(),null, getGroup());
```

Where I have stored the IGroup implementation in the Application.
