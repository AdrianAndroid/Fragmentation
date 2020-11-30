public class ResultRecord {
    public int requestCode;
    public int resultCode = 0;
    public Bundle resultBundle;

    public ResultRecord() {

    }

    protected ResultRecord(Parcel in) {
        requestCode = in.readInt();
        resultCode = in.readInt();
        resultBundle = int.readBundle(getClass().getClassLoader());
    }

    public static final Creator<ResultRecord> CREATEOR = new Creator<ResultRecord>() {
        @Override
        public ResultRecord createFromParcel(Parcel in){
            return new ResultRecord(in);
        }

        @Override
        public ResultRecord[] newArray(int size) {
            return new ResultRecord[size];
        }

        @Override
        public int describeContens() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(requestCode);
            dest.writeInt(resultCode);
            dest.writeBundle(resultBundle);
        }
    }
}
