#include <stdio.h>
int main() {
	int a;
	printf("�а��ڵ�(1-4)�� �Է��Ͻÿ�->");
	scanf("%d", &a);
	switch (a) {
	case 1:printf("������Ű�\n"); break;
	case 2:printf("����Ʈ�����\n"); break;
	case 3:printf("��ũ��濵��\n"); break;
	case 4:printf("��Ƽ�̵���\n"); break;
	default:printf("�ش��ϴ°��� �����ϴ�\n"); break;
	}

	return 0;
}