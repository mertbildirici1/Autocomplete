import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class    PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
        return new PrefixComparator(prefix);
    }


    @Override
    /**
     * Use at most myPrefixSize characters from each of v and w
     * to return a value comparing v and w by words. Comparisons
     * should be made based on the first myPrefixSize chars in v and w.
     * @return < 0 if v < w, == 0 if v == w, and > 0 if v > w
     */
    public int compare(Term v, Term w) {
        //if (v.getWord().compareTo(w.getWord() == 0)){ return 0;}
        // String vWord = v.getWord();
        // String wWord = w.getWord();

        if (v.getWord().length()< myPrefixSize || w.getWord().length() < myPrefixSize) {
            int c = Math.min(v.getWord().length(), w.getWord().length());
            
            for (int a = 0; a<c; a++ ) {
                if (!(v.getWord().charAt(a) == w.getWord().charAt(a)))
                {
                    return v.getWord().charAt(a) - w.getWord().charAt(a);
                };
            }
            
                return v.getWord().length() - w.getWord().length();
        }
        else {
            if (v.getWord().substring(0,myPrefixSize).equals(w.getWord().substring(0,myPrefixSize))) {
                return 0;
            }
            for (int a = 0; a<myPrefixSize; a++ ) {
                if (!(v.getWord().charAt(a) == w.getWord().charAt(a)))
                {
                    return v.getWord().charAt(a) - w.getWord().charAt(a);
                };

        }
        return v.getWord().substring(0, myPrefixSize).compareTo(w.getWord().substring(0, myPrefixSize));
    }
        // return 0;

        // else {
        //     return v.getWord().substring(0, myPrefixSize).compareTo(w.getWord().substring(0, myPrefixSize));
        // }
        // else if (v.getWord().length()<= myPrefixSize) {
        //     return v.getWord().compareTo(w.getWord().substring(0, myPrefixSize));
            
        // }
        // else if  (w.getWord().length() <= myPrefixSize) {
        //     return v.getWord().substring(0, myPrefixSize).compareTo(w.getWord());
            

        // }
        // else {
        //     for (int i = 0; i<myPrefixSize; i++) {
        //         if (!(v.getWord().charAt(i) == w.getWord().charAt(i)))
        //         {
        //             return v.getWord().charAt(i) - w.getWord().charAt(i);
        //             //return v.getWord().substring(0, i).compareTo(w.getWord().substring(0, i));
        //         };
                
        //     }
        // }
        // if (myPrefixSize == 0) {
        //     return v.getWord().charAt(0)-w.getWord().charAt(0);

        // }

        // if (myPrefixSize == 1) {
        //     return v.getWord().charAt(0)-w.getWord().charAt(0);

        // }
       
        // return v.getWord().substring(0, myPrefixSize-1).compareTo(w.getWord().substring(0, myPrefixSize-1));
    }
}
