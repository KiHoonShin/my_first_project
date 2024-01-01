package Main;

public class player {
	 String name;
	 String mark;
	 int wall;
	
	player(String name, String mark , int wall){
		this.name = name;
		this.mark = mark;
		this.wall = wall;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public int getWall() {
		return wall;
	}

	public void setWall(int wall) {
		this.wall = wall;
	}
	
	
}
