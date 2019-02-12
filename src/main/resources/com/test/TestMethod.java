
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.junit.Test;

public class TestMethod {
	
	@Test
	 public static void main(String[] args) {
		
		 java.util.List<Object> list = new ArrayList<>();
		 list.add("1");
		 list.add("2");
		 list.add("2");
		 list.add("3");
		 list.add("4");
		 list.add("45");
		Iterator<Object> iterator = list.iterator();
		while (iterator.hasNext()) {
				Object next = iterator.next();
				System.out.println(next);
		}
	}
 
	 
	 public static List removeRepeat(List list) {
		 HashSet<Object> hashSet = new HashSet<>(list);
		 list.clear();
		 list.addAll(hashSet);
		 return list;
		 
	 }
}
