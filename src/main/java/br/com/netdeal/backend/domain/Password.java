package br.com.netdeal.backend.domain;

import br.com.netdeal.backend.domain.utils.CharUtils;
import br.com.netdeal.backend.domain.utils.JsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class Password {
	/* Constants */
	private static final int ZERO = 0;
	public static final int WEAK_MIN_SCORE = 21;
	public static final int GOOD_MIN_SCORE = 41;
	public static final int STRONG_MIN_SCORE = 61;
	public static final int VERY_STRONG_MIN_SCORE = 81;
	public static final int MAX_SCORE = 100;
	public static final int MIN_REQUIREMENTS = 3;
	public static final int MIN_LENGTH = 8;
	public static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
	public static final String NUMBERS = "01234567890";
	public static final String SYMBOLS = ")!@#$%^&*()";

	private final char[] password;
	private final String passwordAsString;
	private final String passwordLowercase;
	// Additions
	private final int uppercase;
	private final int lowercase;
	private final int numbers;
	private final int symbols;
	private final int middleNumbersOrSymbols;
	private final int requirements;

	// Deductions
	private final int consecutiveLowercase;
	private final int consecutiveUppercase;
	private final int consecutiveNumbers;
	private final int sequentialLetters;
	private final int sequentialNumbers;
	private final int sequentialSymbols;

	public Password(String password) {
		this.password = password.toCharArray();
		this.passwordAsString = password;
		this.passwordLowercase = password.toLowerCase();
		int uppercase = 0;
		int lowercase = 0;
		int numbers = 0;
		int symbols = 0;
		int middleNumbersOrSymbols = 0;
		int consecutiveUppercase = 0;
		int consecutiveLowercase = 0;
		int consecutiveNumbers = 0;
		char prevChar = ' ';

		for (int i = 0; i < password.length(); i++) {
			final char c = password.charAt(i);

			if (Character.isLetter(c)) { // Letter
				if (Character.isUpperCase(c)) {
					uppercase++; // Uppercase
					if (Character.isUpperCase(prevChar)) // prev is upper
						consecutiveUppercase++;
				} else if (Character.isLowerCase(c)) {
					lowercase++; // Lowercase
					if (Character.isLowerCase(prevChar)) // prev is lower
						consecutiveLowercase++;
				}
			} else if (Character.isDigit(c)) {
				numbers++; // Digit
				if (i > 0 && (i + 1) != password.length())
					middleNumbersOrSymbols++;
				if (Character.isDigit(prevChar))
					consecutiveNumbers++;
			} else if (CharUtils.isSymbol(c)) {
				symbols++; // Symbol
				if (i > 0 && (i + 1) != password.length()) {
					middleNumbersOrSymbols++;
				}
			}
			prevChar = c;
		}
		this.uppercase = uppercase;
		this.lowercase = lowercase;
		this.numbers = numbers;
		this.symbols = symbols;
		this.middleNumbersOrSymbols = middleNumbersOrSymbols;
		this.requirements = getRequirementsCount();
		this.consecutiveLowercase = consecutiveLowercase;
		this.consecutiveUppercase = consecutiveUppercase;
		this.consecutiveNumbers = consecutiveNumbers;
		int sequentialLetters = ZERO;
		int sequentialNumbers = ZERO;
		int sequentialSymbols = ZERO;
		// fetching sequential symbols
		for (int i = 0; i < (LETTERS.length() - 3); i++) { // forward
			if (passwordLowercase.contains(LETTERS.substring(i, (i + 3)))) {
				sequentialLetters++;
			}
		}
		for (int i = 0; i < (LETTERS.length() - 3); i++) { // reverse
			if (passwordLowercase.contains(new StringBuilder(LETTERS).reverse()
					.toString().substring(i, (i + 3)))) {
				sequentialLetters++;
			}
		}
		// fetching sequential symbols
		for (int i = 0; i < (SYMBOLS.length() - 3); i++) { // forward
			if (passwordAsString.contains(SYMBOLS.substring(i, (i + 3)))) {
				sequentialSymbols++;
			}
		}
		for (int i = 0; i < (SYMBOLS.length() - 3); i++) { // reverse
			if (passwordAsString.contains(new StringBuilder(SYMBOLS).reverse()
					.toString().substring(i, (i + 3)))) {
				sequentialSymbols++;
			}
		}
		// fetching sequential symbols
		for (int i = 0; i < (NUMBERS.length() - 3); i++) { // forward
			if (passwordAsString.contains(NUMBERS.substring(i, (i + 3)))) {
				sequentialNumbers++;
			}
		}
		for (int i = 0; i < (NUMBERS.length() - 3); i++) { // reverse
			if (passwordAsString.contains(new StringBuilder(NUMBERS).reverse()
					.toString().substring(i, (i + 3)))) {
				sequentialNumbers++;
			}
		}
		this.sequentialLetters = sequentialLetters;
		this.sequentialSymbols = sequentialSymbols;
		this.sequentialNumbers = sequentialNumbers;
	}

	public int getRequirementsCount() {
		int requirements = 0;
		if (hasMinimalLength())
			requirements++;
		if (hasUppercaseLetters())
			requirements++;
		if (hasLowercaseLetters())
			requirements++;
		if (hasNumbers())
			requirements++;
		if (hasSymbols())
			requirements++;
		/*
		 * if (hasMiddleNumbersOrSymbols()) requirements++;
		 */
		return requirements;
	}

	private boolean hasUppercaseLetters() {
		return uppercase > 0;
	}

	private boolean hasLowercaseLetters() {
		return lowercase > 0;
	}

	private boolean hasNumbers() {
		return numbers > 0;
	}

	private boolean hasSymbols() {
		return symbols > 0;
	}

	public int getConsecutiveLowercase() {
		return consecutiveLowercase;
	}

	public int getConsecutiveUppercase() {
		return consecutiveUppercase;
	}

	public int getConsecutiveNumbers() {
		return consecutiveNumbers;
	}

	public int getConsecutiveLowercaseBonus() {
		return consecutiveLowercase * 2;
	}

	public int getConsecutiveUppercaseBonus() {
		return consecutiveUppercase * 2;
	}

	public int getConsecutiveNumbersBonus() {
		return consecutiveNumbers * 2;
	}

	public int getSequentialLetters() {
		return this.sequentialLetters;
	}

	public int getSequentialLettersBonus() {
		return this.sequentialLetters * 3;
	}

	public int getSequentialNumbers() {
		return this.sequentialNumbers;
	}

	public int getSequentialNumbersBonus() {
		return this.sequentialNumbers * 3;
	}

	public int getSequentialSymbols() {
		return this.sequentialSymbols;
	}

	public int getSequentialSymbolsBonus() {
		return this.sequentialSymbols * 3;
	}

	public int getUppercase() {
		return uppercase;
	}

	public int getLowercase() {
		return lowercase;
	}

	public int getNumbers() {
		return numbers;
	}

	public int getSymbols() {
		return symbols;
	}

	public int getMiddleNumbersOrSymbols() {
		return middleNumbersOrSymbols;
	}

	public int getRequirements() {
		return requirements;
	}

	public int length() {
		return this.password.length;
	}

	public boolean hasMinimalLength() {
		return this.password.length >= 8;
	}

	public int getLengthBonus() {
		return password.length * 4;
	}

	public int getUppercaseBonus() {
		if (requirements < 2)
			return ZERO;
		if (uppercase > 0)
			return (this.length() - uppercase) * 2;
		return 0;
	}

	public int getLowercaseBonus() {
		if (requirements < 2)
			return ZERO;
		if (lowercase > ZERO)
			return (this.length() - lowercase) * 2;
		return ZERO;
	}

	public int getMiddleNumbersAndSymbolsBonus() {
		return middleNumbersOrSymbols * 2;
	}

	public int getNumbersBonus() {
		if (requirements < 2)
			return ZERO;
		return numbers * 4;
	}

	public int getSymbolsBonus() {
		if (requirements < 2)
			return ZERO;
		return symbols * 6;
	}

	public int getRequirementsBonus() {
		if (requirements > MIN_REQUIREMENTS && length() >= MIN_LENGTH)
			return requirements * 2;
		return ZERO;
	}

	public int getTotalAdditionBonuses() {
		return getLengthBonus() + getUppercaseBonus() + getLowercaseBonus()
				+ getNumbersBonus() + getSymbolsBonus()
				+ getMiddleNumbersAndSymbolsBonus() + getRequirementsBonus();
	}

	public int getLetterOnly() {
		if ((uppercase + lowercase) == length())
			return length();
		return ZERO;
	}

	public int getLetterOnlyBonus() {
		return (numbers == ZERO && symbols == ZERO) ? length() : 0;
	}

	public int getNumberOnly() {
		if (numbers == length())
			return length();
		return ZERO;
	}

	public int getNumberOnlyBonus() {
		return (numbers == length()) ? length() : 0;
	}

	public int getRepeatedCharacters() {
		int repeated = ZERO;
		final List<Character> prev = new ArrayList<Character>();
		for (char c : password) {
			if (prev.contains(c))
				repeated++;
			prev.add(c);
		}
		return repeated;
	}

	public int getScore() {
		int partial = getTotalAdditionBonuses() - getTotalDeductionBonuses();
		if (partial > MAX_SCORE) {
			return MAX_SCORE;
		}
		if (partial > ZERO)
			return partial;
		return ZERO;
	}

	public int getTotalDeductionBonuses() {
		return getNumberOnlyBonus() + getLetterOnlyBonus()
				+ getConsecutiveLowercaseBonus()
				+ getConsecutiveUppercaseBonus() + getConsecutiveNumbersBonus();
	}

	public String getComplexity() {
		int score = getScore();
		if (score < WEAK_MIN_SCORE)
			return "Ruim";
		if (score >= WEAK_MIN_SCORE && score < GOOD_MIN_SCORE)
			return "Mediana";
		if (score >= GOOD_MIN_SCORE && score < STRONG_MIN_SCORE)
			return "Bom";
		if (score >= STRONG_MIN_SCORE && score < VERY_STRONG_MIN_SCORE)
			return "Forte";
		if (score >= VERY_STRONG_MIN_SCORE)
			return "Forte";
		return "Algo deu errado";
	}

	public String toJson() {
		final JsonBuilder json = new JsonBuilder();
		json.append("score", this.getScore());
		json.append("complexity", this.getComplexity());
		// addition scores
		json.append("totalAdditionsBonuses", this.getTotalAdditionBonuses());
		json.append("totalDeductionBonuses", this.getTotalDeductionBonuses());
		json.append("numberOfCharacters", this.length());
		json.append("numberOfCharactersBonus", this.getLengthBonus());
		json.append("uppercaseLetters", this.getUppercase());
		json.append("uppercaseLettersBonus", this.getUppercaseBonus());
		json.append("lowercaseLetters", this.getLowercase());
		json.append("lowercaseLettersBonus", this.getLowercaseBonus());
		json.append("numbers", this.getNumbers());
		json.append("numbersBonus", this.getNumbersBonus());
		json.append("symbols", this.getSymbols());
		json.append("symbolsBonus", this.getSymbolsBonus());
		json.append("middleNumbersAndSymbols", this.getMiddleNumbersOrSymbols());
		json.append("middleNumbersAndSymbolsBonus",
				this.getMiddleNumbersAndSymbolsBonus());
		json.append("requirements", this.getRequirements());
		json.append("requirementsBonus", this.getRequirementsBonus());
		// deduction scores
		json.append("lettersOnly", this.getLetterOnly());
		json.append("lettersOnlyBonus", this.getLetterOnlyBonus());
		json.append("numbersOnly", this.getNumberOnly());
		json.append("numbersOnlyBonus", this.getNumberOnlyBonus());
		json.append("consecutiveUppercase", this.getConsecutiveUppercase());
		json.append("consecutiveUppercaseBonus",
				this.getConsecutiveUppercaseBonus());
		json.append("consecutiveLowercase", this.getConsecutiveLowercase());
		json.append("consecutiveLowercaseBonus",
				this.getConsecutiveLowercaseBonus());
		json.append("consecutiveNumbers", this.getConsecutiveNumbers());
		json.append("consecutiveNumbersBonus",
				this.getConsecutiveNumbersBonus());
		json.append("sequentialLetters", this.getSequentialLetters());
		json.append("sequentialLettersBonus", this.getSequentialLettersBonus());
		json.append("sequentialNumbers", this.getSequentialNumbers());
		json.append("sequentialNumbersBonus", this.getSequentialNumbersBonus());
		json.append("sequentialSymbols", this.getSequentialSymbols());
		json.append("sequentialSymbolsBonus", this.getSequentialSymbolsBonus());
		json.append("repeatedCharacters", this.getRepeatedCharacters());
		return json.toString();
	}
}