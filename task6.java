import java.math.*;
import java.util.*;
import java.io.*;
import java.net.*;

interface MyConstants {
    String CONSTANT_ONE = "foo";
}

public class task6{   
    
    private static String VOWELS = "AEIOUYАЕЁИОУЫЭЮЯÀÁÂÃÄÅĀĂĄǺȀȂẠẢẤẦẨẪẬẮẰẲẴẶḀÆǼȄȆḔḖḘḚḜẸẺẼẾỀỂỄỆĒĔĖĘĚÈÉÊËȈȊḬḮỈỊĨĪĬĮİÌÍÎÏĲŒØǾȌȎṌṎṐṒỌỎỐỒỔỖỘỚỜỞỠỢŌÒÓŎŐÔÕÖŨŪŬŮŰŲÙÚÛÜȔȖṲṴṶṸṺỤỦỨỪỬỮỰẙỲỴỶỸŶŸÝ";
    
//1. Число Белла - это количество способов, которыми массив из n элементов может
//быть разбит на непустые подмножества. Создайте функцию, которая принимает
//число n и возвращает соответствующее число Белла.
    
    public static long bell(long a){
        //Число Белла можно вычислить как сумму чисел Стирлинга второго рода:
        long r = 0;
        long i = a;
        while (i>=0){
            r+=sterling2(a, i);
            i-=1;
        };
        return r;
    }
    private static Map<Long, HashMap< Long, Long > > COMPUTED = new HashMap<>();
    
    private static long sterling2(long n, long k) {
        //функция для нахождения числа Стирлинга второго рода:
                
        if ( (n > 0 & k == 0) | (n == 0 & k > 0) ) {
            return 0; 
        }
        if ( n == k ) {
            return 1;
        }
        if ( k > n ) {
            return 0;
        }
        long result = k * (sterling2(n-1, k)) + (sterling2(n-1, k-1));
        return result;
    }
  
//2. В «поросячей латыни» (свинский латинский) есть два очень простых правила:
//– Если слово начинается с согласного, переместите первую букву (буквы) слова до
//гласного до конца слова и добавьте «ay» в конец.
//have ➞ avehay
//cram ➞ amcray
//take ➞ aketay
//cat ➞ atcay
//shrimp ➞ impshray
//trebuchet ➞ ebuchettray
//– Если слово начинается с гласной, добавьте "yay" в конце слова.
//ate ➞ ateyay
//apple ➞ appleyay
//oaken ➞ oakenyay
//eagle ➞ eagleyay
//Напишите две функции, чтобы сделать переводчик с английского на свинский латинский.
//Первая функция translateWord (word) получает слово на английском и возвращает это
//слово, переведенное на латинский язык. Вторая функция translateSentence (предложение)
//берет английское предложение и возвращает это предложение, переведенное на латинский
//язык.
    
