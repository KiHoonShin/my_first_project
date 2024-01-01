# 게임 - 쿼리도 게임

쿼리도 게임 룰 -> https://terms.naver.com/entry.naver?docId=5707221&cid=63156&categoryId=63156

-> 쉽게 표현하자면 주어진 벽으로 상대의 길을 막으며 상대의 진영에 먼저 도달하면 승리하는 게임


---------------------------------------------------------------------------------------------------------------------------
플레이어 1과 플레이어2의 위치를 랜덤으로 설정
가진 벽의 개수와 벽의 길이 설정(좌표 기준 우측으로 , 아래로 벽의 길이만큼 생성되게끔)
채팅창을 만듦으로써 온라인 게임과 유사하게 세팅.


실행 -> 
가위바위보를 통해 순서 설정하기

이동 , 벽 사용 , 채팅 중 택1
- [1] 이동 선택 -> 맵 밖으로 벗어날 수 없게 처리 , 벽이 가로막고 있으면 이동 불가, 플레이어끼리 만나면 건너 뛸 수 있음
   * 단 , 건너 뛸 때 벽이 막고 있으면 당연히 건너뛸 수 없음(이동 불가능)
 
- [2] 벽 사용 선택 -> 가로 벽과 세로 벽을 사용하여 맵의 원하는 곳에 벽으로 막을 수 있다.
   * 단 , 이미 벽이 놓였거나 플레이어가 위치해 있는 곳에는 벽을 놓을 수 없다. (벽 사용하면 갖고 있던 벽의 개수에서 1개 차감)
 
- [3] 채팅 -> 자신의 턴이 왔을 때 채팅을 칠 수 있으며 서로 대화를 할 수 있도록 전체 채팅창 출력

상대 진영에 먼저 도착한 플레이어가 승리하며 게임 종료 !
---------------------------------------------------------------------------------------------------------------------------

기초 세팅 -->
> 1. player 클래스 -> playerDAO 클래스를 통해 playerList를 만든다. ( 플레이어 이름 , 플레이어 아이콘, 플레이어의 기본 벽 개수)
  ex : 		playerList.add(new player("p1","🤴", 6));
		      playerList.add(new player("p2","👳‍♂️", 6));
> 2. util 클래스 -> scanner 생성 후 scanner를 통해 int값과 string 값을 내보낼 수 있는 메서드를 만든다.
> 3. GameManager 클래스 -> 각종 변수들을 통해 게임에 필요한 기본 세팅들을 한다.
  ex : final int p1=0; , final int WALL = 9 등 값이 변하면 안 되는 변수들에는 final 사용
    
게임 세팅(GameManager 클래스) -->
> 1. rsp() 메서드 : 플레이어 1과 플레이어 2가 누가 선공을 할지 순서를 정한다.

> 2. init() 메서드 :  map의 초기 값을 설정하였고, map에서 두 플레이어의 위치가 랜덤으로 배치 되게 설정하였다.

> 3. print_map() 메서드 : 세팅된 map을 출력함과 동시에 두 플레이어가 현재 위치한 좌표를 받아올 수 있게 변수를 활용하였다.
                          또한 print_chat() 메서드를 활용하여 채팅창을 만든 후 출력
                          
> 4. gamePlay() 메서드 : 플레이어가 이동할지 , 벽을 사용할지, 채팅을 칠지 선택하는 메서드

> 5. sel_move() 메서드 : 플레이어가 상 , 하 , 좌 , 우 어느 방향으로 이동할지 선택하는 메서드
                        (만약 두 플레이어의 위치가 겹칠 때 건너 뛰게 설정하였다.)
                        (단, 건너 뛸때 뒤에 벽이 있다면 건너 뛸 수 없게도 설정하였다.)

> 6. isValidMove() 메서드 : 플레이어가 이동했을 때 맵 밖으로 벗어나지 못하게 막았으며, 벽이 있을 경우 마찬가지로 이동 못하게 설정

> 7. use_wall() 메서드 : 벽을 사용하는 메서드, 플레이어가 기존에 갖고 있던 벽의 개수가 0개이면 벽을 사용하지 못하게 막았으며
                         벽을 사용할 때 가로로 벽을 사용할 것인지, 세로로 사용할 것인지 선택할 수 있게 하였다. 
                         (벽을 사용하면 플레이어가 갖고 있는 벽의 개수 1개씩 감소)
> 8. isValidGaroWall(int x, int y) , isValidSeroWall(int x, int y)
-- >  위 두 메서드를 통해 벽이 맵을 뚫지 못하게 하였고, 이미 벽이 놓인 위치와 겹치지 못하게 하였으며,
     플레이어가 있는 위치에 벽을 놓지 못하게 설정하였다.

> 9. chatting() : 채팅을 칠 수 있게 메서드를 생성하였다.

> 10. run() : run 메서드를 통해 main에서 실행할 수 있게 하였음.
