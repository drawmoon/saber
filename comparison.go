package klsql

// A comparator to be used in conditions to form comparison predicates.
type Comparator struct {
	keyword *Keyword
}

func newComparator(keyword string) *Comparator {
	return &Comparator{NewKeyword(keyword)}
}

var (
	Eq         *Comparator = newComparator("eq")
	Ne         *Comparator = newComparator("ne")
	Lt         *Comparator = newComparator("lt")
	Gt         *Comparator = newComparator("gt")
	Le         *Comparator = newComparator("le")
	Ge         *Comparator = newComparator("ge")
	In         *Comparator = newComparator("in")
	NotIn      *Comparator = newComparator("not in")
	Like       *Comparator = newComparator("like")
	NotLike    *Comparator = newComparator("not like")
	Between    *Comparator = newComparator("between")
	NotBetween *Comparator = newComparator("not between")
	IsNull     *Comparator = newComparator("is null")
	IsNotNull  *Comparator = newComparator("is not null")
)

// The comparison expression.
type Comparison struct {
	Left     *Expression
	Operator *Comparator
	Right    *Expression
}