    public static String translateWord(String str){
        if (str.length()==0){
            return str;  // если слово пустое, вернуть пустое слово
        }
        else{
            if (VOWELS.indexOf(Character.toUpperCase(str.charAt(0)))!=-1){
                return str+"yay";           //если слово начинается с гласной буквы, добавить yay в конец
            }
            else{
                int i = 1;
                int t = str.length();
                while (i < t){
                    if(VOWELS.indexOf(Character.toUpperCase(str.charAt(i)))!=-1) break;
                    i+=1;                    // иначе переместите первую букву (буквы) слова до
//гласного до конца слова и добавить «ay» в конец.
                }
                if (i!=t){
                    str = str.substring(i)+str.substring(0, i);
                }
                return str + "ay";
            }
        }
    }
    public static String translateSentence(String str){
        StringBuilder temp = new StringBuilder();
        StringBuilder out = new StringBuilder();
        boolean push = false;
        for (char i : str.toCharArray()){
            if (Character.isLetter(i)){
                push = true;
                temp.append(i);
            }
            else{
                if (push){
                    push = false;
                    out.append(translateWord(temp.toString()));
                    temp = new StringBuilder();
                }
                out.append(i);
            }
        }
        return out.append(translateWord(temp.toString())).toString();
    }
    
    
    //3. Учитывая параметры RGB (A) CSS, определите, является ли формат принимаемых
//значений допустимым или нет. Создайте функцию, которая принимает строку
//(например, " rgb(0, 0, 0)") и возвращает true, если ее формат правильный, в
//противном случае возвращает false.
    public static boolean validColor(String str){
        
      //  RGB значения цвета- поддерживается во всех основных браузерах. Формат: 
      //  rgb(красный, зеленый, синий). Каждый параметр (красный, зеленый и синий)
      //   определяет интенсивность цвета целым числом от 0 до 255
      
     // RGBA значения цвета - поддерживаются всеми современными браузерами. 
     // Расширение RGB с альфа-каналом (прозрачность). Формат: 
     // rgba(красный, зеленый, синий, прозрачность). 
     // Параметр альфа число между 0.0 (полностью прозрачный) и 1.0 (полностью непрозрачный).
        StringStream stream = new StringStream(str);
        Token [] tokenArray;
        Token token = getToken(stream);
        //Токен — объект, создающийся в процессе лексического анализа
        //getToken - функция для лексического анализа
        tokenArray = getTokenList(stream);
        if (tokenArray == null) {
            return false;
        }
        for (int i = 0; i<3; i++){
            try{
                if ((Integer.parseInt(tokenArray[i].variable))>255){ 
                    // если одно из значении из каждого параметра (красный, зеленый и синий)
      // будет превышать 255, то вернуть false
                    return false;
                }
            }
            catch(Exception e){
                return false; // если формат данных неверный, то вернуть false
            }
        }
        if (token.variable == "rgba"){
            try{
                if ((Float.parseFloat(tokenArray[3].variable))>1.0){
                    return false; // если альфа число больше 1, то вернуть false
                }
                if (tokenArray[3].variable.length()>3) return false; // если размер альфа числа больше 3,
                // то вернуть false
                if (tokenArray.length > 4) return false; // если параметров больше 4, то вернуть false
                return true;
            }
            catch(Exception e){
                return false;// если формат данных неверный, то вернуть false
            }
        }
        else if (token.variable.equals("rgb")){
            if (tokenArray.length>3) return false; // если параметров больше 3, то вернуть false
            return true;
        }
        return false;
    }
    private static Token[] getTokenList(Stream input){
        // функция возвращает список токенов, заключенных в скобки и разделенных запятыми,
        // или null если формат данных неверен
        ArrayList<Token> tokens = new ArrayList<Token>();
        Token token = getToken(input);
        if (!token.variable.equals("(")){
            return null;
        }
        while (true){
            token = getToken(input);
            tokens.add(token);
            token = getToken(input);
            if (token.variable.equals(")")){
                Token [] ret = new Token[tokens.size()];
                for (int i = 0; i< ret.length; i++){
                    ret[i]=tokens.get(i);
                }
                return ret;
            }
            else if (!token.variable.equals(",")){
                return null;
            }
        }
    }
    
    private static Token getToken(Stream input){
        // функция получает токен из потока данных stream
        int i = ' ';
        char c=' ';
        StringBuilder out = new StringBuilder();
        while (Character.isSpace((char)i)){
            i = input.read(); // пропустить все пробелы
        }
        
        if (i==-1) return new Token("", 0); // если достигнут конец потока
        else c = (char) i;                  // то вернуть пустой токен
        
        if (Character.isLetter(c)){
            //если символ - буква, то
            do{
                out.append(c);
                i = input.read();
                if (i==-1) break;
                else c = (char) i;
            } while (Character.isLetter(c)|Character.isDigit(c));
        
            if (i!=-1) input.jump(-1);
            return new Token(out.toString().toLowerCase(), 1); // вернуть слово,
            // в котором первый символ - буква, остальные - буквы и цифры
        }
        
        if (Character.isDigit(c)){
            //если символ - цифра, то вернуть число
            do{
                out.append(c);
                i = input.read();
                if (i==-1) break;
                else c = (char) i;
            } while (Character.isDigit(c));
            
            if (Character.isLetter(c)){
                return new Token("invalid token", -1);
            }
            
            // если в числе содержится буква, то вернуть InvalidToken
            
            if (out.length()>1){
                if (out.charAt(0)=='0'){
                    return new Token("invalid token", -1);
                    // если в числе первая цифра - 0, то вернуть InvalidToken
                }
            }
            if (c=='.'){
                out.append('.');
                i = input.read();
                c = (char) i;
                if (!Character.isDigit(c)){
                    return new Token("invalid token", -1);
                }
                do{
                    out.append(c);
                    i = input.read();
                    if (i==-1) break;
                    else c = (char) i;
                } while (Character.isDigit(c));
                if (Character.isLetter(c)){
                    return new Token("invalid token", -1);
                }
            }
            if (i!=-1) input.jump(-1);
            return new Token(out.toString(), 2);
        }
        return new Token(Character.toString(c), 3);
    }
    
