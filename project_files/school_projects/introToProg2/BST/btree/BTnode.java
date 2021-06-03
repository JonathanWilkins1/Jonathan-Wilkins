package btree;
// Based on file: BTnode.java from the package edu.colorado.nodes
// Complete documentation of that is available from the BTnode link in:
//   http://www.cs.colorado.edu/~main/docs/
// That version authored by Michael Main
// Beth Katz has rearranged and refactored this to suit our needs

/******************************************************************************
* A BTnode<E> provides a node for a binary tree. Each node contains a piece of 
* data (which is a reference to an E object) and references to a left and right 
* child. The references to children may be null to indicate that there is no 
* child. The reference stored in a node can also be null.
*
* @author Beth Katz 
*    - changed name to be BTnode (Binary Tree node)
*    - shortened to be the methods that belong to the class
*      and must have instance variables to do their tasks
*    - supporting methods such as traversals and prints are in Btree class
*    - added Visitor interface that requires that a visit method be provided 
*      to define what action is done when a BTnode is visited
*
* @version
*   April 2007
******************************************************************************/
public class BTnode<E> {
   // Invariant of the BTnode<E> class:
   //   1. Each node has one reference to an E Object, 
   //      stored in the instance variable data.
   //   2. The instance variables left and right are references 
   //      to the node's left and right children.
   private E data;
   private BTnode<E> left, right;   

   /**
   * Initialize a BTnode with a specified initial data and links
   * children. Note that a child link may be the null reference, 
   * which indicates that the new node does not have that child.
   * @param initialData
   *   the initial data of this new node
   * @param initialLeft
   *   a reference to the left child of this new node--this reference may 
   *   be null to indicate that there is no node after this new node.
   * @param initialRight
   *   a reference to the right child of this new node--this reference may 
   *   be null to indicate that there is no node after this new node.
   * Postcondition:
   *   This node contains the specified data and links to its children.
   **/   
   public BTnode(E initialData, BTnode<E> initialLeft, BTnode<E> initialRight) {
      data = initialData;
      left = initialLeft;
      right = initialRight;
   }       

   /**
   * Initialize a BTnode with a specified initial data and link to
   * left child. Note that a child link may be the null reference, 
   * which indicates that the new node does not have that child.
   * Right child is set to null.
   * @param initialData
   *   the initial data of this new node
   * @param initialLeft
   *   a reference to the left child of this new node--this reference may 
   *   be null to indicate that there is no node after this new node.
   * Postcondition:
   *   This node contains the specified data and links to its children.
   **/   
   public BTnode(E initialData, BTnode<E> initialLeft) {
      data = initialData;
      left = initialLeft;
      right = null;
   }       

   /**
   * Initialize a BTnode with a specified initial data and no children.
   * @param initialData
   *   the initial data of this new node
  * Postcondition:
   *   This node contains the specified data and no children.
   **/   
   public BTnode(E initialData) {
      data = initialData;
      left = null;
      right = null;
   }       
  
   /**
   * Accessor method to get the data from this node.   
   * @param - none
   * @return
   *   the data from this node
   **/
   public E getData( ) {
      return data;
   }  
   
   /**
   * Accessor method to get a reference to the left child of this node. 
   * @param - none
   * @return
   *   a reference to the left child of this node (or the null reference if there
   *   is no left child)
   **/
   public BTnode<E> getLeft( ) {
      return left;                                               
   }   
         
   /**
   * Accessor method to get a reference to the right child of this node. 
   * @param - none
   * @return
   *   a reference to the right child of this node (or the null reference 
   *   if there is no right child)
   **/
   public BTnode<E> getRight( ) {
      return right;                                               
   } 

   
   /**
   * Modification method to set the data in this node.   
   * @param newData
   *   the new data to place in this node
   * Postcondition:
   *   The data of this node has been set to newData.
   **/
   public void setData(E newData) {
      data = newData;
   }                                                               
    
   /**
   * Modification method to set the link to the left child of this node.
   * @param newLeft
   *   a reference to the node that should appear as the left child of this node
   *  (or the null reference if there is no left child for this node)
   * Postcondition:
   *   The link to the left child of this node has been set to newLeft.
   *   Any other node (that used to be the left child) is no longer connected 
   *   to this node.
   **/
   public void setLeft(BTnode<E> newLeft) {                    
      left = newLeft;
   }
      
   /**
   * Modification method to set the link to the right child of this node.
   * @param newRight
   *   a reference to the node that should appear as the right child of this node
   *  (or the null reference if there is no right child for this node)
   * Postcondition:
   *   The link to the right child of this node has been set to newRight.
   *   Any other node (that used to be the right child) is no longer connected 
   *   to this node.
   **/
   public void setRight(BTnode<E> newRight) {                    
      right = newRight;
   }  
   

   /**
    * This interface forces users of BTnode to provide a visit method to specify
    * how a node's data is handled when it is "visited". By placing that visit
    * method in the class using the BTnodes, that method can refer to the class's
    * instance variables and customize what happens at the visit
    * @param <E>
    *   type of element in the BTnode
    */
   public interface Visitor<E> {
	   public void visit(BTnode<E> node);
   }
}
