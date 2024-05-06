package klsql

import (
	"github.com/drawmoon/klsql/clause"
	"github.com/drawmoon/klsql/common"
)

type QueryModel struct {
	Rows      *common.ImmutableSet[interface{}]
	Cols      *common.ImmutableSet[interface{}]
	Condition *interface{}
	Sort      *interface{}
	Page      *interface{}
}

func (qm *QueryModel) BuildSelectStatement() (*clause.Select, error) {
	return nil, nil
}

type Operator struct {
	keyword *clause.Keyword
}

type Comparator struct {
	keyword *clause.Keyword
}

var (
	And Operator = Operator{clause.NewKeyword("and")}
	Or  Operator = Operator{clause.NewKeyword("or")}

	Eq Comparator = Comparator{clause.NewKeyword("eq")}
	Ne Comparator = Comparator{clause.NewKeyword("ne")}
	Lt Comparator = Comparator{clause.NewKeyword("lt")}
	Gt Comparator = Comparator{clause.NewKeyword("gt")}
	Le Comparator = Comparator{clause.NewKeyword("le")}
	Ge Comparator = Comparator{clause.NewKeyword("ge")}
)

type Condition struct {
	Operator   *string
	Field      *string
	Value      *interface{}
	Conditions []*Condition
}

func (c *Condition) WithCondition(item *Condition) *Condition {
	c.Conditions = append(c.Conditions, item)
	return c
}

// Creates a new simple condition.
func NewSimpleCondition(field string, operator string, value *interface{}) *Condition {
	return &Condition{
		Field:      &field,
		Operator:   &operator,
		Value:      value,
		Conditions: nil,
	}
}

// Creates a new compound condition.
func NewCompoundCondition(operator string) *Condition {
	return &Condition{
		Operator:   &operator,
		Conditions: []*Condition{},
		Field:      nil,
		Value:      nil,
	}
}

type Matrix struct {
	elements *common.ImmutableSet[interface{}]
}

func (m *Matrix) Of(items ...*interface{}) {
	m.elements.Of(items)
}

func (m *Matrix) BuildMatrixModel() (*QueryModel, error) {
	return nil, nil
}

func (m *Matrix) BuildRecordModel() (*QueryModel, error) {
	return nil, nil
}
