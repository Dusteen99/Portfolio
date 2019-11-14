import java.util.Random;


/**
 * 
 * @author Dustin Adkins
 */

public class SequenceAligner {
  private static Random gen = new Random();

  private String x, y;
  private int n, m;
  private String alignedX, alignedY;
  private Result[][] cache;
  private Judge judge;

  /**
   * Generates a pair of random DNA strands, where x is of length n and
   * y has some length between n/2 and 3n/2, and aligns them using the 
   * default judge.
   */
  public SequenceAligner(int n) {
    this(randomDNA(n), randomDNA(n - gen.nextInt(n / 2) * (gen.nextInt(2) * 2 - 1)));
  }

  /**
   * Aligns the given strands using the default judge.
   */
  public SequenceAligner(String x, String y) {
    this(x, y, new Judge());
  }
  
  /**
   * Aligns the given strands using the specified judge.
   */
  public SequenceAligner(String x, String y, Judge judge) {
    this.x = x.toUpperCase();
    this.y = y.toUpperCase();
    this.judge = judge;
    n = x.length();
    m = y.length();
    cache = new Result[n + 1][m + 1];
    fillCache();
    traceback();
  }

  /**
   * Returns the x strand.
   */
  public String getX() {
    return x;
  }

  /**
   * Returns the y strand.
   */
  public String getY() {
    return y;
  }
  
  /**
   * Returns the judge associated with this pair.
   */
  public Judge getJudge() {
    return judge;
  }
  
  /**
   * Returns the aligned version of the x strand.
   */
  public String getAlignedX() {
    return alignedX;
  }

  /**
   * Returns the aligned version of the y strand.
   */
  public String getAlignedY() {
    return alignedY;
  }

  /**
   * Returns the Result corresponding to the biggest payoff among the scores
   * for the three different directions.
   *
   * Tiebreaking Rule: So that your code will identify the same alignment
   * as is expected in Testing, we establish the following preferred order
   * of operations: M (diag), I (left), D (up). This only applies when you
   * are picking the operation with the biggest payoff and two or more
   * operations have the same max score.
   */

  public static Result bestResult(int diag, int left, int up) {
	  Direction parent;
	  int max = Math.max(diag, Math.max(left, up));
	  if(max == diag) {
		  parent = Direction.DIAGONAL;
	  }
	  else if(max == left) {
		  parent = Direction.LEFT;
	  }
	  else {
		  parent = Direction.UP;
	  }
	  return new Result(max, parent);
  }

  /**
   *  TODO: Solve the alignment problem using bottom-up dynamic programming
   *  algorithm described in lecture. When you're done, cache[i][j] will hold
   *  the result of solving the alignment problem for the first i characters
   *  in x and the first j characters in y.
   *  
   *  Your algorithm must run in O(n * m) time, where n is the length of x
   *  and m is the length of y.
   *  
   *  Ordering convention: So that your code will identify the same alignment
   *  as is expected in Testing, we establish the following preferred order
   *  of operations: M (diag), I (left), D (up). This only applies when you
   *  are picking the operation with the biggest payoff and two or more  
   *  operations have the same max score. 
   */
  private void fillCache() {
	  for(int i = 0; i <= n; i++) {
		  for(int p = 0; p <= m; p++) {
			  //Conditions for items on top row or first column
			  if(i == 0 && p == 0) {
				  cache[i][p] = new Result(0, Direction.NONE);
			  }
			  else if(i == 0) {
				  cache[i][p] = new Result(cache[i][p-1].getScore() + judge.getGapCost(), Direction.LEFT);
			  }
			  else if(p == 0) {
				  cache[i][p] = new Result(cache[i-1][p].getScore() + judge.getGapCost(), Direction.UP);
			  }
			  //All other indicies can be found by the surrounding caches, which are 
			  //already determined
			  else {
				  //If statement to determine whether to use MatchCost or MismatchCost
				  if(x.charAt(i - 1) == y.charAt(p - 1)) {
					  cache[i][p] = bestResult(cache[i-1][p-1].getScore() + judge.getMatchCost(), 
							  cache[i][p-1].getScore() + judge.getGapCost(),
							  cache[i-1][p].getScore() + judge.getGapCost());
				  }
				  else {
					  cache[i][p] = bestResult(cache[i-1][p-1].getScore() + judge.getMismatchCost(),
							  cache[i][p-1].getScore() + judge.getGapCost(),
							  cache[i-1][p].getScore() + judge.getGapCost());
				  } 
			  }
		  }
	  }
  }
  
  
  /**
   * Returns the result of solving the alignment problem for the 
   * first i characters in x and the first j characters in y. You can
   * find the result in O(1) time by looking in your cache.
   */
  public Result getResult(int i, int j) {
	  return cache[i][j];
  }
  

