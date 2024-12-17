# Library TODO List / Ideas List

## TODO
- [ ] add kill switch for model training **or** show current `weights` and `bias`
- [ ] add name variable for LinearRegression, and add it to `LinearRegression.summary()`
- [ ] write root README.md
- [x] create plotter for total loss *vs* epoch
- [ ] implement dynamic learning rate
- [ ] create 3D plotting for linear regression - do it smart
- [ ] crete smart logging system - make `Logger` interface and add loggers to models using e.g. `setLogger(new SimpleLogger())`
- [x] fix Double[] to double[] conversion
- [x] add option to get multiple columns e.g. `JDataFrame.getColumns( 1 , ... , n)` using dynamic input.
- [ ] create option to drop columns in `JDataFrame`. Examples: `JDataFrame.drop(1)` or `JDataFrame.drop(1, 5, 7)` or `JDataFrame.drop("ID", "Sales")`. Make sure to return new instance of dataframes with columns appropriately shifted. 
- [ ] add feature engineering option to `JDataFrame` using lambda expressions. e.g. `JDataFrame.createColumn( (c1, c2, c3) -> { c1 / (c2 + c3)} )` 
- [ ] look for documentation plugins for IntelliJ

## Errors to Fix
- [ ] **`LinearRegressionPlotter` is dependent on `LinearRegression.logging` variable.**  **When `logging` is set to default:false, then no data is shown on both charts**
