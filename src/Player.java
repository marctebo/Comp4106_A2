import java.util.ArrayList;
import java.util.Stack;

public class Player {
	public int type,score;
	public static final int SIZE = 8;
	private ArrayList<Piece> reserves;
	private ArrayList<Stack[][]> moves;
	
	public Player(int type){
		this.type = type;
		reserves = new ArrayList<Piece>();
	}
	
	public ArrayList<Stack[][]> getMoves(Stack<Piece>[][] board){
		moves = new ArrayList<Stack[][]>();
		for(int i = 0; i<SIZE;i++){
			for(int j=0; j<SIZE; j++){
				if(board[i][j]!=null){
					Piece temp = (Piece)board[i][j].peek();
					if(temp.getType()!=0){
						//GENERATE MOVES HERE
						if(temp.getType()== this.type){
							for(int k = 1;k<=temp.getScore();k++){
								if(i<SIZE-k){
									if(board[i+k][j]!=null){
										addFullStackMove(board,i,j,i+k,j);
									}
								}
								if(i>=k){
									if(board[i-k][j]!=null){
										addFullStackMove(board,i,j,i-k,j);
									}
								}
								if(j<SIZE-k){
									if(board[i][j+k]!=null){
										addFullStackMove(board,i,j,i,j+k);
									}
								}
								if(j>=k){
									if(board[i][j-k]!=null){
										addFullStackMove(board,i,j,i,j-k);
									}
								}
							}
						}
						//get split moves
					}
				}
			}
		}
		addSplitMoves(board);
		return moves;
	}
	
	public Stack<Piece>[][] copyBoard(Stack<Piece>[][] board){
		Stack<Piece>[][] temp = new Stack[SIZE][SIZE];
		for(int i = 0; i<SIZE;i++){
			for(int j = 0;j<SIZE;j++){
				temp[i][j] = new Stack<Piece>();
			}
		}
		
		temp[0][0] = null;
		temp[0][1] = null;
		temp[1][0] = null;
		temp[0][6] = null;
		temp[0][7] = null;
		temp[1][7] = null;
		temp[6][0] = null;
		temp[7][0] = null;
		temp[7][1] = null;
		temp[6][7] = null;
		temp[7][7] = null;
		temp[7][6] = null;
		
		for(int i = 0; i<SIZE;i++){
			for(int j = 0;j<SIZE;j++){
				if(temp[i][j]!=null){
					for(Piece p: board[i][j]){
						temp[i][j].push(p);
					}
				}
			}
		}
		return temp;
	}
	
	public int getReserves(Stack[][] board){
		int count = 0;
		for(int i = 0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				if(board[i][j]!=null){
					Piece p = (Piece)board[i][j].peek();
					if(p.getScore()>5 && p.getType() == type){
						count+=p.getScore()-5;
					}
				}
			}
		}
		return count;
	}
	
	public int getScore(Stack[][] board){
		score = 0;
		for(int i = 0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				if(board[i][j]!=null){
					Piece p = (Piece)board[i][j].peek();
					if(p.getType() == type){
						score+=p.getScore();
					}
				}
			}
		}
		return score;
	}
	
	
	public void addFullStackMove(Stack<Piece>[][] board, int sx, int sy, int dx, int dy){
		Stack<Piece>[][] fake = copyBoard(board);
		ArrayList<Piece> hold = new ArrayList<>();
		Piece temp1 = new Piece();
		for(int i = 0;i<=fake[sx][sy].size();i++){
			temp1 = (Piece)fake[sx][sy].pop();
			if(temp1.getScore()!=0){
				hold.add(0,temp1);
			}
			else{
				fake[sx][sy].push(temp1);
			}
		}
		Piece temp2 = new Piece();
		for(Piece p: hold){
			temp2.setType(p.getType());
			temp2.setScore(fake[dx][dy].peek().getScore()+1);
			fake[dx][dy].push(temp2);
		}

		if(moves!=null){
			moves.add(fake);
		}
		
	}
	public void moveStack(Stack<Piece>[][] board,ArrayList<Piece> hold, int dx, int dy){
		Stack<Piece>[][] fake = copyBoard(board);
		Piece temp2 = new Piece();
		for(Piece p: hold){
			temp2.setType(p.getType());
			temp2.setScore(fake[dx][dy].peek().getScore()+1);
			fake[dx][dy].push(temp2);
		}

		if(moves!=null){
			moves.add(fake);
		}
	}
	
	public void addSplitMoves(Stack<Piece>[][] board){
		Stack<Piece>[][] fake = copyBoard(board);

		for(int i=0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				if(fake[i][j]!=null){
					if(fake[i][j].peek().getScore()> 1 && fake[i][j].peek().getType()==type){
						for(int k = 1;k<fake[i][j].peek().getScore();k++){
							ArrayList<Piece> hold = new ArrayList<>();
							for(int l = 0; l<k;l++){
								if(fake[i][j].peek().getScore()!=0){
									hold.add(0,fake[i][j].pop());
								}
							}

							for(int m = 1;m<=hold.size();m++){
								if(i<SIZE-m){
									if(fake[i+m][j]!=null){
										moveStack(fake,hold,i+m,j);
									}
								}
								if(i>=m){
									if(fake[i-m][j]!=null){
										moveStack(fake,hold,i-m,j);
									}
								}
								if(j<SIZE-m){
									if(fake[i][j+m]!=null){
										moveStack(fake,hold,i,j+m);
									}
								}
								if(j>=m){
									if(fake[i][j-m]!=null){
										moveStack(fake,hold,i,j-m);
									}
								}
							}
							for(Piece p:hold){
								fake[i][j].push(p);
							}
						}
					}
				}
			}
		}
	}
}
