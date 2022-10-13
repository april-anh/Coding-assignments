# PA1 CS3230

## 1 Problem statement

You are given 2n distinct integers, a1, a2, a3, . . . , an, b1, b2, . . . , bn from 1 to 2n (inclusive).
You are required to insert elements of b into the array a. Let c be the resulting array of size 2n
after the insertion. For example, if a = [5, 4, 1, 2] and b = [8, 3, 6, 7], a possible way to insert elements
of b into a is c = [5, 4, 3, 6, 8, 1, 7, 2] (elements of b are underlined).
Note that the relative order of elements from array a must be preserved (i.e. if 5 comes before 4 in
array a, then 5 must appear before 4 in array c. Among all possible insertion sequences, what is the
minimum possible number of inversions c can have?
(An inversion is a pair (i, j) such that i < j and a[i] > a[j].)

## 2 Input
The input consists of three lines. The first line of the input will contain a single integer n. The second
line of the input will contain n space separated integers a1 a2 a3 . . . an. The third line of the input
will contain n space separated integers b1 b2 b3 . . . bn.

## 3 Output
Output a single integer, containing the minimum possible number of inversions c can have, followed
by a newline character.

## 4 Sample input and output
Sample input

4

5 4 1 2

8 3 6 7

Sample output
7

Explanation for sample: among all possible ways to insert b into a, the minimum number of
inversions is minimized when c = [3, 5, 4, 1, 2, 6, 7, 8].

## Constraints
`MainPartial.java`
- n ≤ 104
- O(n^2)

`Main.java`
- n ≤ 106
- O(n log n)
