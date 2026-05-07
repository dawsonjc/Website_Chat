package com.brewery.web.stringutils;

public class TokenJumper {
    int index;
    int endInd;
    String inputString;
    String token;

    public TokenJumper(String inputString, String token) {
        this.index = 0;
        this.endInd = 0;
        this.inputString = inputString;
        this.token = token;
    }


    public String nextToken() {
        String retString = null;
        if(this.endInd != -1) {
            this.endInd = this.inputString.indexOf(this.token, this.index);

            if(endInd != -1) {
                retString = this.inputString.substring(this.index, this.endInd);
            } else {
                retString = inputString.substring(this.index);
            }
            this.index = this.endInd + this.token.length();
        }

        return retString;
    }


    public int skipTokens(int num) {
        int count = 0;

        while(this.endInd != -1 && count < num) {
            this.endInd = this.inputString.indexOf(this.token, this.index);
            this.index = this.endInd + 1;
            count++;
        }

        return count;
    }

    public boolean hasMoreTokens() {
        return this.remainingTokens() > 0;
    }

    public int remainingTokens() {
        int count = 0;
        int tmpInd = this.index;
        int tmpEndInd = this.endInd;

        while(tmpEndInd != -1) {
            tmpEndInd = this.inputString.indexOf(this.token, tmpInd);
            if(tmpEndInd != -1) {
                tmpInd = tmpEndInd + 1;
            }
            count++;
        }

        return count;
    }
}
