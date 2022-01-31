import java.util.*;
import java.util.LinkedList;
import java.util.Queue;
class Node{
    int id;
    int que_id;
    int arrival_time;
    Node left;
    Node right;
    int numb_burg;
    int cust_state;
    int height;
    int que_length;
    int delivery_time;
    int removal_time;
    Node(int id, int que_id, int arrive, int numb_burg, int cust_state){
        this.id=id;
        this.que_id=que_id;
        this.arrival_time=arrive;
        this.numb_burg=numb_burg;
        this.cust_state=cust_state;
        this.height=1;
    }
    Node(int que_id, int que_length){
        this.que_id=que_id;
        this.que_length=que_length;
    }
    Node(int que_id, int id, int delivery_time){
        this.que_id=que_id;
        this.id=id;
        this.delivery_time=delivery_time;
    }
    Node(int removal_time){
        this.removal_time=removal_time;
    }
}
class AVLtree {
	int height(Node x){
		if(x==null){
			return 0;
		}
		return x.height;
	}
	int height_difference(Node x){
		if(x==null){
			return 0;
		}
		return height(x.left)-height(x.right);
	}
	int max_height(int x,int y){
		if(x>y){
			return x;
		}
		else{
			return y;
		}
	}
	Node left_rotation(Node x){
		Node y=x.right;
		Node T=y.left;
		y.left=x;
        x.right=T;
		x.height=max_height(height(x.left),height(x.right))+1;
		y.height=max_height(height(y.left),height(y.right))+1;
		return y;
	}
	Node right_rotation(Node x){
        Node y=x.left;
		Node T=y.right;
        y.right=x;
		x.left=T;
		x.height=max_height(height(x.left),height(x.right))+1;
		y.height=max_height(height(y.left),height(y.right))+1;
		return y;
	}
	Node search_helper(Node x, int key2){
		Node temp=search(x, key2);
		return temp;
	}
	Node search(Node x, int key2){
        if(x==null){
            return x;
        }
		if(x.id==key2){
			return x;
		}
		if(x.id>key2){
			return search(x.left, key2);
		}
		if(x.id<key2){
			return search(x.right, key2);
		}
        return x;
	}
	Node insert(Node x, Node temp){
        if(x==null){
		    return temp;
		}
		else if(x.id>temp.id){
			x.left=insert(x.left,temp);
		}
		else if(x.id<temp.id){
			x.right=insert(x.right,temp);
		}
		x.height=max_height(height(x.left),height(x.right))+1;
		int difference=height_difference(x);
		if(difference>1 && x.left.id>temp.id){
			return right_rotation(x);
		}
		if(difference>1 && x.left.id<temp.id){
			x.left=left_rotation(x.left);
			return right_rotation(x);
		}
		if(difference<-1 && x.right.id<temp.id){
			return left_rotation(x);
		}
		if(difference<-1 && x.right.id>temp.id){
			x.right=right_rotation(x.right);
			return left_rotation(x);
		}
		return x;
	}
	
}
class MinHeap{
    Node [] Heap;
    int K;
    MinHeap(int k){
        K=k;
        Heap = new Node[K+1];
        Node temp=new Node(-10,-10);
        Heap[0]=temp;
        for(int i=1; i<K+1; i++){
            Heap[i]=new Node(i, 0);
        }
    }
    int smallest_que(){
        return Heap[1].que_id;
    }
    int findmin(int i, int j){
        if(Heap[i].que_length>Heap[j].que_length){
            return j;
        }
        else if(Heap[i].que_length<Heap[j].que_length){
            return i;
        }
        else{
            if(Heap[i].que_id>Heap[j].que_id){
                return j;
            }
            else{
                return i;
            }
        }
    }
    int search(int que_id){
        int j=0;
        for(int i=1; i<K+1; i++){
            if(Heap[i].que_id==que_id){
                j=i;
                break;
            }

        }
        return j;

    }
    int parent(int i){
        i=i/2;
        return (int) i;
    }
    void increase(){
        Heap[1].que_length+=1;
        percolate_down(1);
        return;
    }
    void decrease(int que_id){
        int i=search(que_id);
        Heap[i].que_length+=-1;
        percolate_up(i);
        return;
    }
    void percolate_down(int i){
        if(K<2*i){
            return;
        }
        if(K==2*i){
            if(Heap[i].que_length>Heap[2*i].que_length){
                 swap(i,2*i);
                 return;
            }
            else if(Heap[i].que_length==Heap[2*i].que_length){
                if(Heap[i].que_id>Heap[2*i].que_id){
                    swap(i,2*i);
                    return;
                }
                else{
                    return;
                }
            }
            else{
                return;
            }
        }
        if(K>=2*i+1){
            int o=findmin(2*i, 2*i+1);
            if(Heap[i].que_length>Heap[o].que_length){
                swap(i,o);
                percolate_down(o);
            }
            else{
                if(Heap[i].que_length==Heap[o].que_length & Heap[i].que_id>Heap[o].que_id){
                    swap(i,o);
                    percolate_down(o);
                }
                else{
                    return;
                }
            }
        }

    }
    void swap(int i, int j){
        Node temp=Heap[i];
        Heap[i]=Heap[j];
        Heap[j]=temp;
    }
    void percolate_up(int i){
        int j=parent(i);
        if(j==0){
            return;
        }
        else if(Heap[j].que_length>Heap[i].que_length){
            swap(i,j);
            percolate_up(j);
        }
        else if(Heap[j].que_length==Heap[i].que_length){
            if(Heap[j].que_id>Heap[i].que_id){
                swap(i,j);
                percolate_up(j);
            }
            else{
                return;
            }
        }
        else{
            return;
        }
    }
}
public class MMBurgers implements MMBurgersInterface {
    int K;
    int M;
    Queue <Node> [] list;
    Queue <Node> girddle=new LinkedList<Node>();
    Queue <Node> cooking= new LinkedList<Node>();
    Queue <Node> delivery= new LinkedList<Node>();
    Node root;
    AVLtree tree1;
    MinHeap mn;
    int Numb_burg=0;
    int current_time=0;
    float sum=0;
    int n=0;
    MMBurgers(){
        tree1=new AVLtree();
    }
    public void runSimulation(int t){
        int r= current_time+1;
        while(r<=t){
            while(true){
                Node temp=delivery.peek();
                if(temp!=null){
                    if(r==temp.removal_time){
                        delivery.remove();
                    }
                    else{
                        break;
                    }
                }
                else{
                    break;
                }
            }
            for(int i=K-1; i>=0; i--){
                Node temp= list[i].peek();
             if(temp!=null){
                int diff= r-temp.arrival_time;
                if(diff%(i+1)==0){
                    list[i].remove();
                    mn.decrease(temp.que_id);
                    temp.cust_state=K+1;
                    cooking.add(temp);
                    Numb_burg=Numb_burg+temp.numb_burg;
                }
               }

            }
            while(true){
                Node temp=girddle.peek();
             if(temp!=null){
                if(r==temp.delivery_time){
                    Node q=girddle.remove();
                    Node g= new Node(r+1);
                    delivery.add(g);
                }
                else{
                    break;
                }
               }
             else{
                 break;
             }
            }
            int differ=M-girddle.size();
            while(true){
                Node temp=cooking.peek();
              if(temp!=null){
                int burg=temp.numb_burg;
                if(burg>differ){
                    temp.numb_burg=burg-differ;
                    Numb_burg-=differ;
                    for(int j=0; j<differ; j++){
                        Node z= new Node(temp.que_id, temp.id, r+10);
                        girddle.add(z);
                    }
                    break;
                }
                else if(burg==differ){
                    temp.numb_burg=0;
                    Numb_burg-=differ;
                    temp.delivery_time=r+11;
                    for(int j=0; j<differ; j++){
                        Node v= new Node(temp.que_id, temp.id, temp.delivery_time-1);
                        girddle.add(v);
                    }
                    cooking.remove();
                    break;
                }
                else{
                 if(burg!=0){
                    for(int j=0; j<burg; j++){
                        Node m= new Node(temp.que_id, temp.id, r+10);
                        girddle.add(m);
                    }
                    temp.delivery_time=r+11;
                    temp.numb_burg=0;
                    Numb_burg-=burg;
                    cooking.remove();
                    differ=M-girddle.size();
                }
                 else{
                    break;
                 }
                }
             }
             else{
                 break;
             }
            }
            r++;
                
            }
        current_time=t;
        return;

    }
    public boolean isEmpty(){
        //your implementation
        if(girddle.size()==0 && cooking.size()==0 && delivery.size()==0){
            for(int i=0; i<K; i++){
                if(list[i].size()!=0){
                    return false;
                }
            }
            return true;
        }
       return false;
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 
    
    public void setK(int k) throws IllegalNumberException{
        //your implementation
        if(k<=0){
            throw new IllegalNumberException("");
        }
        K=k;
        mn=new MinHeap(K);
        list = new Queue[K]; 
        for(int i = 0; i <K; i++){
             list[i] = new LinkedList<Node>(); 
          }
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    }   
    
    public void setM(int m) throws IllegalNumberException{
        //your implementation
        if(m<=0){
            throw new IllegalNumberException("");
          }
        M=m;
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public void advanceTime(int t) throws IllegalNumberException{
        //your implementation
        if(t<current_time){
            throw new IllegalNumberException("");
        }
        runSimulation(t);
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException{
        //your implementation
        if(id<=0 || t<current_time || numb<=0){
            throw new IllegalNumberException("");
        }
        runSimulation(t);
        int n=mn.smallest_que();
        Node temp= new Node(id, n, t, numb, n);
        mn.increase();
        root=tree1.insert(root,temp);
        list[n-1].add(temp);
        
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public int customerState(int id, int t) throws IllegalNumberException{
        //your implementation
        if(id<=0 || t<current_time){
            throw new IllegalNumberException("");
        }
        runSimulation(t);
        Node h= tree1.search_helper(root, id);
        if(h==null){
            return 0;
        }
        if(h.delivery_time!=0){
           if(h.delivery_time<=t){
               h.cust_state=K+2;
            }
        }
        return h.cust_state;
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public int griddleState(int t) throws IllegalNumberException{
        //your implementation
        if(t<current_time){
            throw new IllegalNumberException("");
        }
        runSimulation(t);
        return girddle.size();
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public int griddleWait(int t) throws IllegalNumberException{
        //your implementation
        if(t<current_time){
            throw new IllegalNumberException("");
        }
        runSimulation(t);
        return Numb_burg;
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public int customerWaitTime(int id) throws IllegalNumberException{
        //your implementation
        Node n= tree1.search_helper(root, id);
        if(n==null){
            throw new IllegalNumberException("");
        }
        return n.delivery_time-n.arrival_time;
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 
    public void preorder(Node root){
        if (root == null)
            return;
 
        /* first recur on left child */
        sum=sum+root.delivery_time-root.arrival_time;
        n+=1;
        preorder(root.left);
        preorder(root.right);
    }

	public float avgWaitTime(){
        //your implementation
        preorder(root);
        return sum/n;

	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    
}
