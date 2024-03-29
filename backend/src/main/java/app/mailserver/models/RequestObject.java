package app.mailserver.models;

import java.util.HashMap;
import java.util.Map;


public class RequestObject {
    private Map<String, Object> params = new HashMap<String, Object>();
    
    public RequestObject() {
    }
    public RequestObject(Map<String,Object> params) {
        this.params = params;
    }

    public Map<String, Object> getparams() {
        return this.params;
    }
    public void setparams(Map<String,Object> params) {
        this.params = params;
    }
    public Object get(String key){
        if(params.get(key)==null){
            return "";
        }
      return params.get(key);
    }

    
}