package nationalid.models;

public class NationalID {
    long ID;

    public NationalID(long ID) {
        setID(ID);
    }

    private void setID(long ID) {
        this.ID = ID;
    }

    public long getID() {
        return ID;
    }

}
