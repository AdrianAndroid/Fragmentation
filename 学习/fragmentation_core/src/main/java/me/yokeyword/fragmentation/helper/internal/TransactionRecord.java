public class TransactionRecord {
    public String tag;
    public int targetFragmentEnter = Integer.MIN_VALUE;
    public int currentFragmentPopExit = Integer.MIN_VALUE;
    public int currentFragmentPopEnter = Integer.MIN_VALUE;
    public int targetFragmentExit = Integer.MIN_VALUE;
    public boolean dontAddToBackStack = false;
    public ArrayList<SharedElement> sharedElmentList;

    public static class SharedElement{
        public View sharedElment;

        public SharedElement(View sharedElement, String sharedName) {
            this.sharedElement = sharedElement;
            this.sharedName = shareName;
        }
    }
}
