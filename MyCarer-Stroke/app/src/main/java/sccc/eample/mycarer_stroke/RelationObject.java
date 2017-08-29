package sccc.eample.mycarer_stroke;

/**
 * Created by User on 10-02-2017.
 */

public class RelationObject {
    private int id;
    private String name;
    private String relationship;
    private byte[] image;

    public RelationObject(String name, byte[] image, /*String notes,*/ int id) {
        this.name = name;
        /*this.relationship = relationship;*/
        this.image = image;
        //this.notes = notes;
        this.id = id;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public byte[] getImage(){
        return image;
    }
    public void setImage(byte[] image){
        this.image = image;
    }
}
