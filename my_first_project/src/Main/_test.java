package Main;

import java.util.Scanner;

public class _test {

	public static void main(String[] args) {

		
		// *** 초기 구현단계 (기초 틀 잡는 용) ***
		
		
		Scanner sc = new Scanner(System.in);
		int[][] map = new int[20][20];
		
		map[0][7] = 1;
		
		map[19][13] = 2;
		
		
		// 플레이어 1 과 2의 위치 설정 -> 랜덤으로 초기 설정하기.
		
		// 가진 벽의 개수 -> 7개 ( 벽 하나당 2칸 or 3칸 )
		// 턴마다 [1] 이동 ( 상 , 하 , 좌  , 우 )   [2] 벽 막기
		//[1] 이동 선택시 -> 상 하 좌 우 선택창 출력
		//[2] 벽 사용 선택시 -> 가로  , 세로 선택 출력 -> 어느 위치에 놓을 것인지.
		// 예외처리 : 플레이어와 벽의 위치는 겹칠 수 없다. 이미 벽이 놓인 곳에 중첩하여 벽을 놓을 수 없음
		// --> 플레이어를 가두면 안됨. 
		
		// 벽 사용하면 가진 벽 -1  -> 벽이 0개인 상태면 벽 사용 불가 .
		
		// p1은 y값이 20에 먼저 도달하면 승리 , p2는 y값이 0에 먼저 도달하면 승리
		
		// 이동할때 벽이 있으면 이동 불가 . 이동 선택 다시하기. 
		// 플레이어끼리 마주치면 한 칸 뒤로 건너뛸 수 있음 단, 건너뛸때 플레이어 뒤에 벽이 있다면 이동 불가.
		
		
		int p1 = 1;
		int p2 = 2;
		
		
		int turn = 1; //1 -> 1턴 // 2 -> 2턴
		boolean run = true;
		
		int WALL = 9;
		
		int p1WallCnt = 6;
		int p2WallCnt = 6;
		
		while(run) {
			int xx1 = 0;
			int yy1 = 0;
			int xx2 = 0;
			int yy2 = 0;
			for(int i = 0; i < map.length; i++) {
				System.out.printf("%-4d", i+1);
				for(int k = 0; k < map[i].length; k+=1) {
					if(map[i][k] == 0) {
						System.out.print("+  ");
					} else if(map[i][k] == p1) {
						System.out.print("🤴 ");
						yy1 = i;
						xx1 = k;
					} else if(map[i][k] == p2) {
						System.out.print("👳‍♂️ ");
						yy2 = i;
						xx2 = k;
					} else if(map[i][k] == WALL) {
						System.out.print("  ");
					}
				}
				System.out.println();
			}
			
			while(true) {
			int py1 = yy1;
			int px1 = xx1;
			
			int py2 = yy2;
			int px2 = xx2;
			
			String mark = turn == 1 ? "🤴" : "🎅";
			
			if(py1 == 19 || py2 == 0) {
				turn = turn == 1 ? 2 : 1;
				System.out.println("게임종료");
				System.out.println(turn + "플레이어 승리 ! ");
				run = false;
			}
				System.out.println("===============================================================");
				System.out.printf("%d턴 [ %s ]\n[1] 이동 [2] 벽 사용 %n", turn , mark);
				int sel = sc.nextInt();
				if (sel == 1) {
					System.out.println("[1] ← 좌  [2] ↑상  [3] ↓하  [4] 우 →");
					int move = sc.nextInt();
					if (move == 1) { // 좌
						if(turn == 1) {
							px1 -=1;
						} else {
							px2 -=1;
						}
					} else if (move == 2) { // 상
						if (turn == 1) {
							py1 -= 1;
						} else {
							// 2턴일때
							py2 -= 1;
						}
					} else if (move == 3) { // 하
						if (turn == 1) {
							py1 += 1;
						} else {
							// 2턴일때
							py2 += 1;
						}

					} else if (move == 4) { // 우
						if(turn == 1) {
							px1 += 1;
						} else {
							px2 +=1;
						}
					}
					
					if((px1 < 0 || px1 > 19) || (px2 < 0 || px2 > 19) || (py1 < 0 || py2 > 19) ||(map[py1][px1] == 9) || map[py2][px2] == 9) {
						System.out.println("===============================================================");
						System.out.println("막혀있습니다.");
						System.out.println("다시 선택해주세요");
						continue;
					}
					
					
					if(turn == 1) {
						map[yy1][xx1] = 0;
						map[py1][px1] = p1;

						py1 = 0;
						px1 = 0;
					} else {
						// 2턴
						map[yy2][xx2] = 0;
						map[py2][px2] = p2;

						py2 = 0;
						px2 = 0;
					}
					
				} // sel = 1
				else if(sel ==2) {
					// 벽은 0 ~ 17까지만. 왜냐하면 벽은 3칸짜리이므로 17보다 큰 위치에 놓을때 맵을 벗어남
					// 세로 벽은 좌표 기준 위에서 아래로 3칸
					// 가로 벽은 좌표 기준 우측으로 3칸 
					int wallCnt = 0;
					if(turn == 1) {
						wallCnt = p1WallCnt;
					} else {
						wallCnt = p2WallCnt;
					}
					if(wallCnt <= 0) {
						System.out.println("사용 가능한 벽이 없습니다.");
						continue;
					}
					System.out.println("===============================================================");
					System.out.printf("%d 플레이어 현재 남아있는 벽 개수 : %d %n" , turn , wallCnt);
					System.out.println("[1] 가로 벽    [2] 세로 벽");
					int wall = sc.nextInt();
					System.out.println("벽을 놓을 위치를 선택해주세요");
					System.out.println("Y 좌표");
					int y = sc.nextInt();
					System.out.println("X 좌표");
					int x = sc.nextInt();
					if(wall == 1) {
						// 가로 벽
						if((x > 17 || y > 19)) {
							System.out.println("벽을 놓을 수 없습니다.");
							System.out.println("다시 선택해주세요.");
							continue;
						}
						for(int i = x; i <x+3; i+=1) {
							if(map[y][i] == 1 || map[y][i] == 2) {
								System.out.println("플레이어 있어서 벽 불가");
								// 클래스 -> return; 
							}
							map[y][i] = 9;
						}
					} else {
						//세로 벽
						if((x > 19 || y > 17)) {
							System.out.println("벽을 놓을 수 없습니다.");
							System.out.println("다시 선택해주세요.");
							continue;
						}
						for(int i = y; i <y+3; i+=1) {
							map[i][x] = 9;
						}
					}
					
					if(turn == 1) {
						p1WallCnt -= 1;
					} else {
						p2WallCnt -=1 ;
					}
					
				} // sel = 2
				break;
			} //while
			turn = turn == 1 ? 2 : 1;
		} //while
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
