# Library TODO List / Ideas List

- [ ] implement dynamic learning rate
- [ ] create 3D plotting for linear regression - do it smart
- [ ] crete smart logging system - make `Logger` interface and add loggers to models using e.g. `setLogger(new SimpleLogger())`
- [ ] fix Double[] to double[] conversion
- [ ] add option to get multiple columns e.g. `JDataFrame.getColumns( 1 , ... , n)` using dynamic input.
- [ ] create option to drop columns in `JDataFrame`. Examples: `JDataFrame.drop(1)` or `JDataFrame.drop(1, 5, 7)` or `JDataFrame.drop("ID", "Sales")`. Make sure to return new instance of dataframes with columns appropriately shifted. 
- [ ] add feature engineering option to `JDataFrame` using lambda expressions. e.g. `JDataFrame.createColumn( (c1, c2, c3) -> { c1 / (c2 + c3)} )` 
- [ ] look for documentation plugins for IntelliJ