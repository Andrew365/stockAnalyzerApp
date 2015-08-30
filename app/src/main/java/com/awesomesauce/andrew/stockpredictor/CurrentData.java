package com.awesomesauce.andrew.stockpredictor;

/**
 * Created by andrew on 8/22/15.
 */
public class CurrentData {

    private String mticker;
    private double mavgPrice;
    private double mlavgPrice;
    private double ml6avgPrice;
    private double ml1avgPrice;
    private double mcurrPrice;
    private double mpredprice;
    private String mbuy;
    private double msellValue;
    private int msince;

    public void setMsince(int since){
        msince = since;
    }
    public int getMsince(){
        return msince;
    }

    public String getMticker() {
        return mticker;
    }

    public void setMticker(String mticker1) {
        mticker = mticker1;
    }

    public double getMavgPrice() {
        return mavgPrice;
    }

    public void setMavgPrice(double mavgPrice1) {
        mavgPrice = mavgPrice1;
    }

    public double getMlavgPrice() {
        return mlavgPrice;
    }

    public void setMlavgPrice(double MlavgPrice1) {
        mlavgPrice = MlavgPrice1;
    }


    public double getMl6avgPrice() {
        return ml6avgPrice;
    }


    public void setMl6avgPrice(double Ml6avgPrice1) {
        ml6avgPrice = Ml6avgPrice1;
    }

    public double getMl1avgPrice() {
        return ml1avgPrice;
    }

    public void setMl1avgPrice(double Ml1avgPrice1) {
        ml1avgPrice = Ml1avgPrice1;
    }

    public double getMcurrPrice(){
        return mcurrPrice;
    }

    public void setMcurrPrice(double McurrPrice1){
        mcurrPrice = McurrPrice1;
    }

    public double getMpredprice(){
        return mpredprice;
    }

    public void setMpredprice(double pp){
        mpredprice = pp;
    }

    public String getMbuy(){
        return mbuy;
    }
    public void setMbuy(String buy){
        mbuy = buy;
    }

    public double getMsellValue(){
        return msellValue;
    }
    public void setMsellValue(double sv){
        msellValue = sv;
    }
}