    private static class StringStream implements Stream{
        // представить строку в виде потока данных stream
        
        private int position;
        private char [] array;
        public StringStream(String a){
            this.array = a.toCharArray();
        }
        
        public void jump(long i){
            this.position += (int) i; // если число i < 0, то вернуться назад, 
            // если i > 0, то пропустить i символов 
            if (this.position > array.length) this.position = array.length;
            if (this.position < 0) this.position = 0;
        }
        
        public int read(){
            if (this.position >= array.length) return -1; // если достигнут конец 
            // строки, то 
            char r = this.array[this.position];
            // прочитать один символ
            this.position ++;
            // сместить позицию вперед
            return r;
        }
        public long position(){
            return this.position;
        }
        
    }
    private static interface Stream{
        public void jump(long i);
        public int read();
        public long position();
    }
    private static class Token{
        public int identifier;
        public String variable;
        public String toString(){
            return this.variable;
        }
        public Token(String str, int iden){
            this.identifier = iden;
            this.variable = str;
        }
    }
    
    
//4. Создайте функцию, которая принимает URL (строку), удаляет дублирующиеся
//параметры запроса и параметры, указанные во втором аргументе (который будет
//необязательным массивом).
    public static String stripUrlParams(String str) throws MalformedURLException {
        return stripUrlParams(str, null);
    }
    public static String stripUrlParams(String str, String [] argv) throws MalformedURLException{
        if (argv == null){
            argv = new String[0];
        }
        
            URL aURL = new URL(str); // пармер для url
            Map<String, String> query_pairs = new HashMap<String, String>(); // параметры запроса
            List<String> list = Arrays.asList(argv); //параметры, которые нужно удалить
            String [] pairs;
            try{
                pairs = aURL.getQuery().split("&");
            }
            catch (Exception e){
                return str;
            }
            String temp;
            for (String pair : pairs){
                int index = pair.indexOf("="); 
                if (index != -1){ 
                    temp = pair.substring(index+1);
                    pair = pair.substring(0, index);
                }
                else temp = ""; 
                if (!list.contains(pair)){ 
                    query_pairs.put(pair, temp); // добавить параметр запроса, который не содержится в list
                    // если параметр существует, то обновить
                }
            }
            StringBuilder out = new StringBuilder();
            
            out.append(aURL.getProtocol());           // добавить протокол 
            out.append("://");                        // добавить ://
            out.append(aURL.getAuthority());          // добавить компонент полномочии (имя хоста и порт, если имеется)
            out.append(aURL.getPath());               // добавить компонент пути
            
            Iterator keys = query_pairs.keySet().iterator(); //создать итератор ключей
            
            if (keys.hasNext()){
                temp = (String)keys.next();
                out.append("?").append(temp).append("=").append(query_pairs.get(temp));
            }
            while (keys.hasNext()){
                temp = (String)keys.next();
                out.append("&").append(temp).append("=").append(query_pairs.get(temp));
            }
            // добавить все параметры
            
            
            temp = aURL.getRef();       // ссылочный компонент
            
            if (temp!=null){
                out.append("#").append(temp);  // добавить ссылочный компонент если имеется
            }
            return out.toString();
            //вернуть измененную строку
            
        
    }
    
//    5. Напишите функцию, которая извлекает три самых длинных слова из заголовка
//газеты и преобразует их в хэштеги. Если несколько слов одинаковой длины,
//найдите слово, которое встречается первым.
    public static String[] getHashTags(String str){
        List<String> list = new ArrayList<String>();  //список всех слов с хэштегами
        
        String [] out;                                //список самых длинных слов
        String temp; 
        
        StringStream strStream = new StringStream(str); // поток данных для строки
        Token token;
        
        do{
            token = getWord(strStream);                                //получить слово
            if (token.identifier == 1) list.add("#"+token.variable);   //если является словом, то добавить в список
        } while (token.identifier != 0);                               //повторить до тех пор, пока token не пуст
        
        
        if (list.size()<=1){
            return list.toArray(new String[0]);        // если размер списка меньше или равен 1, то вернуть список
        }
        
        else if (list.size()==2){
            // если размер списка равен 2, то вернуть список в порядке убывания
            
            out = new String[2];
            temp = list.get(1);
            str = list.get(0);
            
            if (temp.length()>str.length()){
                out[0]=temp;
                out[1]=str;
            }
            else{
                out[1]=temp;
                out[0]=str;
            }
            return out;
        }
        
        else{
            // инача извлечь три самых длинных слова из списка и вернуть
            
            out = new String[3];
         //   System.out.println(list.size());
            int i, u, size, index, temp2;
            int [] indexes = {-1, -1, -1};
            int [] sizes = {0, 0, 0};
            
            for (u = 0; u < list.size(); u++){ // повторить для всех слов в списке
                size = list.get(u).length();   // размер слова 
                index = u;                     // индекс слова
                for (i = 0; i<3; i++){        
                    if (size > sizes[i]){       // если размер слова больше чем размер i-ой слова  
                        temp2 = size;           // в списке самых длинных слов, то поместить слово
                        size = sizes[i];        // в список и убрать из списка самое короткое слово
                        sizes[i] = temp2;
                        temp2 = index;
                        index = indexes[i];
                        indexes[i] = temp2;
                    }
                }
            }
            for (i = 0; i<3; i++) out[i] = list.get(indexes[i]);
            return out;
        }
    }
    
