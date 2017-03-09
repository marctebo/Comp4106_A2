
public class Piece {
	public static final int EMPTY = 0;
	public static final int GREEN = 1;
	public static final int RED = 2;
	private int score,type;
	
	public Piece(){
		score = 0;
		type = EMPTY;
	}
	public Piece(int type){
		this.type = type;
		score = 1;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {

		this.score = Math.abs(score);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String toString(){
		if(type == RED){
			return "R|"+ score;
		}
		else if(type == GREEN){
			return "G|" + score;
		}
		
		return type + "|" + score;		
	}
	public static void main(String args[]){
	}
}
