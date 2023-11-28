package ch.zhaw.ads;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SortServer implements CommandExecutor {

    private final Random random = new Random();

    public int dataElems; // number of data

    /**
     * Swaps the elements at indices i and j in the given array.
     *
     * @param array The array in which the elements are to be swapped.
     * @param i     The index of the first element to swap.
     * @param j     The index of the second element to swap.
     */
    public void swap(int[] array, int i, int j) {
        int h = array[i];
        array[i] = array[j];
        array[j] = h;
    }

    /**
     * Sorts an array of integers using the Bubble Sort algorithm.
     *
     * <ul>
     *     <li>Best-case: &Omega;(n) - When the array is already sorted.</li>
     *     <li>Average-case: &Theta;(n^2) - Typical case with random data.</li>
     *     <li>Worst-case: O(n^2) - When the array is sorted in reverse order.</li>
     * </ul>
     * This algorithm is a simple comparison-based sorting method. It repeatedly steps
     * through the list, compares adjacent elements, and swaps them if they are in the
     * wrong order.
     *
     * @param array The array to be sorted.
     */
    public void bubbleSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        for (int i = 0; i < array.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    /**
     * Sorts an array of integers using the Insertion Sort algorithm.
     *
     * <ul>
     *     <li>Best-case: &Omega;(n) - When the array is already sorted.</li>
     *     <li>Average-case: &Theta;(n^2) - Typical case with random data.</li>
     *     <li>Worst-case: O(n^2) - When the array is sorted in reverse order.</li>
     * </ul>
     * This algorithm builds the final sorted array one item at a time. It is much
     * less efficient on large lists than more advanced algorithms such as quicksort,
     * heapsort, or merge sort.
     *
     * @param array The array to be sorted.
     */
    public void insertionSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        for (int i = 1; i < array.length; i++) {
            int x = array[i];

            int j;
            for (j = i; ((j > 0) && (array[j - 1] > x)); j--) {
                array[j] = array[j - 1];
            }
            array[j] = x;
        }
    }

    /**
     * Sorts an array of integers using the Selection Sort algorithm.
     *
     * <ul>
     *     <li>Best-case: &Omega;(n^2) - Remains quadratic due to the nature of the algorithm.</li>
     *     <li>Average-case: &Theta;(n^2) - Typical case with random data.</li>
     *     <li>Worst-case: O(n^2) - Independent of the initial order of the elements.</li>
     * </ul>
     * This algorithm sorts an array by repeatedly finding the minimum element from
     * the unsorted part and moving it to the beginning.
     *
     * @param array The array to be sorted.
     */
    public void selectionSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        for (int i = 0; i < array.length; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }
            if (min != i) {
                swap(array, min, i);
            }
        }
    }

    public void streamSort(int[] a) {
        // zum Vergleichen (falls Sie Zeit und Lust haben)
        int[] b = Arrays.stream(a).sorted().toArray();
        System.arraycopy(b, 0, a, 0, a.length);
    }

    /**
     * Checks if the given array is sorted in ascending order.
     *
     * @param array The array to check.
     * @return True if the array is sorted, false otherwise.
     */
    public boolean isSorted(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array should not be null!");
        }

        if (array.length <= 1) {
            return true;
        }

        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generates an array of random integers. The array size is determined by the
     * `dataElems` field, and each element is a random integer within the range
     * specified by the `dataRange` local variable.
     *
     * @return An array of random integers.
     */
    public int[] randomData() {
        int[] a = new int[dataElems];
        for (int i = 0; i < dataElems; i++) {
            int dataRange = 10000000;
            a[i] = random.nextInt(dataRange);
        }
        return a;
    }

    /**
     * Generates an array of integers in ascending order. The array size is determined by
     * the `dataElems` field. Starts from 0 and increments by 1 for each subsequent element.
     *
     * @return An array of integers in ascending order.
     */
    public int[] ascendingData() {
        int[] a = new int[dataElems];
        for (int i = 0; i < dataElems; i++) {
            a[i] = i;
        }
        return a;
    }

    /**
     * Generates an array of integers in descending order. The array size is determined by
     * the `dataElems` field. Starts from `dataElems - 1` and decrements by 1 for each
     * subsequent element.
     *
     * @return An array of integers in descending order.
     */
    public int[] descendingData() {
        int[] a = new int[dataElems];
        for (int i = 0; i < dataElems; i++) {
            a[i] = dataElems - i - 1;
        }
        return a;
    }

    /**
     * Measures the time taken to sort an array using a specified sorting algorithm. The
     * data is generated using a supplied generator, and the sorting is performed
     * multiple times within a second to average the sorting time. Throws an exception
     * if no sorting is performed or if the sorted array is not in order.
     *
     * @param generator A supplier function to generate the data array.
     * @param sorter    A consumer function representing the sorting algorithm.
     * @return The average time taken to sort the array in milliseconds.
     * @throws IllegalStateException If no sorting operations are performed or if sorting fails.
     */
    public double measureTime(Supplier<int[]> generator, Consumer<int[]> sorter) {
        int[] originalData = generator.get();
        int[] dataToSort;

        long startTime = System.currentTimeMillis();
        long endTime = startTime;
        int count = 0;

        while (endTime < startTime + 1000) {
            dataToSort = Arrays.copyOf(originalData, originalData.length);
            sorter.accept(dataToSort);
            if (!isSorted(dataToSort)) {
                throw new IllegalStateException("Sorting failed");
            }
            count++;
            endTime = System.currentTimeMillis();
        }

        if (count == 0) {
            throw new IllegalStateException("No sorting operations were performed within the time frame.");
        }

        return (double) (endTime - startTime) / count;
    }

    public String execute(String arg) {
        Map<String, Consumer<int[]>> sorter = Map.of(
                "BUBBLE", this::bubbleSort,
                "INSERTION", this::insertionSort,
                "SELECTION", this::selectionSort,
                "STREAM", this::streamSort
        );
        Map<String, Supplier<int[]>> generator = Map.of(
                "RANDOM", this::randomData,
                "ASC", this::ascendingData,
                "DESC", this::descendingData
        );

        String[] args = arg.toUpperCase().split(" ");
        dataElems = Integer.parseInt(args[2]);
        try {
            double time = measureTime(generator.get(args[1]), sorter.get(args[0]));
            return arg + " " + time + " ms\n";
        } catch (Exception ex) {
            return arg + " " + ex.getMessage();
        }
    }

    public static void main(String[] args) {
        SortServer sorter = new SortServer();
        String sort;
        sort = "BUBBLE RANDOM 10000";
        System.out.println(sorter.execute(sort));
        sort = "SELECTION RANDOM 10000";
        System.out.println(sorter.execute(sort));
        sort = "INSERTION RANDOM 10000";
        System.out.println(sorter.execute(sort));

        sort = "BUBBLE ASC 10000";
        System.out.println(sorter.execute(sort));
        sort = "SELECTION ASC 10000";
        System.out.println(sorter.execute(sort));
        sort = "INSERTION ASC 10000";
        System.out.println(sorter.execute(sort));
    }
}