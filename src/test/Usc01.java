package test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Description
 * @author caorong
 * @date 2013-3-22
 * http://uva.onlinejudge.org/external/1/119.html
 */
public class Usc01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cin = new Scanner(new BufferedInputStream(System.in));
		int count = cin.nextInt();
		Map<String, Integer> peo = new HashMap<String, Integer>();
		String[] namequeue = new String[100];
		// System.out.println(count);
		for (int i = 0; i < count; i++) {
			String name = cin.next();
			peo.put(name, 0);
			namequeue[i] = name;
		}

		// for (int i = 0; i < count; i++) {
		// System.out.println(namequeue[i]);
		// }

		for (int i = 0; i < 5; i++) {
			String owner = cin.next();
			int money = cin.nextInt();
			int parts = cin.nextInt();
			for (int j = 0; j < parts; j++) {
				String receiver = cin.next();
				peo.put(receiver, peo.get(receiver) + money / parts);
				// System.out.println( peo.get(receiver) );
			}
			peo.put(owner, peo.get(owner) - money);
		}

		// output
		// for (String name : peo.keySet()) {
		// System.out.println(name + " " + peo.get(name));
		// }
		for (int i = 0; i < count; i++) {
			System.out.println(namequeue[i] + " " + peo.get(namequeue[i]));
		}
	}

}
