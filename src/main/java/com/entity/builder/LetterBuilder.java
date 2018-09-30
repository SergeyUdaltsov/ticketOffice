package com.entity.builder;

import com.entity.Letter;

/**
 * Created by Serg on 29.09.2018.
 */
public class LetterBuilder {

    private Letter letter;

    public LetterBuilder(){
        this.letter = new Letter();
    }

    public LetterBuilder buildAddress(String address) {
        this.letter.setAddress(address);
        return this;
    }

    public LetterBuilder buildSubject(String topic) {
        this.letter.setSubject(topic);
        return this;
    }

    public LetterBuilder buildText(String text){
        this.letter.setText(text);
        return this;
    }

    public Letter build() {
        return letter;
    }
}
