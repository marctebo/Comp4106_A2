import java.util.ArrayList;
import java.util.Stack;

public class Focus {
	public static Stack<Piece>[][] board;
	public static Player player1, player2;
	public static final int SIZE = 8;
	public static ArrayList<Stack[][]> executedMoves;


	public Focus(){
		generateBoard();
		executedMoves = new ArrayList<Stack[][]>();
		executedMoves.add(board);
	}
	
	public void generateBoard(){
		board = new Stack[SIZE][SIZE];
		int count = 0;
		int type = Piece.RED;
		for(int i = 0; i< SIZE;i++){
			for(int j = 0; j<SIZE;j++){
				board[i][j] = new Stack<Piece>();
				board[i][j].push(new Piece());
				if((i!=0 && j!=0) && (i!=SIZE-1 && j!=SIZE-1)){
					board[i][j].push(new Piece(type));
					count++;
					if(count == 2){
						if(type == Piece.RED){
							type = Piece.GREEN;
						}
						else{
							type = Piece.RED;
						}
						count = 0;
					}
				}
			}
		}
		board[0][0] = null;
		board[0][1] = null;
		board[1][0] = null;
		board[0][6] = null;
		board[0][7] = null;
		board[1][7] = null;
		board[6][0] = null;
		board[7][0] = null;
		board[7][1] = null;
		board[6][7] = null;
		board[7][7] = null;
		board[7][6] = null;
	}
	public Stack[][] getBoard(){
		return board;
	}
	public static void printBoard(Stack<Piece>[][] board){
		for(int i = 0; i< SIZE;i++){
			for(int j = 0; j<SIZE;j++){
				if(board[i][j] == null){
					System.out.print("     ");
				}
				else{
					System.out.print(((Piece)board[i][j].peek()).toString() + "  ");
				}
			}
			System.out.println();
		}
		/*
		for(int i = 0; i< SIZE;i++){
			for(int j = 0; j<SIZE;j++){
				if(board[i][j] != null){
					if(board[i][j].peek().getScore()>1){
						System.out.println(i + " " + j + "  " + board[i][j].toString() );
					}
				}
			}
			System.out.println();
		}*/
		System.out.println("Player 1 Score: " + player1.getScore(board));
		System.out.println("Player 1 Reserves: " + player1.getReserves(board).toString());
		System.out.println("Player 2 Score: " + player2.getScore(board));
		System.out.println("Player 2 Reserves: " + player2.getReserves(board).toString());
		
	}
	public static void fixBoard(Stack<Piece>[][] board){
		Piece[] anArray;
		Stack<Piece> temp;
		for(int i = 0; i< SIZE;i++){
			for(int j = 0; j<SIZE;j++){
				if(board[i][j] != null){
					if(board[i][j].peek().getScore()>1){
						anArray = new Piece[board[i][j].size()];
						temp = new Stack<Piece>();
						board[i][j].copyInto(anArray);
						for(int k = 0;k<anArray.length;k++){
							anArray[k].setScore(k);
							temp.push(anArray[k]);
						}
						board[i][j] = temp;
					}
				}
			}
		}
	}
	public void play(){
		player1 = new Player(Piece.RED,this);
		player2 = new Player(Piece.GREEN, this);
		
		while(true){
			player1.executeTurn(board);
			player1.getReserves(board);
			printBoard(board);
			int countp1 = 0;
			for(Piece p: player1.getReserves(board)){
				if(p.getType()==player2.type){
					countp1++;
				}
				if(countp1==8){
					System.out.println("Player 1 Wins!");
					return;
				}
			}
			player2.executeTurn(board);
			player2.getReserves(board);
			printBoard(board);

			int countp2 = 0;
			for(Piece p: player2.getReserves(board)){
				if(p.getType()==player1.type){
					countp2++;
				}
				if(countp2==8){
					System.out.println("Player 2 Wins!");
					return;
				}
			}
		}
		/*if(player1.getScore(board)>player2.getScore(board)){
			System.out.println("Player 1 Wins!");
			return;
		}
		System.out.println("Player 2 Wins!");*/
	}
	public static void changeBoard(Stack<Piece>[][] newBoard){
		board = newBoard;
		//fixBoard(board);
		executedMoves.add(board);

	} 
	public ArrayList<Stack[][]> getExecutedMoves() {
		return executedMoves;
	}
	public static void main(String args[]){
		Focus focus = new Focus();
		focus.play();
		/*player1 = new Player(Piece.RED);
		player2 = new Player(Piece.GREEN);
		printBoard(player1.bestMoveH1(player1.getMoves(board)));
		/*Piece temp = new Piece(Piece.RED);
		temp.setScore(board[1][2].peek().getScore()+1);
		board[1][2].push(temp);
		Piece temp2 = new Piece(Piece.RED);
		temp2.setScore(board[1][2].peek().getScore()+1);
		board[1][2].push(temp2);
		Piece temp3 = new Piece(Piece.GREEN);
		temp3.setScore(board[3][4].peek().getScore()+1);
		board[3][4].push(temp3);
		Piece temp4 = new Piece(Piece.GREEN);
		temp4.setScore(board[3][4].peek().getScore()+1);
		board[3][4].push(temp4);
		System.out.println("Initial Board");
		printBoard(board);

		for(Stack[][] move: player1.getMoves(board)){
			printBoard(move);
			System.out.println();
		}
		System.out.println("Final Board");
		printBoard(board);
		System.out.println("Total Moves available for Player 1: " + player1.getMoves(board).size());
		System.out.println("Total Moves available for Player 2: " + player2.getMoves(board).size());*/
	}
}
