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

	// �� �������� ���� ������ ����ġ�� ���Ѵ�� �ʱ�ȭ�Ѵ�.(�ּҰ��� ���ϱ�����)
	for (i = 0; i < size; i++)
		for (j = 1; j <= size; j++)
			if (i != j)
				m[i][j] = INF;

	for (i = 0; i < s; i++) // �������� �������� ���� ������ ����ġ�� �Է�
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
	d[0] = 0;        // �������� �Ÿ� 0
	for (i = 0; i < size; i++)
	{	
		min = INF;
		for (j = 0; j < size; j++)
		{
			if (visit[j] == 0 && min > d[j])    // ���� �ִ� �����߿� ���� ����� ���� ����
			{
				min = d[j];
				v = j;
			}
		}
		visit[v] = 1;   // ���� ����� �������� �湮, i�������� ���� ����� �ִܰ�� v
		for (j = 0; j < size; j++)
		{
			if (d[j] > d[v] + m[v][j])       // �湮�� ������ ���� �ٸ� ���������� �Ÿ��� ª�������� ����Ͽ� �����Ȱ� ����
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