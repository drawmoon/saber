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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import javax.annotation.Nonnull;

/**
 * A tree data structure that allows for efficient traversal and manipulation of hierarchical data.
 *
 * @param <K> the type of the ID
 * @param <V> the type of the value
 * @author drash
 * @version 1.0
 * @since 2024
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tree<K extends Comparable<?>, V> implements Serializable {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  /** The ID of the node */
  protected K id;

  /** The value of the node */
  protected V value;

  /** The root node of the tree */
  protected Tree<K, V> root;

  /** The parent node of the node */
  protected Tree<K, V> parent;

  /** The children of the node */
  protected TreeChildren<K, V> children;

  /** Distance from current node to deepest leaf node */
  protected int height;

  /** Distance of the current node from the root node */
  protected int depth;

  /** Whether the tree structure has been updated and needs to be re-calculated */
  protected boolean dirty;

  /** Creates a new tree. */
  public Tree() {
    this(null);
  }

  /**
   * Creates a new tree with the given parent.
   *
   * @param parent the parent node
   */
  public Tree(Tree<K, V> parent) {
    this.root = parent == null ? this : parent.root;
    this.parent = parent;
    this.children = new TreeChildren<>(root, this);
  }

  /**
   * Creates a new tree with the given ID and value.
   *
   * @param id the ID of the node
   * @param value the value of the node
   */
  public Tree(K id, V value) {
    this(null, id, value);
  }

  /**
   * Creates a new tree with the given parent, ID, and value.
   *
   * @param parent the parent node
   * @param id the ID of the node
   * @param value the value of the node
   */
  public Tree(Tree<K, V> parent, K id, V value) {
    this(parent);
    this.id = id;
    this.value = value;
  }

  /**
   * Whether the current node is the root node
   *
   * @return true if the current node is the root node, false otherwise
   */
  @JsonIgnore
  public boolean isRoot() {
    return depth == 0;
  }

  /**
   * Whether the current node is a leaf node
   *
   * @return true if the current node is a leaf node, false otherwise
   */
  @JsonIgnore
  public boolean isLeaf() {
    return height == 0;
  }

  /**
   * Returns the root node of the tree.
   *
   * @return the root node of the tree
   */
  public Tree<K, V> root() {
    return root;
  }

  /**
   * Returns the parent node of the current node.
   *
   * @return the parent node of the current node
   */
  public Tree<K, V> parent() {
    return parent;
  }

  /**
   * Returns the next node in the tree structure.
   *
   * @return the next node in the tree structure
   */
  public Tree<K, V> previous() {
    return null;
  }

  /**
   * Returns the next node in the tree structure.
   *
   * @return the next node in the tree structure
   */
  public Tree<K, V> next() {
    return null;
  }

  /**
   * Returns the first sibling node in the tree structure.
   *
   * @return the first sibling node in the tree structure
   */
  public Tree<K, V> firstSibling() {
    return null;
  }

  /**
   * Returns the last sibling node in the tree structure.
   *
   * @return the last sibling node in the tree structure
   */
  public Tree<K, V> lastSibling() {
    return null;
  }

  /** A path consisting of IDs from the root node to the current node */
  public Set<K> path() {
    return null;
  }

  /** A path consisting of values from the root node to the current node */
  public List<V> pathValues() {
    return null;
  }

  /** A Map representation of a tree structure */
  public Map<K, List<V>> pathValueMap() {
    return null;
  };

  /**
   * Returns the height of the tree structure.
   *
   * @return the height of the tree structure
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns the depth of the tree structure.
   *
   * @return the depth of the tree structure
   */
  public int getDepth() {
    return depth;
  }

  /**
   * Returns the number of nodes in the tree structure.
   *
   * @return the number of nodes in the tree structure
   */
  public int size() {
    return children.size();
  }

  /**
   * Returns the ID of the current node.
   *
   * @return the ID of the current node
   */
  public K getId() {
    return id;
  }

  /**
   * Sets the ID of the current node.
   *
   * @param id the ID of the current node
   */
  public void setId(K id) {
    this.id = id;
  }

  /**
   * Returns the value of the current node.
   *
   * @return the value of the current node
   */
  public V getValue() {
    return value;
  }

  /**
   * Sets the value of the current node.
   *
   * @param value the value of the current node
   */
  public void setValue(V value) {
    this.value = value;
  }

  /**
   * Returns a list of all the nodes in the tree with the given ID.
   *
   * @param id the ID of the node to find
   * @return a list of all the nodes in the tree with the given ID
   */
  public List<Tree<K, V>> getNodeById(K id) {
    List<Tree<K, V>> treeList = new ArrayList<>();

    if (Objects.equals(this.id, id)) {
      treeList.add(this);
      return treeList;
    }

    for (Tree<K, V> node : children) {
      treeList.addAll(node.getNodeById(id));
    }

    return treeList;
  }

  /**
   * Returns a list of all the nodes in the tree with the given depth.
   *
   * @param depth the depth of the nodes to find
   * @return a list of all the nodes in the tree with the given depth
   */
  public List<Tree<K, V>> getNodeByDepth(int depth) {
    return null;
  }

  /**
   * Returns a list of all the node IDs in the tree.
   *
   * @return a list of all the node IDs in the tree
   */
  @JsonIgnore
  public List<K> ids() {
    List<K> keys = new ArrayList<>();

    Tree<K, V> root = Optional.ofNullable(this.root).orElse(this);

    Deque<Tree<K, V>> arrayDeque = new ArrayDeque<>();
    arrayDeque.push(root);

    Tree<K, V> currentTreeNode;
    while ((currentTreeNode = arrayDeque.poll()) != null) {
      keys.add(currentTreeNode.getId());

      for (Tree<K, V> child : currentTreeNode.children) {
        arrayDeque.push(child);
      }
    }

    return keys;
  }

  /**
   * Visits all the nodes in the tree using the given visitor.
   *
   * @param visitor the visitor to use
   */
  public void visitNode(TreeVisitor<K, V> visitor) {
    Deque<Tree<K, V>> deque = new ArrayDeque<>();
    deque.push(this);

    while (!deque.isEmpty()) {
      Tree<K, V> currentTreeNode = deque.pop();
      visitor.visit(currentTreeNode);

      for (Tree<K, V> child : currentTreeNode.children) {
        deque.push(child);
      }
    }
  }

  /**
   * Returns the children of the current node.
   *
   * @return the children of the current node
   */
  @JsonProperty
  public TreeChildren<K, V> children() {
    return children;
  }

  /**
   * Returns the child at the given index.
   *
   * @param index the index of the child to return
   * @return the child at the given index
   */
  public Tree<K, V> children(int index) {
    return children.get(index);
  }

  /**
   * Sets the children of the current node.
   *
   * @param children the children to set
   */
  public void setChildren(TreeChildren<K, V> children) {
    this.children = children;
  }

  /**
   * Adds a child to the current node.
   *
   * @param id the ID of the child
   * @param value the value of the child
   * @return the child
   */
  public Tree<K, V> add(K id, V value) {
    return add(new Tree<>(this, id, value));
  }

  /**
   * Adds a child to the current node.
   *
   * @param element the child to add
   * @return the child
   */
  public Tree<K, V> add(Tree<K, V> element) {
    element.parent = parent;
    children.add(element);

    element.root.updateTreeState();
    return element;
  }

  /**
   * Merges the given tree into the current tree.
   *
   * @param tree the tree to merge
   * @return the current tree
   */
  public Tree<K, V> merge(Tree<K, V> tree) {
    if (Objects.isNull(tree)) return this;

    Tree<K, V> root = this.root();
    MergedTree<K, V> mergedTree = new MergedTree<>(root, tree.root());
    root.children = mergedTree.children();

    root.updateTreeState();
    return this;
  }

  /** Updates the state of the tree. */
  public void updateTreeState() {
    Tree<K, V> root = Optional.ofNullable(this.root).orElse(this);
    Tree<K, V> parent = this.parent;

    Deque<Tree<K, V>> stack = new ArrayDeque<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      Tree<K, V> node = stack.pop();
      node.root = root;
      node.parent = parent;

      parent = node;

      TreeChildren<K, V> children = node.children;
      children.root = root;
      children.parent = parent;

      for (Tree<K, V> child : children) {
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

  /**
   * A tree structure that can be merged with another tree structure.
   *
   * @param <K> the type of the ID
   * @param <V> the type of the value
   */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TreeChildren<K extends Comparable<?>, V> extends AbstractList<Tree<K, V>>
      implements Serializable {
    @Serial private static final long serialVersionUID = -8492251942206794476L;

    private final List<Tree<K, V>> children;

    /** The root of the tree node. */
    private Tree<K, V> root;

    /** The parent of the tree node. */
    private Tree<K, V> parent;

    public TreeChildren() {
      this(null, null);
    }

    /**
     * Constructs a new tree children.
     *
     * @param root the root of the tree node
     * @param parent the parent of the tree node
     */
    public TreeChildren(Tree<K, V> root, Tree<K, V> parent) {
      this.root = root;
      this.parent = parent;
      this.children = new ArrayList<>();
    }

    /**
     * Returns the root of the tree node.
     *
     * @return the root of the tree node
     */
    public Tree<K, V> root() {
      return root;
    }

    /**
     * Returns the parent of the tree node.
     *
     * @return the parent of the tree node
     */
    public Tree<K, V> parent() {
      return parent;
    }

    @Override
    public int size() {
      return children.size();
    }

    @Override
    public Tree<K, V> get(int index) {
      return children.get(index);
    }

    public TreeChildren<K, V> add(K id, V value) {
      add(new Tree<>(parent, id, value));
      return this;
    }

    @Override
    public void add(int index, Tree<K, V> element) {
      element.parent = parent;
      children.add(element);

      element.root.updateTreeState();
    }

    @Override
    public Tree<K, V> set(int index, Tree<K, V> element) {
      element.parent = parent;
      children.add(element);

      element.root.updateTreeState();
      return children.set(index, element);
    }

    @CanIgnoreReturnValue
    public TreeChildren<K, V> remove(Tree<K, V> element) {
      int index = children.indexOf(element);
      if (index != -1) {
        removeAt(index);
      }

      return this;
    }

    @CanIgnoreReturnValue
    public TreeChildren<K, V> removeAt(int index) {
      children.remove(index);
      return this;
    }

    @Override
    public Tree<K, V> remove(int index) {
      return children.remove(index);
    }

    @Nonnull
    @Override
    public Iterator<Tree<K, V>> iterator() {
      return children.iterator();
    }
  }

  /**
   * A tree structure that can be merged with another tree structure.
   *
   * @param <K> the type of the ID
   * @param <V> the type of the value
   */
  static class MergedTree<K extends Comparable<?>, V> extends Tree<K, V> {
    @Serial private static final long serialVersionUID = -8492251942206794476L;

    final Tree<K, V> originalT1;
    final Tree<K, V> originalT2;

    /**
     * Creates a new merged tree.
     *
     * @param a the first tree to merge
     * @param b the second tree to merge
     */
    MergedTree(Tree<K, V> a, Tree<K, V> b) {
      originalT1 = Objects.requireNonNull(a);
      originalT2 = Objects.requireNonNull(b);

      applyMerge();
    }

    /** Applies the merge. */
    private void applyMerge() {
      if (!originalT1.equals(originalT2) && !originalT1.equalsHash(originalT2)) {
        throw new IllegalArgumentException("Cannot merge different trees");
      }

      id = originalT1.id;
      value = originalT1.value;
      root = originalT1.root;
      parent = originalT1.parent;
      children = new TreeChildren<>(root, parent);

      applyMergeChildren(originalT1, originalT2);
    }

    /**
     * Applies the merge children.
     *
     * @param a the first tree to merge
     * @param b the second tree to merge
     */
    private void applyMergeChildren(Tree<K, V> a, Tree<K, V> b) {
      List<Integer> hashes = new ArrayList<>();
      for (Tree<K, V> e : a.children()) {
        children.add(e);
        hashes.add(e.hashCode());
      }

      for (Tree<K, V> e : b.children()) {
        if (!hashes.contains(e.hashCode())) {
          children.add(e);
        } else {
          Tree<K, V> t = children.get(hashes.indexOf(e.hashCode()));
          children.remove(t);
          children.add(new MergedTree<>(t, e));
        }
      }
    }
  }

  /** A helper class to find the deepest node in a tree. */
  public static class TreeDepthFinder {
    /**
     * Finds the deepest node in a tree.
     *
     * @param root the root of the tree
     * @return the deepest node id
     * @param <K> the type of the ID
     * @param <V> the type of the value
     */
    public static <K extends Comparable<?>, V> K findDeepestNodeId(Tree<K, V> root) {
      return findDeepestNodeIdHelper(root, 0).deepestId;
    }

    /**
     * Finds the deepest node in a tree.
     *
     * @param node the current node
     * @param currentDepth the current depth
     * @return the depth info
     * @param <K> the type of the ID
     * @param <V> the type of the value
     */
    private static <K extends Comparable<?>, V> DepthInfo<K> findDeepestNodeIdHelper(
        Tree<K, V> node, int currentDepth) {
      if (node == null) {
        return new DepthInfo<>(currentDepth, null);
      }

      K deepestId = node.id;
      int maxDepth = currentDepth;

      for (Tree<K, V> child : node.children) {
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

    /**
     * A helper class to store the depth and deepest node id.
     *
     * @param <K> the type of the ID
     */
    private static class DepthInfo<K> {
      /** The depth. */
      int depth;

      /** The deepest node id. */
      K deepestId;

      /**
       * Creates a new depth info.
       *
       * @param depth the depth
       * @param deepestId the deepest node id
       */
      DepthInfo(int depth, K deepestId) {
        this.depth = depth;
        this.deepestId = deepestId;
      }
    }
  }

  /**
   * A visitor interface for traversing a tree.
   *
   * @param <K> the type of the ID
   * @param <V> the type of the value
   */
  public abstract static class TreeVisitor<K extends Comparable<?>, V>
      implements Visitor<Tree<K, V>> {
    /**
     * Visits a node.
     *
     * @param node the node
     */
    public abstract void visit(Tree<K, V> node);
  }
}
