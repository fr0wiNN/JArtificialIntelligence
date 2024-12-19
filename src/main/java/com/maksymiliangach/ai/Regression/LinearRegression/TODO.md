# LinearRegression Package TODO List

- [ ] fix issue of exploding `Loss`
- [ ] make abstract class `JModel` with default serialization **and** make all models extend this class
- [ ] create JavaDocs and API documentation
- [ ] perform testing
- [ ] refactor `LinearRegression.plot()` - abstract it from `LinearRegression` class - make plotter pull information from the model. Not the other way around?
- [x] make `LinearRegression` implement `Model` interface  with `save()` and `load()` methods **or** make interface for serialization and deserialization
- [ ] find IntelliJ plugin for code documentation
- [ ] refactor code