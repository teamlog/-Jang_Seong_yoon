#include <stdio.h>

int main() {
	FILE *fp = fopen("data.txt", "wt");
	if (fp == NULL) {
		puts("���Ͽ��� ����");
		return -1;
	}
	fputc('A', fp);
	fputc('B', fp);
	fputc('C', fp);
	fputc('D', fp);
	fclose(fp);
	return 0;
}