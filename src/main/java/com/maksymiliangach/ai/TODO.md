# Library TODO List / Ideas List

## TODO
- [x] ack kaggle for my dataset
- [x] create visual logger that shows progress as a progress bar (current epoch / epochs * 100 = percentage of completion)
- [ ] add kill switch for model training **or** show current `weights` and `bias`
- [x] add name variable for LinearRegression, and add it to `LinearRegression.summary()`
- [x] write root README.md
- [x] create plotter for total loss *vs* epoch
- [ ] implement dynamic learning rate
- [ ] create 3D plotting for linear regression - do it the smart way
- [x] create smart logging system - make `Logger` interface and add loggers to models using e.g. `setLogger(new SimpleLogger())`
- [x] fix Double[] to double[] conversion
- [x] add option to get multiple columns e.g. `JDataFrame.getColumns( 1 , ... , n)` using dynamic input.
- [x] create option to drop columns in `JDataFrame`. Examples: `JDataFrame.drop(1)` or `JDataFrame.drop(1, 5, 7)` or `JDataFrame.drop("ID", "Sales")`. Make sure to return new instance of dataframes with columns appropriately shifted. 
- [x] add feature engineering option to `JDataFrame` using lambda expressions. e.g. `JDataFrame.createColumn( (c1, c2, c3) -> { c1 / (c2 + c3)} )` 
- [ ] change examples to feature LinearRegressionBuilder class way of creating models
## Errors to Fix
- [x] **`LinearRegressionPlotter` is dependent on `LinearRegression.logging` variable.**  **When `logging` is set to default:false, then no data is shown on both charts**
