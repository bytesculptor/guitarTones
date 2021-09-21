/*
 * MIT License
 *
 * Copyright (c) 2021 Byte Sculptor Software
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.bytesculptor.guitartones.utilities;

public class Tuning {
    public final static String[] STRING_BUTTONS = {"e", "B", "G", "D", "A", "E"};
    public final static String[] SCALE = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    public static final String[] TUNING = {"Standard", "Eb", "D", "Drop D", "Drop C", "Drop C#"};

    public final static int[] OFFSET_STD = {4, 11, 7, 2, 9, 4};
    public final static int[] OFFSET_Eb = {3, 10, 6, 1, 8, 3};
    public final static int[] OFFSET_D = {2, 9, 5, 0, 7, 2};
    public final static int[] OFFSET_DROP_D = {4, 11, 7, 2, 9, 2};
    public final static int[] OFFSET_DROP_C = {2, 9, 5, 0, 7, 2};
    public final static int[] OFFSET_DROP_CIS = {3, 10, 6, 1, 8, 1};
}