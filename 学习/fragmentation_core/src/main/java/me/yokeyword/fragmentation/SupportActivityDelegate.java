public class SupportActivityDelegate {
    private ISupportActivity mSupport;
    private FragmentActivity mActivity;
    
    boolean mPopMultipleNoAnim = false;
    boolean mFragmentClickable = true;

    private TransactionDelegate mTransactionDelegate;
    private FragmentAnimator mFragmentAnimator;
    private int mDefaultFragmentBackground = 0;
    private DebugStackDelegate mDebugStackDelegate;

    public SupportActivityDelegate(ISupportActivity support) {
        if(!(support instanceof FragmentActivity)){
            throw new RuntimeException("Must extends FragmentActivity/AppCompatActivity");
        }
        this.mSupport = support;
        this.mActivity = (FragmentActivity) support;
        this.mDebugStackDelegate = new DebugStackDelegate(this.mActivity);
    }

    // Perform some extra transactions.
    // 额外的事务：自定义Tag， 添加SharedElement动画，操作非回退栈Fragment
    public ExtraTransaction extraTransaction() {
        return new ExtraTransaction.ExtraTransactionImpl<>((FragmentActivity)mSupport, getTopFragment(), getTransactionDelegate(), true);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        mTransactionDelegate = getTransactionDelegate();
        mFragmentAnimator = mSupport.onCreateFragmentAnimator();
        mDebugStackDelegate.onCreate(Fragmentation.getDefault().getMode());
    }

    public TransactionDelegate getTransactionDelegate() {
        if(mTransactionDelegate == null){
            mTransactionDelegate = new TransactionDelegate(mSupport);
        }
        return mTransactionDelegate;
    }

    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        mDebugStackDelegate.onPostCreate(Fragmentation.getDefault().getMode());
    }

    // 获取全局动画copy
    public FragmentAnimatr getFragmentAnimator() {
        return mFragmentAnimator.copy();
    }

    // Set all fragments animation
    // 设置Fragment内的全剧动画
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        this.mFragmentAnimator = fragmentAnimator;
        for(Fragment fragment:getSupportFragmentManager().getFragments()) {
            if(fragment instance ISupportFragment) {
                ISupportFragment iF = (ISupportFragment) fragment;
                SupportFragmentDeleate delegate = iF.getSupportDelegate();
                if(delegate.mAnimByActivity) {
                    delegate.mFragmentAnimator = fragmentAnimator.copy();
                    if(delegate.mAnimHelper != null) {
                        delegate.mAnimHelper.notifyChanged(delegate.mFragmentAnimator);
                    }
                }
            }
        }
    }

    // Set all fragments animation.
    // 构建Fragment转场动画
    // 如果是在Activity内实现，则构建的是Activity内所有Fragment的转场动画
    // 如果是在Fragment内实现， 则构建的是该Fragment的转场动画，此时优先级》Activity的onCreateFragmentAnimator()
    // 如果 FragmentAnimator对象
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultVerticalAnimator();
    }

    // 当Fragment根布局 没有 设定background属性时
    // Fragmentation默认使用Theme的android:windownbackground作为Fragmeng的背景
    // 可以通过该方法改变Fragment背景。
    public void setDefaultFragmentBackground(@DrawableRes int backgroundRes) {
        mDefaultFragmentBackground = backgroundRes;
    }

    public int getDefaultFragmentBackround() {
        return mDefaultFragmentBackground;
    }

    // 显示栈视图dialog，调试时使用
    public void showFragmentStackHierarchyView() {
        mDebugStackDelegate.showFragmentStackHierarchyView();
    }

    // 显示栈视图日子，调试时使用
    public void logFragmentStackHierarchy(String TAG) {
        mDebugStackDelegate.logFragmentRecrods(TAG);
    }
    
    // Causes the Runnable r to be added to the action queue
    // The runnable will be run afterall the previous action has been fun.
    // 前面的事务全部执行后 执行该Action
    public void post(final Runnable runnable) {
        mTransactionDelegate.post(runnable);
    }

    // 不建议复写该方法，请使用({@link #onBackPressedSupport})代替
    public void onBackPressed() {
        mTransactionDelegate.mActionQueue.enqueue(new Action(Action.ACTION_BACK, getSupportFragmentManager())) {
            @Override
            public void run() {
                if(!mFragmentClickable) {
                    mFragmentClickable = true;
                }
                // 获取activeFragment:即从栈顶开始 状态为show的那个Fragment
                ISupportFragment activeFragment = SupportHelper.getAddedFragment(getSupportFragmentManager());
                if(mTransactiondelegate.dispatchBackPressedEvent(activeFragment)) return;
                
                mSupport.onBackPressedSupport();
            }
        }
    }

    // 该方法回调时机为，Activity回退栈内Fragment的数量 小于等于1时，默认finish Activity
    // 请尽量复写该方法，避免复写onBackPress(), 以保证SupportFragment   内的onBackPressedSupport()回退事件正常执行
    public void onBackPressedSupport() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(mActivity);
        }
    }

    public void onDestroy() {
        mDebugStackDelegate.onDestroy();
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 防抖动（防止点击速度过快）
        return !mFragmentClickable;
    }

    /*********************** */
    public void loadRootFragment(int containerId, ISupportFragment toFragment) {
        loadRootFragment(containerId, toFragment, true, false);
    }

    public boic loadRootFragment(int containerId, ISupportFragment toFragment, boolean addToBackStack, boolean allowAnimation){
        mTransactionDelegate.loadRootFragment(getSupportFragmentManager(), containerId, toFragment, addToBackStack, allowAnimation)
    }

    // 加载多个同级根Fragment，类似wechat， qq主页的场景
    public void loadMultipleRootFragment(int containerId, int showPosition, ISupportFragment... toFragments){
        mTransactionDelegate.loadMultipleRootFragment(getSupportFragmentManager(), containerId, showPosition, toFragments);
    }

    // show一个Fragment，hide其他同栈所有Fragment
    // 使用该方法时，要确保同级栈内无多余的Fragment，（只有通过loadMultipleRootFragment载入Fragment）
    // 建议使用更明确的@link #showHideFragment(ISupportFragment, ISupportFragment)
    public void showHideFragment(ISupportFragment showFragment){
        showHideFragment(showFragment, null);
    }

    // show一个Fragment，hide一个Fragment；主要用于类似微信主页那种，切换tab的情况
    public void showhideFragment(ISupportFragment showFragment, ISupportFragment hideFragment){
        mTransactionDelegate.showHideFragment(getSupportFragmentManager(), showFragment, hideFragment);
    }

    public void start(ISupportFragment toFragment) {
        start(toFragment, ISupportFragment.STANDARD);
    }

    // launchMode Similar to Activity's LaunchMode
    public void star(ISupportFragment toFragment, @ISupportFragment.LaunchMode int launchMode){
        mTransactionDelegate.dispatchStartTransaction(getSupportFragmentManager(), getTopFragment(), toFragment, 0, launchMode, TransactionDelegate.TYPE_ADD);
    }

    // Launch an fragmen for which you would like a result when it poped.
    public void startForResult(ISupportFragment toFragment, int requestCode){
        mTransactionDelegate.dispatchStartTransaction(getSupportFragmentManager(), getTopFragment(), toFragment, requestCode, ISupportFragment.STANDARD, TransactionDelegate.TYPE_ADD_RESULT);
    }

    // Start the target Fragment and pop iteself
    public void startWithPop(ISupportFragment toFragment){
        mTransactionDelegate.startWithPop(getSupportFragmentManager(), getTopFragment(), toFragment;
    }

    public void startWithPopTo(ISupportFragment toFragment, Class<?> targetFragmentClass, boolean includeTargetFragment){
        mTransactionDelegate.dispatchStartTransaction(getSupportFragmentManager(), getTopFragment(), toFragment, targetFragmentClass.getName(), includeTargetFragment);
    }

    public void replaceFragment(ISupportFragment toFragment, boolean attachToBackStack){
        mTransactionDelegate.dispatchStartTransaction(getSupportFragmentManager(), getTopFragment(), toFragment, 0, ISupportFragment.STANDARD, addToBackStack ? TransactionDelegate.TYPE_REPLACE : TransactionDelegate.TYPE_REPLACE_DONT_BACK);
    }

    // Pop the child fragment
    public void pop(){
        mTransactionDelegate.pop(getSupportFragmentManager());
    }

    // Pop the last fragment transition from the manager's fragment
    // back stack
    // 出栈到目标fragment
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment){
        popTo(targetFragmentClass, includeTargetFragment, null);
    }

    // If you want to begin another FragmentTransaction immediately after popTo().
    // 如果你想在出栈后，立刻进行FragmengTransaction操作，请使用该方法
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, TransactionDelegate.DEFAULT_POPTO_ANIM)
    }

    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
        mTransactionDelegate.popTo(targetFragmentClass.getName(), afterPopTransactionRunnable, getSupportFragmentManager(), popAnim);
    }

    private FragmentManager getSupportFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    private ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getSupportFragmentManager());
    }
}
