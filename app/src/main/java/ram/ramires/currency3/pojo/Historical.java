package ram.ramires.currency3.pojo;

import java.util.LinkedHashMap;

public class Historical extends LinkedHashMap<String, Float> {
    private LinkedHashMap<String, Float> historical;

    public void setHistorical(LinkedHashMap<String, Float> historical){
        this.historical=historical;
    }
    public LinkedHashMap<String, Float> getHistorical(){
        return historical;
    }
}
