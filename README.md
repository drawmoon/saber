# <img src="./docs/logo.svg" height="35" width="35" align="left"> Saber

English | [中文](./README_zh.md)

Receiving query models that generate DSL source code, saber also includes DAO, interception and database behavior logging capabilities.

The features include:

- Model-based DSL source code generator
- Generate different DSL source code using a standardized computational function syntax
- DAOs
- Dynamically manage multiple DataSources
- Fetch interception and database behavior logging capabilities
- Support for
  - MySQL
  - MariaDB
  - PostgreSQL
  - GreenPlum
  - GaussDB
  - Oracle
  - Dameng
  - MSSQL

For function syntax in Saber, [see this](./docs/functions_in_saber.md).

## introduction

```json
{
    "q": ["$1", "$2"],
    "aggregated": "sum",
    "f": {
        "$filter": {
            "input": "$2",
            "as": "a",
            "cond": {"gt": ["$$a", 100]}
        }
    }
}
```

Converts a JSON string into a QueryModel and renders SQL:

```java
QueryModel model = QueryModel.fromJSON(JSON_STRING);
model.prepare();

String sql = model.render();
```

Write a model and get the query:

```java
QueryModel model = QueryModel.fromJSON(JSON_STRING);
model.prepare();

Response response = model.fetch();
```

## Writing Typesafe SQL

```java
Expressions.create(RUBIK_CUBE).select(1);
```

More powerful examples:

```java
Table orders = RUBIK_CUBE.getTable("orders");
Table products = RUBIK_CUBE.getTable("products");

Field month = RUBIK_CUBE.getField("month");
Field sales = RUBIK_CUBE.getField("sales").max().as("total_sales");

Select statement = Expressions.create(RUBIK_CUBE)
                              .select(month, sales)
                              .from(orders)
                              .join(products)
                              .where(sales.gt(10000))
                              .orderBy(month.desc());
```

## License

Apache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0)
