# DataManager Package TODO List

## Manifesto
Make java simple as python.
Create very flexible system for easy (*but optional*) juggling with data.

## List of things TODO
- [x] convert Double[] wrapper output to double[] or long[]
- [ ] create JavaDocs and API documentation
- [ ] perform testing
- [ ] add data types detection
- [ ] add optional head length
- [ ] add getters for dimensions
- [ ] refactor code
- [x] make `drop()` method for dropping columns
- [ ] make `addColumn()` method for feature engineering
- [ ] ~~make `private void shift()` method for shifting columns back after removing columns~~ `List<T>.remove(i)` implements shift of elements after removal.
- [ ] make function for column swaps
- [ ] implement return of JDataFrame in `void` methods. This should allow to easily partition the dataframe and make coding more efficient.
