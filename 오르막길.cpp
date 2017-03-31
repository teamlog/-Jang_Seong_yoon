#include <stdio.h>

int main(){
	int num=0;
	int arr[100000];
	int sum=0;
	int a=0;
	int tmp=0;
	scanf("%d",&num);
	for(int i=0;i<num;i++){
		scanf("%d",&arr[i]);
	}
	for(int j=0;j<num;j++){
		tmp=j;
		while(arr[j]<arr[j+1]){
			a++;
			j++;
		}
		j=tmp;
		if(sum<(arr[j+a]-arr[j])){
			sum=arr[j+a]-arr[j];
		}
		a=0;
	}
	if(a=0){
		printf("0");
	}
	printf("%d",sum);

	return 0;
}