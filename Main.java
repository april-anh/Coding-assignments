import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0,
                    BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    public static HashMap<Integer, Integer> result = new HashMap<>();
    public static int len;
    public static int[] A;
    public static int[] B;

    public static long solve(int[] a, int[] b) {
        if (a.length <= 1) {
            return 0L;
        }
        len = a.length;
        Arrays.sort(b);
        A = a;
        B = b;
        countCrossInversion(0, len,0, 0);
        countCrossInversion(result.get(0), len, len -1, len -1);
        countCrossInversion(result.get(0), result.get(len -1),0, len -1);

        int[] arr = new int[2* len];
        int currentIndex = 0;
        int bIndex = 0;
        int aIndex = 0;
        while (currentIndex < 2* len) {
            if (bIndex < len && result.get(bIndex) <= aIndex) {
                arr[currentIndex] = b[bIndex];
                bIndex++;
            } else {
                arr[currentIndex] = a[aIndex];
                aIndex++;
            }
            currentIndex++;
        }
        return mergeSortAndCount(arr, 0, 2* len - 1);
    }

    public static void countCrossInversion(int optimalLeft, int optimalRight, int left, int right) {
        int index = (left + right)/2;
        if (result.containsKey(index) || optimalLeft > optimalRight) {
            return;
        }

        if (optimalLeft == optimalRight) {
            for (int i = left; i <= right; i++) {
                if (!result.containsKey(i)) {
                    result.put(i, optimalLeft);
                }
            }
        }

        int key = B[index];
        long[] small = new long[len+1];
        long[] large = new long[len+1];
        small[optimalLeft] = 0;
        large[optimalRight] = 0;

        for (int i = optimalLeft; i < optimalRight; i++) {
            if (A[i] > key) {
                large[i+1] = large[i] + 1;
            } else {
                large[i+1] = large[i];
            }
        }

        for (int i = optimalRight; i > optimalLeft; i--) {
            if (A[i-1] < key) {
                small[i-1] = small[i] + 1;
            } else {
                small[i-1] = small[i];
            }
        }

        long m = small[optimalLeft] + large[optimalLeft];
        int tempRes = optimalLeft;

        for (int i = optimalLeft + 1; i <= optimalRight; i++) {
            long temp = small[i] + large[i];
            if (m > temp) {
                m = temp;
                tempRes = i;
            }
        }

        result.put(index, tempRes);
        countCrossInversion(optimalLeft, tempRes, left, index);
        countCrossInversion(tempRes, optimalRight, index, right);
    }

    /**
     * Source: https://www.geeksforgeeks.org/counting-inversions/
     */
    // Function to count the number of inversions
    // during the merge process
    public static long mergeAndCount(int[] arr, int l,
                                     int m, int r)
    {

        // Left subarray
        int[] left = Arrays.copyOfRange(arr, l, m + 1);

        // Right subarray
        int[] right = Arrays.copyOfRange(arr, m + 1, r + 1);

        int i = 0, j = 0, k = l;
        long swaps = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j])
                arr[k++] = left[i++];
            else {
                arr[k++] = right[j++];
                swaps += (m + 1) - (l + i);
            }
        }
        while (i < left.length)
            arr[k++] = left[i++];
        while (j < right.length)
            arr[k++] = right[j++];
        return swaps;
    }

    // Merge sort function
    public static long mergeSortAndCount(int[] arr, int l,
                                         int r)
    {

        // Keeps track of the inversion count at a
        // particular node of the recursion tree
        long count = 0;

        if (l < r) {
            int m = (l + r) / 2;

            // Total inversion count = left subarray count
            // + right subarray count + merge count

            // Left subarray count
            count += mergeSortAndCount(arr, l, m);

            // Right subarray count
            count += mergeSortAndCount(arr, m + 1, r);

            // Merge count
            count += mergeAndCount(arr, l, m, r);
        }

        return count;
    }

    public static void main(String[] args) throws IOException {
        Reader s = new Reader();
        int n = s.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        for(int i=0; i<n; i++) {
            a[i] = s.nextInt();
        }
        for(int i=0; i<n; i++) {
            b[i] = s.nextInt();
        }
        System.out.println(solve(a, b));
    }
}