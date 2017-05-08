package cn.golaxy;


/**
 * 此工具类不应该都抛 {@code Exception}，使用修改
 * @author ppf
 * @since 2017年5月8日
 */
public final class Query {

	private static final String HTTP = "http://";
	private static final String GREMLIN = "/api/v1/query/gizmo";
	private static final String GREMLIN_OLD = "/api/v1/query/gremlin";
	private static final String GRAPHQL = "/api/v1/query/graphql";
	private static final String URL = ":";


	public static String queryByGremlin(String host, int port, String gremlin) throws Exception {
		StringBuilder url = new StringBuilder(HTTP).append(host).append(URL)//
				.append(port).append(GREMLIN);
		return HttpUtil.sendPost(url.toString(), gremlin);
	}
	public static String queryByGremlinOld(String host, int port, String gremlin) throws Exception {
		StringBuilder url = new StringBuilder(HTTP).append(host).append(URL)//
				.append(port).append(GREMLIN_OLD);
		return HttpUtil.sendPost(url.toString(), gremlin);
	}

	public static String queryByGraphql(String host, int port, String graphql) throws Exception {
		StringBuilder url = new StringBuilder(HTTP).append(host).append(URL)//
				.append(port).append(GRAPHQL);
		return HttpUtil.sendPost(url.toString(), graphql);
	}

}
