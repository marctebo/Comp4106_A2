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
		
	}
	
	public static void main(String args[]){
		Focus focus = new Focus();
		Player player = new Player(Piece.RED);
		Piece temp = new Piece(Piece.RED);
		temp.setScore(2);
		board[1][2].push(temp);
		for(Stack[][] move: player.getMoves(board)){
			printBoard(move);
			System.out.println();
		}
		System.out.println("GitStuff");
	}
}
