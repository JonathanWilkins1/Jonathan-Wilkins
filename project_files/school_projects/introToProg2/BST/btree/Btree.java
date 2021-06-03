package btree;
//Based on file: BTnode.java from the package edu.colorado.nodes
//Complete documentation of that is available from the BTnode link in:
//http://www.cs.colorado.edu/~main/docs/
//That version authored by Michael Main
//Beth Katz has rearranged and refactored this to suit our needs

/******************************************************************************
 * This class is a library of methods that act on BTnodes.
 * It is a library because it does not need to access the instance variables
 * of the BTnode. It has no instance variables of its own.
 *
 * @author Michael Main 
 *    split apart to be the basic node and supporting tools 
 *    Beth Katz split class and changed methods to take BTnode as parameter  
 *
 * @version
 *   July 2005 and April 2007
 ******************************************************************************/
public class Btree<E> {

	/**
	 * Uses an inorder traversal to print the data from each node at or below
	 * this node of the binary tree.
	 * @param - none
	 * Postcondition:
	 *   The data of this node and all its descendants have been written by
	 *   System.out.println( ) using an inorder traversal.
	 **/
	public static <E> void inorderPrint(BTnode<E> root) {
		if (root != null) {
			inorderPrint(root.getLeft( ));
			System.out.println(root.getData( ));
			inorderPrint(root.getRight( ));		   
		}
	} 
	
	/**
	 * Uses an inorder traversal to process the data from each node 
	 * at or below this node of the binary tree.
	 * @param - none
	 * Postcondition:
	 *   The data of this node and all its descendants have been handled by
	 *   the provided visitor method using an inorder traversal.
	 **/
	public static <E> void inorder(BTnode<E> root, BTnode.Visitor<E> visitor) {
		if (root != null) {
			inorder(root.getLeft( ), visitor);
			visitor.visit(root);
			inorder(root.getRight( ), visitor);		   
		}
	}  

	/**
	 * Uses a postorder traversal to print the data from each node at or below
	 * this node of the binary tree.
	 * @param - none
	 * Postcondition:
	 *   The data of this node and all its descendants have been written by
	 *   System.out.println( ) using a postorder traversal.
	 **/
	public static <E> void postorderPrint(BTnode<E> root) {
		if (root != null) {
			postorderPrint(root.getLeft( ));
			postorderPrint(root.getRight( ));		   
			System.out.println(root.getData( ));
		}
	}  

	/**
	 * Uses an preorder traversal to print the data from each node at or below
	 * this node of the binary tree.
	 * @param - none
	 * Postcondition:
	 *   The data of this node and all its descendants have been written by
	 *   System.out.println( ) using an inorder traversal.
	 **/
	public static <E> void preorderPrint(BTnode<E> root) {
		if (root != null) {
			System.out.println(root.getData( ));
			preorderPrint(root.getLeft( ));
			preorderPrint(root.getRight( ));		   
		}
	}  

	/**
	 * Accessor method to determine whether a node is a leaf. 
	 * @param - node
	 * 	the node that is possibly a leaf
	 * @return
	 *   true (if this node is a leaf) or 
	 *   false (if this node is not a leaf).
	 **/
	public static <E> boolean isLeaf(BTnode<E> node) {
		return (node.getLeft( ) == null) && (node.getRight( ) == null);                                               
	} 

