package com.company;

import java.util.*;

public class ShannonFanoAlgorithm {

    private String originalText;
    private int originalTextLength;
    private HashMap<Character, String> resultOfCompression;
    private HashMap<Character, Double> frequencyOfCharacter;
    public ShannonFanoAlgorithm(String str) {
        super();
        originalText = str;
        originalTextLength = str.length();
        frequencyOfCharacter = new HashMap<Character, Double>();
        resultOfCompression = new HashMap<Character, String>();
        this.determineFrequency();
        this.compressedText();
    }
    private void compressedText() {
        List<Character> ListOfChar = new ArrayList<Character>();
        LinkedHashMap<Character, Double> compressedFreq = sortDesc();

        Iterator<Map.Entry<Character, Double>> entryIterator = compressedFreq.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<Character, Double> entry = entryIterator.next();
            ListOfChar.add(entry.getKey());
        }

        convertToBit(resultOfCompression, ListOfChar, true);
    }
    private void convertToBit(HashMap<Character, String> outCome, List<Character> ListOfCharacter, boolean up) {
        String toBit = "";
        if (!outCome.isEmpty()) {
            if(up){
                toBit="0";
            }else{
                toBit="1";
            }

        }

        for (Character ch : ListOfCharacter) {
            String string = (outCome.get(ch) == null) ? "" : outCome.get(ch);
            outCome.put(ch, string + toBit);
        }

        if (ListOfCharacter.size() >= 2) {
            int divider = (int) Math.floor((double) ListOfCharacter.size() / 2.0);

            List<Character> characterUpList = ListOfCharacter.subList(0, divider);
            convertToBit(outCome, characterUpList, true);
            List<Character> characterDownList = ListOfCharacter.subList(divider, ListOfCharacter.size());
            convertToBit(outCome, characterDownList, false);
        }
    }
    private void determineFrequency() {
        for (Character ch : originalText.toCharArray()) {
            if (frequencyOfCharacter.containsKey(ch)) {
                frequencyOfCharacter.put(ch, new Double(frequencyOfCharacter.get(ch) + 1.0));
            } else {
                frequencyOfCharacter.put(ch, 1.0);
            }
        }
    }

    public  LinkedHashMap<Character, Double>  sortDesc(){
        LinkedHashMap<Character, Double> sorted = new LinkedHashMap<>();
        frequencyOfCharacter.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));

       return sorted;
    }

    public String toBitText(){
        String text1 = "";
        for (Character c : originalText.toCharArray()){
            text1+=resultOfCompression.get(c);

        }
        return text1;
    }

    @Override
    public String toString() {
        String text = "";
        text += "Original Text: \n" + originalText + "\"\n";
        text += "====================================================================================================================================================\n";
        text += "Symbols|\tProbabilities|\tShannon-Fano Code\n";
        text += "====================================================================================================================================================\n";
        LinkedHashMap<Character, Double> compressedFreq = sortDesc();
         double res=0;
         double resfreq = 0.0;
        for (Character c : compressedFreq.keySet()) {
            String sign;
            if(c==' '){
               sign="space";
            }else{
                sign=String.valueOf(c);
            }
            double prob=Math.round(compressedFreq.get(c) / originalTextLength * 10000.0) / 10000.0;
            text += sign + "\t\t" + prob+ "\t\t" + resultOfCompression.get(c);
            text += "\n";
           res+= (double)(prob*resultOfCompression.get(c).length());
           resfreq += prob;
        }
        text += "====================================================================================================================================================\n";
        text+="Number of bits in the original text: "+originalTextLength*8+"\n";
        text+="Number of bits in the compressed text: "+toBitText().length()+"\n";
        text+= "Compression ratio = " +Math.round((double)(originalTextLength*8)/(toBitText().length())*100.0)/100.0+"\n";
        // str+="Average code length '= "+(double)(compressedResult.size()*8)/(originalStringLength*8);
        text+="Average code length "+Math.round(res/resfreq*1000.0)/1000.0;
        return text;
    }
}
