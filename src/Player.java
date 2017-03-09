import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Player {
	public int type,score;
	public static final int SIZE = 8;
	private ArrayList<Piece> reserves;
	private ArrayList<Stack[][]> moves;
	private ArrayList<Stack[][]> executedMoves;
	private Focus focus;
	
	public Player(int type, Focus focus){
		this.type = type;
		this.focus = focus;
		reserves = new ArrayList<Piece>();
		executedMoves = new ArrayList<Stack[][]>();
	}
	
	public ArrayList<Stack[][]> getMoves(Stack<Piece>[][] board){
		moves = new ArrayList<Stack[][]>();
		addReserveMoves(board,moves);
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
	
	public int getReserveCount(Stack<Piece>[][] board){
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
	
	public int getScore(Stack<Piece>[][] board){
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
			temp1 = fake[sx][sy].pop();
			if(temp1.getScore()!=0){
				hold.add(0,temp1);
			}
			else{
				fake[sx][sy].push(temp1);
			}
		}
		for(Piece p: hold){
			Piece temp2 = new Piece();
			temp2.setType(p.getType());
			temp2.setScore(fake[dx][dy].size());
			fake[dx][dy].push(temp2);
		}

		if(moves!=null && !checkIfPlayed(executedMoves,fake)){
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

		if(moves!=null && !checkIfPlayed(executedMoves,fake)){
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
	public void addReserveMoves(Stack<Piece>[][] board,ArrayList<Stack[][]> moves){
		Stack<Piece>[][] fake = copyBoard(board);
		for(Piece p: reserves){
			if(p.getType()==type){
				for(int i = 0;i<SIZE;i++){
					for(int j=0; j<SIZE;j++){
						if(fake[i][j]!=null){
							Piece temp = new Piece(p.getType());
							temp.setScore(fake[i][j].peek().getScore()+1);
							fake[i][j].push(temp);
							moves.add(fake);
							fake[i][j].pop();
						}
					}
				}
				return;
			}
		}
		
	}
	public Stack<Piece>[][] bestMoveH1(ArrayList<Stack[][]> moves){
		Player p2;
		int max;
		int min = 1000;
		//int count = 0;
		Stack<Piece>[][] alpha = focus.getBoard();
		Stack<Piece>[][] beta = focus.getBoard();
		if(type == Piece.RED){
			p2 = new Player(Piece.GREEN,focus);
		}
		else{
			p2 = new Player(Piece.RED,focus);
		}
		for(Stack<Piece>[][] s: moves){
			max = -1;
			for(Stack<Piece>[][] t:p2.getMoves(s)){
				//if(p2.getScore(t) == min && count> 1){
				//	break;
				//}
				if(p2.getScore(t)>max){
					max = p2.getScore(t);
					beta = t;
				}
			}
			if(getScore(beta)<min){
				min = getScore(beta);
				alpha = s;
			}
			//count++;
		}
		if(getScore(focus.getBoard())==getScore(alpha)){
			Random rand = new Random();
			Stack[][] temp3 = moves.get(rand.nextInt(moves.size()-1));
			while(getScore(temp3)<getScore(focus.getBoard())){
				temp3 = moves.get(rand.nextInt(moves.size()-1));
			}

			return temp3;
		}
		return alpha;
	}
	
	public Stack<Piece>[][] bestMoveH2(ArrayList<Stack[][]> moves){
		Player p2;
		int max;
		int min = 1000;
		int count = 0;
		Stack<Piece>[][] alpha = focus.getBoard();
		Stack<Piece>[][] beta = focus.getBoard();
		if(type == Piece.RED){
			p2 = new Player(Piece.GREEN,focus);
		}
		else{
			p2 = new Player(Piece.RED,focus);
		}
		for(Stack<Piece>[][] s: moves){
			max = -1;
			for(Stack<Piece>[][] t:p2.getMoves(s)){
				//if(p2.getTotalPieces(t)==min && count>1){
				//	break;
				//}
				if(p2.getTotalPieces(t)>max){
					max = p2.getTotalPieces(t);
					beta = t;
				}
			}
			if(getTotalPieces(beta)<min){
				min = getTotalPieces(beta);
				alpha = s;
			}
			//count++;
		}
		if(getTotalPieces(focus.getBoard())==getTotalPieces(alpha)){
			Random rand = new Random();
			Stack[][] temp3 = moves.get(rand.nextInt(moves.size()-1));
			while(getTotalPieces(temp3)<getTotalPieces(focus.getBoard())){
				temp3 = moves.get(rand.nextInt(moves.size()-1));
			}

			return temp3;
		}
		return alpha;
	}
	public int getTotalPieces(Stack<Piece>[][] board){
		int total=0;
		for(int i = 0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				if(board[i][j]!=null){
					if(board[i][j].peek().getType()==type){
						total++;
					}
				}
			}
		}
		return total;
	}
	public boolean checkIfPlayed(ArrayList<Stack[][]> moves, Stack<Piece>[][] move){
		for(Stack<Piece>[][] m: moves){
			if(Arrays.deepEquals(move, m)){
				return true;
			}
			
		}
		return false;
	}
	public void executeTurn(Stack<Piece>[][] board){
		Stack<Piece>[][] bestMove;
		getMoves(board);
		if(type==Piece.RED){
			bestMove = bestMoveH1(moves);
		}
		else{
			bestMove = bestMoveH2(moves);
		}
		executedMoves.add(bestMove);
		focus.changeBoard(bestMove);
	}
	
	public ArrayList<Piece> getReserves(Stack<Piece>[][] board){
		Stack<Piece> temp = new Stack<Piece>();
		Piece[] anArray;
		for(int i = 0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				if(board[i][j]!=null){
					anArray = new Piece[board[i][j].size()];
					int t = board[i][j].peek().getType();
					int s = board[i][j].peek().getScore();
					if(t==type && s>5){
						int count = s-5;
						board[i][j].copyInto(anArray);
						
						for(int k=1;k<=s;k++){
							Piece temp2 = new Piece();
							if(count>0){
								anArray[k].setScore(1);
								if(anArray[k].getType()!=type){
									if(type == Piece.RED){
										anArray[k].setType(Piece.GREEN);
									}
									else{
										anArray[k].setType(Piece.RED);
									}
								}
								reserves.add(anArray[k]);
								count--;
							}
							else{
								temp.push(anArray[0]);
								temp2.setType(anArray[k].getType());
								temp2.setScore(anArray[k].getScore()-(s-5));
								temp.push(temp2);
								
							}
						}
						board[i][j] = temp;
					}
				}
			}
		}
		return reserves;
	}
}
