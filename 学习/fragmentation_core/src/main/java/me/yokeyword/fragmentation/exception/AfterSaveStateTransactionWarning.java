public class AfterSaveStateTransactionWarning {
    
    public AfterSaveStateTransactionWarning(String action) {
        super("Warning: Perform this " + action + " action after onSaveInstanceState!");
        Log.w("Fragmentation", getMessage());
    }

}
