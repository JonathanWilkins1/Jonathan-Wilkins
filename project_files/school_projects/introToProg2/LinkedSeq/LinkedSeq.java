// File: LinkedSeq.java based on the package edu.colorado.collections

// This is an assignment for after completion of Chapters 4 and 5
// of "Data Structures and Other Objects Using Java" by Michael Main.

/******************************************************************************
* This class is a homework assignment;
* A LinkedSeq is a collection of objects that are kept in order.
* The sequence can have a special "current element," which is specified and 
* accessed through four methods that are not available in the bag class 
* (start, getCurrent, advance and isCurrent).
*
* @note
*   Beyond Int.MAX_VALUE elements, the size method does not work.
*
* @see
*   Based on the sequence with linked list assignment by Michael Main
*   in chapter 4 but using generic nodes from chapter 5
*
* @version
*   March 2018
*   
* @author Implementation by Jonathan Wilkins
******************************************************************************/
public class LinkedSeq<E> implements Cloneable
{
   // Invariant of the LinkedSeq class:
   // 1. the number of items in the sequence is maintained in manyNodes  
   // 2. head points to the first node, if any, or it is null 
   // 3. tail points to the last node, if any, or it is null 
   // 4. if there is a current item, cursor points to it and  
   //    precursor points to the node before it, if any 
   // 5. if there is no current item, cursor and precursor are both null

   private Node<E> head, tail, cursor, precursor;
   private int manyNodes;

   /**
   * verifyInvariant checks some properties of the class invariant
   * @param - none
   * @return:
   *   true if no problem is found, false if we have a problem with the invariant
   **/   
   public boolean verifyInvariant() { // DO NOT CHANGE THIS METHOD
      if (tail == null && head != null) {
         return false;
      }
      if (head == null && tail != null) {
         return false;
      }
      if (tail != null && tail.getLink() != null) {
         return false;
      }
      if (precursor == null && cursor != null && cursor != head) {
         return false;
      }
      if (precursor != null) {
         if (cursor == null) {
            return false;
         } else if (precursor.getLink() != cursor) {
            return false;
         }
      }
      int count = 0;
      Node<E> pred = null, curr = head;
      while (curr != null && curr != cursor && curr != tail) {
         pred = curr;
         curr = curr.getLink();
         count++;
      }
      if (cursor != null && cursor != curr) {
         return false;
      }
      if (cursor != null && pred != precursor) {
         return false;
      }
      while (curr != null && curr != tail) {
         curr = curr.getLink();
         count++;
         
      }
      if (curr != tail) {
         return false;
      }
      while (curr != null) {
         curr = curr.getLink();
         count++;
      }
      if (count != manyNodes) {
         return false;
      }
      return true;
   }

   /**
   * Initialize an empty sequence.
   * @param - none
   * @postcondition:
   *   This sequence is empty.
   **/   
   public LinkedSeq( ) {
       head = null;
       tail = null;
       cursor = null;
       precursor = null;
       manyNodes = 0;
   }
    
 
   /**
   * Add a new element to this sequence, after the current element. 
   * @param element
   *   the new element that is being added
   * @postcondition:
   *   A new copy of the element has been added to this sequence. If there was
   *   a current element, then the new element is placed after the current
   *   element. If there was no current element, then the new element is placed
   *   at the end of the sequence. In all cases, the new element becomes the
   *   new current element of this sequence. 
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for a new node.
   **/
   public void addAfter(E element) {
	   if (isCurrent()) {
		   if (cursor == tail) {
			   precursor = tail;
			   cursor = new Node<E>(element, null);
			   tail.setLink(cursor);
			   tail = cursor;
		   }
		   else {
			   precursor = cursor;
			   cursor = new Node<E>(element, cursor.getLink());
			   precursor.setLink(cursor);
		   }
	   }
	   else {
		   if (manyNodes == 0) {
			   tail = new Node<E>(element, null);
			   head = tail;
			   cursor = tail;
		   }
		   else {
			   precursor = tail;
			   tail = new Node<E>(element, null);
			   precursor.setLink(tail);
			   cursor = tail;
		   }
	   }
	   manyNodes ++;
   }


