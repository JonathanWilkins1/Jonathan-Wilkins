// File: Node.java based on the generic Node class by Michael Main
// See Appendix E in text (starting p. 764) for longer version

/**************************************************************************
* Node provides a generic node for a linked list with data in each node.
* @author Beth Katz (based on node classes by Michael Main)
* @version March 2008
***************************************************************************/

public class Node<E>
{
   // Invariant of the Node class:
   //   1. The node's data is in the instance variable data.
   //   2. For the final node of a list, the link is null.
   //      Otherwise, link is a reference to the next node of the list.
   private E data;
   private Node<E> link;   
   
   /**
   * Initialize a node with given initial data and link to the next node.
   * The initialLink may be the null reference, which indicates that the 
   * new node has nothing after it.
   * @param initialData
   *   the initial data of this new node
   * @param initialLink
   *   a reference to the node after this new node--this reference may 
   *   be null to indicate that there is no node after this new node.
   **/   
   public Node(E initialData, Node<E> initialLink) {
      data = initialData;
      link = initialLink;
   }
    
   /**
   * Get the data from this node.   
   * @return
   *   the data from this node
   **/
   public E getData( ) {
      return data;
   }
    
   /**
   * Get a reference to the next node after this node. 
   * @return
   *   a reference to the node after this node (or the null reference 
   *   if there is nothing after this node)
   **/
   public Node<E> getLink( ) {
      return link;                                               
   } 
   
   /**
   * Set the data in this node.   
   * @param newData
   *   the new data to place in this node
   * @postcondition
   *   The data of this node has been set to newData.
   **/
   public void setData(E newData){
      data = newData;
   }                                                               
    
   /**
   * Set the link to the next node after this node.
   * @param newLink
   *   a reference to the node that is after this node in the linked list 
   *   (or the null reference if there is no node after this node)
   **/
   public void setLink(Node<E> newLink) {                    
      link = newLink;
   }  
}
           
