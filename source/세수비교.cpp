#include <stdio.h>

int main(void){
	int A[3];
	int tmp;
	scanf("%d %d %d",&A[0],&A[1],&A[2]);
	for(int i=0;i<3;i++){
		for(int j=0;j<2;j++){
			if(A[j]>A[j+1]){
				tmp=A[j];
				A[j]=A[j+1];
				A[j+1]=tmp;
			}
		}
	}
	printf("%d\n",A[1]);
	
	return 0;
}