   /**
   * Add a new element to this sequence, before the current element. 
   * @param element
   *   the new element that is being added
   * @postcondition:
   *   A new copy of the element has been added to this sequence. If there was
   *   a current element, then the new element is placed before the current
   *   element. If there was no current element, then the new element is placed
   *   at the start of the sequence. In all cases, the new element becomes the
   *   new current element of this sequence. 
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for a new node.
   **/
   public void addBefore(E element) {
	   if (isCurrent()) {
		   if (cursor == head) {
			   head = new Node<E>(element, head);
			   cursor = head;
			   precursor = null;
		   }
		   else {
			   cursor = new Node<E>(element, cursor);
			   precursor.setLink(cursor);
		   }
	   }
	   else {
		   head = new Node<E>(element, head);
		   cursor = head;
		   precursor = null;
	   }
	   if (manyNodes == 0) {
		   tail = head;
	   }
	   manyNodes ++;
   }
   
   
   /**
   * Place the contents of another sequence at the end of this sequence.
   * @param addend
   *   a sequence whose contents will be placed at the end of this sequence
   * @precondition:
   *   The parameter, addend, is not null. 
   * @postcondition:
   *   The elements from addend have been placed at the end of 
   *   this sequence. The current element of this sequence remains where it 
   *   was, and the addend is also unchanged.
   * @exception NullPointerException
   *   Indicates that addend is null. 
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to increase the size of this sequence.
   **/
   public void addAll(LinkedSeq<E> addend) {
	   if (addend == null) {
		   throw new NullPointerException("Addend cannot be null");
	   }
	   else {
		   LinkedSeq<E> addendClone = addend.clone();
		   if (manyNodes == 0 && addendClone.manyNodes > 0) {
			   head = addendClone.head;
			   tail = addendClone.tail;
			   manyNodes = addendClone.manyNodes;
		   }
		   else {
			   for (Node<E> addendCursor = addendClone.head; addendCursor != null; addendCursor = addendCursor.getLink()) {
				   tail.setLink(new Node<E>(addendCursor.getData(), null));
				   tail = tail.getLink();
			   }
			   manyNodes += addendClone.manyNodes;
		   }
	   }
   }   
   
   
   /**
   * Move forward, so that the current element is now the next element in
   * this sequence.
   * @param - none
   * @precondition:
   *   isCurrent() returns true. 
   * @postcondition:
   *   If the current element was already the end element of this sequence 
   *   (with nothing after it), then there is no longer any current element. 
   *   Otherwise, the new element is the element immediately after the 
   *   original current element.
   * @exception IllegalStateException
   *   Indicates that there is no current element, so 
   *   advance may not be called.
   **/
   public void advance( ) {
	   if (!isCurrent()) {
		   throw new IllegalStateException("There is no current element");
	   }
	   else if (cursor == tail) {
		   cursor = null;
		   precursor = null;
	   }
	   else {
		   precursor = cursor;
		   cursor = cursor.getLink();
	   }
   }
   
   
   /**
   * Generate a copy of this sequence.
   * @param - none
   * @return
   *   The return value is a copy of this sequence. Subsequent changes to the
   *   copy will not affect the original, nor vice versa. Note that the return
   *   value must be type cast to a LinkedSeq before it can be used.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for creating the clone.
   * @note
   *   Refer to discussion on p. 228 of text; there's substantial extra work
   *   In the generic implementation, you will get an unavoidable warning on
   *            answer = (LinkedSeq<E>) super.clone( ); 
   *   That's okay. The cure is worse than the disease.
   **/ 
   @SuppressWarnings("unchecked")
   public LinkedSeq<E> clone( ) {   // DO NOT CHANGE THIS METHOD
      LinkedSeq<E> answer;

      try {
         answer = (LinkedSeq<E>) super.clone( );
      }
      catch (CloneNotSupportedException e)
      { 
         throw new RuntimeException ("This class does not implement Cloneable");
      }

      if (head != null) {
         answer.head  = new Node<E>(head.getData(), null);
         if (cursor == head) {
            answer.cursor = answer.head;
         }
         if (precursor == head) {
            answer.precursor = answer.head;
         }
         
         Node<E> thisPtr = head.getLink();
         Node<E> answerPtr = answer.head;
         while (thisPtr != null){
            answerPtr.setLink(new Node<E>(thisPtr.getData(), null));
            answerPtr = answerPtr.getLink( );
            if (cursor == thisPtr) {
               answer.cursor = answerPtr;
            }
            if (precursor == thisPtr) {
               answer.precursor = answerPtr;
            }
            thisPtr = thisPtr.getLink( );
         }
         answer.tail = answerPtr;
      }
      return answer;
   }
   

