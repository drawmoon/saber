package common

type ImmutableSet[T any] struct {
	elements []T
}

func (m *ImmutableSet[T]) Of(items ...T) {
	m.elements = items
}

func (m *ImmutableSet[T]) Get(i int) T {
	return m.elements[i]
}
