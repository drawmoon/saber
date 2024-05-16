package klsql

// // The common base type for all objects that can be used for query composition.
// type QueryPart interface{}

// Provides functionality to evaluate queries against a specific data source.
type Queryable interface {
	ExecuteQuery() interface{}
}

// func NewQuery(...interface{}) Queryable {
// 	return Queryable{}
// }
