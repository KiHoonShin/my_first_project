package Main;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

	private ArrayList<player> p_list = null;
	private ArrayList<String> chat = new ArrayList<>();
	
	private int size = 16;
	private final int p1 = 0;
	private final int p2 = 1;
	private final int WALL = 9;
	private final int wall_length = 3; // 벽의 길이 설정
	private int turn; // 0 -> 1턴 // 1 -> 2턴
	
	private int p1_x; // p1의 현위치 x 좌표
	private int p1_y; // p1의 현위치 y좌표
	
	private int b1_x; // p1의 현위치 x 좌표 복사 (움직일때 사용하기 위함)
	private int b1_y; // p1의 현위치 y 좌표 복사 (움직일때 사용하기 위함)
	
	private int p2_x; // p2의 현위치 x좌표
	private int p2_y; // p2의 현위치 y좌표
	
	private int b2_x; // p2의 현위치 x 좌표 복사 (움직일때 사용하기 위함)
	private int b2_y; // p2의 현위치 y 좌표 복사 (움직일때 사용하기 위함)
	
	private int[][] map = new int[size][size];
	
	private Random rd = new Random();
	
	private void init() {
		
		for(int i = 0; i < map.length; i+=1) {
			for(int k = 0; k < map[i].length; k+=1) {
				map[i][k] = 10;
			}
		} // -> p1 , p2의 값을 0 ,1 로 설정하기 위해 초기 맵 세팅을 아무 수 와도 겹치지 않는 10으로 세팅함
		
		playerDAO dao = new playerDAO();
		p_list = dao.playerList;
		int r1 = rd.nextInt(size-1);
		map[0][r1] = p1; // p1 랜덤 설정
		int r2 = rd.nextInt(size-1);
		map[size-1][r2] = p2; // p2 랜덤 설정
		rsp();
	}
	
	private void print_map() {
		System.out.println("===================================================");
		for(int i = 0; i < map.length; i++) {
			System.out.printf("%-4d", i+1);
			for(int k = 0; k < map[i].length; k+=1) {
				if(map[i][k] == 10) {
					System.out.print("+  ");
				} else if(map[i][k] == p1) {
					System.out.printf("%s ", p_list.get(p1).mark);
					p1_y = i;
					p1_x = k;
				} else if(map[i][k] == p2) {
					System.out.printf("%s " , p_list.get(p2).mark);
					p2_y = i;
					p2_x = k;
				} else if(map[i][k] == WALL) {
					System.out.print("🌫 ");
				}
			}
			System.out.println();
		}
		System.out.println("===================================================");
		print_chat();
	}
	
	private void print_chat() {
		System.out.println("--------------채팅창---------------");
		if(chat.size() == 0) {
			System.out.println("현재 채팅창이 비었습니다");
		}
		for(String s : chat) {
			System.out.println(s);
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("----------------------------------");
	}
	
	
	public void run() {
		init();
		while(true) {
			print_map();
			if(gamePlay()) {
				return;
			}
		} //while
	}
	
	private boolean gamePlay() {
		while(true) {
			b1_y = p1_y;
			b1_x = p1_x;
			
			b2_y = p2_y;
			b2_x = p2_x;
			if(b1_y == 19 || b2_y == 0) {
				turn = turn == 0? 1 : 0;
				System.out.println("게임 종료");
				System.out.printf("%s [%s] 플레이어 승리 !  %n" , p_list.get(turn).name, p_list.get(turn).mark);
				return true;
			}
			System.out.printf("%s턴 [ %s ]\n[1] 이동 [2] 벽 사용 [3] 채팅하기 %n", p_list.get(turn).name , p_list.get(turn).mark);
			int sel = util.getValue(1, 3);
			if(sel == 1) { // 이동
				if(!sel_move()) {
					continue;
				}
			} else if(sel == 2){ // 벽 사용
				if(!use_wall()) {
					continue;
				}
			} else {
				//채팅
				chatting();
				continue;
			}
			break;
		} // while
		turn = turn == 0 ? 1 : 0;
		return false;
	}
	
	// 채팅
	private void chatting() {
		System.out.println("----------------------------------");
		System.out.print("채팅 : ");
		String input = util.getValue("");
		chat.add(p_list.get(turn).name+": "+input);
		System.out.println("----------------------------------");
	}
	
	
	// 벽 사용하기
	private boolean use_wall() {
		if(p_list.get(turn).wall == 0) {
			System.out.println("사용 가능한 벽이 없습니다.");
			return false;
		}
		System.out.println("===================================================");
		System.out.printf("%s 플레이어 현재 남아있는 벽 개수 : %d %n" , p_list.get(turn).name , p_list.get(turn).wall);
		System.out.println("[1] 가로 벽    [2] 세로 벽");
		int wall = util.getValue(1, 2);
		System.out.println("벽을 놓을 위치를 선택해주세요");
		System.out.println("Y 좌표");
		int y = util.getValue(1, size) - 1;
		System.out.println("X 좌표");
		int x = util.getValue(1, size) - 1;
		
		if(wall == 1) { 
			// 가로 벽
			if(!isValidGaroWall(x, y)) {
				System.out.println("벽을 놓을 수 없습니다.");
				System.out.println("다시 선택해주세요.");
				return false;
			}
		} else {
			// 세로 벽
			if(!isValidSeroWall(x, y)) {
				System.out.println("벽을 놓을 수 없습니다.");
				System.out.println("다시 선택해주세요.");
				return false;
			}
		}
		int wallCnt = p_list.get(turn).getWall();
		wallCnt -= 1;
		p_list.get(turn).setWall(wallCnt);
		return true;
	}
	
	// 벽 놓을 수 있는지
	private boolean isValidGaroWall(int x , int y) {
		if((x > size-wall_length || y > size-1)) {
			return false;
		}
		for(int i = x; i <x+wall_length; i+=1) {
			if(map[y][i] == p1 || map[y][i] == p2 || map[y][i] == WALL) {
				return false;
			}
		}
		for(int i = x; i <x+wall_length; i+=1) {
			map[y][i] = WALL;
		}
		return true;
	}
	
	private boolean isValidSeroWall(int x, int y) {
		if((x > size-1 || y > size-wall_length)) {
			return false;
		}
		for(int i = y; i <y+wall_length; i+=1) {
			if(map[i][x] == p1 || map[i][x] == p2 || map[i][x] == WALL) {
				return false;
			}
		}
		for(int i = y; i <y+wall_length; i+=1) {
			map[i][x] = WALL;
		}
		return true;
	}
	
	// 이동
	private boolean sel_move() {
		System.out.println("[1] ← 좌  [2] ↑상  [3] ↓하  [4] 우 →");
		int sel = util.getValue(1, 4);
		if (sel == 1) { // 좌
			if(turn == 0) {
				b1_x -=1;
			} else {
				b2_x -=1;
			}
		} else if (sel == 2) { // 상
			if (turn == 0) {
				b1_y -= 1;
			} else {
				// 2턴일때
				b2_y -= 1;
			}
		} else if (sel == 3) { // 하
			if (turn == 0) {
				b1_y += 1;
			} else {
				// 2턴일때
				b2_y += 1;
			}

		} else if (sel == 4) { // 우
			if(turn == 0) {
				b1_x += 1;
			} else {
				b2_x +=1;
			}
		}
		
		if(!isValidMove()) {
			return false;
		}
		
		if(turn == 0) {
			map[p1_y][p1_x] = 10;
			if(map[b1_y][p1_x] == p2) {
				b1_y += 1;
			} else if(map[p1_y][b1_x] == p2) {
				b1_x += 1;
			} // -> 만났을때 건너 뛰기 
			if(!isValidMove()) {
				return false;
			}
			map[b1_y][b1_x] = p1;
			b1_x = 0;
			b1_y = 0;
		} else {
			map[p2_y][p2_x] = 10;
			if(map[b2_y][p2_x] == p1) {
				b2_y += 1;
			} else if(map[p2_y][b2_x] == p1) {
				b2_x += 1;
			} // -> 만났을때 건너 뛰기 
			if(!isValidMove()) {
				return false;
			}
			map[b2_y][b2_x] = p2;
			b2_x = 0;
			b2_y = 0;
		}
		if(!isValidMove()) {
			return false;
		}
		
		return true;
	}
	
	
	// 막혀있는지 체크
	private boolean isValidMove() {
		if((b1_x < 0 || b1_x > size-1) || (b2_x < 0 || b2_x > size-1) || (b1_y < 0 || b2_y > size-1) ||(map[b1_y][b1_x] == WALL || map[b2_y][b2_x] == WALL)) {
			System.out.println("===================================================");
			System.out.println("막혀있습니다.");
			System.out.println("다시 선택해주세요");
			return false;
		}
		return true;
	}
	
	// 가위바위보로 순서 정하는 메서드
	private void rsp() { 
		String[] rsp = {"✌","👊","🖐"};
		while(true) {
			int r1 = rd.nextInt(2); // 1턴의 가위 바위 보
			int r2 = rd.nextInt(2);
			System.out.println(" 가위 !    바위 !    보 !");
			System.out.printf("%s [%s]   %s vs %s   %s [%s] %n" , p_list.get(p1).name, p_list.get(p1).mark , rsp[r1] , rsp[r2] , p_list.get(p2).name, p_list.get(p2).mark);
			System.out.println();
			if ((r1 == 0 && r2 == 2) || (r1 > r2)) {
				// 1턴 승리
				turn = 0;
			} else if (r1 == r2) {
				// 비김
				System.out.println("[ 무승부 ! 재경기 ]");
				System.out.println();
				continue;
			} else if (r2 > r1) {
				// 2턴 승리
				turn = 1;
			}
			System.out.printf("		[ %s [%s] 승리 ! ] %n" ,  p_list.get(turn).name , p_list.get(turn).mark);
			System.out.println("===================================================");
			System.out.println("		   쿼리도 GAME START");
			return;
		} //while
	}
}
