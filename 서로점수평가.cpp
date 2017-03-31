#include <stdio.h>

int main() {
	int score[5][6];

	for (int i = 0;i < 5;i++) {
		scanf("%d %d %d %d", &score[i][0], &score[i][1], &score[i][2], &score[i][3]);
		score[i][4] = score[i][0] + score[i][1] + score[i][2] + score[i][3];
		score[i][5] = i;
	}
	for (int i = 0;i<5 - 1;i++)
	{
		for (int j = 0;j<5 - 1;j++)
		{
			if (score[j][4] > score[j + 1][4])
			{
				int temp = score[j][4];
				score[j][4] = score[j + 1][4];
				score[j + 1][4] = temp;
				
				temp = score[j][5];
				score[j][5] = score[j + 1][5];
				score[j + 1][5] = temp;

			}
		}
	}
	printf("%d %d", score[4][5]+1, score[4][4]);
	return 0;
}