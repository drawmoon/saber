package clause

// A condition or predicate.
// Conditions can be used in a variety of SQL clauses. They're mainly used in a
// Select statement's WHERE clause.
type Condition interface {
	// Performs a logical AND operation between this Condition and the specified expression.
	//
	// The expression to be logically ANDed with this Condition.
	//
	// return a new Condition representing the logical AND operation.
	And(expr *Expression) *Condition

	// Performs a logical OR operation between this Condition and the specified expression.
	//
	// The expression to be logically ORed with this Condition.
	//
	// return a new Condition representing the logical OR operation.
	Or(expr *Expression) *Condition
}
