package cn.golaxy;
/**
 * 本类提供cayley数据<br>
 * 数据结构开放，允许自定义工具
 * @author ppf
 * @since 2017年4月14日
 */
public final class Cayley {

	private final static String DOT = " .";
	private final static String SPACE = " ";

	/**
	 * 生成{@link Predicate} 、 {@link Subject} 或者 {@link Label}
	 */
	public static Subject factory(String value) {
		return new Predicate(value);
	}
	public static BNode factory(int value){
		return new BNode(value);
	}

	public static ValueNode factory(String value, String append, boolean isLang) {
		if (null==append) {
			return new ValueNode(value);
		}
		if (isLang) {
			return new ValueNode(value).appendLang(append);
		} else {
			return new ValueNode(value).appendType(append);
		}
	}
	

	/**
	 * 除了{@code label}外不允许为空
	 */
	public static String buliderNode(Subject subject, Subject predicate, Node object, Subject label) {
		StringBuilder s = new StringBuilder();
		s.append(subject).append(SPACE)//
				.append(predicate).append(SPACE)//
				.append(object);
		if (null == label) {
			s.append(DOT);
		} else {
			s.append(SPACE).append(label).append(DOT);
		}
		return s.toString();
	}

	/**
	 * 都不能为空
	 * 
	 * @param object
	 *            只能为{@link ValueNode} 、 {@link Subject} 或者 {@link BNode}
	 */
	public static String buliderBNode(Subject subject, Subject predicate, BNode bnode) {
		StringBuilder s = new StringBuilder();
		s.append(subject).append(SPACE)//
				.append(predicate).append(SPACE)//
				.append(bnode).append(DOT);
		return s.toString();
	}

	/**
	 * 都不能为空
	 * 
	 * @param object
	 *            只能为{@link ValueNode} 、 {@link Subject} 或者 {@link BNode}
	 */
	public static String buliderBNode(BNode bnode, Subject predicate, Node object) {
		StringBuilder s = new StringBuilder();
		s.append(bnode).append(SPACE)//
				.append(predicate).append(SPACE)//
				.append(object).append(DOT);
		return s.toString();
	}

	public static interface Node {
	}

	public static abstract class AbstractNode implements Node {
		/**
		 * 保存node本身字符串值
		 */
		protected String value;
	}

	/**
	 * 节点或关系，位于节点数据第一个位置，或者第二个位置
	 * @author ppf
	 * @since 2017年4月14日
	 */
	public static class Subject extends AbstractNode {
		public Subject(String value) {
			super.value = value;
		}

		@Override
		public String toString() {
			return "<" + value + ">";
		}
	}

	/**
	 * 关系，位于节点数据第二个位置
	 * 
	 * @author ppf
	 * @since 2017年4月14日
	 */
	public static class Predicate extends Subject {
		public Predicate(String value) {
			super(value);
		}
	}

	/**
	 * 中间节点，位于节点数据第三个位置
	 * 
	 * @author ppf
	 * @since 2017年4月14日
	 */
	public static class BNode extends AbstractNode {
		public BNode(int bnode) {
			super.value = "_:" + bnode;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 位于节点数据第三个位置，与{@link BNode}二选一 节点数据，如果执行另外一个节点，直接拼接{@link Subject}
	 * 
	 * @author ppf
	 * @since 2017年4月14日
	 */
	public static class ValueNode extends AbstractNode {
		private StringBuilder valueNode;
		static final String LANG = "@";
		static final String SCHEMA = "^^<schema:";

		public ValueNode(String value) {
			this.valueNode = new StringBuilder("\"" + value + "\"");
		}

		@Override
		public String toString() {
			return valueNode.toString();
		}

		/**
		 * 语言类型
		 * 
		 * @param lang
		 * @return
		 */
		public ValueNode appendLang(String lang) {
			valueNode.append(LANG).append(lang);
			return this;
		}

		/**
		 * 数据类型
		 * 
		 * @param lang
		 * @return
		 */
		public ValueNode appendType(String dataType) {
			valueNode.append(SCHEMA).append(dataType).append(">");
			return this;
		}
	}

	/**
	 * 位于节点数据第四个位置可有可无。
	 * 
	 * @author ppf
	 * @since 2017年4月14日
	 */
	public static class Label extends Subject {
		public Label(String value) {
			super(value);
		}
	}
}
