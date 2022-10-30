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
    
		if (terms == null || weights == null) 
        {
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
            List<Term> list = new ArrayList<Term>();
            return list;
        }
        // TODO Auto-generated method stub
       //return null;
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        // TODO Auto-generated method stub
        Map<String, List<Term>> myMap = new HashMap<String, List<Term>>();
        int i = 0;
        List<Term> al = new ArrayList<Term>();
        myMap.put("", al);
        for (String term : terms)
        {

            Term toAdd = new Term(term, weights[i]);
            String[] temp = term.split("");
            String loop = "";
            myMap.get(loop).add(toAdd);
            
            while (loop.length() < MAX_PREFIX + 1)
            {
                for (String charac : temp)
                {
                    loop += charac;
                    if (myMap.containsKey(loop))
                    {
                        myMap.get(loop).add(toAdd);
                    }
                    else
                    {
                        myMap.put(loop, al);
                    }
                }
            }
            i++;
        }
        for (String key: myMap.keySet()){
            Collections.sort(myMap.get(key), Comparator.comparing(Term:: getWeight).reversed());
        }
        
    }

    @Override
    public int sizeInBytes() {
        if (mySize == 0) {
            for (String key : myMap.keySet()) {
                mySize += key.length()*BYTES_PER_CHAR;
                List<Term> n = myMap.get(key);
                for (Term t: n) {
                    mySize= mySize+ t.getWord().length()*BYTES_PER_CHAR + BYTES_PER_DOUBLE;
                }
            }
        }
        return mySize;
    }
    
}

