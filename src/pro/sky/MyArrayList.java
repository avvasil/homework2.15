package pro.sky;

import pro.sky.exceptions.ArrayIsFullException;
import pro.sky.exceptions.ItemIsAbsentException;
import pro.sky.exceptions.NullItemException;
import pro.sky.exceptions.OutOfBoundsExceptions;

import java.util.Arrays;
import java.util.Objects;

public class MyArrayList implements IntegerList {

    private Integer[] myArrayList;
    private final int myArrayListSize = 10;
    int size;
    static Integer[] randomArray = generateRandomArray();
    public static Integer[] arr1 = Arrays.copyOf(randomArray, 100000);
    public static Integer[] arr2 = Arrays.copyOf(randomArray, 100000);
    public static Integer[] arr3 = Arrays.copyOf(randomArray, 100000);

    public MyArrayList() {
        this.myArrayList = new Integer[myArrayListSize];
    }

    public static Integer[] generateRandomArray() {
        java.util.Random random = new java.util.Random();
        Integer[] randomArray = new Integer[100000];
        for (int i = 0; i < randomArray.length; i++) {
            randomArray[i] = random.nextInt(100_000) + 100_000;
        }
        return randomArray;
    }

    public static void executionTimeMeasurement() {
        long start1 = System.currentTimeMillis();
        sortBubble(arr1);
        long bubblesTime = System.currentTimeMillis() - start1;
        System.out.println("Bubbles   " + bubblesTime + " ms.");
        long start2 = System.currentTimeMillis();
        sortInsertion(arr2);
        long insertionTime = System.currentTimeMillis() - start2;
        System.out.println("Insertion " + insertionTime + " ms.");
        long start3 = System.currentTimeMillis();
        sortSelection(arr3);
        long selectionTime = System.currentTimeMillis() - start3;
        System.out.println("Selection  " + selectionTime + " ms.");
        System.out.println("------------------------------------");

        if (bubblesTime <= insertionTime && bubblesTime <= selectionTime) {
            System.out.println("Fastest is bubbles.");
        } else if (insertionTime <= selectionTime) {
            System.out.println("Fastest is insertion.");
        } else {
            System.out.println("Fastest is selection.");
        }
}

