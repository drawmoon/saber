# Condition model

## 基础结构

基本的条件对象结构，它能够表示单一的条件表达式：

- Arguments

| Name | Type | Description |
| --- | --- | --- |
| field | string | 字段名称 |
| operator | string | 操作符 |
| value | object | 值 |

- Operators

| Operator | Description | Support |
| --- | --- | --- |
| eq | 等于 | ✓ |
| ne | 不等于 | ✓ |
| gt | 大于 | ✓ |
| ge | 大于等于 | ✓ |
| lt | 小于 | ✓ |
| le | 小于等于 | ✓ |
| in | 在列表中 | × |
| nin | 不在列表中 | × |
| like | 模糊匹配 | × |
| notlike | 不模糊匹配 | × |
| isnull | 为空 | × |
| notnull | 不为空 | × |
| between | 在区间内 | × |
| notbetween | 不在区间内 | × |

- Example

```json
{
    "field": "age",
    "operator": "gt",
    "value": 18
}
```

- Output

```sql
age > 18
```

## 复合条件

为了支持多个条件之间的逻辑组合（`AND`、`OR`），我们可以引入一个复合条件结构：

- Arguments

| Name | Type | Description |
| --- | --- | --- |
| operator | string | 类型 |
| conditions | array | 条件数组 |

- Operator

| Type | Description | Support |
| --- | --- | --- |
| and | 且 | ✓ |
| or | 或 | ✓ |

- Example

```json
{
    "operator": "and",
    "conditions": [
        {
            "field": "age",
            "operator": "gt",
            "value": 18
        },
        {
            "operator": "or",
            "conditions": [
                {
                    "field": "name",
                    "operator": "eq",
                    "value": "John"
                },
                {
                    "field": "name",
                    "operator": "eq",
                    "value": "Mary"
                }
            ]
        }
    ]
}
```

- Output

```sql
age > 18 AND (name = 'John' OR name = 'Mary')
```
