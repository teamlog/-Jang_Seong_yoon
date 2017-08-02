#include<stdio.h>
int arr[500000] = { 10, 20, 40, 25, 20, 50, 30, 70 ,85};
int temp[100000];
int arr1[10000];
int BinarySearch(int dataArr[], int size, int findData)
{
	int low = 0, high = size - 1, mid;
	while (low <= high)
	{
		mid = (low + high) / 2;
		if (dataArr[mid] > findData) high = mid - 1;
		else if (dataArr[mid] < findData) low = mid + 1;
		else return mid;
	}
	return mid;
}
int lis(int arr[], int size,int temp[]){
	temp[0] = arr[0];
	int i = 1;
	int j = 1;
	while (i<size){
		if (temp[j - 1] < arr[i]){
			temp[j] = arr[i];
			j++;
		}
		else temp[BinarySearch(temp, j, arr[i])] = arr[i];
		i++;
	}
	return j;
}
int main(){

	printf("%d\n", lis(arr, 9, temp));
	return 0;
}