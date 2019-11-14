import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

public class Testing {

  /**
   * Note: These tests are not necessarily exhaustive, although they
   * do provide you with a good roadmap to follow in developing your
   * code for this project.
   * 
   * Add some of your own tests, but do not remove the provided tests 
   * or change any of the expected results.
   */

  @Test
  public void emptyFreqTable() {
    FrequencyTable ft = new FrequencyTable();
    assertEquals(0, ft.size());
    assertTrue(ft.isEmpty());
    assertTrue(0 == ft.get('a'));
    assertTrue(0 == ft.get('b'));
    assertTrue(0 == ft.get('c'));    
  }

  @Test
  public void smallFreqTable() {
    FrequencyTable ft = new FrequencyTable("abacca");
    assertEquals(3, ft.size());
    assertFalse(ft.isEmpty());
    assertTrue(3 == ft.get('a'));
    assertTrue(1 == ft.get('b'));
    assertTrue(2 == ft.get('c'));    
  }

  @Test
  public void mediumFreqTable() {
    FrequencyTable ft = new FrequencyTable("a");
    assertTrue(1 == ft.get('a'));
    ft = new FrequencyTable("aaaaaaaa");
    assertTrue(8 == ft.get('a'));
    ft = new FrequencyTable("ab");
    assertTrue(1 == ft.get('a'));
    assertTrue(1 == ft.get('b'));
    assertTrue(0 == ft.get('c'));
    assertTrue(0 == ft.get('d'));
  }

  @Test
  public void bigFreqTable() {
    String alpha = "abcdefghijklmnopqrstuvwxyz";
    alpha += alpha.toUpperCase();
    Random gen = new Random();
    int[] freqs = new int[alpha.length()];
    for (int i = 0; i < freqs.length; i++)
      freqs[i] = gen.nextInt(1000);
    String text = "";
    for (int i = 0; i < alpha.length(); i++) 
      for (int j = 0; j < freqs[i]; j++)
        text += alpha.charAt(i);
    // Do some cuts.
    for (int i = 0; i < freqs.length; i++) {
      int pos = gen.nextInt(text.length());
      text = text.substring(pos) + text.substring(0, pos);
    }
    FrequencyTable ft = new FrequencyTable(text);
    for (int i = 0; i < alpha.length(); i++)
      assertTrue(freqs[i] == ft.get(alpha.charAt(i)));
    String other = "0123456789!@#$%^&*()";
    for (int i = 0; i < other.length(); i++)
      assertTrue(0 == ft.get(other.charAt(i)));
  }

  @Test
  public void heapUtils() {
    /**
     *       0
     *     /   \
     *    1     2
     *   / \   /
     *  3  4  5
     */
    assertEquals(1, Heap.getLeft(0));
    assertEquals(3, Heap.getLeft(1));
    assertEquals(5, Heap.getLeft(2));
    assertEquals(2, Heap.getRight(0));
    assertEquals(4, Heap.getRight(1));
    assertEquals(0, Heap.getParent(1));
    assertEquals(0, Heap.getParent(2));
    assertEquals(1, Heap.getParent(3));
    assertEquals(1, Heap.getParent(4));
    assertEquals(2, Heap.getParent(5));
  }

  /**********************************************************************************
   * We leave it to you to write tests for Heap.swap(), Heap.siftUp(), and 
   * Heap.siftDown(). Here would be a good place to put those tests.
   **********************************************************************************/
  
