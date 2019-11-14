import java.util.HashMap;


public class FrequencyTable extends HashMap<Character, Integer> {
  /**
   * Constructs an empty table.
   */
  public FrequencyTable() {
    super();
  }
    
  /**
   * Constructs a table of character counts from the given text string.
   */
  public FrequencyTable(String text) {
	  int length = text.length();
	  for(int i = 0; i < length; i++) {
		  int freq = get(text.charAt(i));
		  freq += 1;
		  put(text.charAt(i), freq);
	  }  
  }
  
  /**
   * Returns the count associated with the given character. In the case that
   * there is no association of ch in the map, return 0.
   */
  @Override
  public Integer get(Object ch) {
	  if(super.get(ch) != null) {
		  return super.get(ch);
	  }
    return 0;
  }
}
