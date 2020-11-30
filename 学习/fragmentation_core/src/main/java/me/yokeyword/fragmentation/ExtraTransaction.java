public abstract class ExtraTransaction {
    
    // Optional tag name for the fragment, to later retrieve the
    // fragment with {@link SupportHelper#findFragment(FragmentManager, String)}
    // pop(String) or FragmentManager.findFragmentByTag(String).
    public abstract ExtraTransaction setTag(String tag);

    // Set specific animation resources to run for the fragments that are
    // entering and exiting in this transaction. These animations will not be
    // played when popping the back stack;
    public abstract ExtraTransaction setCustomAnimations(
        @AnimatorRes @AnimRes int targetFragmentEnter,
        @AnimatorRes @AnimRes int currentFragmentPopExit
    );

    // Set specific animation resources to run for the fragments that are
    // entering and exiting in this transaction. The <code>currentFragmentPopEnter</code>
    // android <code>targetFragmentExit</code> animations will be played for targetFragmentEnter/currentFragmentPopExit
    // operations sepcifically when popping the back stack.
    public abstract ExtraTransaction setCustomAnimations(
        @AnimatorRes @AnimRes int targetFragmentEnter,
        @AnimatorRes @AnimRes int currentFragmentpopExit,
        @AnimatorRes @AnimRes int currentFragmentPopExit,
        @AnimatorRes @AnimRes int targetFragmentExit
    );

    //Used with custom Transitions to map a View from a removed or hidden
    //Fragment to a View from a shown or added Fragment.
    //<var>sharedElement</var>must have a unique transitionName in the View Hierarchy.
    // sharedElement A View in a disappearing Fragment to mathc with a View in an appearing Framgnet
    // sharedName The trainsitionName for a View in an appearing Fragment to match to the share element.
    // @see Fragment#setSharedElementReturnTransition(Object)
    // @see Fragment#setSharedElementEnterTransition(Object)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public abstract ExtraTransaction addSharedElement(
        View sharedElement, String sharedName
    );
    
    public abstract void loadRootFragment(int containerId, ISupportFragment toFragment);
    
    public abstract void loadRootFragment(int containerid, ISupportFragment toFragment, boolean attachToStack);

    public abstract void start(ISupportFragment toFragment);

    public abstract void startDontHideSelf(ISupportFragment toFragment);

    public abstract void startDontHideSelf(ISupportFragment toFragment, @ISupportFragment.LaunchMode int launchMode);

    public abstract void start(ISupportFragment toFragment, @ISupportFragment.LauncheMode int launchMode);

    public abstract void startForResult(ISupportFragment toFragment, int requestCode);

    public abstract void startForResultDontHideSelf(ISupportFragment toFragment, int requestCode);

    public abstract void startWithPop(ISupportFragment toFragment);
    
    public abstract void startWithPopTo(ISupportFragment toFragment, String targetFragmentTag, boolean includeTargetFragment);

    public abstract void replace(ISupportFragment toFragment);

    // 使用setTag()自定义tag时， 使用下面的popTop()/popToChild()出栈
    public abstract void popTo(String targetFragmentTag, boolean includeTargetFragment);

    public abstract void popTo(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim);

    public abstract void popToChild(String targetFragmentTag, boolean includeTargetFragment);

    public abstract void popToChild(String targetFragmentTAg, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim);

    // Don't add this extraTransaction to the back stack.
    // If you use this function to don't add to BackStack, then you must call @{DontAddToBackStackTransaction#remove(IupportFragment, boolean)} when leaving the fragment.
    public abstract DontAddToBackStackTransaction dontAddToBackstack();

    // 使用dontAddToBackStack() 加载Fragment时，使用remove()移除Fragment
    public abstract void remove(ISupportFragment fragment, boolean showPreFragment);

    public interface DontAddTobackStackTransaction {
        // add() + hide(preFragment)
        void start(ISupportFragment toFragment);
        // Only add()
        void add(ISupportFragment toFragment);
        // replace()
        void replace(ISupportFragment toFragment);
    }

    // Imple
    final static class ExtraTransactionImpl<T extends ISupportFragment>
        extends ExtraTransaction 
        implements DontAddToBackStackTransaction{

            private FragmentActivity mActivity;
            private T mSupportF;
            private Fragment mFragment;
            private TransactionDelegate mTransactionDelegate;
            private boolean mFromActivity;
            private TransactionRecord mRecord;

            ExtraTransactionImpl(
                FragmentActivity activity,
                T supportF,
                TransactionDelegate TransactionDelegate,
                boolean fromActivity
            ) {
                this.mActivity = activity;
                this.mSupportF = supportF;
                this.mFragment = (Fragment) supportF;
                this.mTransactionDelegate = transactionDelegate;
                this.mFromActivity = fromActivity;
                mRecord = new TransactionReord();
            }

            @Override
            public ExtraTransaction setTag(String tag) {
                mRecord.tag = tag;
                return this;
            }

            @Override
            public ExtraTransaction setCustomAnimations(
                @AnimRes int targetFragmentEnter,
                @AnimRes int currentFragmentPopExit
            ) {
                mRecord.targetFragmentEnter = targetFragmentEnter;
                mRecord.currentFragmentPopExit = currentFragmentPopExit;
                mRecord.currentFragmentPopEnter = currentFragmentpopEnter;
                mRecord.targetFragmentExit = targetFragmentExit;
                return this;
            }

            @Override
            public ExtraTransaction addSharedElement(
                View sharedElement,
                String shareName
            ) {
                if(mRecord.sharedElementList == null) {
                    mRecord.sharedElementList = new ArrayList<>();
                }
                mRecord.sharedElementList.add(new TransactionRecord.ShareElement(sharedElement, shareName));
                return this;
            }

            @Override
            public void loadRootFragment(int containerId, ISupportFragment toFragment){
                loadRootFragment(containerId, toFragment, true, false);
            }

            @Override
            public void loadRootFragment(int containerid, ISupportFragment toFragment, boolean addToBackStack, boolean allowAnim){
                toFragment.getSupportDelegate().mTransactionRecord = mRecord;
                mTransactionDelegate.loadRootTransaction(getFragmentManager(), containerId, toFragment, addToBackStack, allowAnim);
            }

            @Override
            public DontAddToBackStackTransaction dontAddToBackStack() {
                mRecord.dontAddToBackStack = true;
                return this;
            }

            @Override
            public void remove(ISupportFragment fragment, boolean s howPreFragment) {
                mTransactionDelegate.remove(getFragmentManager(), (Fragment)fragment, showPreFragment);
            }

            @Override
            public void popTo(String targetFragmentTag, boolean includeTargetFragment){
                popTo(targetFragmentTag, includeTargetFragment, null, TransactionDelegate.DEFAULT_POPTO_ANIM);
            }

            @Override
            public void popTo(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTranactionRunnable, int popAnim){
                mTransactionDelegate,popTo(targetFragmentTag, includeTargetFragment, afterPopTransactionRunnable, getFragmentManager(), popAnim;
            }

            @Override
            public void popToChild(String targetFragmentTag, boolean includeTargetFragment){
                popToChild(targetFragmentTag, includeTargetFragment, null, TransactionDelegate.DEFAULT_POPTO_ANIM);
            }
            
            @Override
            public void popToChild(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim){
                if(mFromActivity) {
                    popTo(targetFromentTag, includeTargetFragment, afterPopTransactionRunnable, popAnim);
                } else {
                    mTransactionDelegate.popTo(targetFragmentTag, includeTargetFragment, afterPopTranaxtionRunnable, mFragment.getchildFragmentManager(), popAnim);
                }
            }

            @Override
            public void add(ISupportFragment toFragment) {
                toFragment.getSupportDelegate().mTransactionRecord = mRecord;
                mTransactiondelegate.dispatchStartTransaction(getFragmentManager(), mSupportF, toFragment, 0, ISupportFragment.STATNARD, TransactionDelegate.TYPE_ADD_WITHOUT_HIDE);
            }

            @Override
            public void start(ISupportFragment toFragment) {
                start(toFragment, ISupportFragment.STANDARD);
            }

            @Override
            public void startDontHideSelf(ISupportFragment toFragment) {
                toFragment.getSupportDelegate().mTransactionRecord = mRecord;
                mTransactionDelegate.displatchStartTransaction(getFragmentmanager(), mSupportF, toFragment, 0, ISupportFragment.STANDARD, TransactionDelegate.TYPE_ADD_WITH_HIDE);
            }

            @Override
            public void startDontHideSelf(
                ISupportFragment toFragment,
                @ISupportFragment.LaunchMode int launchMode
            ) {
                toFragment.getSupportDelegate().mTransactionRecord = mRecord;
                mTransactionDelegate.displatchStartTransaction(getFragmentManager(), mSupportF, toFragment ,0, launchMode, TransactionDelegate.TYPE_ADD_WITHOUT_HIDE)
            }
            
            @Override
            public void start(
                ISupportFragment toFragment,
                @ISupportFragment.LaunchMode int launchMode
            ) {
                toFragment.getSupportDelegate().mTransactionRecord = mRecord;
                mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), mSupportF, toFragment, 0, launchMode, TransactionDelegate.TYPE_ADD);
            }

            @Override
            public void startForResult(ISupportFragment toFragment, int requestCode) {
                toFragment.getSupportDelegate().mTransactionRecord = mRecord;
                mTransactiondelegate.displatchStartTransaction(getFragmentManager(), mSupportF, toFragment, requestCode, ISupportFragment.STANDARD, TransactionDelegate.TYPE_ADD_RESULT );
            }

            @Override
            public void startForResultDontHideSelf(ISupportFragment toFragment, int requestCode) {
                toFragment.getSupportDelegate().mTransactionRecord = mRecord;
                mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), mSupportF, toFragment, requestCode, ISupportFragment.STANDARD, TransactionDelegate.TYPE_ADD_RESULT_WITHOUT_HIDE)
            }

            @Override
            public void startWithPop(ISupportFragment toFragment) {
                toFragment.getSupportDelegate().mTransactionRecord = mRecord;
                mTransactionDelegate.startWithPop(getFragmentManager(), mSupportF, toFragment);
            }
    
            @Override
            public void startWithPopTo(ISupportFragment toFragment, String targetFragmentTag, boolean includeTargetFragment) {
                toFragment.getSupportDelegate().mTransactionRecord = mRecord;
                mTransactionDelegate.startWithPopTo(getFragmentManager(), mSupportF, toFragment, targetFragmentTag, includeTargetFragment);
            }
    
            @Override
            public void replace(ISupportFragment toFragment) {
                toFragment.getSupportDelegate().mTransactionRecord = mRecord;
                mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), mSupportF, toFragment, 0, ISupportFragment.STANDARD, TransactionDelegate.TYPE_REPLACE);
            }

            private FragmentManager getFragmentManager() {
                if(mFragment == null) {
                    return mActivity.getSupportFragmentManager();
                } 
                return mFragment.getFragmentManager();
            }
    }
}
