package br.com.netdeal.backend.domain.utils;

public class JsonBuilder {
	private StringBuilder json;
	private static final char QUOTE = '\"';
	private static final char MARK = ':';
	private static final char COMMA = ',';

	public JsonBuilder() {
		this.json = new StringBuilder("{");
	}

	public JsonBuilder append(String node, String value) {
		json.append(COMMA).append(QUOTE).append(node).append(QUOTE)
				.append(MARK);
		json.append(QUOTE).append(value).append(QUOTE);
		return this;
	}

	public JsonBuilder append(String node, Integer value) {
		json.append(COMMA).append(QUOTE).append(node).append(QUOTE)
				.append(MARK);
		json.append(value);
		return this;
	}

	public String evaluate() {
		return this.json.append("}").toString().replace("{,", "{");
	}

	@Override
	public String toString() {
		return this.evaluate();
	}
}