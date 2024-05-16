package klsql

// The join hint type.
type JoinHint struct {
	keyword *Keyword
}

func newJoinHint(keyword string) *JoinHint {
	return &JoinHint{NewKeyword(keyword)}
}

var (
	Hash   *JoinHint = newJoinHint("hash")
	Loop   *JoinHint = newJoinHint("loop")
	Merge  *JoinHint = newJoinHint("merge")
	Remote *JoinHint = newJoinHint("remote")
)
