package application;

import java.util.Stack;

/* Basic node element that is used by the linked list*/
class StackNode {
	String data; //the node stored value
	StackNode next; //pointing to next node
	
	//Default constructor
	public StackNode()
	{
		
	}
	
	//Constructor with data value assigned
	public StackNode(String value)
	{
		data = value;
	}
	

	//Constructor with data value and next node assigned
	public StackNode(String value, StackNode next)
	{
		data = value;
		this.next = next;
	}
	
	//Basic setters and getters
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public StackNode getNext() {
		return next;
	}

	public void setNext(StackNode next) {
		this.next = next;
	}


	
}


/* MyStack class is used to store string into the stack
 * According to the stack logic, you need to implement the function members of MyStack based on the linked list structure
 * */
public class MyStack{
	 private StackNode top_node; //pointing to the first node
	 

	//Default constructor
	 public MyStack()
	 {
		 top_node = null; //by default, the node is empty.
	 }

	//Constructor with the first node
	public MyStack(StackNode node) {
		top_node = node;
	}
	
	//check if the stack is empty
	public boolean isEmpty()
	{
		if(top_node == null)
			return true;
		else
			return false;
	}
	
	//clear the stack
	public void clear()
	{
		top_node = null;
	}
	
	
	//A general push() method for stack
	public void pushNode(StackNode node)
	{
		if(top_node == null)
		{
			top_node = node;
		}
		else
		{
			node.setNext(top_node);
			top_node = node;
		}
	}
	
	
	//a specific push method for this calculator project uses only 
	public void push(StackNode node)
	{
		//if there is no any element in the stack, the calculator only accepts numbers or "-"
		if(top_node == null)
		{
			if(node.getData().matches("^[0-9]+$") || node.getData().equals("-"))
				top_node = node;
		}
		else if(node.getData().matches("^[0-9]+$")) //if the inserted node is a number
		{
			node.setNext(top_node);
			top_node = node;
		}
		else //if the inserted node is "+", "-", "*"
		{
			if(top_node.getData().matches("^[0-9]+$")) //if the recently inserted node is a number, then just insert the "node" straight away
			{
				node.setNext(top_node);
				top_node = node;
			}
			else //if recently inserted node is an operator, e.g. "+", "-", "*", then replace its value by the "node" value
			{
				if(top_node.getNext() != null )
					top_node.setData(node.getData());
			}
		}
		
	}
	
	//remove the most recently inserted node
	public void pop()
	{
		if(top_node != null)
		{
			top_node = top_node.getNext();
		}
		else
		{
			System.out.println("The stack is already empty");
		}
		
	}
	

	//get recently inserted node
	public StackNode getTop() {
		if(top_node == null)
		{
			System.out.println("The stack is empty");
		}
		else
		{
			return top_node;
		}
		
		return null;
	}
	
	
	
	//get and remove the most recently inserted node
	public StackNode getTopandPop() {
		if(top_node == null)
		{
			System.out.println("The stack is empty");
		}
		else
		{
			StackNode temp = top_node;
			top_node = top_node.getNext();
			return temp;
		}
		
		return null;
	}
	
