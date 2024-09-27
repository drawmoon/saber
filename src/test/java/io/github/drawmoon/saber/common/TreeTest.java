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
package io.github.drawmoon.saber.common;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.drawmoon.saber.common.Tree.TreeDepthFinder;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

public class TreeTest {

  @Test
  public void createSimpleTreeCriteriaTest() {
    Tree<String, String> root = new Tree<>("level0", "level0");
    Tree<String, String> node1 = root.add("level1", "level1");
    Tree<String, String> node2 = node1.add("level2", "level2");

    assertEquals(1, root.size());

    assertEquals(1, node1.size());
    assertEquals(root, node1.root);
    assertEquals(root, node1.parent);

    assertEquals(root, node2.root);
    assertEquals(node1, node2.parent);
  }

  @Test
  public void createTreeCriteriaTest() {
    Tree<String, String> root = new Tree<>("level0", "level0");

    root.children().add("level1_1", "level1_1").add("level1_2", "level1_2");
    root.children(0).children().add("level2", "level2");
    root.children(1).children().add("level2", "level2");

    assertEquals(2, root.size());
  }

  @Test
  public void createTreeCriteriaTest2() {
    Tree<String, String> root = new Tree<>("level0", "level0");
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
    Tree<String, String> root =
        new Tree<>("level0", "level0").add("level1", "level1").add("level2", "level2").root();

    List<Tree<String, String>> treeList = root.getNodeById("level0");
    assertEquals(1, treeList.size());

    Tree<String, String> root2 =
        new Tree<>("level0", "level0")
            .children()
            .add("level1", "level1")
            .add("level1", "level1")
            .root();

    List<Tree<String, String>> treeList2 = root2.getNodeById("level1");
    assertEquals(2, treeList2.size());
  }

  @Test
  public void visitNodeTest() {
    Tree<String, String> root =
        new Tree<>("level0", "level0").add("level1", "level1").add("level2", "level2").root();

    AtomicInteger index = new AtomicInteger(0);
    root.visitNode(
        new Tree.TreeVisitor<>() {
          @Override
          public void visit(Tree<String, String> node) {
            int currentIndex = index.get();
            if (currentIndex == 0) {
              assertEquals("level0", node.getValue());
            }
            if (currentIndex == 1) {
              assertEquals("level1", node.getValue());
            }
            if (currentIndex == 2) {
              assertEquals("level2", node.getValue());
            }
            index.set(currentIndex + 1);
          }
        });
  }

  @Test
  public void mergeTest() {
    Tree<String, String> tree1 =
        new Tree<>("level0", "level0").add("level1", "level1").add("level2", "level2");
    Tree<String, String> tree2 =
        new Tree<>("level0", "level0").add("level1", "level1").add("level3", "level3");

    Tree<String, String> mergedTree = tree1.merge(tree2);
    assertNotNull(mergedTree);
    assertThat(
        mergedTree.root().getNodeById("level1").stream()
            .map(Tree::children)
            .flatMap(c -> c.stream().map(Tree::getValue))
            .toArray(),
        equalTo(new String[] {"level2", "level3"}));
  }

  @Test
  public void findDeepestNodeTest() {
    Tree<String, String> root =
        new Tree<>("level0", "level0").add("level1", "level1").add("level2", "level2").root();

    String deepestNodeId = TreeDepthFinder.findDeepestNodeId(root);
    assertThat(deepestNodeId, equalTo("level2"));
  }

  @Test
  public void jsonSerialTest() throws JsonProcessingException {
    Tree<String, String> root =
        new Tree<>("level0", "level0").add("level1", "level1").add("level2", "level2").root();

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(root);

    assertNotNull(json);
    assertEquals(
        "{\"id\":\"level0\",\"value\":\"level0\",\"children\":[{\"id\":\"level1\",\"value\":\"level1\",\"children\":[{\"id\":\"level2\",\"value\":\"level2\",\"children\":[],\"height\":0,\"depth\":0}],\"height\":0,\"depth\":0}],\"height\":0,\"depth\":0}",
        json);
  }

  @Test
  public void jsonParseTest() throws JsonProcessingException, JsonMappingException {
    String jsonStr =
        "{\"id\":\"level0\",\"value\":\"level0\",\"children\":[{\"id\":\"level1\",\"value\":\"level1\",\"children\":[{\"id\":\"level2\",\"value\":\"level2\",\"children\":[],\"height\":0,\"depth\":0}],\"height\":0,\"depth\":0}],\"height\":0,\"depth\":0}";

    ObjectMapper mapper = new ObjectMapper();
    Tree<String, String> root = mapper.readValue(jsonStr, new TypeReference<>() {});
    assertNotNull(root);

    root.updateTreeState();

    assertEquals(1, root.size());
    assertEquals(1, root.children().size());
  }
}
