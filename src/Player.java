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
	
	public ArrayList<Stack[][]> getMoves(Stack[][] board){
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
										addMove(board,i,j,i+k,j);
									}
								}
								if(i>=k){
									if(board[i-k][j]!=null){
										addMove(board,i,j,i-k,j);
									}
								}
								if(j<SIZE-k){
									if(board[i][j+k]!=null){
										addMove(board,i,j,i,j+k);
									}
								}
								if(j>=k){
									if(board[i][j-k]!=null){
										addMove(board,i,j,i,j-k);
									}
								}
							}
						}
					}
				}
			}
		}
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
	public void addMove(Stack[][] board, int sx, int sy, int dx, int dy){
		Stack<Piece>[][] fake = copyBoard(board);
		Piece temp1 = (Piece)fake[sx][sy].pop();
		Piece temp2 = new Piece(this.type);
		temp2.setScore(temp1.getScore()+fake[dx][dy].peek().getScore());
		fake[sx][sy].push(new Piece());
		//temp.setScore(temp.getScore()+1);
		fake[dx][dy].push(temp2);
		if(moves!=null){
			moves.add(fake);
		}
	}
}