   /**
   * Create a new sequence that contains all the elements from one sequence
   * followed by the other.
   * @param s1
   *   the first of two sequences
   * @param s2
   *   the second of two sequences
   * @precondition:
   *   Neither s1 nor s2 is null.
   * @return
   *   a new sequence that has the elements of s1 followed by the
   *   elements of s2 (with no current element)
   * @exception NullPointerException.
   *   Indicates that one of the arguments is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for the new sequence.
   **/   
   public static <E> LinkedSeq<E> concatenation(LinkedSeq<E> s1, LinkedSeq<E> s2) {
	   if (s1 == null || s2 == null) {
		   throw new NullPointerException("One of the arguments is null");
	   }
       LinkedSeq<E> newSeq = new LinkedSeq<E>();
       newSeq.addAll(s1);
       newSeq.addAll(s2);
       return newSeq;
   }


   /**
   * Accessor method to get the current element of this sequence. 
   * @param - none
   * @precondition:
   *   isCurrent() returns true.
   * @return
   *   the current element of this sequence
   * @exception IllegalStateException
   *   Indicates that there is no current element, so 
   *   getCurrent may not be called.
   **/
   public E getCurrent( ) {
	   if (!isCurrent()) {
		   throw new IllegalStateException("There is no current element");
	   }
	   return cursor.getData();
   }


   /**
   * Accessor method to determine whether this sequence has a specified 
   * current element that can be retrieved with the getCurrent method. 
   * @param - none
   * @return
   *   true (there is a current element) 
   *   or false (there is no current element at the moment)
   **/
   public boolean isCurrent( ) {
       return cursor != null;
   }
              
   /**
   * Remove the current element from this sequence.
   * @param - none
   * @precondition:
   *   isCurrent() returns true.
   * @postcondition:
   *   The current element has been removed from this sequence, and the 
   *   following element (if there is one) is now the new current element. 
   *   If there was no following element, then there is now no current 
   *   element.
   * @exception IllegalStateException
   *   Indicates that there is no current element, so 
   *   removeCurrent may not be called. 
   **/
   public void removeCurrent( ) {
	   if (!isCurrent()) {
		   throw new IllegalStateException("There is no current element");
	   }
	   else {
		   if (cursor == head) {
			   head = head.getLink();
			   cursor = head;
		   }
		   else if (cursor == tail) {
			   tail = precursor;
			   tail.setLink(null);
			   cursor = null;
			   precursor = null;
		   }
		   else {
			   cursor = cursor.getLink();
			   precursor.setLink(cursor);
		   }
	   }
	   if (manyNodes == 1) {
		   head = null;
		   tail = null;
		   cursor = null;
		   precursor = null;
		   
	   }
	   manyNodes --;
   }
                 
   
   /**
   * Determine the number of elements in this sequence.
   * @param - none
   * @return
   *   the number of elements in this sequence
   **/ 
   public int size( ) {
       return manyNodes;
   }
   
   
   /**
   * Set the current element at the front of this sequence.
   * @param - none
   * @postcondition:
   *   The front element of this sequence is now the current element (but 
   *   if this sequence has no elements at all, then there is no current 
   *   element).
   **/ 
   public void start( ) {
      cursor = head;
      precursor = null;
   }

   /**
    * Provide a string representation of the sequence with current item 
    * in parentheses
    * @param - none
    * @postcondition string representation returned but sequence is unchanged
    * @return string displaying sequence 
    **/
   public String toString( ) {  // DO NOT CHANGE THIS METHOD
      String answer = "";
      Node<E> current;

      for (current = head; current != null; current = current.getLink( )) {
         if (current == cursor) {
            answer += "(" + current.getData( ) + ") ";
         } else {
            answer += current.getData( ) + " ";                   
         }
      }
      return answer;
   }
 
}
           
