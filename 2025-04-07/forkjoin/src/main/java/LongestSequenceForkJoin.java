public class LongestSequenceForkJoin {

    /**
     * Somewhat arbitrary comparison function, intended to slow
     * things down a little
     */
    public static boolean compare(int x, int y) {
        return (int)(Math.sin(x)*10) == (int)(Math.sin(y)*10);
    }

    /**
     * Find the longest sequence
     *
     * @param item item to look for repeats
     * @param arr array to examine
     * @return length of longest sequence of consecutive item
     * @throws InterruptedException shouldn't happen
     */
    public static int longestSequence(int item, int[] arr, int seqCutoff)
        throws InterruptedException {
        return 0;
    }
}
