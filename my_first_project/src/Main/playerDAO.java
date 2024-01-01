package Main;

import java.util.ArrayList;

public class playerDAO {

	ArrayList<player> playerList = new ArrayList<>();
	
	 playerDAO(){
		playerList.add(new player("p1","🤴", 6));
		playerList.add(new player("p2","👳‍♂️", 6));
	}
	
}