  @Test
  public void smallMinHeap() {
    Heap<Integer> heap = new Heap<>((x, y) -> x.compareTo(y));
    Comparator<Integer> comp = heap.comparator();
    assertTrue(comp.compare(2, 3) < 0);
    assertTrue(comp.compare(4, 4) == 0);
    assertTrue(comp.compare(5, 4) > 0);   
    assertTrue(heap.isEmpty());
    assertEquals(0, heap.size());
    heap.insert(5);
    assertFalse(heap.isEmpty());
    assertEquals(1, heap.size());
    assertTrue(5 == heap.peek());
    assertEquals(1, heap.size());
    assertTrue(5 == heap.delete());
    assertTrue(heap.isEmpty());
    heap.insert(1);
    heap.insert(2);
    heap.insert(3);
    assertEquals(3, heap.size());
    assertTrue(1 == heap.delete());
    assertTrue(2 == heap.delete());
    assertTrue(3 == heap.delete());
    heap.insert(1);
    heap.insert(3);
    heap.insert(2);
    assertTrue(1 == heap.delete());
    assertTrue(2 == heap.delete());
    assertTrue(3 == heap.delete());
    heap.insert(2);
    heap.insert(1);
    heap.insert(3);
    assertTrue(1 == heap.delete());
    assertTrue(2 == heap.delete());
    assertTrue(3 == heap.delete());
    heap.insert(2);
    heap.insert(3);
    heap.insert(1);
    assertTrue(1 == heap.delete());
    assertTrue(2 == heap.delete());
    assertTrue(3 == heap.delete());
    heap.insert(3);
    heap.insert(1);
    heap.insert(2);
    assertTrue(1 == heap.delete());
    assertTrue(2 == heap.delete());
    assertTrue(3 == heap.delete());
    heap.insert(3);
    heap.insert(2);
    heap.insert(1);
    assertTrue(1 == heap.delete());
    assertTrue(2 == heap.delete());
    assertTrue(3 == heap.delete());
  }

  @Test
  public void smallMaxHeap() {
    Heap<Integer> heap = new Heap<>((x, y) -> y.compareTo(x));
    heap.insert(1);
    heap.insert(2);
    heap.insert(3);
    assertTrue(3 == heap.delete());
    assertTrue(2 == heap.delete());
    assertTrue(1 == heap.delete());
    heap.insert(1);
    heap.insert(3);
    heap.insert(2);
    assertTrue(3 == heap.delete());
    assertTrue(2 == heap.delete());
    assertTrue(1 == heap.delete());
    heap.insert(2);
    heap.insert(1);
    heap.insert(3);
    assertTrue(3 == heap.delete());
    assertTrue(2 == heap.delete());
    assertTrue(1 == heap.delete());
    heap.insert(2);
    heap.insert(3);
    heap.insert(1);
    assertTrue(3 == heap.delete());
    assertTrue(2 == heap.delete());
    assertTrue(1 == heap.delete());
    heap.insert(3);
    heap.insert(1);
    heap.insert(2);
    assertTrue(3 == heap.delete());
    assertTrue(2 == heap.delete());
    assertTrue(1 == heap.delete());
    heap.insert(3);
    heap.insert(2);
    heap.insert(1);
    assertTrue(3 == heap.delete());
    assertTrue(2 == heap.delete());
    assertTrue(1 == heap.delete());
  }

  @Test
  public void heapOfStrings() {
    // Shorter strings come first.
    Heap<String> heap = new Heap<>((s, t) -> s.length() - t.length());
    Comparator<String> comp = heap.comparator();
    assertTrue(comp.compare("zz", "aaaa") < 0);
    assertTrue(comp.compare("cat", "dog") == 0);
    assertTrue(comp.compare("bbbbbb", "aa") > 0);
    heap.insert("aaa");
    heap.insert("bb");
    heap.insert("cccccc");
    assertEquals("bb", heap.delete());
    assertEquals("aaa", heap.delete());
    assertEquals("cccccc", heap.delete());
  }

  @Test
  public void mediumMinHeap() {    
    Heap<Integer> heap = new Heap<>((x, y) -> x.compareTo(y));
    for (int x : new int[] { 9, 3, 4, 7, 6, 8, 2, 1, 5, 10 }) 
      heap.insert(x);
    for (int i = 1; i <= 10; i++) {
      assertTrue(i == heap.peek());
      assertTrue(i == heap.delete());
    }
  }

  @Test
  public void heapFormation() {
    Heap<Integer> heap = new Heap<>((x, y) -> y.compareTo(x));
    for (int x : new int[] { 14, 18, 9, 3, 12, 4, 19, 7, 11, 6, 16, 8, 13, 2, 20, 1, 5, 10, 15, 17 }) 
      heap.insert(x);
    assertEquals("[20, 17, 19, 15, 16, 13, 18, 5, 11, 14, 12, 4, 8, 2, 9, 1, 3, 7, 10, 6]",
        heap.toString());
    for (int i = heap.size(); i >= 1; i--) {
      assertTrue(i == heap.peek());
      assertTrue(i == heap.delete());
    }
    assertTrue(heap.isEmpty());
  }

