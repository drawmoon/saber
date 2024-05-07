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

// An operator used for combining conditions.
type Operator struct {
	keyword string
}

var (
	And Operator = Operator{"and"}
	Or  Operator = Operator{"or"}
)

// The logical expression.
//
// Combine this condition with another condition using the specified operator.
type Logical struct {
	Left     *Condition
	Right    *Condition
	Operator *Operator
}

// A comparator to be used in conditions to form comparison predicates.
type Comparator struct {
	keyword string
}

var (
	Eq         Comparator = Comparator{"eq"}
	Ne         Comparator = Comparator{"ne"}
	Lt         Comparator = Comparator{"lt"}
	Gt         Comparator = Comparator{"gt"}
	Le         Comparator = Comparator{"le"}
	Ge         Comparator = Comparator{"ge"}
	In         Comparator = Comparator{"in"}
	NotIn      Comparator = Comparator{"not in"}
	Like       Comparator = Comparator{"like"}
	NotLike    Comparator = Comparator{"not like"}
	Between    Comparator = Comparator{"between"}
	NotBetween Comparator = Comparator{"not between"}
	IsNull     Comparator = Comparator{"is null"}
	IsNotNull  Comparator = Comparator{"is not null"}
)

// The comparison expression.
type Comparison struct {
	Left     *Expression
	Operator *Comparator
	Right    *Expression
}
