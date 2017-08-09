#include<stdio.h>
#define INF 0x7F7F7F7F
#define SIZE 1000

int size,s;
int m[SIZE][SIZE];
int visit[SIZE];
int d[SIZE];
void input()
{
	int i, j;
	int from, to;
	int w;
	scanf("%d %d", &size,&s);

	// 각 정점으로 가는 간선의 가중치를 무한대로 초기화한다.(최소값을 구하기위해)
	for (i = 0; i < size; i++)
		for (j = 1; j <= size; j++)
			if (i != j)
				m[i][j] = INF;

	for (i = 0; i < s; i++) // 정점에서 정점으로 가는 간선의 가중치가 입력
	{
		scanf("%d %d %d", &from, &to, &w);
		m[from][to] = w;
	}

	for (i = 1; i <= size; i++)
		d[i] = INF;

}
void dijkstra()
{	int i, j;
	int min;
	int v=0;
	d[0] = 0;        // 시작점의 거리 0
	for (i = 0; i < size; i++)
	{	
		min = INF;
		for (j = 0; j < size; j++)
		{
			if (visit[j] == 0 && min > d[j])    // 갈수 있는 정점중에 가장 가까운 정점 선택
			{
				min = d[j];
				v = j;
			}
		}
		visit[v] = 1;   // 가장 가까운 정점으로 방문, i정점에서 가장 가까운 최단경로 v
		for (j = 0; j < size; j++)
		{
			if (d[j] > d[v] + m[v][j])       // 방문한 정점을 통해 다른 정점까지의 거리가 짧아지는지 계산하여 누적된값 저장
				d[j] = d[v] + m[v][j];
		}
	}
}
int main(){
	input();
	dijkstra();
	for (int i = 0; i < size;i++){
		printf("%d", d[i]);
	}
	return 0;
}