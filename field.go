package klsql

// A field contained in a table.
type Field interface {
	// Sets the alias for the table.
	As(alias string) *Field

	// The EQUAL operator.
	Eq() *Condition

	// The NOT_EQUAL operator.
	Ne() *Condition

	// The LESS_THAN operator.
	Lt() *Condition

	// The GREATER_THAN operator.
	Gt() *Condition

	// The LESS_THAN_OR_EQUAL operator.
	Le() *Condition

	// The GREATER_THAN_OR_EQUAL operator.
	Ge() *Condition
}

// An expression to be used exclusively in ORDER BY clauses.
//
// Examples:
//
//	Select(table.Field("first_name"))
//		.From(table)
//		.OrderBy(table.Field("first_name").Asc())
type OrderField interface {
	// Add an ASC clause to this sort field.
	Asc() *OrderField

	// Add a DESC clause to this sort field.
	Desc() *OrderField

	// Add a NULLS FIRST clause to this sort field.
	NullsFirst() *OrderField

	// Add a NULLS LAST clause to this sort field.
	NullsLast() *OrderField
}

// An expression to be used exclusively in GROUP BY clauses.
//
// Examples:
//
//	Select(table.Field("first_name"))
//		.From(table)
//		.GroupBy(table.Field("first_name"))
type GroupField interface{}

// A column expression.
type TableField struct{}