    private static Token getWord(Stream input){
        //  получить слово из потока 
        int i = ' ';
        char c=' ';
        StringBuilder out = new StringBuilder();
        while (Character.isSpace((char)i)){
            i = input.read();
        }
        
        if (i==-1) return new Token("", 0);
        else c = (char) i;
        
        if (Character.isLetter(c)|Character.isDigit(c)|c=='_'){
            do{
                out.append(c);
                i = input.read();
           //     System.out.print(c);
                if (i==-1) break;
                else c = (char) i;
            } while (Character.isLetter(c)|Character.isDigit(c)|c=='_');
            if (i!=-1) input.jump(-1);
            return new Token(out.toString().toLowerCase(), 1);
        }
        return new Token(Character.toString(c), 3);
    }
    
//6    Создайте функцию, которая принимает число n и возвращает n-е число в
//последовательности Улама
    public static int ulam(int i){
        int current = (int)ulamSequence.get(ulamSequence.size()-1)+1;
        int count;
        int size = ulamSequence.size();
        while (size<=i){
            count = 0;
            for (int j = 0; j < size - 1; j++) { 
                for (int k = j + 1; k < size; k++) { 
                    if ((int)ulamSequence.get(j) + (int)ulamSequence.get(k) == current) { 
                        count++; 
                    } 
                    if (count > 1) 
                        break; 
                } 
                if (count > 1) 
                    break; 
            } 
            if (count == 1) { 
                ulamSequence.add(current); 
            } 
            current +=1;
            size = ulamSequence.size();
        //    System.out.println(current);
        }
        return (int)ulamSequence.get(i-1);
    }
    static private List ulamSequence = new ArrayList<Integer> (Arrays.asList(1, 2));
    
//7. Напишите функцию, которая возвращает самую длинную неповторяющуюся
//подстроку для строкового ввода.
    static String longestNonrepeatingSubstring(String str)
    {   
        int longestSubstringSize = 0;
        int indexOfLongestSubstring = 0;
        int index = 0; int length;
        StringBuilder sequence = new StringBuilder();
        char i; int u, strlen = str.length(); 
        for (u = 0; u< strlen; u++){
            i = str.charAt(u);
            index = sequence.indexOf(Character.toString(i));
            if (index!=-1){
                length = sequence.length();
                if (length > longestSubstringSize){
                    longestSubstringSize = length;
                    indexOfLongestSubstring = u - length;
                }
                sequence = new StringBuilder(sequence.substring(index+1));
            }
            sequence.append(i);
        }
        if (sequence.length()>longestSubstringSize){
            return sequence.toString();
        }
        else{
            return str.substring(indexOfLongestSubstring, longestSubstringSize);
        }
    }
    
//8. Создайте функцию, которая принимает арабское число и преобразует его в римское
//число.
    public static String convertToRoman(int number) {
        int l;
        StringBuilder out = new StringBuilder();
        while (number != 0){
            l=roman.floorKey(number);//возвращается наибольший ключ из treemap
        // который меньше или равен number
            number -= l;                 // вычесть из number наибольший ключ из treemap
            // который меньше или равен number
            
            out.append(roman.get(l)); // добавить в строку значение из ключа
        } 
        return out.toString();
    }
    private static TreeMap<Integer, String> roman = new TreeMap<Integer, String>();
    static {
        roman.put(1000, "M");
        roman.put(900, "CM");
        roman.put(500, "D");
        roman.put(400, "CD");
        roman.put(100, "C");
        roman.put(90, "XC");
        roman.put(50, "L");
        roman.put(40, "XL");
        roman.put(10, "X");
        roman.put(9, "IX");
        roman.put(5, "V");
        roman.put(4, "IV");
        roman.put(1, "I");
    }
    
//9. Создайте функцию, которая принимает строку и возвращает true или false в
//зависимости от того, является ли формула правильной или нет


