package klsql

// A combine operator is used to combine result sets of two arbitrary select queries.
type CombineOperator struct{ keyword *Keyword }

func newCombineOperator(keyword string) *CombineOperator {
	return &CombineOperator{NewKeyword(keyword)}
}

var (
	Union        *CombineOperator = newCombineOperator("union")
	UnionAll     *CombineOperator = newCombineOperator("union all")
	Except       *CombineOperator = newCombineOperator("except")
	ExceptAll    *CombineOperator = newCombineOperator("except all")
	Intersect    *CombineOperator = newCombineOperator("intersect")
	IntersectAll *CombineOperator = newCombineOperator("intersect all")
)
