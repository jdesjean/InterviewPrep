package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/design-search-autocomplete-system/">LC: 642</a>
 */
public class AutocompleteSystem {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { Arrays.asList(
				Arrays.asList(new String[] {"i love you", "island", "i love leetcode"}),
				Arrays.asList(new String[] {"i love you", "i love leetcode"}),
				Arrays.asList(new String[0]),
				Arrays.asList(new String[0])
				), new TestCase1()};
		Object[] tc2 = new Object[] { Arrays.asList(
				Arrays.asList(new String[] {"i love you", "island", "i love leetcode"}),
				Arrays.asList(new String[] {"i love you", "i love leetcode"}),
				Arrays.asList(new String[0]),
				Arrays.asList(new String[0]),
				Arrays.asList(new String[] {"i love you", "island", "i love leetcode"}),
				Arrays.asList(new String[] {"i love you", "i love leetcode", "i a"}),
				Arrays.asList(new String[] {"i a"}),
				Arrays.asList(new String[0]),
				Arrays.asList(new String[] {"i love you", "island", "i a"}),
				Arrays.asList(new String[] {"i love you", "i a", "i love leetcode"}),
				Arrays.asList(new String[] {"i a"}),
				Arrays.asList(new String[0])
				), new TestCase2()};
		Object[] tc3 = new Object[] { Arrays.asList(
				Arrays.asList(new String[] {"jqqgpjvfkgjh","jiaqkaxovsqtkpdjfbkajpvpyetuoqwnrnpjdhoojbsdvneecsdvgqpyurmsvcy","jzgsdjdawrqfladolduldhpdpagmvllvzamypuqlrpbmhxxadqaqrqavtxeghcyysjynovkiyjtvdluttodtmtocajgttmv"})
				), new TestCase3()};
		Object[] tc4 = new Object[] { Arrays.asList(
				Arrays.asList(new String[] {"vvxbsgfehetdqwevsnpgpeltqpkwspqeujismbtnriberrljmbfdkvwdgfddlngvsegrguohbhyyv","veuuduihmffxammdteekpowjkeuiizegiprxftzeklvrhglqzvecchwsbvcekde","vdtwruzonupsfopwsadhmkdaltasrgemwejdrnvgnnugoblwtacnwsfldutlqqiguuub"})
				), new TestCase4()};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2(), new Solver3() };
		Test.apply(solvers, tcs);
	}
	static class Solver3 implements Problem {
		private final static List<String> EMPTY = new ArrayList<>();
		private Trie trie;
		private TrieNode current;
		String prefix = "";
		public Solver3() {
			
		}
		public Solver3(String[] sentences, int[] times) {
			Trie trie = new Trie();
			this.trie = trie;
			current = trie.root;
			for (int i = 0; i < sentences.length; i++) {
				trie.add(sentences[i], times[i]);
			}
		}

		@Override
		public List<List<String>> apply(Function<Problem, List<List<String>>> t) {
			return t.apply(this);
		}

		@Override
		public Problem problem(String[] sentences, int[] times) {
			return new Solver3(sentences, times);
		}

		@Override
		public List<String> input(char c) {
			if (c == '#') {
				current = trie.root;
				trie.add(prefix, 1);
				prefix = "";
				return EMPTY;
			}
			prefix += c;
			if (current == null) return EMPTY;
			current = current.childs.get(c);
			if (current == null) return EMPTY;
			List<String> res = new ArrayList<String>(3);
			for (SortedSet<String> set : current.sentences.descendingMap().values()) {
				for (String string : set) {
					res.add(string);
					if (res.size() == 3) return res;
				}
			}
			return res;
		}
		static class Trie {
			TrieNode root = new TrieNode();
			public void add(String s, Integer count) {
				TrieNode current = root;
				for (int i = 0; i < s.length(); i++) {
					TrieNode next = current.childs.computeIfAbsent(s.charAt(i), (k) -> new TrieNode());
					next.add(s,  count);
					current = next;
				}
			}
		}
		static class TrieNode {
			Map<Character, TrieNode> childs = new HashMap<>();
			Map<String, Integer> counts = new HashMap<>();
			NavigableMap<Integer, SortedSet<String>> sentences = new TreeMap<>();
			public void add(String s, Integer count) {
				Integer prevCount = counts.get(s);
				int nextCount = count; 
				if (prevCount != null) {
					sentences.get(prevCount).remove(s);
					nextCount += prevCount;
				}
				counts.put(s, nextCount);
				Set<String> set = sentences.computeIfAbsent(nextCount, (k) -> new TreeSet<>());
				set.add(s);
			}
		}
	}
	static class Solver2 implements Problem {
		private final static List<String> EMPTY = new ArrayList<>();
		private Trie trie;
		private TrieNode current;
		String prefix = "";
		public Solver2() {
			
		}
		public Solver2(String[] sentences, int[] times) {
			Trie trie = new Trie();
			this.trie = trie;
			current = trie.root;
			for (int i = 0; i < sentences.length; i++) {
				trie.add(sentences[i], times[i]);
			}
		}

		@Override
		public List<List<String>> apply(Function<Problem, List<List<String>>> t) {
			return t.apply(this);
		}

		@Override
		public Problem problem(String[] sentences, int[] times) {
			return new Solver2(sentences, times);
		}

		@Override
		public List<String> input(char c) {
			if (c == '#') {
				current = trie.root;
				trie.add(prefix, 1);
				prefix = "";
				return EMPTY;
			}
			prefix += c;
			if (current == null) return EMPTY;
			current = current.childs.get(c);
			if (current == null) return EMPTY;
			int size = Math.min(3, current.counts.size());
			String[] res = new String[size];
			LLNode tail = current.tail;
			for (int i = 0; i < size && tail != null;) {
				for (String string : tail.sentences) {
					res[i++] = string;
					if (i >= size) break;
				}
				tail = tail.prev;
			}
			return Arrays.asList(res);
		}
		static class Trie {
			TrieNode root = new TrieNode();
			public void add(String s, Integer count) {
				TrieNode current = root;
				for (int i = 0; i < s.length(); i++) {
					TrieNode next = current.childs.computeIfAbsent(s.charAt(i), (k) -> new TrieNode());
					next.add(s,  count);
					current = next;
				}
			}
		}
		static class TrieNode {
			Map<Character, TrieNode> childs = new HashMap<>();
			Map<String, Integer> counts = new HashMap<>();
			Map<Integer, LLNode> sentences = new HashMap<>();
			LLNode head;
			LLNode tail;
			public void add(String s, Integer count) {
				Integer prevCount = counts.get(s);
				if (prevCount == null) {
					counts.put(s, count);
					LLNode llNode = sentences.get(count);
					if (llNode == null && head == null) {
						llNode = head = tail = new LLNode(count);
					} else if (llNode == null) {
						for (llNode = head; llNode.val < count && llNode.next != null; llNode = llNode.next) {
							
						}
						if (llNode.val < count) { //append next
							llNode = addNext(llNode, new LLNode(count));
						} else { // append prev
							llNode = addPrev(new LLNode(count), llNode);
						}
					}
					llNode.sentences.add(s);
					sentences.put(count, llNode);
				} else {
					int nextCount = prevCount + count;
					LLNode llNode = sentences.get(prevCount);
					llNode.sentences.remove(s);
					if (llNode.next != null && llNode.next.val == nextCount) {
						llNode = llNode.next;
					} else {
						llNode = addNext(llNode, new LLNode(nextCount));
						sentences.put(nextCount, llNode);
					}
					llNode.sentences.add(s);
					counts.put(s, nextCount);
				}
			}
			public LLNode addNext(LLNode current, LLNode next) {
				next.next = current.next;
				next.prev = current;
				if (current.next == null) {
					tail = next;
				} else {
					current.next.prev = next;
				}
				current.next = next;
				return next;
			}
			public LLNode addPrev(LLNode prev, LLNode current) {
				prev.prev = current.prev;
				prev.next = current;
				if (current.prev == null) {
					head = prev;
				} else {
					current.prev.next = prev;
				}
				current.prev = prev;
				return prev;
			}
		}
		static class LLNode {
			LLNode next;
			LLNode prev;
			final int val;
			SortedSet<String> sentences = new TreeSet<>();
			public LLNode(int val) {
				this.val = val;
			}
		}
	}
	static class Solver implements Problem {
		private final static List<String> EMPTY = new ArrayList<>();
		private Trie trie;
		private Node current;
		String prefix = "";
		public Solver() {
			
		}
		public Solver(String[] sentences, int[] times) {
			Trie trie = new Trie();
			this.trie = trie;
			current = trie.root;
			for (int i = 0; i < sentences.length; i++) {
				trie.add(sentences[i], times[i]);
			}
		}

		@Override
		public List<List<String>> apply(Function<Problem, List<List<String>>> t) {
			return t.apply(this);
		}

		@Override
		public Problem problem(String[] sentences, int[] times) {
			return new Solver(sentences, times);
		}

		@Override
		public List<String> input(char c) {
			if (c == '#') {
				current = trie.root;
				trie.add(prefix, 1);
				prefix = "";
				return EMPTY;
			}
			prefix += c;
			if (current == null) return EMPTY;
			current = current.childs.get(c);
			if (current == null) return EMPTY;
			PriorityQueue<SentenceHot> pq = new PriorityQueue<>();
			for (Map.Entry<String, Integer> entry : current.counts.entrySet()) {
				pq.add(new SentenceHot(entry.getKey(), entry.getValue()));
				if (pq.size() > 3) {
					pq.remove();
				}
			}
			String[] res = new String[Math.min(3, current.counts.size())];
			for (int i = Math.min(2, pq.size() - 1); i >= 0; i--) {
				res[i] = pq.remove().sentence;
			}
			return Arrays.asList(res);
		}
		static class Trie {
			Node root = new Node();
			public void add(String s, Integer count) {
				Node current = root;
				for (int i = 0; i < s.length(); i++) {
					Node next = current.childs.computeIfAbsent(s.charAt(i), (k) -> new Node());
					next.counts.compute(s, (k, v) -> v == null ? count : v + count);
					current = next;
				}
			}
		}
		static class Node {
			Map<Character, Node> childs = new HashMap<>();
			Map<String, Integer> counts = new HashMap<>();
		}
		static class SentenceHot implements Comparable<SentenceHot>{
			final String sentence;
			final int hot;
			public SentenceHot(String sentence, int hot) {
				this.sentence = sentence;
				this.hot = hot;
			}
			@Override
			public int compareTo(SentenceHot o) {
				int c = Integer.compare(this.hot, o.hot);
				if (c != 0) return c;
				return o.sentence.compareTo(this.sentence);
			}
		}
	}
	interface Problem extends Function<Function<Problem, List<List<String>>>, List<List<String>>> {
		public Problem problem(String[] sentences, int[] times);
		public List<String> input(char c);
	}
	static class TestCase1 implements Function<Problem, List<List<String>>> {

		@Override
		public List<List<String>> apply(Problem t) {
			// TODO Auto-generated method stub
			Problem copy = t.problem(new String[] {"i love you", "island", "iroman", "i love leetcode"}, new int[] {5, 3, 2, 2});
			List<List<String>> res = new ArrayList<>();
			res.add(copy.input('i'));
			res.add(copy.input(' '));
			res.add(copy.input('a'));
			res.add(copy.input('#'));
			return res;
		}
		
	}
	static class TestCase2 implements Function<Problem, List<List<String>>> {
		@Override
		public List<List<String>> apply(Problem t) {
			// TODO Auto-generated method stub
			Problem copy = t.problem(new String[] {"i love you","island","iroman","i love leetcode"}, new int[] {5, 3, 2, 2});
			List<List<String>> res = new ArrayList<>();
			res.add(copy.input('i'));
			res.add(copy.input(' '));
			res.add(copy.input('a'));
			res.add(copy.input('#'));
			res.add(copy.input('i'));
			res.add(copy.input(' '));
			res.add(copy.input('a'));
			res.add(copy.input('#'));
			res.add(copy.input('i'));
			res.add(copy.input(' '));
			res.add(copy.input('a'));
			res.add(copy.input('#'));
			return res;
		}
	}
	static class TestCase3 implements Function<Problem, List<List<String>>> {
		@Override
		public List<List<String>> apply(Problem t) {
			// TODO Auto-generated method stub
			Problem copy = t.problem(new String[] {"uqpewwnxyqxxlhiptuzevjxbwedbaozz","ewftoujyxdgjtazppyztom","pvyqceqrdrxottnukgbdfcr","qtdkgdbcyozhllfycfjhdsdnuhycqcofaojknuqqnozltrjcabyxrdqwrxvqrztkcxpenbbtnnnkfhmebj","jwfbusbwahyugiaiazysqbxkwgcawpniptbtmhqyrlxdwxxwhtumglihrgizrczv","cfptjitfzdcrhw","aitqgitjgrcbacgnaasvbouqsqcwbyskkpsnigtfeecmlkcjbgduban","utsqkmiqqgglufourfdpgdmrkbippffacwvtkpflzrvdlkdxykfpkoqcb","ethtbdopotpamvrwuomlpahtveyw","jiaqkaxovsqtkpdjfbkajpvpyetuoqwnrnpjdhoojbsdvneecsdvgqpyurmsvcy","j","btbnuplyeuccjbernsfbnveillrwdbqledwvpmvdbcugkurrkabtpykhlcogeszclyfuquafouv","hndjzblegevtfkgbjttektox","gtvnlninpvenapyfgmsjdisfnmiktitrutctawosjflvzfkbegnprixzqwzcyhoovsivuwmofsveqkyosowuyamuvy","sawrirvrfrbfagreahrioaombukmdwztbpggnxd","mgdcwptvbvhzyvvumvbjjn","otjvvkegwleyyxtghwgfmlsqlhrlibdvqfinyyebotjpwoaejhtornfgikmifdmwswbqgwhcbzuhrpajxuqicegcptszct","zlondsttyvnnnnxjtoqnlktitwzurissczzbyfsbgpoawodwjpsmavaugnhqtsbeixwl","yehvdehbtmwqkmcjmvpivfzqvevkotwzvjoyfvp","bjximtpayjdcxbrnksbtfnpynzaygygdflowewprqngdadzdhxcpgapjejojrkzrutgcsfpfvpluagniqimfqddldxqiw","bysyrxfykivyauysytgxfhqcrxliulahuizjvozpywrokxujhzpauxwufcxiitukljiiclatfrspqcljjoxpxziumstnhqr","uxtvutlgqapyfltiulwrplesmtowzoyhhjhzihatpuvmutxqgxfawpwypedbz","jzgsdjdawrqfladolduldhpdpagmvllvzamypuqlrpbmhxxadqaqrqavtxeghcyysjynovkiyjtvdluttodtmtocajgttmv","mbijfkmepalhdiubposdksdmmttxblkodcdrxbnxaqebnwliatnxpwaohbwkidia","ljggggbyxwrwanhjonoramexdmgjigrtpz","cqfvkutpipxjepfgsufonvjtotwfxyn","kvseesjazssavispavchdpzvdhibptowhyrrshyntpwkez","nveuzbaosuayteiozmnelxlwkrrrjlwvhejxhupvchfwmvnqukphgoacnazuoimcliubvhv","uwrpwhfdrxfnarxqpkhrylkwiuhzubjfk","bniyggdcloefwy","ihranmhbsahqjxesbtmdkjfsupzdzjvdfovgbtwhqfjdddwhdvrnlyscvqlnqpzegnvvzyymrajvso","lscreasfuxpdxsiinymuzybjexkpfjiplevqcjxlm","uwgnfozopsygnmptdtmmuumahoungpkodwxrcvfymqpeymaqruayvqqgoddgbnhemtsjifhxwiehncswxzrghf","nyfbxgcpfrzyqwfjzgmhuohjhrjizsyjqgmertmooeiaadcmiuyyylpcosnweoyydeauazhzbeaqn","tpylrxbwnkrfxckfdlvrbytaezuzmyscpvruthuvbxjenkeolvqsrjqzizyclzmqtjvnamdansmzyspcfghfprorqprua","nhldlmxpuckxeekipkrzugatjiivtazjbjyxokksyueyjbgmrovbckbxqcqefaiavzsarbbypgmpxe","sylraqsd","xr","xkzpxkhrucyatpatkigvntohihibyisyqtkjdhatdvyvxbjttz","nvnz","blzddwxphkgqfsfzfclwytstpvpzgcdeggdwzukzirscfzcteeuqbmmrfxcnokbbyxkqrxtjfarcefiynwfmy","inuxmuhtdwpuvyludwtokhtalxbuccepsayrjycbcwbtnfholjvkmypodv","awwillrm","xznodxngrstjrwqzmlmigpw","khlxjdtictufdfbkgfusdtaaeuspbbfmtjodflgqofzlqnulkdztflm","nlngmckslyqzjiyiexbropbxnynjcstziluewypboqhqndqsxhtnosrgrameajovsclrgwqjgnztvxrkhwnxkfrf","yroadxhxyacaexrwju","ujxlbpcbxdqrvubifnpzjmmkolyljzjhdegaiowaal","tnfnjgtxbckbpyplucprxcqzhrfjimylmlhdglntfydepltjvklyxesndzuubienhvuaqc","ouedhtkpkg","ygchsrrubucqffewifsxaefwocfaiiupqbomktvrcddggqfgnaycstpccwtbheyaqwhosxajqeqqxzyjsfng","jqqgpjvfkgjh","csowoazaiyejgyixszqmtctpzlkccccqregyhtvxccvrpkupwcyhqatxscevzdfbdqnuyadiyfnhysddfyxpgqtjiogmxsmzbbkr","dlzxdpchkdaztkqtrjmuujgoiae","plcjkwukkyqluxjbhxsyeaqvviinfuujsafwsquidvmutsrukxwrv","yopqbtpoqhpcktjangauzcvvpephhprpaaclbbkgchlqkrwdsaupeizlwxzcpkchoagmrrkwdkthosmrjefgbumnrjsb"}, new int[] {12,9,4,4,1,5,3,4,7,9,2,4,2,3,11,13,1,3,4,10,7,1,9,5,10,14,5,3,2,11,5,14,4,13,11,5,15,8,1,12,2,11,4,2,11,14,9,12,1,7,13,11,7,2,6,10});
			List<List<String>> res = new ArrayList<>();
			res.add(copy.input('j'));
			return res;
		}
	}
	static class TestCase4 implements Function<Problem, List<List<String>>> {
		@Override
		public List<List<String>> apply(Problem t) {
			// TODO Auto-generated method stub
			Problem copy = t.problem(new String[] {"zqbpvckicqaigaikyffhbpdugilagijzfenofsxywlurtkxujbavhozxxqvzglzjbnhodwgdeh","trpsgjymzyujnphkoqhouslayubtlmljfthylhiocspxrwtptoedsehwfxughytoee","qyzeuhjqwxwvuhmizljnfkedhrhxjjznfugilekgruiuaarzidrjrpkxytksxfifldrsxvrsrgqhufxdqscazrloqlmckbcrqe","uqxsrfldwlblarwnfwazextlvonlshqzzjvnttvikdzdvhkxtba","vmixpdtrebafyzbwanarljbusvxcizcsdfhnrinzzysybcixczxtlqbnvxyttwbehessmhdygevlpubly","gahrdmixpojnrmxswnzphkmogzdmoowfkvajneopbzspyq","zlwiaqvnuxewzrvnaoyvukdoufbo","asxmzvdbdmclnkmtcsimjianbbrkwhosdnmilewxgkxzkuhptglocrmkqwnovivjdmgfbrk","vvxbsgfehetdqwevsnpgpeltqpkwspqeujismbtnriberrljmbfdkvwdgfddlngvsegrguohbhyyv","veuuduihmffxammdteekpowjkeuiizegiprxftzeklvrhglqzvecchwsbvcekde","egscisbiexsznujummptdrsehhyyhpudlhhewjixitkjgkznhotdlxmcmenjeyefjbuxyqi","yli","fxjswtmqvjygyjyrtfemoucwfrnjykskolycmvmfrzjwcsqjccepmtxse","mprzudifvdzzbvxborqyulrkkhryfkjfagtrirhrlwatgohfkqdrnflpmuedyvmwmabxurtymizfwqddfetblicdbmsw","tssdvqcvrvjiemzmhxjpvxyhjsacavmnsesvufhzcudopmkhphhzysofccdkzceds","wlpgxtjspefjmpmmdvxfemlxqsysujeyykyxzorkjicebdhtdgrt","fvqmpsaokpztbkfjnfyzgtvoltuuspulvixxukwpstmxfdpnoownknhyitycqpzgcdbqdyvimdjiuiazzjfzowqxe","sjkaqbcgxcqwmgfyikkorpvsdnw","prlajmlbbglumfvaubqfwrnvlhwsypqqfbqjgjszzeudlzwojfppmexidijepzpdsoigxzbifwfzhlzimdxcqjvjtzmqv","wbrbwerkzrqsgyxtcwhmunsqpyxjpgxdvceefsjquwkuvzcy","jrtscybhidadeinsvlnduwzmbdogzmbsiidieqlbbtcwveazqnpdyxjcsvafrwnewsjdilsmdhqztkevxwi","oavtwevxtkyfbtorziwovgrzydwoaijwdrdaztnutnaebemptxyinryesiup","ukbquogulqsqdtwsbgcrzcqbbmcybowfucqxcrrnjeyyxunzvaqjsewdtvlmufvfnejnbtdfri","kxwfgsegorrfbnfodlxwrxfjqhiekpjwvwvwdjzcgnpmfpoejqhrqlbaeakzlaunqzzovcxikrkgpofqwypem","zacrlpftuexioxjdihzflvbsdvuzsjmoyyhgjldrioygihgjaqdbpzwnzhcuz","ocooy","gczdtscysdajtknsiqmsyhxcheexmhqbepmcsurcaqupzxjdhrrkvytovh","vdtwruzonupsfopwsadhmkdaltasrgemwejdrnvgnnugoblwtacnwsfldutlqqiguuub","qzeadawuijyetgqirkqznujdecokzwbizkbagtsbpptjfmhjlcyqgcz","wxjawnqfqkoqjonk","ekkxzkxdcvdemanmqaksgcfxpzcicxivjrlgsvjviyksitcqdvahqvtbincztnprlqfokzuhixqgpaosfnfgwgszjx"}, new int[] {11,2,9,1,5,12,14,9,13,12,3,4,13,5,3,6,6,2,4,11,15,1,12,11,1,15,9,10,3,2,15});
			List<List<String>> res = new ArrayList<>();
			res.add(copy.input('v'));
			return res;
		}
	}
	
}
