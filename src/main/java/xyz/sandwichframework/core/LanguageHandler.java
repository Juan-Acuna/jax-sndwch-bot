package xyz.sandwichframework.core;

import xyz.sandwichframework.models.Language;

public class LanguageHandler {
	public static Language getLenguageParent(Language lang) {
		switch(lang) {
			case ES_MX:
			case ES_ES:
				return Language.ES;
			case EN_US:
			case EN_EN:
				return Language.EN;
			default:
				return lang;
		}
	}
}
