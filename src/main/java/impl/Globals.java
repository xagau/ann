package impl;
/** Copyright (c) 2019-2022 placeh.io,
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author xagau
 * @email seanbeecroft@gmail.com
 *
 */
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.concurrent.Semaphore;

public class Globals {

    public static int minor = 53;
    public static int major = 1;

    public static String title = "Genetic Based Multi-Agent Reinforcement Learning " + major + "." + minor;
    public static boolean screenSaverMode = false;

    public static boolean verbose = false;
    public static Semaphore semaphore = new Semaphore(100);


    public static double increment = 0.00001000;
    public static int ATC = 10;
    public static int TOP = 10;

    public static double maxPayout = 2.50;
    public static double minPayout = 0.5;
    public static double minManualPayout = 0.01;

    public static int FPS = 32;

    public static int epoch = 1;

    public static boolean installed = false;
    public static boolean benchmark = false;

    public static boolean sound = true;
    public static int repeat = 12;
    public static long soundDuration = 1500;

    public static int neuronIobe = 0;
    static {
        try {
            Locale.setDefault(Locale.US);
        } catch(Exception ex) { }
    }

}

