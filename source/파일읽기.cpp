#include <stdio.h>

int main() {
	int ch;
	FILE *fp = fopen("data.txt", "rt");
	if (fp == NULL) {
		puts("���Ͽ��� ����");
		return -1;
	}
	for (int i = 0; i < 4; i++) {
		ch = fgetc(fp);
		printf("%c\n", ch);
	}
	fclose(fp);
	return 0;
}