	/**
	 * Uses an inorder traversal to print the data from each node at or below
	 * this node of the binary tree, with indentations to indicate the depth
	 * of each node.
	 * @param root
	 *   the root node of this subtree
	 * @param depth
	 *   the depth of this node (with 0 for root, 1 for the root's
	 *   children, and so on)
	 * Precondition:
	 *   depth is the depth of this node.
	 * Postcondition:
	 *   The data of this node and all its descendants have been written by
	 *   System.out.println( ) using an inorder traversal.
	 *   The indentation of each line of data is four times its depth in the
	 *   tree. A dash "--" is printed at any place where a child has no
	 *   sibling.
	 *   This is Michael Main's code adapted to having a node parameter.
	 **/
	public void print(BTnode<E> root, int depth) {
		int i;

		// Print the indentation and the data from the current node:
		for (i = 1; i <= depth; i++) {
			System.out.print("    ");
		}
		System.out.println(root.getData( ));

		// Print the left subtree (or a dash if there is a right child and no left child)   
		if (root.getLeft( ) != null) {
			print(root.getLeft( ), depth+1);
		} else if (root.getRight( ) != null) {
			for (i = 1; i <= depth+1; i++) {
				System.out.print("    ");
			}
			System.out.println("--");
		}

		// Print the right subtree (or a dash if there is a left child and no left child)  
		if (root.getRight( ) != null) {
			print(root.getRight( ), depth+1);
		} else if (root.getLeft( ) != null) {
			for (i = 1; i <= depth+1; i++) {
				System.out.print("    ");
			}
			System.out.println("--");
		}
	}

	/**
	 * prints a string as many times as specified and doesn't end line
	 * @param count
	 * 	the number of times t print string
	 * @param s
	 * 	the string to print multiple times
	 */
	public static void printMany(int count, String s) {
		for (int i = 1; i <= count; i++) {
			System.out.print(s);
		}
	}

	/**
	 * Uses a right-to-left inorder traversal to print the data from each 
	 * node at or below this node of the binary tree, with indentations to 
	 * indicate the depth of each node.
	 * @param root
	 *   the root of this subtree
	 * @param depth
	 *   the depth of this node (with 0 for root, 1 for the root's
	 *   children, and so on)
	 * Precondition:
	 *   depth is the depth of this node.
	 * Postcondition:
	 *   The data of this node and all its descendants have been writeen by
	 *   System.out.println( ) using an inorder traversal.
	 *   The indentation of each line of data corresponds to its depth in the
	 *   tree. A tilde "~" is printed at any place where a child has no
	 *   sibling.
	 *   Based on the C++ binary tree print by Michael Main
	 **/
	public static <E> void printSideways(BTnode<E> root, int depth) {
		String empty = "~";
		String indent = "   ";

		if (root == null) {
			return;
		}
		
		if (root.getRight( ) != null) {         // print right subtree
			printSideways(root.getRight( ), depth+1);
		} else if (root.getLeft( ) != null) {   // empty right but non-empty left
			printMany(depth+1, indent);
			System.out.println(empty); 
		}
		
		printMany(depth, indent);              // print the root
		System.out.println(root.getData( ));
		
		if (root.getLeft( ) != null) {         // print left subtree
			printSideways(root.getLeft( ), depth+1);
		} else if (root.getRight( ) != null) { // empty left but non-empty right
			printMany(depth+1, indent);
			System.out.println(empty); 
		}
	}

	/**
	 * Copy a binary tree.
	 * @param source
	 *   a reference to the root of a binary tree that will be copied 
	 *   (which may be an empty tree where source is null)
	 * @return
	 *   The method has made a copy of the binary tree starting at 
	 *   source. The return value is a reference to the root of the copy. 
	 * @exception OutOfMemoryError
	 *   Indicates that there is insufficient memory for the new tree.   
	 **/ 
	public static <E> BTnode<E> treeCopy(BTnode<E> source) {
		BTnode<E> leftCopy, rightCopy;

		if (source == null) {
			return null;
		} else {
			leftCopy = treeCopy(source.getLeft( ));
			rightCopy = treeCopy(source.getRight( ));
			return new BTnode<E>(source.getData( ), leftCopy, rightCopy);
		}
	}

	/**
	 * Count the number of nodes in a binary tree.
	 * @param root
	 *   a reference to the root of a binary tree (which may be
	 *   an empty tree where source is null)
	 * @return
	 *   the number of nodes in the binary tree  
	 **/ 
	public static <E> long treeSize(BTnode<E> root) {
		if (root == null) {
			return 0;
		} else {
			return 1 + treeSize(root.getLeft( )) + treeSize(root.getRight( ));
		}
	}   

}

