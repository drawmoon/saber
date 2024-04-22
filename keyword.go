package klsql

import (
	"errors"
	"strings"
)

// A SQL keyword.
type Keyword struct {
	asIs string
}

func (k *Keyword) Render(style RenderKeywordCase) (string, error) {
	switch style {
	case AsIs:
		return k.asIs, nil
	case Lower:
		return strings.ToLower(k.asIs), nil
	case Upper:
		return strings.ToUpper(k.asIs), nil
	}
	return k.asIs, errors.New("Unknown render style: " + string(style))
}

// Specify the style of rendering SQL keywords.
type RenderKeywordCase int32

const (
	AsIs  RenderKeywordCase = 0
	Lower RenderKeywordCase = 1
	Upper RenderKeywordCase = 2
)

// Render style for the generated SQL.
type RenderStyle struct {
	Format            *RenderKeywordCase
	DelimiterRequired bool
	Delimiter         string
}
