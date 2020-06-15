import java.lang.Math;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
import java.lang.Math;


public class Task_5 {
    //1
    //Пришло время отправлять и получать секретные сообщения.
    //Создайте две функции, которые принимают строку и массив и возвращают
    //закодированное или декодированное сообщение.
    //Первая буква строки или первый элемент массива представляет собой символьный код
    //этой буквы. Следующие элементы-это различия между символами: например, A +3 --> C
    //или z -1 --> y.

    public static int[] encrypt(String msg) {
        int[] result = new int[msg.length()];
        char[] chars = msg.toCharArray();
        result[0] = chars[0];
        for (int i = 1; i < chars.length; i++) {
            result[i] = chars[i] - chars[i - 1];
        }

        return result;
    }

    public static String decrypt(int[] arr) {
        char[] chars = new char[arr.length];
        chars[0] = (char) arr[0];
        int code = arr[0];
        for (int i = 1; i < arr.length; i++) {
            code += arr[i];
            chars[i] = (char) (code);
        }

        return new String(chars);
    }


    //2
    //Создайте функцию, которая принимает имя шахматной фигуры, ее положение и
    //целевую позицию. Функция должна возвращать true, если фигура может двигаться
    //к цели, и false, если она не может этого сделать.
    //Возможные входные данные - "пешка", "конь", "слон", "Ладья", "Ферзь"и " король".

    private static int LetterDiff;
    private static int NumDiff;

    private static void FindDiffs(String start, String end) {
        LetterDiff = Math.abs(end.charAt(0) - start.charAt(0));
        NumDiff = Math.abs(end.charAt(1) - start.charAt(1));
    }


    private static boolean rookCanMove(String start, String end) {
        FindDiffs(start, end);
        return LetterDiff == 0 || NumDiff == 0;
    }

    private static boolean knightCanMove(String start, String end) {
        FindDiffs(start, end);
        return LetterDiff == 2 && NumDiff == 1 || LetterDiff == 1 && NumDiff == 2;
    }

    private static boolean bishopCanMove(String start, String end) {
        FindDiffs(start, end);
        return LetterDiff == NumDiff;
    }

    private static boolean queenCanMove(String start, String end) {
        return rookCanMove(start, end) || bishopCanMove(start, end);
    }

    private static boolean kingCanMove(String start, String end) {
        return NumDiff <= 1 && LetterDiff <= 1;
    }

    private static boolean pawnCanMove(String start, String end) {
        if (LetterDiff != 0)
            return false;
        if (NumDiff > 2)
            return false;
        if (start.charAt(1) != '2' && NumDiff > 1)
            return false;
        return true;
    }

    public static boolean canMove(String figure, String start, String end) {
        if (figure.equals("Pawn")) return pawnCanMove(start, end);
        else if (figure.equals("Rook")) return rookCanMove(start, end);
        else if (figure.equals("Knight")) return knightCanMove(start, end);
        else if (figure.equals("Bishop")) return bishopCanMove(start, end);
        else if (figure.equals("Queen")) return queenCanMove(start, end);
        else if (figure.equals("King")) return kingCanMove(start, end);
        return false;
    }


    //3
    //Входная строка может быть завершена, если можно добавить дополнительные
    //буквы, и никакие буквы не должны быть удалены, чтобы соответствовать слову.
    //Кроме того, порядок букв во входной строке должен быть таким же, как и порядок
    //букв в последнем слове.
    //Создайте функцию, которая, учитывая входную строку, определяет, может ли слово быть
    //завершено.

    public static boolean canComplete(String sub, String full) {
        int count = 0;
        for (int i = 0; i < full.length(); i++) {
            if (full.charAt(i) == sub.charAt(count)) {
                count++;
                if (count == sub.length())
                    return true;
            }
        }

        return false;
    }

    //4
    //Создайте функцию, которая принимает числа в качестве аргументов, складывает их
    //вместе и возвращает произведение цифр до тех пор, пока ответ не станет длиной
    //всего в 1 цифру.

    public static int sumDigProd(int[] numbers) {
        int result = 0;

        for (int i = 0; i < numbers.length; i++) {
            result += numbers[i];
        }

        result = Block4.Math.multiplyDigits(result);
        while (Integer.toString(result).length() != 1)
            result = Block4.Math.multiplyDigits(result);
        return result;
    }


    //5
    //Напишите функцию, которая выбирает все слова, имеющие все те же гласные (в
    //любом порядке и / или количестве), что и первое слово, включая первое слово.

    public static List<String> sameVowelGroup(String[] arr) {
        List<String> result = new LinkedList<String>();
        String vowels = Block4.StringOps.takeVowels(arr[0]);
        result.add(arr[0]);

        for (int i = 1; i < arr.length; i++) {
            boolean flag = true;
            for (int j = 0; j < arr[i].length(); j++) {
                if (isVowel(arr[i].charAt(j)) && !vowels.contains(arr[i].substring(j, j + 1)))
                    flag = false;
            }
            if (flag)
                result.add(arr[i]);
        }
        return result;

    }