	//This function will all the stored strings connected and return it as a single string
	public String getAllNodeValues()
	{
		StringBuilder all_strings = new StringBuilder(); //used to store all the strings from the stack
	
		int i = 0;
		StackNode temp = top_node;
		while(temp != null)
		{
			all_strings.append(temp.getData());
			temp = temp.getNext();
		}
		all_strings.reverse();
		return all_strings.toString();
	}
	
	
	/*This function will to implement the "=" key that process the expression entered by users and calculates a final number. 
	  In addition to the calculation, the final number needs to be converted into a string format and output to the display.
	  So there are five basic steps involved below. 
	  Steps 1, 4, 5 are already completed. Your tasks will focus on Step 2 and Step 3 
	  */
	public void computeExp()
	{
		String exp = getAllNodeValues(); //get the current expression
		System.out.println("exp :::::: "+exp);
		//Step 1: convert a string into an infix queue
		MyQueue infix_queue = getInfixFromString(getAllNodeValues());
		System.out.println("infix_queue::::::"+ infix_queue);
		//Step 2: convert an infix queue into an postfix queue
		MyQueue postfix_queue = infix2postfix(infix_queue);
		System.out.println("postfix_queue::::::"+ postfix_queue);
		//Step 3: Compute the final value from the postfix queue
		String final_value = processPostfix(postfix_queue);
		System.out.println("final_value::::::"+ final_value);
		//Step 4: Clear the stack
		this.clear();
		
		//Step 5: put the final_value into the stack
		for(int i = 0; i < final_value.length(); i++)
			this.pushNode(new StackNode(final_value.substring(i, i + 1)));
		
	}
	
	
	/* Generate an infix expression according to an input String 
	 * The infix expression is stored in a MyQueue variable, which is the returned value of the function */
	private MyQueue getInfixFromString(String exp)
	{
		//Declare queue to store infix
		MyQueue infix_queue = new MyQueue(); //used as a temporary holder that extract operator and operand from exp
		
		//Check if exp has at least one character
		if (exp.length() < 1)
			return infix_queue;
		
		// Check the first character if it is a negative sign
		int j = 0;
		if (exp.substring(0, 1).equals("-")) {
			System.out.println("checking '-'");
			j = 1;
		}

		// Check the last character if it is an operator, just drop it
		if (exp.substring(exp.length() - 1, exp.length()).equals("+")
				|| exp.substring(exp.length() - 1, exp.length()).equals("-")
				|| exp.substring(exp.length() - 1, exp.length()).equals("*")) {
			exp = exp.substring(0, exp.length() - 1);
		}

		// Traverse all the characters and push an operand or operator into
		// infix_queue
		for (int i = j; i < exp.length(); i++) {
			
			String character = exp.substring(i, i + 1);
			System.out.println("Character :::::::"+character);
			// If current character is an operator, then just push it to the
			// stack and move to the next one
			if (character.equals("+") || character.equals("*") || character.equals("-")) {
				System.out.println("If current character is an operator, then just push it to the stack and move to the next one");
				infix_queue.enqueue(character);
			} else // If the current character is a number, it is an operand
			{
				System.out.println("If the current character is a number, it is an operand");
				StringBuilder builder = new StringBuilder();
				if (j == 1 && i == 1)
					builder.append("-");

				builder.append(character);

				i++; // let the cursor moves one step forward
				if (i >= exp.length()) // check whether this move causes out of
										// range
				{
					infix_queue.enqueue(builder.toString());
					break;
				}

				while (exp.substring(i, i + 1).matches("^[0-9]+$")) {
					builder.append(exp.substring(i, i + 1));

					i++;
					if (i >= exp.length()) // check whether this move causes out
											// of range
						break;
				}
				infix_queue.enqueue(builder.toString());
				i--; // let the cursor moves one step back as at the end of the
						// for-loop i++ automatically
			}
		}
		//insert testing code here
		/*MyQueue copy_infix_queue = new MyQueue(); 
		while(!infix_queue.isEmpty())
		{
		String token = (String)infix_queue.dequeue();
		System.out.println(token);
		copy_infix_queue.enqueue(token);
		}
		infix_queue = copy_infix_queue;*/

		return infix_queue;
	}
	
	
	/*Convert an input infix queue into A postfix queue, which is the output of the function */
	private MyQueue infix2postfix(MyQueue infix_queue)
	{
		//Implementation here...

		 /*return	postfix_queue;*/
	
		//Below is just a start, you need to fill the values for postfix_queue
		
		//Declare a queue to store postfix 
		
		
		
		
		MyQueue postfix_queue = new MyQueue(); 
		Stack<String> stack = new Stack<String>();
		
		while(!infix_queue.isEmpty()){
			String token = (String)infix_queue.dequeue();
			if(Prec(token)=='2'){
				postfix_queue.enqueue(token);
				
			}
			else {
				if(stack.isEmpty()&&token!="-") {
					stack.push(token);
				}
				else {
					while(!stack.isEmpty()&&Prec(token)<=Prec(stack.peek()))
						{
							postfix_queue.enqueue(stack.peek());
							stack.pop();
						}
					stack.push(token);
				}
		}
			
	}
	while(!stack.isEmpty()) {
		postfix_queue.enqueue(stack.pop());
	}
	
	//insert testing code here
	MyQueue copy_postfix_queue = new MyQueue();
	while(!postfix_queue.isEmpty())
	{
	String token = (String) postfix_queue.dequeue();
	System.out.println(token);
	copy_postfix_queue.enqueue(token);
	}
	postfix_queue = copy_postfix_queue;
	return postfix_queue;
	
}
	int Prec(String s)
    {
        switch (s)
        {
        case "+":
        case "-":
            return 0;
      
        case "*":
            return 1;
            
       default:
        	return 2;
    }

		
	       
}

	/* Process the postfix expression to compute the final value */
	private String processPostfix(MyQueue postfix)
	{
		//Implementation here...
		//Below is just a start, you need to fill the values for final_value
		
		String final_value = ""; 
		Stack<Integer> stack = new Stack<Integer>();
		while(!postfix.isEmpty()) {
			String str = (String)postfix.dequeue();
			System.out.println("STR :::::" +str+" "+str.length());
			if(str.matches("^[0-9]+$")) {
				System.out.println("Enter into if");
				int a= Integer.parseInt(str);
				int var=stack.push(a);
			}
			else if(str.contains("-")&&str.length()>1) {
				int a=Integer.parseInt(str);
				int var=stack.push(a);
			}
			else {
				System.out.println("Enter into else");
				int b=stack.pop();
				int a=stack.pop();
				switch(str) {
				case"+":
					stack.push(a+b);
					break;
				case"-":
					stack.push(a-b);
					break;
				case"*":
					stack.push(a*b);
					break;
				
				}
			}
		}
		
		
		return final_value=Integer.toString(stack.pop());
	}
	
	
}

