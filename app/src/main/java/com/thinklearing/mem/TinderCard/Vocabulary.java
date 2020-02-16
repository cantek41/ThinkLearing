package com.thinklearing.mem.TinderCard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vocabulary {

    public  Vocabulary(){}

    public  Vocabulary(int id,String word, String translate, String sentence) {
        this.id = id;
        this.word = word;
        this.translate = translate;
        this.sentence = sentence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("word")
    @Expose
    private String word;

    @SerializedName("translate")
    @Expose
    private String translate;

    @SerializedName("sentence")
    @Expose
    private String sentence;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
