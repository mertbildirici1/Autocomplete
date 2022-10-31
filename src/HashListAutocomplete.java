import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashListAutocomplete implements Autocompletor {
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize = 0;

    public HashListAutocomplete(String[] terms, double[] weights){
		if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}
    if (terms.length != weights.length) {
			throw new IllegalArgumentException("terms and weights are not the same length");
		}
		initialize(terms,weights);
	}
    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (prefix.length() > MAX_PREFIX)
        {
          prefix = prefix.substring(0, MAX_PREFIX);
        }
        if (myMap.containsKey(prefix))
        {
          List<Term> all = myMap.get(prefix);
          List<Term> list = all.subList(0, Math.min(k, all.size()));
          return list;
        }
        else
        {
          List<Term> newList = new ArrayList<Term>();
          return newList;
        }
    }
    @Override
    public void initialize(String[] terms, double[] weights) {
          mySize = 0;
          myMap = new HashMap<>();
          for (int i = 0; i < terms.length; i++) {
            String t = terms[i];
            double w = weights[i];
            int upper = (MAX_PREFIX) > t.length() ? t.length() : (MAX_PREFIX);
            Term mertTerm = new Term(t, w);
      
            mySize += BYTES_PER_DOUBLE+ BYTES_PER_CHAR * t.length();
      
            for (int j = 0; j < upper+1; j++) {
              String key = t.substring(0, j);
              if(!myMap.containsKey(key)) 
              {
              mySize += BYTES_PER_CHAR * key.length();
              myMap.put(key, new ArrayList<>());
              }
              myMap.get(key).add(mertTerm);
            }
          }
          for (String key : myMap.keySet()) {
            Collections.sort(myMap.get(key), Comparator.comparing(Term::getWeight).reversed());
          }
    }
    @Override
    public int sizeInBytes() {
        return mySize;
    }
}