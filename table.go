package klsql

// A table.
type Table interface {
	// Gets a field from this table.
	Field(f string) *Field

	// Sets the alias for the table.
	As(alias string) *Table

	// Create a qualified asterisk expression from this table, table.* for use with SELECT.
	Asterisk() interface{}

	// Join a table to this table.
	Join(t *Table, jt interface{}, jh interface{}) *JoinTable

	// Use the index hints for this table.
	//
	// Examples:
	//
	//	table.UseIndex("index1", "index2")
	UseIndex(i ...string) *Table

	// Use the index hints for this table.
	//
	// Examples:
	//
	//	table.UseIndexForJoin("index1", "index2")
	UseIndexForJoin(i ...string) *Table

	// Use the index hints for this table.
	//
	// Examples:
	//
	//	table.UseIndexForOrderBy("index1", "index2")
	UseIndexForOrderBy(i ...string) *Table

	// Use the index hints for this table.
	//
	// Examples:
	//
	//	table.UseIndexForGroupBy("index1", "index2")
	UseIndexForGroupBy(i ...string) *Table

	// Ignore the index hints for this table.
	//
	// Examples:
	//
	//	table.IgnoreIndex("index1", "index2")
	IgnoreIndex(i ...string) *Table

	// Ignore the index hints for this table.
	//
	// Examples:
	//
	//	table.IgnoreIndexForJoin("index1", "index2")
	IgnoreIndexForJoin(i ...string) *Table

	// Ignore the index hints for this table.
	//
	// Examples:
	//
	//	table.IgnoreIndexForOrderBy("index1", "index2")
	IgnoreIndexForOrderBy(i ...string) *Table

	// Ignore the index hints for this table.
	//
	// Examples:
	//
	//	table.IgnoreIndexForGroupBy("index1", "index2")
	IgnoreIndexForGroupBy(i ...string) *Table

	// Ignore the index hints for this table.
	//
	// Examples:
	//
	//	table.ForceIndex("index1", "index2")
	ForceIndex(i ...string) *Table

	// Ignore the index hints for this table.
	//
	// Examples:
	//
	//	table.ForceIndexForJoin("index1", "index2")
	ForceIndexForJoin(i ...string) *Table

	// Ignore the index hints for this table.
	//
	// Examples:
	//
	//	table.ForceIndexForOrderBy("index1", "index2")
	ForceIndexForOrderBy(i ...string) *Table

	// Ignore the index hints for this table.
	//
	// Examples:
	//
	//	table.ForceIndexForGroupBy("index1", "index2")
	ForceIndexForGroupBy(i ...string) *Table
}
