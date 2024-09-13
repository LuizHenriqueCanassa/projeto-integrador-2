package br.com.luizcanassa.projetintegrador2.utils;

import lombok.experimental.UtilityClass;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

@UtilityClass
public final class StringUtils {

    public static String removeMobilePhoneMask(final String mobilePhone) {
        return mobilePhone
                .replace("-", "")
                .replace(" ", "")
                .replace("(", "")
                .replace(")", "");
    }

    public static String removeDocumentMask(final String document) {
        return document
                .replace("-", "")
                .replace(".", "");
    }

    public static String addDocumentMask(final String document) throws ParseException {
        var maskFormater = new MaskFormatter("###.###.###-##");
        maskFormater.setValueContainsLiteralCharacters(false);
        return maskFormater.valueToString(document);
    }

    public static String addMobilePhoneMask(final String mobilePhone) throws ParseException {
        var maskFormater = new MaskFormatter("(##) #####-####");
        maskFormater.setValueContainsLiteralCharacters(false);
        return maskFormater.valueToString(mobilePhone);
    }
}
