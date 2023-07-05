package br.com.netdeal.backend.domain.utils;

public class CharUtils {
	public static boolean isSymbol(char c) {
		return !Character.isAlphabetic(c) && !Character.isDigit(c)
				&& !Character.isWhitespace(c) && c != '_'; // underscore filter
	}
}
