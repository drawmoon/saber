package klsql

// A base table.
type BaseTable struct {
	Name   string
	Alias  string
	Schema string
}

// Gets a field from this table.
func (b *BaseTable) Field(f string) *Field {
	panic("not implemented") // TODO: Implement
}

// Sets the alias for the table.
func (b *BaseTable) As(alias string) *Table {
	panic("not implemented") // TODO: Implement
}

// Create a qualified asterisk expression from this table, table.* for use with SELECT.
func (b *BaseTable) Asterisk() interface{} {
	panic("not implemented") // TODO: Implement
}

// Join a table to this table.
func (b *BaseTable) Join(t *Table, jt interface{}, jh interface{}) *JoinTable {
	panic("not implemented") // TODO: Implement
}

// Use the index hints for this table.
//
// Examples:
//
//	table.UseIndex("index1", "index2")
func (b *BaseTable) UseIndex(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Use the index hints for this table.
//
// Examples:
//
//	table.UseIndexForJoin("index1", "index2")
func (b *BaseTable) UseIndexForJoin(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Use the index hints for this table.
//
// Examples:
//
//	table.UseIndexForOrderBy("index1", "index2")
func (b *BaseTable) UseIndexForOrderBy(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Use the index hints for this table.
//
// Examples:
//
//	table.UseIndexForGroupBy("index1", "index2")
func (b *BaseTable) UseIndexForGroupBy(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.IgnoreIndex("index1", "index2")
func (b *BaseTable) IgnoreIndex(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.IgnoreIndexForJoin("index1", "index2")
func (b *BaseTable) IgnoreIndexForJoin(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.IgnoreIndexForOrderBy("index1", "index2")
func (b *BaseTable) IgnoreIndexForOrderBy(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.IgnoreIndexForGroupBy("index1", "index2")
func (b *BaseTable) IgnoreIndexForGroupBy(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.ForceIndex("index1", "index2")
func (b *BaseTable) ForceIndex(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.ForceIndexForJoin("index1", "index2")
func (b *BaseTable) ForceIndexForJoin(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.ForceIndexForOrderBy("index1", "index2")
func (b *BaseTable) ForceIndexForOrderBy(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}

// Ignore the index hints for this table.
//
// Examples:
//
//	table.ForceIndexForGroupBy("index1", "index2")
func (b *BaseTable) ForceIndexForGroupBy(i ...string) *Table {
	panic("not implemented") // TODO: Implement
}
