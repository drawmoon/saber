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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import javax.annotation.Nonnull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TreeCriteria<K extends Comparable<?>, V> implements Serializable {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  protected K id;
  protected V value;
  protected TreeCriteria<K, V> root;
  protected TreeCriteria<K, V> parent;
  protected ChildrenTreeCriteria<K, V> children;

  /** A path consisting of IDs from the root node to the current node */
  protected Set<K> path;

  /** A path consisting of values from the root node to the current node */
  protected List<V> pathValues;

  /** A Map representation of a tree structure */
  protected Map<K, List<V>> pathValueMap;

  /** Distance from current node to deepest leaf node */
  protected int height;

  /** Distance of the current node from the root node */
  protected int depth;

  protected boolean dirty;

  public TreeCriteria() {
    this(null);
  }

  public TreeCriteria(TreeCriteria<K, V> parent) {
    this.root = parent == null ? this : parent.root;
    this.parent = parent;
    this.children = new ChildrenTreeCriteria<>(root, this);
  }

  public TreeCriteria(K id, V value) {
    this(null, id, value);
  }

  public TreeCriteria(TreeCriteria<K, V> parent, K id, V value) {
    this(parent);
    this.id = id;
    this.value = value;
  }

  @JsonIgnore
  public boolean isRoot() {
    return depth == 0;
  }

  @JsonIgnore
  public boolean isLeaf() {
    return height == 0;
  }

  public TreeCriteria<K, V> root() {
    return root;
  }

  public TreeCriteria<K, V> parent() {
    return parent;
  }

  public TreeCriteria<K, V> previous() {
    return null;
  }

  public TreeCriteria<K, V> next() {
    return null;
  }

  public TreeCriteria<K, V> firstSibling() {
    return null;
  }

  public TreeCriteria<K, V> lastSibling() {
    return null;
  }

  public int getHeight() {
    return height;
  }

  public int getDepth() {
    return depth;
  }

  /** Total number of nodes in the subtree rooted at the current node */
  public int size() {
    return children.size();
  }

  public K getId() {
    return id;
  }

  public void setId(K id) {
    this.id = id;
  }

  public V getValue() {
    return value;
  }

  public void setValue(V value) {
    this.value = value;
  }

  @JsonProperty
  public ChildrenTreeCriteria<K, V> children() {
    return children;
  }

  public TreeCriteria<K, V> children(int index) {
    return children.get(index);
  }

  public void setChildren(ChildrenTreeCriteria<K, V> children) {
    this.children = children;
  }

  public List<TreeCriteria<K, V>> getNodeById(K id) {
    List<TreeCriteria<K, V>> treeList = new ArrayList<>();

    if (Objects.equals(this.id, id)) {
      treeList.add(this);
      return treeList;
    }

    for (TreeCriteria<K, V> node : children) {
      treeList.addAll(node.getNodeById(id));
    }

    return treeList;
  }

  public List<TreeCriteria<K, V>> getNodeByDepth(int depth) {
    return null;
  }

  public TreeCriteria<K, V> add(K id, V value) {
    return add(new TreeCriteria<K, V>(this, id, value));
  }

  public TreeCriteria<K, V> add(TreeCriteria<K, V> element) {
    element.parent = parent;
    children.add(element);

    element.root.updateTreeState();
    return element;
  }

  public TreeCriteria<K, V> merge(TreeCriteria<K, V> tree) {
    if (Objects.isNull(tree)) return this;

    TreeCriteria<K, V> root = this.root();
    MergedTreeCriteria<K, V> mergedTree = new MergedTreeCriteria<>(root, tree.root());
    root.children = mergedTree.children();

    root.updateTreeState();
    return this;
  }

  public void updateTreeState() {
    TreeCriteria<K, V> root = Optional.ofNullable(this.root).orElse(this);
    TreeCriteria<K, V> parent = this.parent;

    Deque<TreeCriteria<K, V>> stack = new ArrayDeque<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      TreeCriteria<K, V> node = stack.pop();
      node.root = root;
      node.parent = parent;

      parent = node;

      ChildrenTreeCriteria<K, V> children = node.children;
      children.root = root;
      children.parent = parent;

      for (TreeCriteria<K, V> child : children) {
        stack.push(child);
      }
    }
  }

  protected boolean equalsHash(Object obj) {
    return obj != null && hashCode() == obj.hashCode();
  }

  @Override
  public String toString() {
    return id + ":" + value + " " + super.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, value);
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ChildrenTreeCriteria<K extends Comparable<?>, V>
      extends AbstractList<TreeCriteria<K, V>> implements Serializable {
    private static final long serialVersionUID = -8492251942206794476L;

    private final List<TreeCriteria<K, V>> children;
    private TreeCriteria<K, V> root;
    private TreeCriteria<K, V> parent;

    public ChildrenTreeCriteria() {
      this(null, null);
    }

    public ChildrenTreeCriteria(TreeCriteria<K, V> root, TreeCriteria<K, V> parent) {
      this.root = root;
      this.parent = parent;
      this.children = new ArrayList<>();
    }

    public TreeCriteria<K, V> root() {
      return root;
    }

    public TreeCriteria<K, V> parent() {
      return parent;
    }

    @Override
    public int size() {
      return children.size();
    }

    @Override
    public TreeCriteria<K, V> get(int index) {
      return children.get(index);
    }

    public ChildrenTreeCriteria<K, V> add(K id, V value) {
      add(new TreeCriteria<>(parent, id, value));
      return this;
    }

    @Override
    public void add(int index, TreeCriteria<K, V> element) {
      element.parent = parent;
      children.add(element);

      element.root.updateTreeState();
    }

    @Override
    public TreeCriteria<K, V> set(int index, TreeCriteria<K, V> element) {
      element.parent = parent;
      children.add(element);

      element.root.updateTreeState();
      return children.set(index, element);
    }

    @CanIgnoreReturnValue
    public ChildrenTreeCriteria<K, V> remove(TreeCriteria<K, V> element) {
      int index = children.indexOf(element);
      if (index != -1) {
        removeAt(index);
      }

      return this;
    }

    @CanIgnoreReturnValue
    public ChildrenTreeCriteria<K, V> removeAt(int index) {
      children.remove(index);
      return this;
    }

    @Override
    public TreeCriteria<K, V> remove(int index) {
      return children.remove(index);
    }

    @Nonnull
    @Override
    public Iterator<TreeCriteria<K, V>> iterator() {
      return children.iterator();
    }
  }

  static class MergedTreeCriteria<K extends Comparable<?>, V> extends TreeCriteria<K, V> {
    @Serial private static final long serialVersionUID = -8492251942206794476L;

    final TreeCriteria<K, V> originalT1;
    final TreeCriteria<K, V> originalT2;

    MergedTreeCriteria(TreeCriteria<K, V> a, TreeCriteria<K, V> b) {
      originalT1 = Objects.requireNonNull(a);
      originalT2 = Objects.requireNonNull(b);

      applyMerge();
    }

    private void applyMerge() {
      if (!originalT1.equals(originalT2) && !originalT1.equalsHash(originalT2)) {
        throw new IllegalArgumentException("Cannot merge different trees");
      }

      id = originalT1.id;
      value = originalT1.value;
      root = originalT1.root;
      parent = originalT1.parent;
      children = new ChildrenTreeCriteria<>(root, parent);

      applyMergeChildren(originalT1, originalT2);
    }

    private void applyMergeChildren(TreeCriteria<K, V> a, TreeCriteria<K, V> b) {
      List<Integer> hashes = new ArrayList<>();
      for (TreeCriteria<K, V> e : a.children()) {
        children.add(e);
        hashes.add(e.hashCode());
      }

      for (TreeCriteria<K, V> e : b.children()) {
        if (!hashes.contains(e.hashCode())) {
          children.add(e);
        } else {
          TreeCriteria<K, V> t = children.get(hashes.indexOf(e.hashCode()));
          children.remove(t);
          children.add(new MergedTreeCriteria<>(t, e));
        }
      }
    }
  }

  public static class TreeDepthFinder {
    public static <K extends Comparable<?>, V> K findDeepestNodeId(TreeCriteria<K, V> root) {
      return findDeepestNodeIdHelper(root, 0).deepestId;
    }

    private static <K extends Comparable<?>, V> DepthInfo<K> findDeepestNodeIdHelper(
        TreeCriteria<K, V> node, int currentDepth) {
      if (node == null) {
        return new DepthInfo<>(currentDepth, null);
      }

      K deepestId = node.id;
      int maxDepth = currentDepth;

      for (TreeCriteria<K, V> child : node.children) {
        DepthInfo<K> info = findDeepestNodeIdHelper(child, currentDepth + 1);
        if (info.depth > maxDepth) {
          maxDepth = info.depth;
          deepestId = info.deepestId;
        }
      }

      if (maxDepth == currentDepth) {
        maxDepth++;
      }

      return new DepthInfo<>(maxDepth, deepestId);
    }

    private static class DepthInfo<K> {
      int depth;
      K deepestId;

      DepthInfo(int depth, K deepestId) {
        this.depth = depth;
        this.deepestId = deepestId;
      }
    }
  }
}
