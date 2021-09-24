package com.example.Engine;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static com.example.Engine.Evaluate.scoreMove;



public class MoveSort {

    private static final int INSERTION_SORT_THRESHOLD = 47;

    public static void sortList(MoveList list) {
        int[] scoreList = new int[list.count];
        for(int i = 0; i < scoreList.length; i++) {
            scoreList[i] = scoreMove(list.moves[i].move);
        }
//        sort(scoreList, 0, scoreList.length-1, true, list.moves);
        //need to reverse
        for(int i = 0, j = list.count-1; i < list.count>>>1; i++, j--) {
            Move temp = list.moves[i];
            list.moves[i] = list.moves[j];
            list.moves[j] = temp;



        }

    }
    //        DualPivotQuicksort.sort(a, 0, a.length - 1, null, 0, 0);
//    private static void sort(int[] a, int left, int right, boolean leftmost, Move[] moveList) {
//        int length = right - left + 1;
//
//        // Use insertion sort on tiny arrays
////        if (length < INSERTION_SORT_THRESHOLD) {
//        if(length < 100) {
//            if (leftmost) {
//                /*
//                 * Traditional (without sentinel) insertion sort,
//                 * optimized for server VM, is used in case of
//                 * the leftmost part.
//                 */
//                for (int i = left, j = i; i < right; j = ++i) {
//                    int ai = a[i + 1];
//                    int mli = moveList[i+1].move;
//                    while (ai < a[j]) {
//                        a[j + 1] = a[j];
//                        moveList[j+1] = moveList[j];
//                        if (j-- == left) {
//                            break;
//                        }
//                    }
//                    a[j + 1] = ai;
//                    moveList[j+1].move = mli;
//                }
//            } else {
//                /*
//                 * Skip the longest ascending sequence.
//                 */
//                do {
//                    if (left >= right) {
//                        return;
//                    }
//                } while (a[++left] >= a[left - 1]);
//
//                /*
//                 * Every element from adjoining part plays the role
//                 * of sentinel, therefore this allows us to avoid the
//                 * left range check on each iteration. Moreover, we use
//                 * the more optimized algorithm, so called pair insertion
//                 * sort, which is faster (in the context of Quicksort)
//                 * than traditional implementation of insertion sort.
//                 */
//                for (int k = left; ++left <= right; k = ++left) {
//                    int a1 = a[k], a2 = a[left]; int ml1 = moveList[k]; int ml2 = moveList[left];
//
//                    if (a1 < a2) {
//                        a2 = a1; a1 = a[left]; ml2 = ml1; ml1 = moveList[left];
//                    }
//                    while (a1 < a[--k]) {
//                        a[k + 2] = a[k];
//                        moveList[k+2] = moveList[k];
//                    }
//                    a[++k + 1] = a1;
//                    moveList[k+1] = ml1;
//
//                    while (a2 < a[--k]) {
//                        a[k + 1] = a[k];
//                        moveList[k+1] = moveList[k];
//                    }
//                    a[k + 1] = a2;
//                    moveList[k+1] = ml2;
//                }
//                int last = a[right];
//                int mlLast = moveList[right];
//
//                while (last < a[--right]) {
//                    a[right + 1] = a[right];
//                    moveList[right+1] = moveList[right];
//                }
//                a[right + 1] = last;
//                moveList[right+1]=mlLast;
//            }
//            return;
//        }
//
//        // Inexpensive approximation of length / 7
//        int seventh = (length >> 3) + (length >> 6) + 1;
//
//        /*
//         * Sort five evenly spaced elements around (and including) the
//         * center element in the range. These elements will be used for
//         * pivot selection as described below. The choice for spacing
//         * these elements was empirically determined to work well on
//         * a wide variety of inputs.
//         */
//        int e3 = (left + right) >>> 1; // The midpoint
//        int e2 = e3 - seventh;
//        int e1 = e2 - seventh;
//        int e4 = e3 + seventh;
//        int e5 = e4 + seventh;
//
//        // Sort these elements using insertion sort
//        if (a[e2] < a[e1]) {
//            int t = a[e2];
//            int mlT = moveList[e2];
//            a[e2] = a[e1];
//            moveList[e2] = moveList[e1];
//            a[e1] = t;
//            moveList[e1] = mlT;
//        }
//
//        if (a[e3] < a[e2]) {
//            int t = a[e3];
//            int mlT = moveList[e3];
//            a[e3] = a[e2];
//            moveList[e3] = moveList[e2];
//            a[e2] = t;
//            moveList[e2] = mlT;
//            if (t < a[e1]) {
//                a[e2] = a[e1];
//                a[e1] = t;
//            }
//        }
//        if (a[e4] < a[e3]) {
//            int t = a[e4];
//            int mlT = moveList[e4];
//
//            a[e4] = a[e3];
//            moveList[e4] = moveList[e3];
//            a[e3] = t;
//            moveList[e3] = mlT;
//            if (t < a[e2]) {
//                a[e3] = a[e2];
//                moveList[e3] = moveList[e3];
//                a[e2] = t;
//                moveList[e2] = mlT;
//                if (t < a[e1]) {
//                    a[e2] = a[e1];
//                    moveList[e2] = moveList[e1];
//                    a[e1] = t;
//                    moveList[e1] = mlT;
//                }
//            }
//        }
//        if (a[e5] < a[e4]) {
//            int t = a[e5];
//            int mlT = moveList[e5];
//            a[e5] = a[e4];
//            moveList[e5] = moveList[e4];
//            a[e4] = t;
//            moveList[e4] = mlT;
//            if (t < a[e3]) {
//                a[e4] = a[e3];
//                moveList[e4] = moveList[e3];
//                a[e3] = t;
//                moveList[e3] = t;
//                if (t < a[e2]) {
//                    a[e3] = a[e2];
//                    moveList[e3] = moveList[e2];
//                    a[e2] = t;
//                    moveList[e2] = mlT;
//                    if (t < a[e1]) {
//                        a[e2] = a[e1];
//                        moveList[e2] = moveList[e1];
//                        a[e1] = t;
//                        moveList[e1] = mlT;
//                    }
//                }
//            }
//        }
//
//        // Pointers
//        int less  = left;  // The index of the first element of center part
//        int great = right; // The index before the first element of right part
//
//        if (a[e1] != a[e2] && a[e2] != a[e3] && a[e3] != a[e4] && a[e4] != a[e5]) {
//            /*
//             * Use the second and fourth of the five sorted elements as pivots.
//             * These values are inexpensive approximations of the first and
//             * second terciles of the array. Note that pivot1 <= pivot2.
//             */
//            int pivot1 = a[e2];
//            int pivot2 = a[e4];
//            int mlpivot1 = moveList[e2];
//            int mlpivot2 = moveList[e4];
//
//            /*
//             * The first and the last elements to be sorted are moved to the
//             * locations formerly occupied by the pivots. When partitioning
//             * is complete, the pivots are swapped back into their final
//             * positions, and excluded from subsequent sorting.
//             */
//            a[e2] = a[left];
//            a[e4] = a[right];
//            moveList[e2] = moveList[left];
//            moveList[e4] = moveList[right];
//
//            /*
//             * Skip elements, which are less or greater than pivot values.
//             */
//            while (a[++less] < pivot1);
//            while (a[--great] > pivot2);
//
//            /*
//             * Partitioning:
//             *
//             *   left part           center part                   right part
//             * +--------------------------------------------------------------+
//             * |  < pivot1  |  pivot1 <= && <= pivot2  |    ?    |  > pivot2  |
//             * +--------------------------------------------------------------+
//             *               ^                          ^       ^
//             *               |                          |       |
//             *              less                        k     great
//             *
//             * Invariants:
//             *
//             *              all in (left, less)   < pivot1
//             *    pivot1 <= all in [less, k)     <= pivot2
//             *              all in (great, right) > pivot2
//             *
//             * Pointer k is the first index of ?-part.
//             */
//            outer:
//            for (int k = less - 1; ++k <= great; ) {
//                int ak = a[k];
//                int mlk = moveList[k];
//                if (ak < pivot1) { // Move a[k] to left part
//                    a[k] = a[less];
//                    moveList[k] = moveList[less];
//                    /*
//                     * Here and below we use "a[i] = b; i++;" instead
//                     * of "a[i++] = b;" due to performance issue.
//                     */
//                    a[less] = ak;
//                    moveList[less] = mlk;
//                    ++less;
//
//                } else if (ak > pivot2) { // Move a[k] to right part
//                    while (a[great] > pivot2) {
//                        if (great-- == k) {
//                            break outer;
//                        }
//                    }
//                    if (a[great] < pivot1) { // a[great] <= pivot2
//                        a[k] = a[less];
//                        moveList[k] = moveList[less];
//                        a[less] = a[great];
//                        moveList[less] = moveList[great];
//                        ++less;
//                    } else { // pivot1 <= a[great] <= pivot2
//                        a[k] = a[great];
//                        moveList[k] = moveList[great];
//                    }
//                    /*
//                     * Here and below we use "a[i] = b; i--;" instead
//                     * of "a[i--] = b;" due to performance issue.
//                     */
//                    a[great] = ak;
//                    moveList[great] = mlk;
//                    --great;
//                }
//            }
//
//            // Swap pivots into their final positions
//            a[left]  = a[less  - 1]; a[less  - 1] = pivot1;
//            a[right] = a[great + 1]; a[great + 1] = pivot2;
//
//            moveList[left]  = moveList[less  - 1]; moveList[less  - 1] = mlpivot1;
//            moveList[right] = moveList[great + 1]; moveList[great + 1] = mlpivot1;
//
//            // Sort left and right parts recursively, excluding known pivots
//            sort(a, left, less - 2, leftmost, moveList);
//            sort(a, great + 2, right, false, moveList);
//
//            /*
//             * If center part is too large (comprises > 4/7 of the array),
//             * swap internal pivot values to ends.
//             */
//            if (less < e1 && e5 < great) {
//                /*
//                 * Skip elements, which are equal to pivot values.
//                 */
//                while (a[less] == pivot1) {
//                    ++less;
//                }
//
//                while (a[great] == pivot2) {
//                    --great;
//                }
//
//                /*
//                 * Partitioning:
//                 *
//                 *   left part         center part                  right part
//                 * +----------------------------------------------------------+
//                 * | == pivot1 |  pivot1 < && < pivot2  |    ?    | == pivot2 |
//                 * +----------------------------------------------------------+
//                 *              ^                        ^       ^
//                 *              |                        |       |
//                 *             less                      k     great
//                 *
//                 * Invariants:
//                 *
//                 *              all in (*,  less) == pivot1
//                 *     pivot1 < all in [less,  k)  < pivot2
//                 *              all in (great, *) == pivot2
//                 *
//                 * Pointer k is the first index of ?-part.
//                 */
//                outer:
//                for (int k = less - 1; ++k <= great; ) {
//                    int ak = a[k];
//                    int mlk = moveList[k];
//                    if (ak == pivot1) { // Move a[k] to left part
//                        a[k] = a[less];
//                        moveList[k] = moveList[less];
//                        a[less] = ak;
//                        moveList[less] = mlk;
//                        ++less;
//                    } else if (ak == pivot2) { // Move a[k] to right part
//                        while (a[great] == pivot2) {
//                            if (great-- == k) {
//                                break outer;
//                            }
//                        }
//                        if (a[great] == pivot1) { // a[great] < pivot2
//                            a[k] = a[less];
//                            moveList[k] = moveList[less];
//                            /*
//                             * Even though a[great] equals to pivot1, the
//                             * assignment a[less] = pivot1 may be incorrect,
//                             * if a[great] and pivot1 are floating-point zeros
//                             * of different signs. Therefore in float and
//                             * double sorting methods we have to use more
//                             * accurate assignment a[less] = a[great].
//                             */
//                            a[less] = pivot1;
//                            moveList[less] = mlpivot1;
//                            ++less;
//                        } else { // pivot1 < a[great] < pivot2
//                            a[k] = a[great];
//                            moveList[k] = moveList[great];
//                        }
//                        a[great] = ak;
//                        moveList[great] = mlk;
//                        --great;
//                    }
//                }
//            }
//
//            // Sort center part recursively
//            sort(a, less, great, false, moveList);
//
//        } else { // Partitioning with one pivot
//            /*
//             * Use the third of the five sorted elements as pivot.
//             * This value is inexpensive approximation of the median.
//             */
//            int pivot = a[e3];
//            int mlpivot = moveList[e3];
//
//            /*
//             * Partitioning degenerates to the traditional 3-way
//             * (or "Dutch National Flag") schema:
//             *
//             *   left part    center part              right part
//             * +-------------------------------------------------+
//             * |  < pivot  |   == pivot   |     ?    |  > pivot  |
//             * +-------------------------------------------------+
//             *              ^              ^        ^
//             *              |              |        |
//             *             less            k      great
//             *
//             * Invariants:
//             *
//             *   all in (left, less)   < pivot
//             *   all in [less, k)     == pivot
//             *   all in (great, right) > pivot
//             *
//             * Pointer k is the first index of ?-part.
//             */
//            for (int k = less; k <= great; ++k) {
//                if (a[k] == pivot) {
//                    continue;
//                }
//                int ak = a[k];
//                int mlk = moveList[k];
//                if (ak < pivot) { // Move a[k] to left part
//                    a[k] = a[less];
//                    moveList[k] = moveList[less];
//                    a[less] = ak;
//                    moveList[less] = mlk;
//                    ++less;
//                } else { // a[k] > pivot - Move a[k] to right part
//                    while (a[great] > pivot) {
//                        --great;
//                    }
//                    if (a[great] < pivot) { // a[great] <= pivot
//                        a[k] = a[less];
//                        moveList[k] = moveList[less];
//                        a[less] = a[great];
//                        moveList[less] = moveList[great];
//                        ++less;
//                    } else { // a[great] == pivot
//                        /*
//                         * Even though a[great] equals to pivot, the
//                         * assignment a[k] = pivot may be incorrect,
//                         * if a[great] and pivot are floating-point
//                         * zeros of different signs. Therefore in float
//                         * and double sorting methods we have to use
//                         * more accurate assignment a[k] = a[great].
//                         */
//                        a[k] = pivot;
//                        moveList[k] = mlpivot;
//                    }
//                    a[great] = ak;
//                    moveList[great] = mlk;
//                    --great;
//                }
//            }
//
//            /*
//             * Sort left and right parts recursively.
//             * All elements from center part are equal
//             * and, therefore, already sorted.
//             */
//            sort(a, left, less - 1, leftmost, moveList);
//            sort(a, great + 1, right, false, moveList);
//        }
//    }
}
