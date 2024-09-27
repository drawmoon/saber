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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TreeCriteria<T, V> implements Serializable {

  protected T id;
  protected V value;
  protected TreeCriteria<T, V> root;
  protected TreeCriteria<T, V> parent;
  protected ChildrenTreeCriteria<T, V> children;

  /** A path consisting of IDs from the root node to the current node */
  protected Set<T> path;

  /** A path consisting of values from the root node to the current node */
  protected List<V> pathValues;

  /** A Map representation of a tree structure */
  protected Map<T, List<V>> pathValueMap;

  /** Distance from current node to deepest leaf node */
  protected int height;

  /** Distance of the current node from the root node */
  protected int depth;

  protected boolean dirty;

  public TreeCriteria() {
    this(null);
  }

  public TreeCriteria(TreeCriteria<T, V> parent) {
    this.root = parent == null ? this : parent.root;
    this.parent = parent;
    this.children = new ChildrenTreeCriteria<>(root, this);
  }

  public TreeCriteria(T id, V value) {
    this(null, id, value);
  }

  public TreeCriteria(TreeCriteria<T, V> parent, T id, V value) {
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

  public TreeCriteria<T, V> root() {
    return root;
  }

  public TreeCriteria<T, V> parent() {
    return parent;
  }

  public TreeCriteria<T, V> previous() {
    return null;
  }

  public TreeCriteria<T, V> next() {
    return null;
  }

  public TreeCriteria<T, V> firstSibling() {
    return null;
  }

  public TreeCriteria<T, V> lastSibling() {
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

  public T getId() {
    return id;
  }

  public void setId(T id) {
    this.id = id;
  }

  public V getValue() {
    return value;
  }

  public void setValue(V value) {
    this.value = value;
  }

  @JsonProperty("children")
  public ChildrenTreeCriteria<T, V> children() {
    return children;
  }

  public TreeCriteria<T, V> children(int index) {
    return children.get(index);
  }

  public void setChildren(ChildrenTreeCriteria<T, V> children) {
    this.children = children;
  }

  public List<TreeCriteria<T, V>> getNodeById(T id) {
    List<TreeCriteria<T, V>> treeList = new ArrayList<>();

    if (Objects.equals(this.id, id)) {
      treeList.add(this);
      return treeList;
    }

    for (TreeCriteria<T, V> node : children) {
      treeList.addAll(node.getNodeById(id));
    }

    return treeList;
  }

  public List<TreeCriteria<T, V>> getNodeByDepth(int depth) {
    return null;
  }

  public TreeCriteria<T, V> add(T id, V value) {
    return add(new TreeCriteria<T, V>(this, id, value));
  }

  public TreeCriteria<T, V> add(TreeCriteria<T, V> element) {
    element.parent = parent;
    children.add(element);

    element.root.updateTreeState();
    return element;
  }

  public TreeCriteria<T, V> merge(TreeCriteria<T, V> tree) {
    if (Objects.isNull(tree)) return this;

    TreeCriteria<T, V> root = this.root();
    MergedTreeCriteria<T, V> mergedTree = new MergedTreeCriteria<>(root, tree.root());
    root.children = mergedTree.children();

    root.updateTreeState();
    return this;
  }

  protected boolean equalsHash(Object obj) {
    return obj != null && hashCode() == obj.hashCode();
  }

  protected void updateTreeState() {}

  @Override
  public String toString() {
    return id + ":" + value + " " + super.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, value);
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ChildrenTreeCriteria<T, V> implements Iterable<TreeCriteria<T, V>> {
    private final TreeCriteria<T, V> root;
    private final TreeCriteria<T, V> parent;
    private final List<TreeCriteria<T, V>> children;

    public ChildrenTreeCriteria(TreeCriteria<T, V> root, TreeCriteria<T, V> parent) {
      this.root = root;
      this.parent = parent;
      this.children = new ArrayList<>();
    }

    public TreeCriteria<T, V> root() {
      return root;
    }

    public TreeCriteria<T, V> parent() {
      return parent;
    }

    public int size() {
      return children.size();
    }

    public TreeCriteria<T, V> get(int index) {
      return children.get(index);
    }

    public ChildrenTreeCriteria<T, V> add(T id, V value) {
      return add(new TreeCriteria<>(parent, id, value));
    }

    public ChildrenTreeCriteria<T, V> add(TreeCriteria<T, V> element) {
      element.parent = parent;
      children.add(element);

      element.root.updateTreeState();
      return this;
    }

    @CanIgnoreReturnValue
    public ChildrenTreeCriteria<T, V> remove(TreeCriteria<T, V> element) {
      int index = children.indexOf(element);
      if (index != -1) {
        removeAt(index);
      }

      return this;
    }

    @CanIgnoreReturnValue
    public ChildrenTreeCriteria<T, V> removeAt(int index) {
      children.remove(index);
      return this;
    }

    @Nonnull
    @Override
    public Iterator<TreeCriteria<T, V>> iterator() {
      return children.iterator();
    }
  }

  static class MergedTreeCriteria<T, V> extends TreeCriteria<T, V> {
    final TreeCriteria<T, V> originalT1;
    final TreeCriteria<T, V> originalT2;

    MergedTreeCriteria(TreeCriteria<T, V> a, TreeCriteria<T, V> b) {
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

    private void applyMergeChildren(TreeCriteria<T, V> a, TreeCriteria<T, V> b) {
      List<Integer> hashes = new ArrayList<>();
      for (TreeCriteria<T, V> e : a.children()) {
        children.add(e);
        hashes.add(e.hashCode());
      }

      for (TreeCriteria<T, V> e : b.children()) {
        if (!hashes.contains(e.hashCode())) {
          children.add(e);
        } else {
          TreeCriteria<T, V> t = children.get(hashes.indexOf(e.hashCode()));
          children.remove(t);
          children.add(new MergedTreeCriteria<>(t, e));
        }
      }
    }
  }
}