  @Test
  public void smallHuff() {
    FrequencyTable ft = new FrequencyTable("aaaabbc");
    assertTrue(4 == ft.get('a'));
    assertTrue(2 == ft.get('b'));
    assertTrue(1 == ft.get('c'));
    HuffmanTree ht = new HuffmanTree(ft);
    assertTrue(7 == ht.root.priority);
    HuffmanTree.Node smallChild = ht.root.left.priority != 3? ht.root.right: ht.root.left;
    HuffmanTree.Node largerChild = smallChild == ht.root.left? ht.root.right: ht.root.left;
    assertTrue(3 == smallChild.priority);
    assertTrue(4 == largerChild.priority);
    HuffmanTree.Node smallerChild = smallChild.right.priority != 2? smallChild.left: smallChild.right;
    HuffmanTree.Node smallestChild = smallerChild == smallChild.left? smallChild.right: smallChild.left;
    assertTrue(2 == smallerChild.priority);
    assertTrue(1 == smallestChild.priority);
    assertTrue('a' == largerChild.key);
    assertTrue('b' == smallerChild.key);
    assertTrue('c' == smallestChild.key);
  }

  @Test
  public void lookupHuff() {
    HuffmanTree ht = new HuffmanTree(new FrequencyTable("aaaabbc"));
    HuffmanTree.Node smallChild = ht.root.left.priority != 3? ht.root.right: ht.root.left;
    HuffmanTree.Node largeChild = ht.root.left == smallChild? ht.root.right: ht.root.left;
    String smallChildPath = ht.root.left == smallChild? "0" : "1";
    String largeChildPath = smallChildPath.equals("0")? "1" : "0";
    String secondLargestChildPath = ht.lookup('b');

    assertEquals(largeChildPath, ht.lookup('a'));
    assertTrue(secondLargestChildPath.equals(smallChildPath + "0") || secondLargestChildPath.equals(smallChildPath + "1"));
    assertEquals(secondLargestChildPath.equals("00")? "01" : "00", ht.lookup('c'));
  }

  @Test
  public void decodeHuff() {
    HuffmanTree ht = new HuffmanTree(new FrequencyTable("aaaabbc"));
    String aNodePath = ht.root.left.key == 'a' ? "0" : "1";
    String bcNodePath = aNodePath.equals("1") ? "0" : "1";
    HuffmanTree.Node bcNode = bcNodePath.equals("0") ? ht.root.left : ht.root.right;
    String bNodePath = bcNodePath + (bcNode.left.key == 'b' ? "0": "1");
    String cNodePath = bNodePath.equals(bcNodePath + "0") ? bcNodePath + "1" : bcNodePath + "0";

    assertEquals('a', ht.decodeChar(aNodePath));
    assertEquals('b', ht.decodeChar(bNodePath));
    assertEquals('c', ht.decodeChar(cNodePath));
  }

  @Test
  public void notInCodeBook() {
    CodeBook book = new CodeBook("a");
    try {
      book.encodeChar('b');
    }
    catch (EncodeException e) {
      assertTrue(e.getMessage().endsWith("b"));
    }
    try {
      book.getHuffmanTree().decodeChar("101");
    }
    catch (DecodeException e) {
      assertTrue(e.getMessage().endsWith("101"));
    }
  }