    private static void swapElements(Integer[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }

    public static void sortBubble(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swapElements(arr, j, j + 1);
                }
            }
        }
    }

    private static void sortInsertion(Integer[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }

    public static Integer[] sortSelection(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(arr, i, minElementIndex);
        }
        return arr;
    }

    private static boolean binarySearch(Integer[] arr, int item) {
        int min = 0;
        int max = arr.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (item == arr[mid]) {
                return true;
            }

            if (item < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Integer item) {
        if (item == null) {
            throw new NullItemException("Trying to manipulate with null.");
        }
        if (binarySearch(MyArrayList.sortSelection(myArrayList), item)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "MyArrayList" + Arrays.toString(myArrayList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyArrayList that = (MyArrayList) o;
        return Arrays.equals(myArrayList, that.myArrayList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myArrayListSize);
    }

    @Override
    public Integer add(Integer item) {
        if (item == null) {
            throw new NullItemException("Trying to manipulate with null.");
        }
        if (myArrayList.length == size) {
            throw new ArrayIsFullException("No free capacity in the array");
        }
        myArrayList[size++] = item;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        Integer[] tempArray = new Integer[myArrayListSize];
        if (item == null) {
            throw new NullItemException("Trying to manipulate with null.");
        }
        if (myArrayList.length == size + 1) {
            throw new ArrayIsFullException("No free capacity in the array");
        }
        if (index < 0 || index > myArrayListSize) {
            throw new OutOfBoundsExceptions("Index is out of range");
        }
        for (int i = 0; i < myArrayList.length; i++) {
            tempArray[i] = myArrayList[i];
        }
        if (index == 0) {
            myArrayList[index + 1] = tempArray[0];
        }
        for (int k = index + 1; k < myArrayList.length; k++) {
            myArrayList[k] = tempArray[k - 1];
        }
        myArrayList[index] = item;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        if (item == null) {
            throw new NullItemException("Trying to manipulate with null.");
        }
        if (myArrayList.length == size + 1) {
            throw new ArrayIsFullException("No free capacity in the array");
        }
        if (index < 0 || index > myArrayListSize) {
            throw new OutOfBoundsExceptions("Index is out of range");
        }
        myArrayList[index] = item;
        return item;
    }

    @Override
    public Integer removeItem(Integer item) {
        boolean itemExists = false;
        int indexNumberToRemove = 0;
        Integer[] tempArray = new Integer[myArrayListSize];
        if (item == null) {
            throw new NullItemException("Trying to manipulate with null.");
        }
        for (int i = 0; i < myArrayList.length; i++) {
            tempArray[i] = myArrayList[i];
        }
        for (int j = 0; j < myArrayList.length; j++) {
            if (Objects.equals(myArrayList[j], item)) {
                itemExists = true;
                indexNumberToRemove = j;
                if (indexNumberToRemove == 0) {
                    myArrayList[indexNumberToRemove + 1] = tempArray[0];
                }
                for (int k = indexNumberToRemove + 1; k < myArrayList.length; k++) {
                    myArrayList[k - 1] = tempArray[k];
                }
                if (indexNumberToRemove == myArrayList.length - 1) {
                    myArrayList[indexNumberToRemove] = 0;
                }
            }
        }
        if (!itemExists) {
            throw new ItemIsAbsentException("Item is absent.");
        }
        return item;
    }

    @Override
    public Integer remove(int index) {
        Integer[] tempArray = new Integer[myArrayListSize];
        if (index < 0 || index > myArrayListSize) {
            throw new OutOfBoundsExceptions("Index is out of range");
        }
        Integer deletingItem = myArrayList[index];
        for (int i = 0; i < myArrayList.length; i++) {
            tempArray[i] = myArrayList[i];
        }
        if (index == 0) {
            myArrayList[index + 1] = tempArray[0];
        }
        for (int k = index + 1; k < myArrayList.length; k++) {
            myArrayList[k - 1] = tempArray[k];
        }
        return deletingItem;
    }

    @Override
    public int indexOf(Integer item) {
        if (item == null) {
            throw new NullItemException("Trying to manipulate with null.");
        }
        for (int i = 0; i < myArrayList.length; i++) {
            if (Objects.equals(myArrayList[i], item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        if (item == null) {
            throw new NullItemException("Trying to manipulate with null.");
        }
        for (int i = myArrayList.length - 1; i >= 0; i--) {
            if (Objects.equals(myArrayList[i], item)) {
                return ((myArrayList.length - 1) - i);
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        if (index < 0 || index > myArrayListSize) {
            throw new OutOfBoundsExceptions("Index is out of range");
        }
        return myArrayList[index];
    }

    @Override
    public boolean equals(Integer[] otherList) {
        if (otherList == null) {
            throw new NullItemException("Trying to manipulate with null.");
        }
        if (!Arrays.equals(myArrayList, otherList)) {
            return false;
        }
        return true;
    }

    @Override
    public int size() {
        int numberOfItems = 0;
        for (int i = 0; i < myArrayList.length; i++) {
            if (myArrayList[i] != null) {
                numberOfItems++;
            }
        }
        return numberOfItems;
    }

    @Override
    public boolean isEmpty() {
        int numberOfItems = 0;
        for (int i = 0; i < myArrayList.length; i++) {
            if (myArrayList[i] != null) {
                numberOfItems++;
            }
        }
        if (numberOfItems == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < myArrayList.length; i++) {
            myArrayList[i] = null;
        }
    }

    @Override
    public Integer[] toArray() {
        Integer[] newArray = new Integer[myArrayListSize];
        for (int i = 0; i < myArrayList.length; i++) {
            newArray[i] = myArrayList[i];
        }
        return newArray;
    }
}