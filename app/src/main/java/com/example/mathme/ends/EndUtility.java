package com.example.mathme.ends;

class EndUtility {
    String chosenOperators(String strChosenOperation) {
        String plus = "+", minus = "-", mult = "*", div = "/", comma = ", ";
        if (strChosenOperation.equalsIgnoreCase("a")) {
            return plus;
        } else if (strChosenOperation.equalsIgnoreCase("s")) {
            return minus;
        } else if (strChosenOperation.equalsIgnoreCase("m")) {
            return mult;
        } else if (strChosenOperation.equalsIgnoreCase("d")) {
            return div;
        } else if (strChosenOperation.equalsIgnoreCase("as")) {
            return plus + comma + minus;
        } else if (strChosenOperation.equalsIgnoreCase("am")) {
            return plus + comma + mult;
        }
        if (strChosenOperation.equalsIgnoreCase("ad")) {
            return plus + comma + div;
        }
        if (strChosenOperation.equalsIgnoreCase("sm")) {
            return minus + comma + mult;
        }
        if (strChosenOperation.equalsIgnoreCase("sd")) {
            return minus + comma + div;
        }
        if (strChosenOperation.equalsIgnoreCase("md")) {
            return minus + comma + div;
        }
        if (strChosenOperation.equalsIgnoreCase("asm")) {
            return plus + comma + minus + comma + mult;
        }
        if (strChosenOperation.equalsIgnoreCase("amd")) {
            return plus + comma + mult + comma + div;
        }
        if (strChosenOperation.equalsIgnoreCase("asd")) {
            return plus + comma + minus + comma + div;
        }
        if (strChosenOperation.equalsIgnoreCase("smd")) {
            return minus + comma + mult + comma + div;
        }
        //four checked
        if (strChosenOperation.equalsIgnoreCase("asmd")) {
            return plus + comma + minus + comma + mult + comma + div;
        }

        return "";
    }
}
