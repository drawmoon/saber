package klsql

// The join type.
type JoinType struct {
	keyword *Keyword
}

func newJoinType(keyword string) *JoinType {
	return &JoinType{NewKeyword(keyword)}
}

var (
	// A self join is a regular join, but the table is joined with itself.
	SelfJoin *JoinType = newJoinType("self join")
	// The INNER JOIN keyword selects records that have matching values in both tables.
	InnerJoin *JoinType = newJoinType("inner join")

	// The LEFT JOIN keyword returns all records from the left table (table1), and the
	// matching records from the right table (table2). The result is 0 records from
	// the right side, if there is no match.
	LeftJoin      *JoinType = newJoinType("left join")
	LeftOuterJoin *JoinType = newJoinType("left outer join")

	// The RIGHT JOIN keyword returns all records from the right table (table2), and the
	// matching records from the left table (table1). The result is 0 records from the
	// left side, if there is no match.
	RightJoin      *JoinType = newJoinType("right join")
	RightOuterJoin *JoinType = newJoinType("right outer join")

	// The FULL OUTER JOIN keyword returns all records when there is a match in left (table1)
	// or right (table2) table records.
	FullJoin      *JoinType = newJoinType("full join")
	FullOuterJoin *JoinType = newJoinType("full outer join")
)
