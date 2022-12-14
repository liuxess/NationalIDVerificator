package nationalid.models;

import nationalid.SegmentedNationalID;

public class NationalID {

    private long ID;

    public NationalID(long ID) {
        setID(ID);
    }

    private void setID(long ID) {
        this.ID = ID;
    }

    public long getID() {
        return ID;
    }

    public SegmentedNationalID Segment() {
        return new SegmentedNationalID(this);
    }

}
