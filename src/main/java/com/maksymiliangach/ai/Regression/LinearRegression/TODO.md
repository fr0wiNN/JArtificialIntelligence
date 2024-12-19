# LinearRegression Package TODO List

- [ ] add config file for hyper parameter tuning
- [ ] make it visually accessible to determine feature weights meaning. Example: (Weights: [1,2,3] Bias: 10, Input Features: [Square_Feet, Garage_Size, Location_Score], Output Feature: Price)    
- [ ] fix issue of exploding `Loss`
- [ ] make abstract class `JModel` with default serialization **and** make all models extend this class
- [ ] create JavaDocs and API documentation
- [ ] perform testing
- [ ] refactor `LinearRegression.plot()` - abstract it from `LinearRegression` class - make plotter pull information from the model. Not the other way around?
- [x] make `LinearRegression` implement `Model` interface  with `save()` and `load()` methods **or** make interface for serialization and deserialization
- [ ] find IntelliJ plugin for code documentation
- [ ] refactor code