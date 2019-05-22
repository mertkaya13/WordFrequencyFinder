public class CircularDoublyLinkedList<E extends Comparable<E>>{ 

  private Node<E> header;

  //each node holds prev,next,element and frequency
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


  public CircularDoublyLinkedList() { //no dummy nodes
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
   
    if(isEmpty()){ //if cdll is empty,makes it header.
  
      newNode = new Node<E>(e,1,null,null);
      newNode.setNext(newNode);
      newNode.setPrev(newNode);
      header = newNode;
    
      size++; 
      return;
  
    }

    //Adds To the first position.Which is header position.
    newNode = new Node<E>(e,1,header.getPrev(),header);
    header.getPrev().setNext(newNode);
    header.setPrev(newNode);
    header = newNode;
    size++;
        
  }

  public void addLast(E e) {
    Node<E> newNode;

    //If CDLL is empty makes it header. It is the only node.
    if(isEmpty()){
    
      newNode = new Node<E>(e,1,null,null);
      newNode.setNext(newNode);
      newNode.setPrev(newNode);
      header = newNode;
      size++;
      return;
    }

    /* Headers previous is tail node.
    *Adds to that position
    */
    newNode = new Node<E>(e,1, header.getPrev(), header);
    header.getPrev().setNext(newNode);
    header.setPrev(newNode);
    size++;

    //checks the position because list might have more than one 1 freq. words.
    correctPos(newNode);
  }


  public E removeFirst() {


    if (isEmpty()){return null;} // nothing to remove
    return remove(header);         // first element is beyond header
  }

  public E removeLast() {
    if (isEmpty()) {return null;}    // nothing to remove
    
    if(size == 1) //only element is header
      remove(header);
 
    return remove(header.getPrev()); // last element is before header
  }


  //Adds element between pred. and succ.
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

    //Fixing the prev and next Node.
    Node<E> predecessor = node.getPrev();
    Node<E> successor = node.getNext();
    predecessor.setNext(successor);
    successor.setPrev(predecessor);

    //If it was header. Now next Node takes that place.
    if(node == header)
    header = header.getNext();
    size--;
    return node.getElement();
  
  }//End of remove


  public String toString() {

    Node<E> walk = null;
    StringBuilder sb = new StringBuilder("(");
   

    //Sort of a do-while idea
    if(!isEmpty()){
    
      sb.append(header.getElement());
      sb.append(" ");
      sb.append(header.getFrequency());
	 
	    if(size != 1)
        sb.append(", ");
    
      // if list is empty,will throw nullPointerException
      walk = header.getNext(); 
    
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
  
  }//End of toString


  //iterates through list returns the node which has target element
  private Node<E> getTargetNode(E target){
    if(isEmpty())
      return null;
    
    Node<E> walk = header;


    do { //to check the header
      
      if(walk.getElement().equals(target)){
        return walk;
      }
      
      walk = walk.getNext();
      
    }while(walk != header);
  
      return null;
    }//End of getTargetNode


  //iterates through list removes the node which has target element
  public E removeTarget(E target){
  
    Node<E> removed = getTargetNode(target);
    if(removed == null)
      return null;
    else
      return remove(removed);

  }

  //uses getTargetNode to find if list has target element
  protected boolean isThere(E element){
    Node<E> target = getTargetNode(element); 
    return(target != null); 

  }//End of removeTarget

  /**
   * Adds nextNode to the previous position of prevNode
   */
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

  }//End of addToPos


  /** 
   * Adds frequency to given elements node 
   * Calls correctPos to switch node to correct position
   */
  protected void addFreq(E element){

    Node<E> targetNode = getTargetNode(element);
    if(targetNode == null){
      return; //element is not in the list
    }
    targetNode.frequency++;
    correctPos(targetNode);
  
  }//End of addFreq


  /**
   * Insertion sort idea moving to prev nodes finding the position than calling switch
   */

  protected void correctPos(Node<E> node){

    //Header has the biggest val.
    Node<E> walk = node.getPrev(); 

    //holds the lowest lexiographicly
    Node<E> hold = null; 
    
	  while(walk != header.getPrev()){
    
      //Means : Walk went too much , break for faster result.
      if(node.getFrequency() < walk.getFrequency()){ 
        break;
      }

      //Means : walk might be smaller than node.
      if(node.getFrequency() > walk.getFrequency()){
        hold = walk;
      }

      //Means : Who is bigger lexiographicly.
      if(node.getFrequency() == walk.getFrequency()){

        //Comparing lexiographic order.
        if(node.getElement().compareTo(walk.getElement()) < 0){
          hold = walk; //holding the same freq but lower lexiographic      
        }
    
      }


      //in first comparison it must have a value if it is not biggest
      if(hold == null) {
        return;
      }

      //Moving the walk.
      walk = walk.getPrev();

      
    }

    //It will be null if node is already biggest.No need for a switch.
    if(hold != null) {

      //add node to prev of hold
      addToPos(hold,node); 

    }
  }//End of correctPos



  /**
   * returns the node in given frequency.
   * If it is not in the list returns null
   */
  private Node<E> getFreqNode(int num){

    Node<E> walk = header;
    do{
      if(walk.getFrequency() == num){
        return walk;
      }
      walk = walk.getNext();

    }
    while(walk != header);

    return null;

  }//End of getFreqNode


  //Prints words that are in from times freq - to times freq
  protected void printRange(int from , int to){

    if(isEmpty())
      System.out.println("This range is empty.");

    if(to < 1){
      System.out.println("This range is empty.");
    }

    //Gets the biggest(first) node that holds from times frequency
    Node<E> walk = getFreqNode(from);
    int num = from;


    //If from times is not there try lowering it by 1.
    while( walk == null){

      //If there is no nodes in the range
      if(num > to){
      
        //Search for n-1
        walk = getFreqNode(--num);
        
      }else{
        System.out.println("This range is empty.");
        return;
      }

    }

    //Prints until 'to' freq
    while(walk.getFrequency() >= to){ 

      System.out.println(walk.getElement() +" "+walk.getFrequency() );
      
      //moves to next node
      walk = walk.getNext(); 

      //Preventing infinite looping caused by (to == 1)
      if(walk == header)
        break;
    
    }

  }//End of printRange

  //Prints the frequencys from top 'number' frequency words
  protected void printMax(int number){
    if(isEmpty())
      return;

    if(number > size)
      System.out.println(this);
  
    //header holds the maximum frequency 
    int top = header.getFrequency();

    /**
     * calls printRange.
     * Prints From top
     * until top-number+1
     * +1 means we dont need the one at (top-number)
     * 
    */
    printRange(top,top-number+1); 
  
  }//End of printMax
 
 

  /**
   * Print the N lowest frequency words and their frequencies in the list, 
   * one word per line and
   * sorted lowest to highest. 
   * If N is larger than the number of words in the list, 
   * prints the entire list.
   * 
   */
    protected void printMin(int number){
      if(isEmpty())
      return;

    if(number > size)
      System.out.println(this);
  
    //Lowest freq. aka tail.
    Node<E> walk = header.getPrev(); 

    //number of different elements
    int counter = 0; 

    //to check the header
     while(counter < number){
      
      System.out.println(walk.getElement() +" "+walk.getFrequency() );

      //Moves backwards.
      walk = walk.getPrev();

      //freq =? previous.freq?
      if(walk.getNext().getFrequency() != walk.getFrequency()){
        counter++;
      }

      if(walk == header){ //loops the entire list
        break;  
      }

    }
  
  }//End of printMin


  //Prints given word and frequency of the word.
  protected void printFreq(E word){

    //Gets Node that holds 'word'
    Node<E> target = getTargetNode(word);

    //If word is in list
    if(target != null) {
      System.out.println(target.getFrequency());
    }

  }


  /** 
   * Prints the word in nth position in frequency.
   * number = 1 will print header
   * */
  protected void printNth(int number){

    //Starts from the biggest.
    Node<E> walk = header;
    int counter = 0;

    if(number == 1){
      System.out.println(header.getElement()+" "+header.getFrequency());
      return;
    }


    //Counts frequency
    while(counter != number){
      
      //Is it different.(in terms of frequency)
      if(walk.getPrev().getFrequency() != walk.getFrequency()){
        counter++;
      }

      //Walk = nth in freq
      if(counter == number){
        break;
      }

      //Moves biggest to lowest
      walk = walk.getNext();

    }

    //Print the nth freq words (if more than one) 
    int freq = walk.getFrequency();
    while(walk.getFrequency() == freq){
      
      System.out.println(walk.getElement()+" "+walk.getFrequency());
      walk = walk.getNext();

    }

  }//End of printNth



  /**
   * Remove the N lowest frequency words from the list. 
   * If N is larger than the number of words in the list,
   * clear the entire list.
   */
  protected void truncateList(int number){

        //Lowest freq. aka tail.
        Node<E> walk = header.getPrev(); 

        //number of different elements
        int counter = 0; 
    
        //to check the header
         while(counter < number){
          
          //Holds previous node to not to lost after remove
          Node<E> walkHold = walk.getPrev();
          remove(walk);
          walk = walkHold;

          //freq =? previous.freq?
          if(walk.getNext().getFrequency() != walk.getFrequency()){
            counter++;
          }
    
          if(walk == header){ //loops the entire list
            break;  
          }
    
        }

        //Prints the list
        System.out.println(this);
  }
  


} //End of CDLL class