    private static boolean isVowel(char ch) {
        String vowels = "AOUIEaouie";
        return vowels.contains(Character.toString(ch));
    }

    //6
    //Создайте функцию, которая принимает число в качестве аргумента и возвращает
    //true, если это число является действительным номером кредитной карты, а в
    //противном случае-false.
    //Номера кредитных карт должны быть длиной от 14 до 19 цифр и проходить тест Луна,
    //описанный ниже:
    //– Удалите последнюю цифру (это"контрольная цифра").
    //– Переверните число.
    //– Удвойте значение каждой цифры в нечетных позициях. Если удвоенное значение имеет
    //более 1 цифры, сложите цифры вместе (например, 8 x 2 = 16 ➞ 1 + 6 = 7).
    //– Добавьте все цифры.
    //– Вычтите последнюю цифру суммы (из шага 4) из 10. Результат должен быть равен
    //контрольной цифре из Шага 1.

    public static boolean validateCard(long number) {

        String num = Long.toString(number);
        if (num.length() < 14 || num.length() > 19)
            return false;

        int lastDigit = Character.digit(num.charAt(num.length() - 1), 10);

        num = num.substring(0, num.length() - 1);
        StringBuilder input = new StringBuilder();
        input.append(num);
        input = input.reverse();
        char[] digits = input.toString().toCharArray();


        for (int i = 0; i < input.length(); i += 2) {
            digits[i] = doubleDigit(digits[i]);
        }

        int sum = 0;

        for (int i = 0; i < digits.length; i++) {
            sum += Character.digit(digits[i], 10);
        }

        String Sum = Integer.toString(sum);

        int tenDiff = 10 - Character.digit(Sum.charAt(Sum.length() - 1), 10);

        return tenDiff == lastDigit;

    }

    private static char doubleDigit(char digit) {
        int result = Character.digit(digit, 10);
        result *= 2;
        if (Integer.toString(result).length() != 1) {
            char firstDigit = Integer.toString(result).charAt(0);
            char secondDigit = Integer.toString(result).charAt(1);

            result = Character.digit(firstDigit, 10) + Character.digit(secondDigit, 10);
            return Character.forDigit(result, 10);
        }

        return Character.forDigit(result, 10);
    }

    //7
    //Напишите функцию, которая принимает положительное целое число от 0 до 999
    //включительно и возвращает строковое представление этого целого числа,
    //написанное на английском языке.Тоже самое нужно сделать и для русского языка.

    public static String toEng(int num) {
        String result = "";
        if (num / 100 > 0)
            result += digitToEng(num / 100) + " hundred ";
        num = num % 100;
        if (num / 10 > 1) {
            result += tensToEng(num / 10) + " ";
        } else {
            if (num / 10 == 0 && num % 10 != 0)
                result += digitToEng(num % 10);
            else if (num / 10 == 1)
                result += teenToEng(num % 100);

            return result;
        }

        result += digitToEng(num % 10);
        return result;
    }

    private static String digitToEng(int digit) {
        switch (digit) {
            case 0:
                return "zero";
            case 1:
                return "one";
            case 2:
                return "two";
            case 3:
                return "three";
            case 4:
                return "four";
            case 5:
                return "five";
            case 6:
                return "six";
            case 7:
                return "seven";
            case 8:
                return "eight";
            case 9:
                return "nine";
        }

        return "Unknown";
    }

    private static String tensToEng(int tens) {
        switch (tens) {
            case 2:
                return "twenty";
            case 3:
                return "thirty";
            case 4:
                return "fourty";
            case 5:
                return "fifty";
            case 6:
                return "sixty";
            case 7:
                return "seventy";
            case 8:
                return "eighty";
            case 9:
                return "ninety";
        }

        return "Unknown";
    }

    private static String teenToEng(int teen) {
        switch (teen) {
            case 10:
                return "ten";
            case 11:
                return "eleven";
            case 12:
                return "twelve";
            case 13:
                return "thirteen";
            case 14:
                return "fourteen";
            case 15:
                return "fifteen";
            case 16:
                return "sixteen";
            case 17:
                return "seventeen";
            case 18:
                return "eighteen";
            case 19:
                return "nineteen";
        }

        return "Unknown";
    }

    public static String toRus(int num) {
        String result = "";
        if (num / 100 > 0)
            result += hundredsToRus(num / 100);
        num = num % 100;
        if (num / 10 > 1) {
            result += tensToRus(num / 10) + " ";
        } else {
            if (num / 10 == 0 && num % 10 != 0)
                result += digitToRus(num % 10);
            else if (num / 10 == 1)
                result += teenToRus(num % 100);

            return result;
        }

        result += digitToRus(num % 10);
        return result;
    }

    private static String hundredsToRus(int hundreds) {
        switch (hundreds) {
            case 1:
                return "сто ";
            case 2:
                return "двести ";
            case 3:
                return "триста ";
            case 4:
                return "четыреста ";
            case 5:
                return "пятьсот ";
            case 6:
                return "шестьсот ";
            case 7:
                return "семьсот ";
            case 8:
                return "восемьсот ";
            case 9:
                return "девятьсот ";
        }

        return "Unknown";
    }

