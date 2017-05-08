package cn.golaxy;

import cn.golaxy.Cayley.Subject;
import cn.golaxy.Cayley.ValueNode;

/**
 * 需要注意{@link ValueNode} 与{@link Subject} 不要一样
 * 
 * @author ppf
 * @since 2017年4月14日
 */
public class NodeUtil {

	public static void main(String[] args) {
		String subject = "jiumao";
		String predicate = "friends";
		String object = "friendly people";
		String value = "ryz";
		String label = "good guys";
		String s = buliderNode(subject, predicate, object, label);
		System.out.println(s);
		String lang = "Chinese";
		s = buliderNodeLang(subject, predicate, value, lang, label);
		System.out.println(s);
		String datatype = "bool";
		s = buliderNodeType(subject, predicate, value, datatype, label);
		System.out.println(s);
		s = buliderNodeValue(subject, predicate, value, label);
		System.out.println(s);
		int bnode = 007;
		s = buliderBNodeBefore(subject, predicate, bnode);
		System.out.println(s);
		ValueNode v = new ValueNode(value).appendLang(lang);
		s = buliderBNodeAfter(bnode, predicate, v);
		System.out.println(s);
		int bnode2 = 110;
		s = buliderBNodeAfter(bnode, predicate, bnode2);
		System.out.println(s);
		s = buliderBNodeAfter(bnode2, predicate, object);
		System.out.println(s);

	}

	public static String buliderNode(String subject, String predicate, String object, String label) {
		return Cayley.buliderNode(Cayley.factory(subject), Cayley.factory(predicate), Cayley.factory(object),
				Cayley.factory(label));
	}

	public static String buliderNodeValue(String subject, String predicate, String object, String label) {
		if (null == label) {
			return Cayley.buliderNode(Cayley.factory(subject), Cayley.factory(predicate),
					Cayley.factory(object, null, true), null);
		} else {
			return Cayley.buliderNode(Cayley.factory(subject), Cayley.factory(predicate),
					Cayley.factory(object, null, true), Cayley.factory(label));
		}
	}

	public static String buliderNodeLang(String subject, String predicate, String object, String lang, String label) {
		if (null == label) {
			return Cayley.buliderNode(Cayley.factory(subject), Cayley.factory(predicate),
					Cayley.factory(object, lang, true), null);
		} else {
			return Cayley.buliderNode(Cayley.factory(subject), Cayley.factory(predicate),
					Cayley.factory(object, lang, true), Cayley.factory(label));
		}
	}

	public static String buliderNodeType(String subject, String predicate, String object, String datatype,
			String label) {
		if (null == label) {
			return Cayley.buliderNode(Cayley.factory(subject), Cayley.factory(predicate),
					Cayley.factory(object, datatype, false), null);
		} else {
			return Cayley.buliderNode(Cayley.factory(subject), Cayley.factory(predicate),
					Cayley.factory(object, datatype, false), Cayley.factory(label));
		}
	}

	public static String buliderBNodeBefore(String subject, String predicate, int bnode) {
		return Cayley.buliderBNode(Cayley.factory(subject), Cayley.factory(predicate), Cayley.factory(bnode));
	}

	public static String buliderBNodeAfter(int bnode, String predicate, ValueNode value) {
		return Cayley.buliderBNode(Cayley.factory(bnode), Cayley.factory(predicate), value);
	}

	public static String buliderBNodeAfter(int bnode, String predicate, int bnode2) {
		return Cayley.buliderBNode(Cayley.factory(bnode), Cayley.factory(predicate), Cayley.factory(bnode2));
	}

	public static String buliderBNodeAfter(int bnode, String predicate, String object) {
		return Cayley.buliderBNode(Cayley.factory(bnode), Cayley.factory(predicate), Cayley.factory(object));
	}

}
