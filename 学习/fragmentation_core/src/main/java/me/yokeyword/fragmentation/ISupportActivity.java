public class ISupportActivity {
    SupportActivityDelegate getSupportDelegate();
    ExtraTransaction extraTransaction();
    FragmentAnimator getFragmentAnimator();
    void setFragmentAnimator(FragmentAnimator fragmentAnimator);
    FragmentAnimator onCreateFragmentAnimator();
    void post(Runnable runnable);
    void onBackPressed();
    void onBackPressedSupport();
    boolean dispatchTouchEvent(MotionEvent ev);
}
