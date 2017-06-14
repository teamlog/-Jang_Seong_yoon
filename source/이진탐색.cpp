#include <stdio.h>
int BinarySearch(int dataArr[], int size, int findData)
{
    int low = 0, high = size - 1, mid;
     while (low <= high)
    {
        mid = (low + high) / 2;
        if (dataArr[mid] > findData) high = mid - 1;
        else if (dataArr[mid] < findData) low = mid + 1;
        else return mid+1;
    }
    return -1;
}
int main(){
	int arr[1000000];
	int que[1000000];
	int n;
	int a;
	int b;
	scanf("%d", &n);
	for (int i = 0; i < n; i++){
		scanf("%d", &arr[i]);
	}
	scanf("%d", &a);
	for (int i = 0; i < a; i++){
		scanf("%d", &b);
		que[i]=BinarySearch(arr, n, b);
	}
	for (int i = 0; i < a; i++){
		printf("%d ", que[i]);
	}
	return 0;
}