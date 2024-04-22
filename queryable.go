package klsql

// Provides functionality to evaluate queries against a specific data source.
type Queryable interface {
	ExecuteQuery() interface{}
}

// func NewQuery(...interface{}) Queryable {
// 	return Queryable{}
// }
