package pl.od.orderit.shops.fileStorage;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class File {

    private String name;
    private String url;

    public File(String name, String url){
        this.name = name;
        this.url = url;
    }


}
