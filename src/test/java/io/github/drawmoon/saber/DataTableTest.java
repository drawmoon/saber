/*
 *            _
 *  ___  __ _| |__   ___ _ __
 * / __|/ _` | '_ \ / _ \ '__|
 * \__ \ (_| | |_) |  __/ |
 * |___/\__,_|_.__/ \___|_|
 *
 * Copyright 2024 drash
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.drawmoon.saber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.drawmoon.saber.DataTable.DataColumn;
import io.github.drawmoon.saber.DataTable.DataRow;
import io.github.drawmoon.saber.exceptions.SerialException;
import org.junit.jupiter.api.Test;

public class DataTableTest {

  @Test
  public void createDataTableTest() {
    DataTable table = new DataTable();
    table.addColumn(new DataColumn("name"));
    table.addColumn(new DataColumn("age"));

    DataRow firstRow = table.newRow();
    firstRow.setRowData("name", "darsh");
    firstRow.setRowData("age", 18);
    table.addRow(firstRow);

    DataRow secondRow = table.newRow();
    secondRow.setRowData("name", "saber");
    secondRow.setRowData("age", 19);
    table.addRow(secondRow);

    assertEquals(2, table.rowCount());

    Object[] expectedNameList = new Object[] {"darsh", "saber"};
    Object[] expectedAgeList = new Object[] {18, 19};

    int rowIndex = 0;
    for (DataRow row : table) {
      Object name = row.getObject("name");
      Object age = row.getObject("age");

      assertEquals(expectedNameList[rowIndex], name);
      assertEquals(expectedAgeList[rowIndex], age);

      rowIndex++;
    }
  }

  @Test
  public void dataTableJsonSerialTest() throws JsonProcessingException {
    DataTable table = new DataTable();
    table.addColumn(new DataColumn("name"));
    table.addColumn(new DataColumn("age"));

    DataRow firstRow = table.newRow();
    firstRow.setRowData("name", "darsh");
    firstRow.setRowData("age", 18);
    table.addRow(firstRow);

    DataRow secondRow = table.newRow();
    secondRow.setRowData("name", "saber");
    secondRow.setRowData("age", 19);
    table.addRow(secondRow);

    ObjectMapper om = new ObjectMapper();
    String json = om.writeValueAsString(table);

    assertNotNull(json);
    assertEquals("[{\"name\":\"darsh\",\"age\":18},{\"name\":\"saber\",\"age\":19}]", json);
  }

  @Test
  public void dataTableJsonParseTest() throws JsonMappingException, JsonProcessingException {
    String jsonStr = "[{\"name\":\"darsh\",\"age\":18},{\"name\":\"saber\",\"age\":19}]";

    ObjectMapper om = new ObjectMapper();
    DataTable table = om.readValue(jsonStr, DataTable.class);

    assertNotNull(table);
    assertNotNull(table.getColumns());
    assertEquals(2, table.rowCount());

    String[] expectedColumns = new String[] {"name", "age"};

    int columnIndex = 0;
    for (DataColumn column : table.getColumns()) {
      assertEquals(expectedColumns[columnIndex], column.getName());

      columnIndex++;
    }

    Object[] expectedNameList = new Object[] {"darsh", "saber"};
    Object[] expectedAgeList = new Object[] {18, 19};

    int rowIndex = 0;
    for (DataRow row : table) {
      Object name = row.getObject("name");
      Object age = row.getObject("age");

      assertEquals(expectedNameList[rowIndex], name);
      assertEquals(expectedAgeList[rowIndex], age);

      rowIndex++;
    }
  }

  @Test
  public void dataRowJsonSerialTest() {
    DataTable table = new DataTable();
    table.addColumn(new DataColumn("name"));
    table.addColumn(new DataColumn("age"));

    DataRow row = table.newRow();
    row.setRowData("name", "darsh");
    row.setRowData("age", 18);

    ObjectMapper om = new ObjectMapper();
    JsonMappingException jme =
        assertThrows(JsonMappingException.class, () -> om.writeValueAsString(row));
    assertEquals(SerialException.class, jme.getCause().getClass());
  }

  @Test
  public void dataRowJsonParseTest() {
    String jsonStr = "{\"name\":\"darsh\",\"age\":18}";

    ObjectMapper om = new ObjectMapper();
    assertThrows(SerialException.class, () -> om.readValue(jsonStr, DataRow.class));
  }
}
