#include<stdio.h>
int arr[10];
void reverse(int *arr,const int a, const int b){
	int temp;
	int cnt=-1;
	int num=-1;
	for (int i = b-1; i >= a; i--){
		if (a+cnt == num) break;
		temp = *(arr+(i));
		*(arr+(i)) = *(arr+(a+cnt));
		*(arr+(a+cnt)) = temp;
		cnt++;
		num = i;
	}
	return;
}
void money(int arr[]){
	int high=0;
	int sum=0;
	for (int i = 0; i < 10; i++){
		sum = 0;
		for (int j = i; j < 10; j++){
			sum += arr[j];
		}
		if (high < sum) high = sum;
	}
	printf("%d", high);
}
void kadane(int arr[]){
	int size=10;
	int max_sum = -987654321;
	int cur_max_sum = -987654321;
	for (int i = 0; i<size; i++){
		int t;
		scanf("%d", &t);
		if (t < cur_max_sum + t){
			cur_max_sum = cur_max_sum + t;
		}
		else {
			cur_max_sum = t;
		}
		if (cur_max_sum > max_sum){
			max_sum = cur_max_sum;
		}
	}
	printf("%d", max_sum);
}
int main(){
	kadane(arr);
	return 0;
}
//I want SeX -병신한솔