    public static Object _a1(Stream l, Object b, String k) throws Exception {
        // _а1 - генерирует синтаксическое дерево, b - корень
        Token a = getToken(l); // получить токен
        
        if (a.variable.equals(k)){
            return b;  // если токен равен k, то вернуть b
        }
        
        else if (!keys.contains(a.variable)){          // если a - не оператор ( "+", "-", "*", "/", "^" )
            if (b==null){                      
                if (a.variable.equals("(")){                // если а - скобка 
                    return _a1(l, _a1(l, null, ")"), k);     
                }
                else if (a.identifier==3|a.identifier==-1){                        //если а - не число или слово  
                    throw new Exception("invalid expression");
                }
                return _a1(l, a, k);           
            }
            else{
                throw new Exception("an expression is expected");  // если ожидался оператор
            }
        }
        else{
            List y = new ArrayList<Object>(); // новый корень
            while (true){
                Object [] w = _a3(l, _a2(l, null), 0);    // 
                // _а3 возвращает 
                // w[0] узел если следующий оператор приоритетный,  
                // иначe число или слово
                // w[1] - следующий неприоритетный оператор или просто токен
                // w[2] - является ли w[0] узлом
                y.add(a);
                if (!(boolean)w[2]){ 
                    y.add(b);             /// 
                    y.add(w[0]);          ///
                }                         /// если w[0] - узел, 
                else{                     /// то w[0] идет первым
                    y.add(w[0]);          ///
                    y.add(b);             ///
                }
                
                
                a = (Token)w[1];               // присвоить 
                
                if (a.variable.equals(k)){
                    return y;                  // если токен равен k, то вернуть b
                }
                
                if (!keys.contains(a.variable)){ // если токен не оператор, то вернуть ошибку
                    throw new Exception("illegal expression");
                }
                b = y;                          
                y = new ArrayList<Object>();    
            }
        }
    }
    public static Object _a2(Stream l, Object b) throws Exception{
        Token a = getToken(l);         // получить токен
        if (!simple_keys.contains(a.variable)){  // если токен не равен "-" или "+"
            if (a.variable.equals("(")) return _a1(l, null, ")");    // если токен = "(",
            // то вернуть выражение в скобках
            
            if (a.variable=="") throw new Exception("an expression is expected");
            //вывести ошибку, если токен пустой
            
            if (a.identifier==3|a.identifier==-1) throw new Exception("illegal expression");
            // вывести ошибку, если токен не число или слово
            
            return a;
        }
        List c = new ArrayList<Object> (); // корень узла
        List y = c;  // текущий узел
        List k;   // новый узел
    
        y.add(a);   //добавить оператор
        y.add(null); // добавить null
        a = getToken(l); // получить новый токен
        while (simple_keys.contains(a.variable)){ 
            //выполнить до тех пор, пока а равен "-" или "+"
            k = new ArrayList<Object>(); // объявить новый узел
            y.add(k); // добавить 
            y = k;
            y.add(a);
            y.add(null);
            a = getToken(l); // получить новый токен
        }
        
        if (a.variable=="") throw new Exception("an expression is expected");
            //вывести ошибку, если токен пустой
        if (a.identifier==3|a.identifier==-1) throw new Exception("illegal expression");
            // вывести ошибку, если токен не число или слово
        
        if (a.variable.equals("(")) y.add(_a1(l, null, ")"));    // если токен = "(",
            // то добавить выражение в скобках    
        else y.add(a);
        return c;
    }
    
