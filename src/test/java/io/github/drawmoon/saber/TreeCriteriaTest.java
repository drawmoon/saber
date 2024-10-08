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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;

public class TreeCriteriaTest {

  @Test
  public void createSimpleTreeCriteriaTest() {
    TreeCriteria<String, String> root = new TreeCriteria<>("level0", "level0");
    TreeCriteria<String, String> node1 = root.add("level1", "level1");
    TreeCriteria<String, String> node2 = node1.add("level2", "level2");

    assertEquals(1, root.size());

    assertEquals(1, node1.size());
    assertEquals(root, node1.root);
    assertEquals(root, node1.parent);

    assertEquals(root, node2.root);
    assertEquals(node1, node2.parent);
  }

  @Test
  public void createTreeCriteriaTest() {
    TreeCriteria<String, String> root = new TreeCriteria<>("level0", "level0");

    root.children().add("level1_1", "level1_1").add("level1_2", "level1_2");
    root.children(0).children().add("level2", "level2");
    root.children(1).children().add("level2", "level2");

    assertEquals(2, root.size());
  }

  @Test
  public void createTreeCriteriaTest2() {
    TreeCriteria<String, String> root = new TreeCriteria<>("level0", "level0");
    root.children()
        .add("level1_1", "level1_1")
        .add("level1_2", "level1_2")
        .parent()
        .children(0)
        .children()
        .add("level2", "level2")
        .root()
        .children(1)
        .children()
        .add("level2", "level2");

    assertEquals(2, root.size());
  }

  @Test
  public void getNodeByIdTest() {
    TreeCriteria<String, String> root =
        new TreeCriteria<>("level0", "level0")
            .add("level1", "level1")
            .add("level2", "level2")
            .root();

    List<TreeCriteria<String, String>> treeList = root.getNodeById("level0");
    assertEquals(1, treeList.size());

    TreeCriteria<String, String> root2 =
        new TreeCriteria<>("level0", "level0")
            .children()
            .add("level1", "level1")
            .add("level1", "level1")
            .root();

    List<TreeCriteria<String, String>> treeList2 = root2.getNodeById("level1");
    assertEquals(2, treeList2.size());
  }

  @Test
  public void mergeTest() {
    TreeCriteria<String, String> tree1 =
        new TreeCriteria<>("level0", "level0").add("level1", "level1").add("level2", "level2");
    TreeCriteria<String, String> tree2 =
        new TreeCriteria<>("level0", "level0").add("level1", "level1").add("level3", "level3");

    TreeCriteria<String, String> mergedTree = tree1.merge(tree2);
    assertNotNull(mergedTree);
    assertThat(
        mergedTree.root().getNodeById("level1").stream()
            .map(TreeCriteria::children)
            .flatMap(c -> StreamSupport.stream(c.spliterator(), false).map(TreeCriteria::getValue))
            .toArray(),
        equalTo(new String[] {"level2", "level3"}));
  }

  @Test
  public void JsonSerialTest() throws JsonProcessingException {
    TreeCriteria<String, String> root =
        new TreeCriteria<>("level0", "level0")
            .add("level1", "level1")
            .add("level2", "level2")
            .root();

    ObjectMapper om = new ObjectMapper();
    String json = om.writeValueAsString(root);

    assertNotNull(json);
    assertEquals(
        "{\"id\":\"level0\",\"value\":\"level0\",\"height\":0,\"depth\":0,\"children\":[{\"id\":\"level1\",\"value\":\"level1\",\"height\":0,\"depth\":0,\"children\":[{\"id\":\"level2\",\"value\":\"level2\",\"height\":0,\"depth\":0,\"children\":[]}]}]}",
        json);
  }

  // TODO: Not implemented
  // @Test
  // public void JsonParseTest() throws JsonMappingException, JsonProcessingException {
  //   String jsonStr =
  //
  // "{\"id\":\"level0\",\"value\":\"level0\",\"height\":0,\"depth\":0,\"children\":[{\"id\":\"level1\",\"value\":\"level1\",\"height\":0,\"depth\":0,\"children\":[{\"id\":\"level2\",\"value\":\"level2\",\"height\":0,\"depth\":0,\"children\":[]}]}]}";

  //   ObjectMapper om = new ObjectMapper();
  //   TreeCriteria<String, String> root = om.readValue(jsonStr, TreeCriteria.class);

  //   assertNotNull(root);
  //   assertEquals(1, root.size());
  //   assertEquals(1, root.children().size());
  // }
}
