# SWExpertAcademy_MockTest_Java_1953

## SW Expert Academy 1953. [모의 SW 역량테스트] 탈주범 검거

### 1. 문제설명

출처: https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5PpLlKAQ4DFAUq

input으로 지도의 세로, 가로의 길이 `N, M`, 시작점의 좌표 `R, C` 경과한 시간 `L`가 들어온다. 탈주범이 시작점에서 맨홀로 들어가서 이어진 동굴을 `L`시간동안 이동할 때 있을 수 있는 위치의 갯수를 출력하는 문제. 


[제약 사항]

    1. 시간 제한 : 최대 50개 테이트 케이스를 모두 통과하는데, C/C++/Java 모두 1초
    2. 지하 터널 지도의 세로 크기 N, 가로 크기 M은 각각 5 이상 50 이하이다. (5 ≤ N, M ≤ 50)
    3. 맨홀 뚜껑의 세로 위치 R 은 0 이상 N-1이하이고 가로 위치 C 는 0 이상 M-1이하이다. (0 ≤ R ≤ N-1, 0 ≤ C ≤ M-1)
    4. 탈출 후 소요된 시간 L은 1 이상 20 이하이다. (1 ≤ L ≤ 20)
    5. 지하 터널 지도에는 반드시 1개 이상의 터널이 있음이 보장된다.
    6. 맨홀 뚜껑은 항상 터널이 있는 위치에 존재한다.

[입력]

> 첫 줄에 총 테스트 케이스의 개수 `T`가 주어진다.
> 두 번째 줄부터 `T`개의 테스트 케이스가 차례대로 주어진다.
> 각 테스트 케이스의 첫 줄에는 지하 터널 지도의 세로 크기 `N`, 가로 크기 `M`, 맨홀 뚜껑이 위치한장소의 세로 위치 `R`, 가로 위치 `C`, 그리고 탈출 후 소요된 시간 `L` 이 주어진다.
> 그 다음 `N` 줄에는 지하 터널 지도 정보가 주어지는데, 각 줄마다 `M` 개의 숫자가 주어진다.
> 숫자 `1 ~ 7`은 해당 위치의 터널 구조물 타입을 의미하며 숫자 `0` 은 터널이 없는 장소를 의미한다.

[출력]

> 테스트 케이스의 개수만큼 `T`줄에 `T`개의 테스트 케이스 각각에 대한 답을 출력한다.
> 각 줄은 `#x`로 시작하고 공백을 하나 둔 다음 정답을 기록한다. (`x`는 `1`부터 시작하는 테스트 케이스의 번호이다)
> 출력해야 할 정답은 탈주범이 위치할 수 있는 장소의 개수이다.


### 2. 풀이

시간 `L`동안 시작점에서 출발하여 시간이 지날 수 록 존재할 수 있는 위치는 이어져있는 동굴로 퍼져나갈 수 있다. 해결 방식은 BFS로 잡고 위치와 그 위치에 도달할 수 있는 시간을 변수로 가진 클래스 `Point`를 선언하였다.

* Point Class
```java
	static class Point {
		int x, y;
		int time;

		public Point(int x, int y, int time) {
			this.x = x;
			this.y = y;
			this.time = time;
		}

		public String toString() {
			return "(" + x + ", " + y + ") = time " + time;
		}
	}

```

큐에 시작점을 담고 큐가 빌때까지 탐색을 진행한다. 탐색은 큐에서 하나의 `Point`를 꺼내어 해당 위치에 도달할 시간을 체크한다. `L`보다 더 오래걸려 해당위치로 왔다면 탐색을 종료하고 아니라면 시간내 존재할 수 있으므로 카운트를 한다. 이어서 이동할 수 있는 길에 대해 찾아 큐에 담아준다.

이동할 수 있는 길을 찾는 방법은 이렇다. 지도에서 값을 들고와 동굴의 모양에 따라 이동할 수 있는 곳에 대한 정보값을 받아온다. 상하좌우를 각각 `1`비트를 할당하여 네 자릿수의 비트로 표현하기로한다. 이를 비트연산자로 분석하여 해당위치의 비트가 켜져있으면 이동할 수 있는 방향이므로 그위치에 대해 유망하다 생각한다. 하지만 이동할 수 있는 방향이 동굴이 존재하지 않거나 존재해도 통로가 맞지 않을 수도 있다. 그렇기 때문에 이동할 방향에 대해서도 통로가 일치함을 비트연산자를 이용하여 확인한 후 유망하다면 다음위치를 큐에넣는다.

```java

	// 상하좌우 4개의 비트로 표현
	private static int getDirInfo(int val) {
		int dir = 0;
		switch (val) {
		case 1:
			dir = 15;
			break;
		case 2:
			dir = 12;
			break;
		case 3:
			dir = 3;
			break;
		case 4:
			dir = 9;
			break;
		case 5:
			dir = 5;
			break;
		case 6:
			dir = 6;
			break;
		case 7:
			dir = 10;
			break;
		}
		return dir;
	}

}


```

위 작업을 반복하여 시간이 `L`보다 크다면 탐색을 멈추고 현재까지의 `count`를 출력하여 해결하였다.

```java

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int T = Integer.parseInt(br.readLine());

		for (int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			startX = Integer.parseInt(st.nextToken());
			startY = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());
			map = new int[N][M];
			
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < M; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			int count = 0;
			boolean[][] visit = new boolean[N][M];

			Queue<Point> q = new LinkedList<>();
			q.add(new Point(startX, startY, 1));
			visit[startX][startY] = true;
			while (!q.isEmpty()) {
				Point p = q.poll();
				if (p.time <= L)
					count++;
				else
					break;
				
				int dirInfo = getDirInfo(map[p.x][p.y]);
				for (int i = 0; i < 4; i++) {
					if ((dirInfo >> i & 1) == 1) {
						int nextX = p.x + dir[i][0];
						int nextY = p.y + dir[i][1];
						if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < M) { // in boundary
							if (visit[nextX][nextY] == false && map[nextX][nextY] != 0) {
								int nextDir = getDirInfo(map[nextX][nextY]);
								int j;
								if (i % 2 == 0)
									j = i + 1;
								else 
									j = i - 1;
								if ((nextDir >> j & 1) == 1) {
									q.add(new Point(nextX, nextY, p.time+1));
									visit[nextX][nextY] = true;									
								}
							}
						}
					}
				}
				
			}
			
			bw.append("#"+test_case+" "+count+"\n");
		}
		bw.flush();
		bw.close();
		br.close();

	}

```