    public static Object[] _a3(Stream l, Object b, int c) throws Exception{
        Token a;
        // возвращает массив из объектов
        // w[0] узел если следующий оператор имеет приоритет "с",  
        // иначe число или слово
        // w[1] - следующий неприоритетный оператор или просто токен
        // w[2] - является ли w[0] узлом
        
        if (c>=prior_keys_length){  // если нет ключей с приоритетом "с"
            a = (Token) getToken(l);
            return new Object [] { b, a, false };
        }
        
        Object[] w = _a3(l, b, c+1);  // 
        
        a = (Token) w[1]; // w[1] - cледующий оператор с приоритетом "с"
        // или ниже или просто токен
        
        if (!prior(a, c)) return w; // если приоритет а ниже "с", то вернуть w
        
        List y = new ArrayList<Object>();
        
        while (prior(a, c)){
            w = _a3(l, _a2(l, null), c+1);
            y.add(a);
            if ( !(boolean) w[2]){
                y.add(b);
                y.add(w[0]);
            }
            else{
                y.add(w[0]);
                y.add(b);
            }
            a = (Token) w[1];
            b = y;
            y = new ArrayList<Object>();
        }
        return new Object[]{ b, a, true };
    }    
    
    public static boolean prior(Token a, int c){
        // возвращает true, если приоритет оператора выше или равен "c"
        try{
            while (!((List)prior_keys.get(c)).contains(a.variable)){
                c++;
            }
            return true; 
        }
        catch(IndexOutOfBoundsException e){
            return false;
        }
    }
     
    public static List keys = new ArrayList<String> (Arrays.asList("+", "-", "*", "/", "^")); // операторы 
    public static List simple_keys = new ArrayList<String> (Arrays.asList("+", "-")); // операторы "+", "-"
    public static List prior_keys = new ArrayList<ArrayList<String>>(); // приоритетные операторы
    public static Map operations = new HashMap<String, Expression>();   // свойства оператора (какие операции выполняет оператор)
    public static int prior_keys_length = 2; // 
    
    static{
        // задать свойства операторов и добавить приоритетные операторы
        operations.put("+", new Expression(){
           @Override
           public float evaluate(float a, float b){
               return a+b;
           } 
        });
        operations.put("-", new Expression(){
           @Override
           public float evaluate(float a, float b){
               return a-b;
           } 
        });
        operations.put("*", new Expression(){
           @Override
           public float evaluate(float a, float b){
               return a*b;
           } 
        });
        operations.put("/", new Expression(){
           @Override
           public float evaluate(float a, float b){
               return a/b;
           } 
        });
        operations.put("^", new Expression(){
           @Override
           public float evaluate(float a, float b){
               return (float)Math.pow((double)a, (double)b);
           } 
        });
        prior_keys.add(Arrays.asList("*", "/")); 
        prior_keys.add(Arrays.asList( "^"));
    }
    
