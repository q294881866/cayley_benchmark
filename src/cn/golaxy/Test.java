package cn.golaxy;


@FunctionalInterface
interface RunQuery {
	void useCase() throws Exception;
}

/**
 * cayley 测试用例 <br>
 * 耗时计算：800w:2100w和120w:140w数量集，同样的查询
 * @author ppf
 * @since 2017年5月8日
 */
public class Test {
	public static void main(String[] args) {
		System.out.println(query);
	}

	static final String ENTER = "\n";

	/** 只支持老版本cayley，7.0以下，在192.168.200.205:62110测试 */
	static final String query = " var director = g.V(\"<m.06pj8>\").Out(\"<type.object.name>\").ToArray()[0]" + ENTER//
			+ " var b = g.V(\"<m.06pj8>\").Out(\"<film.director.film>\").ToArray()" + ENTER //
			+ " _.each(b, function(s){" + ENTER //
			+ "    var film_name = g.V(s).Out(\"<type.object.name>\").ToArray()[0]" + ENTER//
			+ "    var file_release = g.V(s).Out(\"<film.film.initial_release_date>\").ToArray()[0]" + ENTER//
			+ "    var movie = {}" + ENTER //
			+ "    movie[\"director\"] = director" + ENTER //
			+ "    movie[\"film_name\"] = film_name" + ENTER //
			+ "    movie[\"file_release\"] = file_release" + ENTER //
			+ "    var genre = g.V(s).Out(\"<film.film.genre>\").ToArray()" + ENTER//
			+ "    var genre_list = \"\"" + ENTER //
			+ "    _.each(genre, function(gen){" + ENTER//
			+ "    		var genre_name =  g.V(gen).Out(\"<type.object.name>\").ToArray()[0]" + ENTER//
			+ "    		genre_list += genre_name + \", \"" + ENTER //
			+ "    })" + ENTER //
			+ "    movie[\"genre_list\"] = genre_list" + ENTER //
			+ "    g.Emit(movie)" + ENTER //
			+ " })";

	String host = "192.168.200.205";

	static final String valueToSub = "g.V(\"cool_person\").In(\"<status>\").All()",//
			greNoSub = "g.V().Out(\"<follows>\").Out(\"<status>\").All()";
	/** 
	 * {@link #valueToSub} 
	 * <pre>
	 * null{ "result": [  {   "id": "\u003cbob\u003e"  },  {   "id": "\u003cdani\u003e"  },  {   "id": "\u003cgreg\u003e"  } ]}
	 * 执行10000次，总共耗时：7652056
	 * 执行10000次，总共耗时：454352
	 * </pre>
	 * {@link #greNoSub} 
	 * <pre>
	 * null{ "result": [  {   "id": "smart_person"  },  {   "id": "cool_person"  },  {   "id": "smart_person"  },  {   "id": "cool_person"  },  {   "id": "cool_person"  },  {   "id": "cool_person"  },  {   "id": "cool_person"  },  {   "id": "cool_person"  } ]}
	 * 执行10000次，总共耗时：557828
	 * </pre>
	 * @throws Exception
	 */
	@org.junit.Test
	public void noIndex() throws Exception {
		RunQuery r = () -> {
			res = Query.queryByGremlin(host, 64211, valueToSub);
//			res = Query.queryByGremlin(host, 64211, greNoSub);
		};
		runTest(r);
	}

	static final String gre = "g.V(\"<alice>\").Out(\"<follows>\").Out(\"<follows>\").In(\"<follows>\").All()";
	/**
	 * <pre>
	 * null{ "result": [  {   "id": "\u003cbob\u003e"  },  {   "id": "\u003cemily\u003e"  } ]}
	 * 执行10000次，总共耗时：633495
	 * @throws Exception
	 */
	@org.junit.Test
	public void useIndex() throws Exception {
		RunQuery r = () -> {
			res = Query.queryByGremlin(host, 64211, gre);
		};
		runTest(r);
	}


