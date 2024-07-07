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
import ann.NeuralLayer;
import ann.NeuralNet;
import ann.Neuron;

import java.io.Serializable;
import java.util.ArrayList;

public class Genome implements Serializable {

    String code = null;
    int numAppends = 0;
    int numDeletions = 0;
    int numRecodes = 0;
    char last = 0;
    int index = 0;

    public Genome(String c) {
        if(this.code == null && c == null ) {
            this.code = GenomeFactory.create(Settings.GENOME_LENGTH);
        } else {
            this.code = c;
        }

    }


    public synchronized char last() {
        return last;
    }

    public synchronized char read(int loc) {
        if (loc < 0) {
            loc = Settings.GENOME_LENGTH + (int) code.charAt(Settings.GENOME_LENGTH + Gene.DECISION);
        }
        if (loc < code.length()) {
            last = code.charAt(loc);
            return last;
        } else if (loc >= code.length()) {
            try {
                int more = code.length() - loc;
                return code.charAt(more);
            } catch (Exception ex) {
            }
        }
        char c = code.charAt(Settings.GENOME_LENGTH + (int) code.charAt(Settings.GENOME_LENGTH + Gene.DECISION));
        last = c;
        return c;
    }

    public synchronized char read() {
        int loc = index;
        if (loc < 0) {
            loc = Settings.GENOME_LENGTH ;
        }
        if (loc < code.length()) {
            last = code.charAt(loc);
            return last;
        } else if (loc >= code.length()) {
                int more = code.length() - loc;
                if( more < code.length() ) {
                    if( more < 0 ){
                        more = Settings.GENOME_LENGTH ;
                    }
                    return code.charAt(more);
                }
        }
        int mloc = Settings.GENOME_LENGTH + code.charAt(Gene.DECISION);
        char c = code.charAt(mloc);
        last = c;
        advance();
        return c;
    }

    public static void recode(Genome ent, NeuralLayer input )
    {

        String header = ent.code.substring(0, Settings.GENOME_LENGTH);

        NeuralLayer layer = input;
        do {
            int sz = layer.neuron.length;
            for (int i = 0; i < sz; i++) {
                Neuron n = layer.neuron[i];
                ArrayList<Double> weights = n.weight;
                //header += Utility.dtoc(n.bias);
                for (int j = 0; j < weights.size(); j++) {
                    double w = weights.get(j);
                    //char c = Utility.dtoc(w);
                    //header += c;
                }
            }
            if( layer != null ) {
                layer = layer.nextLayer;
            }
        } while( layer != null );
        ent.code = header;

    }

    public synchronized void recode(int loc, char c) {

        if( c == '-'){
            return;
        }
        if (loc + Settings.GENOME_LENGTH > code.length()) {
            return;
        }
        char[] g = code.toCharArray();

        g[Settings.GENOME_LENGTH + loc] = c; //garl.Utility.flatten(c, 26);
        code = String.valueOf(g);
        numRecodes++;
    }

    public synchronized void jump(int loc) {
        if (index + loc < code.length()) {
            index += loc;
        }
    }

    public synchronized void advance() {
        index++;
        if (index >= code.length()) {
            index = Settings.GENOME_LENGTH + 1;
        }
        if (index < Settings.GENOME_LENGTH) {
            index = Settings.GENOME_LENGTH + 1;
        }
    }

    public synchronized void reverse() {
        index--;
        if (index <= Settings.GENOME_LENGTH) {
            index = Settings.GENOME_LENGTH + 1;
        }
    }


    public synchronized int index() {
        advance();
        return index;
    }

    public synchronized void mutate() {

        char[] c = code.toCharArray();
        int index = c.length - 1;
        int mutations = (int) Math.min((c[Gene.GENE_MUTATION_PROBABILITY] * 0.005 * c[Gene.GENE_MUTATION_MULTIPLIER]), Settings.GENOME_LENGTH / 2);
        for (int j = 0; j <= mutations; j++) {
            index = (int) (Math.random()) * index;
            if (index < 0) {
                index = 0;
            } else if (index >= c.length) {
                index = c.length - 1;
            }
            try {
                char t = c[index];
                c[index] = c[index - 1];
                c[index - 1] = t;
            } catch (Exception ex) {
            }
        }
        String time = "" + Long.toHexString(System.currentTimeMillis());
        time = reverse(time);

        c[Gene.KIN] = KinFactory.create(time.charAt(0));
        code = String.valueOf(c);
    }

    public static String reverse(String in) {
        char[] c = in.toCharArray();
        String o = "";
        for (int i = in.length() - 1; i > 0; i--) {
            o += c[i];
        }
        return o;
    }
}