    public static float solve(Object expr, HashMap<String, Float> values){
        // функция вычисляет значение по AST 
        if (expr instanceof List){ // если expr - список  
            List expr1 = (List) expr; 
            return ((Expression)operations.get(expr1.get(0).toString())) 
            //получить свойства оператора 
            // 
            .evaluate(
                solve(expr1.get(1), values), // вычислить первый операнд
                solve(expr1.get(2), values)  // вычислить второй операнд
            );
        }
        else if (expr == null) return 0; // если expr - null
        else{     
            // иначе 
            Float r; 
            String s = expr.toString();
            try{
                r = Float.parseFloat(s); // ecли s - число
                return r;
            }
            catch(NumberFormatException e){
                // иначе
                r = values.get(s);           //если values содержит ключ s
                if (r!=null) return r;       //
                else{
                    Scanner input = new Scanner(System.in);  // если values не содержит
                    System.out.print("enter value: ");       // ключ, то запросить
                    System.out.print(s);                     // пользователя
                    System.out.print(":= ");
                    while (!input.hasNextFloat()){          
                        System.out.print("incorrect value\n");       
                        System.out.print(s);                     
                        System.out.print(":= ");
                        input.next();
                    }
                    r = input.nextFloat();
                    values.put(s, r);
                    return r;
                }
            }
        }
    }
    
    public static float solve(Object expr){
        return solve(expr, new HashMap<String, Float>());
    }
    
    public static Object getAST(Stream input) throws Exception{
        return _a1(input, null, "");
    }
    
    public static interface Expression{
        public float evaluate(float a, float b); // интерфейс оператора
    }
     
    public static boolean formula(String input) throws Exception{
        String [] array = input.split("="); // разделить строки на '='
        if (array.length < 1){
            return false; 
        }
        else{
            
            float t = (solve(getAST(new StringStream(array[0])))); // решить первое уравнение
          //  System.out.println(getAST(new StringStream(array[0])));
            int i = 1;
            while (i < array.length){ // сравнить значения других уравнении с первым уравнение
            //    System.out.println(getAST(new StringStream(array[i])));
                if (t!=(solve(getAST(new StringStream(array[i]))))) return false; // если хоть одно 
                // уравнение не равно первому, то вернуть false
                i+=1;
            }
        }
        return true; // вернуть true если все уравнения равны
    }
    
    
    //10. Число может не быть палиндромом, но его потомком может быть. Прямой потомок
//числа создается путем суммирования каждой пары соседних цифр, чтобы создать
//цифры следующего числа.
//Например, 123312 – это не палиндром, а его следующий потомок 363, где: 3 = 1 + 2; 6 = 3
//+ 3; 3 = 1 + 2.
//Создайте функцию, которая возвращает значение true, если само число является
//палиндромом или любой из его потомков вплоть до 2 цифр (однозначное число -
//тривиально палиндром).
    public static boolean palindromedescendant(int input){
        return palindromedescendant((""+input));
    }
    
    public static boolean palindromedescendant(String str){
        char [] q = str.toCharArray();
        if (q.length<=1){
            return false;
        }
        int i = 0;
        int u = q.length-1;
        while (i<u){
            if (q[i] != q[u]){
                if ((q.length % 2)==1){
                    String out = new String(q);
                    boolean r = false;
                    r = palindromedescendant(nextdescendant(Character.toString(q[0])+
                    out.substring(1)));
                    r = r|( palindromedescendant(nextdescendant(
                    out.substring(0, out.length()-2))+q[q.length-1]));
                    return r;
                }
                else{
                    return palindromedescendant(nextdescendant(new String(q)));
                }
            };
            i+=1;
            u-=1;
        }
        return true;
    }
    
    public static String nextdescendant(String str){
        char [] q = str.toCharArray();
        StringBuilder out = new StringBuilder();
        int i = 0;
        while (i>q.length){
            int x = q[i];
            x -= '0';
            i += 1;
            x += q[i];
            x -= '0';
            i += 1;
            out.append(Integer.toString(i));
        }
        return out.toString();
    }
            
    public static void main(String [] argv) throws Exception {
        System.out.println(formula(" - 34 - 123^2 - 3 = 6 - 6"));
    }
}
