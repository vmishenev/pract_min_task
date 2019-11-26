

import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import static junit.framework.TestCase.*;

public class TestSet {
    private static final long SEED = 10;
    private static final int MAX_ITEM = 10;
    private static final float PERCENT_OF_ADDITIONS = 0.5f;
    Set<Integer> s;

    @BeforeEach
    public void createSet() {
        s = new HashSet<>();

    }

    @Test
    public void negateTest1() {
        assertTrue(s.isEmpty());
    }

    @Property
    public void negateTest2(@ForAll int param) {
        assertFalse(s.contains(param));
    }
    
    @Test
    public void unionOperation() {
        assertTrue(s.add(2));
        assertTrue(s.add(1));
        Set<Integer> a = new HashSet<>(); 
        a.addAll(Arrays.asList(new Integer[] {1, 3})); 
        s.addAll(a); 
        assertEquals(s, new HashSet<>(Arrays.asList(new Integer[] {1, 2, 3})));
    }
    
    @Test
    public void intersectionOperation() {
        assertTrue(s.add(2));
        assertTrue(s.add(1));
        Set<Integer> a = new HashSet<>(); 
        a.addAll(Arrays.asList(new Integer[] {1, 3})); 
        s.retainAll(a); 
        assertEquals(s, new HashSet<>(Arrays.asList(new Integer[] {1})));
    }
    
    
    @Test
    public void differenceOperation() {
        assertTrue(s.add(2));
        assertTrue(s.add(1));
        Set<Integer> a = new HashSet<>(); 
        a.addAll(Arrays.asList(new Integer[] {1, 3})); 
        s.removeAll(a); 
        assertEquals(s, new HashSet<>(Arrays.asList(new Integer[] {2})));
    }
    
    @Property
    public void addPropertyAddRemove(@ForAll int param) {
        assertTrue(s.add(param));
        assertTrue(s.remove(param));
        assertTrue(s.isEmpty());
    }
    
    
    @Test
    public void addRemoveAdd() {
        int n = 4;

        assertTrue(s.add(n));
        assertFalse(s.add(n));
        assertFalse(s.isEmpty());

        assertTrue(s.remove(n));
        assertFalse(s.remove(n));
        assertTrue(s.isEmpty());

        assertTrue(s.add(n));
        assertFalse(s.add(n));
        assertFalse(s.isEmpty());
    }

    @Test
    public void simple() {
        assertFalse(s.remove(-8));
        assertTrue(s.add(2));
        assertFalse(s.remove(-6));
        assertTrue(s.add(-7));
        assertFalse(s.isEmpty());
        assertTrue(s.add(9));
        assertFalse(s.add(9));
    }

    @Test
    public void testIterator() {

        int n = 100;
        int n2 = 10;
        for (int i = 0; i < n; i++) {
            assertTrue(s.add(i));
        }
        Set<Integer> items = new HashSet<>();
        Iterator<Integer> it = s.iterator();

        for (int i = n; i < n + n2; i++) {
            assertTrue(s.add(i));
        }

        while (it.hasNext()) {
            items.add(it.next());
        }

        assertEquals(n, items.size());
        for (int i = 0; i < n; i++) {
            assertTrue(items.contains(i));
        }
    }

    @Test
    public void addRemoveAddExtented() {

        int n = 100;
        for (int i = 0; i < n; i++) {
            assertTrue(s.add(i));
        }
        assertFalse(s.isEmpty());
        for (int i = 0; i < n; i++) {
            assertTrue(s.contains(i));
        }
        for (int i = 0; i < n; i++) {
            assertTrue(s.remove(i));
        }
        for (int i = 0; i < n; i++) {
            assertFalse(s.contains(i));
        }
        assertTrue(s.isEmpty());
    }

    @Test
    public void addRandoms() {
        Random rnd = new Random(SEED);;
        for (int i = 0; i < 100; i++) {
            int r = rnd.nextInt() % MAX_ITEM;

            boolean prePresent = s.contains(r);
            boolean added = s.add(r);
            assertTrue(s.contains(r));
            assertFalse(added == prePresent);
        }
    }

    @Test
    public void addAndRemoveRandoms() {
        Random rnd = new Random(SEED);;
        for (int i = 0; i < 100; i++) {
            int v = rnd.nextInt(MAX_ITEM);

            if (rnd.nextFloat() < PERCENT_OF_ADDITIONS) {
                boolean prePresent = s.contains(v);
                boolean added = s.add(v);
                assertTrue(s.contains(v));
                assertFalse(added == prePresent);
            } else {
                boolean prePresent = s.contains(v);
                boolean removed = s.remove(v);
                assertFalse(s.contains(v));
                assertEquals(prePresent, removed);
            }
        }
    }




}