    private static String digitToRus(int digit) {
        switch (digit) {
            case 0:
                return "ноль ";
            case 1:
                return "один ";
            case 2:
                return "два ";
            case 3:
                return "три ";
            case 4:
                return "четыре ";
            case 5:
                return "пять ";
            case 6:
                return "шесть ";
            case 7:
                return "семь ";
            case 8:
                return "восемь ";
            case 9:
                return "девять ";
        }

        return "Unknown";
    }

    private static String tensToRus(int tens) {
        switch (tens) {
            case 2:
                return "двадцать ";
            case 3:
                return "тридцать ";
            case 4:
                return "сорок ";
            case 5:
                return "пятьдесят ";
            case 6:
                return "шестьдесят ";
            case 7:
                return "семьдесят ";
            case 8:
                return "восемьдесят ";
            case 9:
                return "девяносто ";
        }

        return "Unknown";
    }

    private static String teenToRus(int teen) {
        switch (teen) {
            case 10:
                return "десять ";
            case 11:
                return "одиннадцать ";
            case 12:
                return "двенадцать ";
            case 13:
                return "тринадцать ";
            case 14:
                return "четырнадцать ";
            case 15:
                return "пятнадцать ";
            case 16:
                return "шестнадцать ";
            case 17:
                return "семнадцать ";
            case 18:
                return "восемнадцать ";
            case 19:
                return "девятнадцать ";
        }

        return "Unknown";
    }

    //8
    //Создайте функцию, которая возвращает безопасный хеш SHA-256 для данной строки.
    //Хеш должен быть отформатирован в виде шестнадцатеричной цифры.

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String getSha256Hash(String msg) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(msg.getBytes(StandardCharsets.UTF_8));

        char[] hexChars = new char[hash.length * 2];
        for (int j = 0; j < hash.length; j++) {
            int v = hash[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    //9
    //Напишите функцию, которая принимает строку и возвращает строку с правильным
    //регистром для заголовков символов в серии "Игра престолов".
    //Слова and, the, of и in должны быть строчными. Все остальные слова должны иметь
    //первый символ в верхнем регистре, а остальные-в Нижнем.
    //Примечания:
    //– Знаки препинания и пробелы должны оставаться в своих первоначальных положениях.
    //– Дефисные слова считаются отдельными словами.
    //– Будьте осторожны со словами, которые содержат and, the, of или in.

    public static String correctTitle(String title) {
        String[] words = title.split(" ");
        String result = "";
        for (String word : words) {
            if (word.toLowerCase().equals("the") || word.toLowerCase().equals("in") ||
                    word.toLowerCase().equals("and") || word.toLowerCase().equals("of")) {
                result += word.toLowerCase() + " ";
                continue;
            }

            if (word.contains("-")) {
                String firstHalf = word.substring(0, 1).toUpperCase() + word.substring(1, word.indexOf('-'));
                String secondHalf = word.substring(word.indexOf('-') + 1, word.indexOf('-') + 2).toUpperCase() +
                        word.substring(word.indexOf('-') + 2);
                result += firstHalf + "-" + secondHalf + " ";
                continue;
            }
            word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            result += word + " ";
        }
        return result;
    }

    //10
    //Напишите функцию, которая принимает целое число n и возвращает "недопустимое", если
    //n не является центрированным шестиугольным числом или его иллюстрацией в виде
    //многострочной прямоугольной строки в противном случае.

    public static String hexLattice(int num) {
        int n = centeredHexNum(num);
        if (n == -1)
            return "Invalid";

        String result = "";

        for (int i = 0; i < n; i++) {

            for (int k = 0; k < n - i; k++) {
                result += " ";
            }

            for (int j = 0; j < n + i; j++) {
                result += "o ";
            }
            result += "\n";
        }


        for (int i = n - 2; i >= 0; i--) {

            for (int k = 0; k < n - i; k++)
                result += " ";

            for (int j = 0; j < n + i; j++) {
                result += "o ";
            }
            result += "\n";
        }
        return result;


    }

    private static int centeredHexNum(int num) {
        int count = 1;
        int result = count * 3 * (count - 1) + 1;
        while (result <= num) {
            if (result == num)
                return count;
            else {
                count++;
                result = count * 3 * (count - 1) + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("№1: " + encrypt("Hello"));
        System.out.println("№1: " + decrypt(new int[]{72, 29, 7, 0, 3}));
        System.out.println("№2: " + canMove("Pawn", "E2", "E4"));
        System.out.println("№3: " + canComplete("butl", "beautiful"));
        System.out.println("№4: " + sumDigProd(new int[]{0}));
        System.out.println("№5: " + sameVowelGroup(new String[]{"hoops", "chuff", "bot", "bottom"}));
        System.out.println("№6: " + validateCard(1234567890123452L));
        System.out.println("№7: " + toEng(919));
        System.out.println("№7: " + toRus(919));
        System.out.println("№8: " + getSha256Hash("password123"));
        System.out.println("№9: " + correctTitle("jOn SnoW, kINg IN thE noRth."));
        System.out.println("№10: " + hexLattice(37));
    }
}