	/**
	 * <pre>
	 * null{ "result": [  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1968-12-18\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Amblin'",   "genre_list": "Cortometraggio, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1981-06-12\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "A la recerca de l'arca perduda",   "genre_list": "Actiefilm, Abenteuerfilm, "  },  {   "director": "スティーヴン・スピルバーグ",   "film_name": "The BFG",   "genre_list": ""  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"2008-05-18\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "ขุมทรัพย์สุดขอบฟ้า 4: อาณาจักรกะโหลกแก้ว",   "genre_list": "Abenteuerfilm, Actiefilm, Costume Adventure, Aile Filmi, Cerita seru, Adventure Comedy, Fantasia, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1998-07-24\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Der Soldat James Ryan",   "genre_list": "Cine bélico, Draama, Actiefilm, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1974-03-31\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Asfalto quente",   "genre_list": "Abenteuerfilm, Brott, Draama, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"2011-10-23\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "As Aventuras de Tintim: O Segredo do Licorne",   "genre_list": "Abenteuerfilm, Actiefilm, Animaatio, Aile Filmi, Gizem, Adventure Comedy, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"2012-10-08\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "リンカーン",   "genre_list": "Ajalooline romaan, Cine épico, Biografinis filmas, Cine bélico, Draama, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1989-05-24\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "ขุมทรัพย์สุดขอบฟ้า 3 ตอน ศึกอภินิหารครูเสด",   "genre_list": "Adventure Comedy, Costume Adventure, Abenteuerfilm, Actiefilm, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1972\"^^\u003chttp://www.w3.org/2001/XMLSchema#gYear\u003e",   "film_name": "Influència diabòlica",   "genre_list": "Film horror su casa stregata, Cine de terror, Cerita seru, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1997-05-19\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "쥬라기 공원 2 : 잃어버린 세계",   "genre_list": "Actiefilm, Cerita seru, Abenteuerfilm, Bilim kurgu, Adaptação fílmica, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1971-11-10\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Belâ",   "genre_list": "Cerita seru, Actiefilm, Cestni film, Gizem, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1989-12-22\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Além da eternidade",   "genre_list": "Draama, Airplanes and airports, Cine romántico, Fantasia, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1983-06-24\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Ai confini della realtà",   "genre_list": "Bilim kurgu, Cine de terror, Cerita seru, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1975-06-20\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Cápa",   "genre_list": "Abenteuerfilm, Comèdia, Draama, Cine de terror, Cerita seru, Abenteuer, Efectul Zeigarnik, Detektivski film, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1964-03-24\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Debesu ugunis",   "genre_list": "Bilim kurgu, Cerita seru, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"2005-06-13\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Dünyalar Savaşı",   "genre_list": "Cine de terror, Abenteuerfilm, Bilim kurgu, Alien Film, Cine catástrofe, Cerita seru, Doomsday film, Draama, Actiefilm, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1984-05-23\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "인디아나 존스2 - 마궁의 사원",   "genre_list": "Costume Adventure, Abenteuerfilm, Ação/Aventura, Actiefilm, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1973-03-31\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Savage",   "genre_list": "Draama, Cerita seru, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1991-12-08\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Gặp Lại Dưới Biển",   "genre_list": "Comèdia, Fantasia, Children's Fantasy, Abenteuerfilm, Fantasy Adventure, Cine bélico, Children's/Family, Aile Filmi, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"2004-06-09\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "ターミナル",   "genre_list": "Comèdia, Comèdia romàntica, Comèdia dramàtica, Draama, Cine romántico, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1977-11-15\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Bliskie spotkania trzeciego stopnia",   "genre_list": "Draama, Abenteuerfilm, Bilim kurgu, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1985-12-16\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "A Cor Púrpura",   "genre_list": "Draama, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1967\"^^\u003chttp://www.w3.org/2001/XMLSchema#gYear\u003e",   "film_name": "Slipstream",   "genre_list": "Cortometraggio, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"2015-10-16\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "A Ponte dos Espiões",   "genre_list": "Cerita seru, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"2011-12-04\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Calul de luptă",   "genre_list": "Draama, Cine bélico, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"2002-12-16\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Apanha-me se Puderes",   "genre_list": "Comèdia, Crime Fiction, Biografinis filmas, Draama, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1979-12-13\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "1941 - Allarme a Hollywood",   "genre_list": "Ação/Aventura, Action Comedy, Comédia maluca, Actiefilm, Comèdia, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1993-11-30\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "A Lista de Schindler",   "genre_list": "Cine épico, Biografinis filmas, Cine bélico, Draama, Ajalooline romaan, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1982-05-26\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Citplanētietis",   "genre_list": "Draama, Aile Filmi, Abenteuerfilm, Fantasia, Bilim kurgu, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1993-06-09\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Công viên kỷ Jura",   "genre_list": "Cerita seru, Bilim kurgu, Abenteuerfilm, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1997-12-04\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Amistadas",   "genre_list": "Gizem, Ajalooline romaan, Draama, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"1987-12-11\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "A nap birodalma",   "genre_list": "Dönem İşi, Draama, Biografinis filmas, Cérémonie de la majorité, Adaptação fílmica, Cine bélico, Childhood Drama, "  },  {   "director": "スティーヴン・スピルバーグ",   "film_name": "The Attack of the Mummies",   "genre_list": ""  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"2002-06-17\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "Azınlık Raporu",   "genre_list": "Cinema negre, Crime Fiction, Antiutópia, Future noir, Draama, Cerita seru, Gizem, Chase Movie, Ação/Aventura, Actiefilm, Bilim kurgu, Adaptação fílmica, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"2001-06-26\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "A.I. Artificial Intelligence",   "genre_list": "Future noir, Draama, Abenteuerfilm, Bilim kurgu, "  },  {   "director": "スティーヴン・スピルバーグ",   "file_release": "\"2005-12-23\"^^\u003chttp://www.w3.org/2001/XMLSchema#date\u003e",   "film_name": "ミュンヘン",   "genre_list": "Draama, Poliitiline thriller, Dramă politică, Cerita seru, Ajalooline romaan, "  },  {   "director": "スティーヴン・スピルバーグ",   "film_name": "Robopocalypse",   "genre_list": "Bilim kurgu, Actiefilm, Draama, "  },  {   "director": "スティーヴン・スピルバーグ",   "film_name": "Amazing Stories: Book One",   "genre_list": "Draama, Comèdia, "  } ]}
	 * 执行10000次，总共耗时：10240615
	 * @throws Exception
	 */
	@org.junit.Test
	public void funcQuery() throws Exception {
		RunQuery r = () -> {
			res = Query.queryByGremlinOld(host, 64210, query);
		};
		runTest(r);
	}

	static final String json = "{nodes(first: 10){ id } }";
	/**
	 * null{"data":{"nodes":[{"id":"dani"},{"id":"100001"},{"id":"/en/christine_mcintyre"},{"id":"100012"},{"id":"100020"},{"id":"100060"},{"id":"100062"},{"id":"100074"},{"id":"/en/dorothy_vernon"},{"id":"/en/ethan_laidlaw"}]}}
	 * 执行100次，总共耗时：82213
	 * @throws Exception
	 */
	@org.junit.Test
	public void list() throws Exception {
		RunQuery r = () -> {
			res = Query.queryByGraphql(host, 64211, json);
		};
		runTest(r);
	}

	private String res;

	public void runTest(RunQuery r) throws Exception {
		long begin = System.currentTimeMillis();
		int n = 100;

		for (int i = 0; i < n; i++) {
			r.useCase();
		}
		long cost = System.currentTimeMillis() - begin;
		System.out.println(res);
		System.out.println("执行" + n + "次，总共耗时："+cost);
	}

}