  @Test
  public void smallZips() {
    String text = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "bbbbbbbbbbbbb"
        + "ccccccccccc"
        + "dddddddddddddddd" 
        + "eeeeeeeee"
        + "fffff";
    CodeBook cb = new CodeBook(text);
    Zipper zipper = new Zipper(cb);

    assertEquals(cb.encodeChar('a'), zipper.encode("a"));
    assertEquals(cb.encodeChar('b'), zipper.encode("b"));
    assertEquals(cb.encodeChar('c'), zipper.encode("c"));
    assertEquals(cb.encodeChar('d'), zipper.encode("d"));
    assertEquals(cb.encodeChar('e'), zipper.encode("e"));
    assertEquals(cb.encodeChar('f'), zipper.encode("f"));
    assertEquals(cb.encodeChar('a') + cb.encodeChar('a'), zipper.encode("aa"));
    assertEquals(cb.encodeChar('b') + cb.encodeChar('b'), zipper.encode("bb"));
    assertEquals(cb.encodeChar('c') + cb.encodeChar('c'), zipper.encode("cc"));
    assertEquals(cb.encodeChar('d') + cb.encodeChar('d'), zipper.encode("dd"));
    assertEquals(cb.encodeChar('e') + cb.encodeChar('e'), zipper.encode("ee"));
    assertEquals(cb.encodeChar('f') + cb.encodeChar('f'), zipper.encode("ff"));
    assertEquals(cb.encodeChar('a') + cb.encodeChar('b'), zipper.encode("ab"));
    assertEquals(cb.encodeChar('b') + cb.encodeChar('c'), zipper.encode("bc"));
    assertEquals(cb.encodeChar('c') + cb.encodeChar('d'), zipper.encode("cd"));
    assertEquals(cb.encodeChar('d') + cb.encodeChar('e'), zipper.encode("de"));
    assertEquals(cb.encodeChar('e') + cb.encodeChar('f'), zipper.encode("ef"));
    assertEquals(cb.encodeChar('a') + cb.encodeChar('b') + cb.encodeChar('c'), zipper.encode("abc"));
    assertEquals(cb.encodeChar('b') + cb.encodeChar('c') + cb.encodeChar('d'), zipper.encode("bcd"));
    assertEquals(cb.encodeChar('c') + cb.encodeChar('d') + cb.encodeChar('e'), zipper.encode("cde"));
    assertEquals(cb.encodeChar('d') + cb.encodeChar('e') + cb.encodeChar('f'), zipper.encode("def"));

    assertEquals("a", zipper.decode(zipper.encode("a")));
    assertEquals("b", zipper.decode(zipper.encode("b")));
    assertEquals("c", zipper.decode(zipper.encode("c")));
    assertEquals("d", zipper.decode(zipper.encode("d")));
    assertEquals("e", zipper.decode(zipper.encode("e")));
    assertEquals("f", zipper.decode(zipper.encode("f")));
    assertEquals("aa", zipper.decode(zipper.encode("aa")));
    assertEquals("bb", zipper.decode(zipper.encode("bb")));
    assertEquals("cc", zipper.decode(zipper.encode("cc")));
    assertEquals("dd", zipper.decode(zipper.encode("dd")));
    assertEquals("ee", zipper.decode(zipper.encode("ee")));
    assertEquals("ff", zipper.decode(zipper.encode("ff")));
    assertEquals("ab", zipper.decode(zipper.encode("ab")));
    assertEquals("bc", zipper.decode(zipper.encode("bc")));
    assertEquals("cd", zipper.decode(zipper.encode("cd")));
    assertEquals("de", zipper.decode(zipper.encode("de")));
    assertEquals("ef", zipper.decode(zipper.encode("ef")));
    assertEquals("abc", zipper.decode(zipper.encode("abc")));
    assertEquals("bcd", zipper.decode(zipper.encode("bcd")));
    assertEquals("cde", zipper.decode(zipper.encode("cde")));
    assertEquals("def", zipper.decode(zipper.encode("def")));

    assertEquals(text, zipper.decode(zipper.encode(text)));    
  }

  @Test
  public void bigRandomZips() {
    String text = makeUniformRandomText(10000);
    CodeBook book = new CodeBook(text);
    Zipper zipper = new Zipper(book);
    assertEquals(text, zipper.decode(zipper.encode(text)));
    text = makeBiasedRandomText(10000);
    book = new CodeBook(text);
    zipper = new Zipper(book);
    assertEquals(text, zipper.decode(zipper.encode(text)));
  }
  
  /**********************************************************************************
   * When you've reached this point, run the Driver and start writing your report.
   **********************************************************************************/

  private static String makeUniformRandomText(int n) {
    Random rand = new Random();
    String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int m = alpha.length();
    StringBuilder ans = new StringBuilder();
    for (int i = 0; i < n; i++) 
      ans.append(alpha.charAt(rand.nextInt(m)));
    return ans.toString();
  }

  private static String makeBiasedRandomText(int n) {
    Random rand = new Random();
    String alphaFrequent = "ABCDEFGH";
    String alphaRare = "IJKLMNOPQRSTUVWXYZ";
    StringBuilder ans = new StringBuilder();
    for (int i = 0; i < n; i++) 
      if (rand.nextInt(10) == 0)
        ans.append(alphaRare.charAt(rand.nextInt(alphaRare.length())));
      else
        ans.append(alphaFrequent.charAt(rand.nextInt(alphaFrequent.length())));
    return ans.toString();
  }

}