import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
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

	static int N, M, L;
	static int startX, startY;
	static int[][] map;
	static int[][] dir = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

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
