import java.util.ArrayList;

public class Tree 
{
	Node root;
	public Tree()
	{
		
	}
	
	public boolean insert(int x)
	{
		//If the root is null, create a new node as the root with an int x as its value
		if(root == null)
		{
			root = new Node(x);
			return true;
		}
		return root.add(x);
	}
	
	public int size(int x)
	{
		Node n = root.search(x);
		if(n == null)
		{
			 return 0;
		}
		else
		{
			return n.checkChildren();
		}
	}
	
	public class Node
	{
		ArrayList<Integer> values = new ArrayList<Integer>();
		ArrayList<Node> children = new ArrayList<Node>();
		Node parent;
		
		public Node(int x)
		{
			values.add(x);
		}
		
		public boolean add(int x)
		{
			//If value is found in node, return false
			for(int val : values)
			{
				if(val == x)
				{
					return false;
				}
			}
			//If node is a leaf node, add the value to the node. If it is size 3 after adding, create sub-tree
			if(children.size() == 0)
			{
				addValue(x);
				if(size() == 3)
				{
					createTree();
				}
				return true;
			}
			
			//If it is not a leaf node, recursively call the method on its children
			else
			{
				for(Node n : children)
				{
					if(getValue1() > x && n.getValue2() < getValue1() )
					{
						return n.add(x);
					}
					else if((getValue1() < x && getValue2() > x) && (n.getValue1() > getValue1() && n.getValue2() < getValue2()))
					{
						return n.add(x);
					}
					else if(getValue2() < x && n.getValue1() > getValue2())
					{
						return n.add(x);
					}
				}
			}
			return false;
		}
		
		public void createTree()
		{
			if(this.parent == null)
			{	
				addChild(values.remove(0));
				addChild(values.remove(1));
			}
			else
			{
				//Adds the middle value to the parent
				parent.addValue(values.remove(1));
				parent.addChild(values.remove(0));
				//If the parent has 3 fix the tree
				if(parent.size() == 3)
				{
					parent.fixIntegrity();
				}
			}
		}
		
		//Fixes the integrity of the tree. Used in the case where creating a subtree results in
		//the parent having 3 values.
		public void fixIntegrity()
		{
			//Creates two nodes that contains the left and right values of the parent
			Node node1 = new Node(values.remove(0));
			Node node2 = new Node(values.remove(1));
			
			ArrayList<Node> temp = new ArrayList<Node>();
			
			for(Node n : children)
			{
				if(getValue1() > n.getValue2())
				{
					node1.children.add(n);
					n.parent = node1;
				}
				else if(getValue2() < n.getValue1())
				{
					node2.children.add(n);
					n.parent = node2;
				}
				temp.add(n);
			}
			
			children.removeAll(temp);
			
			children.add(node1);
			node1.parent = this;
			children.add(node2);	
			node2.parent = this;
			
			if(parent != null)
			{
				parent.addValue(values.remove(0));
				parent.children.remove(this);
				for(Node n : children)
				{
					parent.children.add(n);
					n.parent = parent;
				}
				if(parent.values.size() == 3)
				{
					parent.fixIntegrity();
				}
			}
		}
		
		public void addValue(int x)
		{
			//If size is 1, see which value is bigger and add value accordingly
			if(values.size() == 1)
			{
				if(values.get(0) > x)
				{
					int temp = values.get(0);
					values.set(0, x);
					values.add(temp);
				}
				else
				{
					values.add(x);
				}
			}
			//If size is 2, see where it belongs in the array and add the value accordingly
			else if(values.size() == 2)
			{
				int temp1 = values.get(0);
				int temp2 = values.get(1);
				//If value at index 0 is bigger than x and value at index 1 is bigger than x
				//put x at index 0
				if(temp1 > x && temp2 > x)
				{
					values.set(0, x);
					values.set(1, temp1);
					values.add(temp2);
				}
				//If x is between the two values, put x between them
				else if(temp1 < x && temp2 > x)
				{
					values.set(1, x);
					values.add(temp2);
				}
				//If it does not belong in the two previous positions add it to the end of the array
				else
				{
					values.add(x);
				}
			}
		}
		
		public Node search(int x)
		{
			if(getValue1() == x || getValue2() == x)
			{
				return this;
			}
			for(Node n : children)
			{
				if(getValue2() < n.getValue1() && getValue2() < x)
				{
					return n.search(x);
				}
				else if(getValue1() > n.getValue2() && getValue1() > x)
				{
					return n.search(x);
				}
				else if((x > getValue1() && x < getValue2()) && (n.getValue1()>getValue1() && x<getValue2() ))
				{
					return n.search(x);
				}
			}
			return null;
		}
		
		public int checkChildren()
		{
			int size = 0;
			size += values.size();
			if(children.size() > 0)
			{
				for(Node n : children)
				{
					size += n.checkChildren();
				}
			}
			return size;
		}
		
		public int getValue1()
		{
			return values.get(0);
		}
		public int getValue2()
		{
			if(values.size() == 2)
			{
				return values.get(1);
			}
			else
			{
				return values.get(0);
			}
		}
		
		public void addChild(int x)
		{
			Node temp = new Node(x);
			temp.parent = this;
			children.add(temp);
		}
		public int size()
		{
			return values.size();
		}
	}

}
