public class SupportActivity extends AppCompatActivity implements ISupportActivity{
    final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return mDelegate;
    }

    // Perform some extra transactions.
    // 额外的事物：自定义Tag， 添加SharedElement动画，操作非回退栈Fragment
    @Override
    public ExtraTransaction extraTransaction(){
        return mDelegate.extraTransaction();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(saveInstanceState);
    }

    @Override
    protected void onDestroy() {
        mDelegate.onDestroy();
        super.onDestroy();
    }

    // Note: return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    // 不建议复写该方法，请使用onBackPressedSupport代替
    @Override
    public void onBackPressedSupport() {
        mDelegate.onBackPressedSupport();
    }

    // 获取设置的全局动画copy
    @Override
    public FragmentAnimator getFragmentAnimator() {
        return mDelegate.getFragmentAnimator();
    }

    // Set all fragments animation.
    // 设置Fragment内的全局动画
    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    // Set all fragments animation.
    // 构建Fragment转场动画
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return mDelegate.onCreateFragmentAnimaor();
    }

    @Override
    public void post(Runnable runnable) {
        mDelegate.post(runnable);
    }

    // 以下为可选方法
    // 加载根Fragment， 即Activity内的第一个Fragment或Fragment内的第一个子Fragment
    public void loadRootFragment(int containerId, @NonNull ISupportFragment toFragment) {
        mDelegate.loadRootFragment(containerId, toFragment);
    }


    public void loadRootFragment(int containerId, ISupportFragment toFragment, boolean addToBackStack, boolean allowAnimation) {
        mDelegate.loadRootFragment(containerId, toFragment, addToBackStack, allowAnimation);
    }

    // 加载多个同级根Fragment，类似wechat，QQ主页场景
    public void loadMultipleRootFragment(int containerId, int showPosition, ISupportFragment.. toFragment){
        mDelegate.loadMultipleRootFragment(containerId, showPosition, toFragment);
    }

    // showy一个Fragment，hide其他同栈所有Fragment
    // 使用该方法时，要确保同级栈内无多余的Fragment，（）只有通过loadMultipleRootFragment（）载入的Fragment（）；
    // 建议使用更明确的 showHideFragment(ISupportFragment, ISupportFragment);
    public void showHideFragment(ISupportFragment showFragment){
        mDelegate.showHideFragment(showFragment);
    }

    // show一个Fragment，hide一个Fragment；主要用于类似微信主页那种 切换tab的情况
    public void showHideFragment(ISupportFragment showFragment, ISupportFragment hideFragment){
        mDelegate.showHideFragment(showFragment, hideFragment);
    }

    public void start(ISupportFragment toFragment) {
        mDelegate.start(toFragment);
    }

    public void start(ISupportFragment toFragment, @ISupportFragment.LaunchMode int launchMode) {
        mDelegate.start(toFragment, launchMode);
    }

    public void startForResult(ISupportFragment toFragment, int requestCode) {
        mDelegate.startForResult(toFragment, requestCode);
    }

    public void startWithPop(ISupportFragment toFragment) {
        mDelegate.startWithPop(toFragment);
    }

    public void startWithPopTo(ISupportFragment toFragment, Class<?> targetFragmentClass, boolean includeTargetFragment) {
        mDelegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment);
    }

    public void replaceFragment(ISupportFragment toFragment, boolean addToBackStack) {
        mDelegate.replaceFragment(toFragment, addToBackStack);
    }

    public void pop() {
        mDelegate.pop();
    }

    // Pop the last fragment transition from th manager's fragment
    // back stack.
    // 出栈到目标fragment
    public void popTo(Class<?> targetFragmentClass, boolean includeTargeFragment) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment);
    }

    // If you want to begin another FragmentTranaction immediately after popTo()
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        mDelegate.popTo(targetFragmentClass, includeTargeFragment, afterPopTransactionRunnable);
    }

    public void popTo(Class<?> targetFragmentClass, boolean includeTargeFragment, Runnable afterPopTransactionRunnable, int popAnim) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransaction, popAnim);
    }


    // 当Fragment根布局没有设置background属性时候
    // Fragmentation模式使用Theme的android:windowbackground作为Fragment的背景
    // 可以通过该方法改变其内所有Fragment的默认背景
    public void setDefaultFragmentBackground(@DrawableRes int backgroundRes) {
        mDelegate.setDefaultFragmentBackground(backgroundRes);
    }

    public ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getSupportFragmentManager);
    }

    public <T extends ISupportFragment> T findFragment(Class<?> fragmentClass) {
        return SupportHelper.findFragment(getSupportFragmentManager, fragmentClass);
    }
}


