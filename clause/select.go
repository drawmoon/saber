package clause

// A select statement.
type Select interface {
	// Sets the alias for the select statement.
	As(alias string) *Select

	// Sets the table to be queried.
	From(t Table) *Select

	// Sets the where clause.
	Where(p *Condition) *Select

	// Sets the having clause.
	Having(p *Condition) *Select

	// Sets the order by clause.
	OrderBy(f ...*OrderField) *Select

	// Sets the group by clause.
	GroupBy(f ...*GroupField) *Select

	// Sets the offset.
	Offset(l interface{}) *Select
}
