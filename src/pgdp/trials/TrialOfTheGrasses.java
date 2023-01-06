package pgdp.trials;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrialOfTheGrasses {

	protected TrialOfTheGrasses() {
		throw new UnsupportedOperationException();
	}

	public static class TreeNode<T> {

		private final T label;
		private final TreeNode<T>[] nodes;

		/**
		 * Initializes a {@code TreeNode} with the specified {@code label} and the
		 * specified children.
		 * 
		 * @param label value stored in this node.
		 * @param nodes children of this node.
		 */
		@SafeVarargs
		public TreeNode(T label, TreeNode<T>... nodes) {
			this.label = label;
			this.nodes = nodes;
		}

		/**
		 * Returns the {@code label} of this node.
		 * 
		 * @return {@code label} of the node.
		 */
		public T getLabel() {
			return label;
		}

		/**
		 * Returns an array containing the children of this node.
		 * 
		 * @return array of children.
		 */
		public TreeNode<T>[] getNodes() {
			return nodes;
		}

		/**
		 * Returns {@code true} if this node is a leaf.
		 * 
		 * @return {@code true} if this node is a leaf.
		 */
		public boolean isLeaf() {
			return nodes == null || nodes.length == 0;
		}

		/**
		 * Returns a Stream of {@code TreeNode} in preorder.
		 * 
		 * @return Stream of {@code TreeNode} in preorder.
		 */
		public Stream<TreeNode<T>> flatten() {
			// TODO
			Stream.Builder<TreeNode<T>> treeNodeBuilder = Stream.builder();
			treeNodeBuilder.add(this);
			Arrays.stream(nodes)
					.forEach(childNode -> {
						TreeNode<T> currentNode = childNode;
						if (currentNode.nodes != null) {
							Stream<TreeNode<T>> tempStream = currentNode.flatten();
							tempStream.forEach(treeNodeBuilder::add);
						}
					});
			return treeNodeBuilder.build();
		}

		@Override
		public String toString() {
			return flatten().map(TreeNode::getLabel).map(String::valueOf).collect(Collectors.joining(", "));
		}
	}

	public static void main(String... args) {
		// Example (expected order: 1, 2, 3, 4, 5, 5, 6, 7, 8)
		TreeNode<Integer> t = new TreeNode<>(1,
				new TreeNode<>(2, new TreeNode<>(3, new TreeNode<>(4), new TreeNode<>(5))), new TreeNode<>(5),
				new TreeNode<>(6, new TreeNode<>(7), new TreeNode<>(8)));
		System.out.println(t);
	}

}
