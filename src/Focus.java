import java.util.Stack;

public class Focus {
	public static Stack<Piece>[][] board;
	public static Player player1, player2;
	public static final int SIZE = 8;
	public Focus(){
		generateBoard();
	}
	
	public void generateBoard(){
		board = new Stack[SIZE][SIZE];
		int count = 0;
		int type = Piece.RED;
		for(int i = 0; i< SIZE;i++){
			for(int j = 0; j<SIZE;j++){
				if((i!=0 && j!=0) && (i!=SIZE-1 && j!=SIZE-1)){
					board[i][j] = new Stack<Piece>();
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
				else{
					board[i][j] = new Stack<Piece>();
					board[i][j].push(new Piece());
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
	public static void printBoard(Stack[][] board){
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
		System.out.println("Player 1 Potential Reserves: " + player1.getReserves(board));
		System.out.println("Player 1 Potential Score: " + player1.getScore(board));
		System.out.println("Player 2 Potential Reserves: " + player2.getReserves(board));
		System.out.println("Player 2 Potential Score: " + player2.getScore(board));
		
	}
	
	public static void main(String args[]){
		Focus focus = new Focus();
		player1 = new Player(Piece.RED);
		player2 = new Player(Piece.GREEN);
		Piece temp = new Piece(Piece.RED);
		temp.setScore(board[1][2].peek().getScore()+1);
		//board[1][2].push(temp);
		System.out.println("Initial Board");
		printBoard(board);

		for(Stack[][] move: player1.getMoves(board)){
			printBoard(move);
			System.out.println();
		}
		System.out.println("Final Board");
		printBoard(board);
		System.out.println("Total Moves available for Player 1: " + player1.getMoves(board).size());
		System.out.println("Total Moves available for Player 2: " + player2.getMoves(board).size());
	}
}
