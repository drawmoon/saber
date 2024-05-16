package klsql

// A table consisting of two joined tables and possibly a join condition.
type JoinTable struct {
	Lhs       *Table
	Rhs       *Table
	Type      interface{}
	Hint      interface{}
	Condition *Condition
}

// Gets a field from this table.
func (j *JoinTable) Field(f string) *Field {
	panic("not implemented") // TODO: Implement
}

// Sets the alias for the table.
func (j *JoinTable) As(alias string) *Table {
	panic("not implemented") // TODO: Implement
}

// Create a qualified asterisk expression from this table, table.* for use with SELECT.
func (j *JoinTable) Asterisk() interface{} {
	panic("not implemented") // TODO: Implement
}

// Join a table to this table.
func (j *JoinTable) Join(t *Table, jt interface{}, jh interface{}) *JoinTable {
	panic("not implemented") // TODO: Implement
}

// Use the index hints for this table.
//
// Examples:
//
//	table.UseIndex("index1", "index2")
func (j *JoinTable) UseIndex(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Use the index hints for this table.
//
// Examples:
//
//	table.UseIndexForJoin("index1", "index2")
func (j *JoinTable) UseIndexForJoin(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Use the index hints for this table.
//
// Examples:
//
//	table.UseIndexForOrderBy("index1", "index2")
func (j *JoinTable) UseIndexForOrderBy(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Use the index hints for this table.
//
// Examples:
//
//	table.UseIndexForGroupBy("index1", "index2")
func (j *JoinTable) UseIndexForGroupBy(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.IgnoreIndex("index1", "index2")
func (j *JoinTable) IgnoreIndex(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.IgnoreIndexForJoin("index1", "index2")
func (j *JoinTable) IgnoreIndexForJoin(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.IgnoreIndexForOrderBy("index1", "index2")
func (j *JoinTable) IgnoreIndexForOrderBy(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.IgnoreIndexForGroupBy("index1", "index2")
func (j *JoinTable) IgnoreIndexForGroupBy(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.ForceIndex("index1", "index2")
func (j *JoinTable) ForceIndex(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.ForceIndexForJoin("index1", "index2")
func (j *JoinTable) ForceIndexForJoin(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.ForceIndexForOrderBy("index1", "index2")
func (j *JoinTable) ForceIndexForOrderBy(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.ForceIndexForGroupBy("index1", "index2")
func (j *JoinTable) ForceIndexForGroupBy(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}