  /**
   * Mark the path by tracing back through parent pointers, starting
   * with the Result in the lower right corner of the cache. Run Result.markPath()
   * on each Result along the path. The GUI will highlight all such marked cells
   * when you check 'Show path'. As you're tracing back along the path, build 
   * the aligned strings in alignedX and alignedY (using Constants.GAP_CHAR
   * to denote a gap in the strand).
   * 
   * Your algorithm must run in O(n + m) time, where n is the length of x
   * and m is the length of y. 
   */
  private void traceback() {
	  alignedX = "";
	  alignedY = "";
	  int i = n;
	  int j = m;
	  cache[i][j].markPath();
	  //Marking bottom right corner
	  if(cache[i][j].getParent() == Direction.DIAGONAL) {
			  alignedX += x.charAt(i-1);
			  alignedY += y.charAt(j-1);
			  i -= 1;
	          j -= 1;
	          cache[i][j].markPath();
			  
	  }
	  else if(cache[i][j].getParent() == Direction.LEFT) {
			  alignedX = "_";
			  alignedY += y.charAt(j-1);
			  j -= 1;
			  cache[i][j].markPath();
	  }
	  else if(cache[i][j].getParent() == Direction.LEFT) {
			  alignedX += x.charAt(i-1);
			  alignedY = "_";
			  i -= 1;
			  cache[i][j].markPath();
	  } 
	//Loop until we reach the top left, following parents
    while(cache[i][j].getParent() != Direction.NONE) {
    	Direction dir = cache[i][j].getParent();
    	if(dir == Direction.DIAGONAL) {
    		i -= 1;
    		j -= 1;
    		cache[i][j].markPath();
    		alignedX = x.charAt(i) + alignedX;
    		alignedY = y.charAt(j) + alignedY;
    	}
    	else if(dir == Direction.LEFT) {
    		j -= 1;
    		cache[i][j].markPath();
    		alignedX = "_" + alignedX;
    		alignedY = y.charAt(j) + alignedY;
    	}
    	else {
    		i -= 1;
    		cache[i][j].markPath();
    		alignedX = x.charAt(i) + alignedX;
    		alignedY = "_" + alignedY;
    	}
    }
    //Mark top left
    cache[0][0].markPath();
  }

  /**
   * Returns true iff these strands are seemingly aligned.
   */
  public boolean isAligned() {
    return alignedX != null && alignedY != null &&
        alignedX.length() == alignedY.length();
  }
  
  /**
   * Returns the score associated with the current alignment.
   */
  public int getScore() {
    if (isAligned())
      return judge.score(alignedX, alignedY);
    return 0;
  }

  /**
   * Returns a nice textual version of this alignment.
   */
  public String toString() {
    if (!isAligned())
      return "[X=" + x + ",Y=" + y + "]";
    final char GAP_SYM = '.', MATCH_SYM = '|', MISMATCH_SYM = ':';
    StringBuilder ans = new StringBuilder();
    ans.append(alignedX).append('\n');
    int n = alignedX.length();
    for (int i = 0; i < n; i++)
      if (alignedX.charAt(i) == Constants.GAP_CHAR || alignedY.charAt(i) == Constants.GAP_CHAR)
        ans.append(GAP_SYM);
      else if (alignedX.charAt(i) == alignedY.charAt(i))
        ans.append(MATCH_SYM);
      else
        ans.append(MISMATCH_SYM);
    ans.append('\n').append(alignedY).append('\n').append("score = ").append(getScore());
    return ans.toString();
  }

  /**
   * Returns a DNA strand of length n with randomly selected nucleotides.
   */
  private static String randomDNA(int n) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++)
      sb.append("ACGT".charAt(gen.nextInt(4)));
    return sb.toString();
  }

}
