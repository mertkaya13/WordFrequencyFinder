public class CircularDoublyLinkedList<E extends Comparable<E>>{

  private Node<E> header;
  private static class Node<E> {
    private E element;
    private int frequency;
    private Node<E> prev;       
    private Node<E> next;            
    
    public Node(E e,int f, Node<E> p, Node<E> n) {
      element = e;
      frequency = f;
      prev = p;
      next = n;
    }

    public E getElement() { 
      return element; 
    }

    public int getFrequency() { 
      return frequency; 
    }

    public Node<E> getPrev() { return prev; }

    public Node<E> getNext() { return next; }

    public void setPrev(Node<E> p) { prev = p; }

    public void setNext(Node<E> n) { next = n; }
  } 

  private int size = 0;   // number of elements in the list


  public CircularDoublyLinkedList() {
    header = null;
  }
  public int size() { return size; }

  public boolean isEmpty() { return size == 0; }

  public E first() {
    if (isEmpty()) return null;
    return header.getElement();   // header has first element
  }
  public E last() {
    if (isEmpty()) return null;
    return header.getPrev().getElement();    // last element is before trailer
  }

  public void addFirst(E e) {  // makes it header
    Node<E> newNode ;
  
    if(isEmpty()){
  
      newNode = new Node<E>(e,1,null,null);
      newNode.setNext(newNode);
      newNode.setPrev(newNode);
      header = newNode;
    
      size++; 
      return;
  
    }
  
    newNode = new Node<E>(e,1,header.getPrev(),header);
    header.getPrev().setNext(newNode);
    header.setPrev(newNode);
    header = newNode;
    size++;
        
  }

  public void addLast(E e) {
    Node<E> newNode;
    if(isEmpty()){
    
      newNode = new Node<E>(e,1,null,null);
      newNode.setNext(newNode);
      newNode.setPrev(newNode);
      header = newNode;
      size++;
      return;
    }
    newNode = new Node<E>(e,1, header.getPrev(), header);
    header.getPrev().setNext(newNode);
    header.setPrev(newNode);
    size++;
    // place just before the header
  }


  public E removeFirst() {
    if (isEmpty()) return null;                  // nothing to remove
    return remove(header);             // first element is beyond header
  }

  public E removeLast() {
    if (isEmpty()) return null;                     // nothing to remove
    
    if(size == 1)
      remove(header);
 
    return remove(header.getPrev());            // last element is before header
  }

  private void addBetween(E e,int f, Node<E> predecessor, Node<E> successor) {
  
    // create and link a new node
    Node<E> newest = new Node<E>(e,f, predecessor, successor);
    predecessor.setNext(newest);
    successor.setPrev(newest);
    size++;
  
  }

  private E remove(Node<E> node) {
   if(size == 1){ //node == header?
     header = null;
      size--;
      return node.getElement();
    }
    Node<E> predecessor = node.getPrev();
    Node<E> successor = node.getNext();
    predecessor.setNext(successor);
    successor.setPrev(predecessor);
    if(node == header)
    header = header.getNext();
    size--;
    return node.getElement();
  }


  public String toString() {

    Node<E> walk = null;
    StringBuilder sb = new StringBuilder("(");
   
    if(!isEmpty()){
    
      sb.append(header.getElement());
      sb.append(" ");
      sb.append(header.getFrequency());
	 
	    if(size != 1)
        sb.append(", ");
		
      walk = header.getNext(); // if empty would throw nullPointerException
    
    }
  
    while (walk != header && walk != null ) {

      sb.append(walk.getElement());
      sb.append(" ");
      sb.append(walk.getFrequency());

      if(walk.getNext() != header)
        sb.append(", ");
      
      walk = walk.getNext();

    }
    sb.append(")");
    return sb.toString();
  }

  private Node<E> getTargetNode(E target){
    
    Node<E> walk = header;


	do {
    
    if(walk.getElement().equals(target)){
			return walk;
		}
    
    walk = walk.getNext();
    
	}while(walk != header);
    return null;
  }

  public E removeTarget(E target){
  
    Node<E> removed = getTargetNode(target);
    if(removed == null)
      return null;
    else
      return remove(removed);

  }

  protected boolean isThere(String element){
    if(isEmpty())
      return false;
    if(size == 1)
      return header.getElement().equals(element);


    Node<E> walk = header.getNext();
  
    if(header.getElement().equals(element)){
      return true;
    }

    while(walk != header){
      if(walk.getElement().equals(element)){
        return true;
      }
      walk = walk.getNext();
    }
    return false;
  }

  //adds nextNode to the previous position of prevNode
  protected void addToPos(Node<E> prevNode , Node<E> nextNode){
    
    //getting nextNode out of Position
    nextNode.getPrev().setNext(nextNode.getNext());
    nextNode.getNext().setPrev(nextNode.getPrev());
    
    //putting our node into position
    prevNode.getPrev().setNext(nextNode);
    nextNode.setPrev(prevNode.getPrev());
    prevNode.setPrev(nextNode);
    nextNode.setNext(prevNode);
    if (header == prevNode)
      header = nextNode;

  }

  protected void addFreq(E element){

    Node<E> targetNode = getTargetNode(element);
    targetNode.frequency++;
    correctPos(targetNode);
  
  }

  protected void correctPos(Node<E> node){

    Node<E> walk = node.getPrev(); //insertion sort idea
    Node<E> hold = null; //lowest lexiographic
    
	  while(walk != header.getPrev()){
    
      if(node.getFrequency() > walk.getFrequency()){
        hold = walk;
      }

      if(node.getFrequency() == walk.getFrequency()){

        if(node.getElement().compareTo(walk.getElement()) < 0){
          hold = walk; //holding the same freq but lower lexiographic      
        }
    
      }


      //in first comparison it must have a value if it is not biggest
      if(hold == null) 
        return;

      walk = walk.getPrev();

      
    }

    if(hold != null)
      addToPos(hold,node);

  }


} //End of CDLL class