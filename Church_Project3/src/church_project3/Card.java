/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package church_project3;

/**
 *
 * @author trent_000
 */
public class Card implements Comparable<Card> {

    private String suite;
    private String rank;

    public Card(String suite, String rank) {
        this.suite = suite;
        this.rank = rank;
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append(rank.toLowerCase());
        sb.append("_of_");
        sb.append(suite.toLowerCase());
        sb.append(".png");
        return sb.toString();
    }

    public String getSuite() {
        return suite;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suite;
    }

    @Override
    public int compareTo(Card t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
