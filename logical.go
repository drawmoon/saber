package klsql

// An operator used for combining conditions.
type Operator struct {
	keyword *Keyword
}

func newOperator(keyword string) *Operator {
	return &Operator{NewKeyword(keyword)}
}

var (
	And *Operator = newOperator("and")
	Or  *Operator = newOperator("or")
)

// The logical expression.
//
// Combine this condition with another condition using the specified operator.
type Logical struct {
	Left     *Condition
	Right    *Condition
	Operator *Operator
}
