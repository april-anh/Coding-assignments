import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

public class MainPartial {
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

    public static long solve(int[] a, int[] b) {
        int l = a.length;
        long abInversion = 0;
        for (int i = 0; i < l; i++) {
            abInversion += countCrossInversion(a, l, b[i]);
        }
        return abInversion + mergeSortAndCount(a, 0, a.length - 1);
    }

    public static long countCrossInversion(int[] a, int length, int b) {
        long[] larger = new long[length + 1];
        long[] smaller = new long[length + 1];

        larger[0] = 0;
        smaller[length] = 0;
        for(int i = 1; i <= length; i++) {
            if (a[i - 1] > b) {
                larger[i] = larger[i - 1] + 1;
            } else {
                larger[i] = larger[i - 1];
            }
        }

        for(int i = length; i >= 1; i--) {
            if (a[i - 1] < b) {
                smaller[i - 1] = smaller[i] + 1;
            } else {
                smaller[i - 1] = smaller[i];
            }
        }

        long result = larger[0] + smaller[0];
        for (int i = 1; i <= length; i++) {
            result = Math.min(result, larger[i] + smaller[i]);
        }
        return result;
    }

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