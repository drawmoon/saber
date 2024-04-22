package klsql

// The join hint type.
type JoinHint Keyword

// The join type.
type JoinType Keyword

var (
	Hash   JoinHint = JoinHint{"hash"}
	Loop   JoinHint = JoinHint{"loop"}
	Merge  JoinHint = JoinHint{"merge"}
	Remote JoinHint = JoinHint{"remote"}

	// A self join is a regular join, but the table is joined with itself.
	SelfJoin JoinType = JoinType{"self join"}
	// The INNER JOIN keyword selects records that have matching values in both tables.
	InnerJoin JoinType = JoinType{"inner join"}

	// The LEFT JOIN keyword returns all records from the left table (table1), and the
	// matching records from the right table (table2). The result is 0 records from
	// the right side, if there is no match.
	LeftJoin      JoinType = JoinType{"left join"}
	LeftOuterJoin JoinType = JoinType{"left outer join"}

	// The RIGHT JOIN keyword returns all records from the right table (table2), and the
	// matching records from the left table (table1). The result is 0 records from the
	// left side, if there is no match.
	RightJoin      JoinType = JoinType{"right join"}
	RightOuterJoin JoinType = JoinType{"right outer join"}

	// The FULL OUTER JOIN keyword returns all records when there is a match in left (table1)
	// or right (table2) table records.
	FullJoin      JoinType = JoinType{"full join"}
	FullOuterJoin JoinType = JoinType{"full outer join"}
)

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
