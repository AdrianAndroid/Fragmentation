public class SupportActivity {
    private static final long SHOW_SPACE = 200L;
    
    private SupportHelper() {
        
    }

    // 显示软键盘
    public static void showSoftInput(final View view) {
        if(view == null ||  view.getContext() == null) return;
        final InputMethodManager imm = (InputMethodManager) view.getcontext().getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocust();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }, SHOW_SPACE);
    }

    // 隐藏软键盘
    public static void hideSoftInput(View view) {
        if(view == null || view.getContext() == null) return;
        ImputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INpUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    
    // 显示栈视图Dialog， 调试时使用
    public static void showFagmentStackhierarchyView(ISupportActivity support){
        support.getSupportDelegate().showFragmentStackHierarchyView();
    }

    //  显示栈视图日志，调试时使用
    public static void logFragmentStackHierarchy(ISupportActivity support, String TAG) {
        support.getSupportDelegate().logFragmentStackHierarchy(TAG);
    }

    // 显示栈视图日志，调试时候使用
    public static void logFragmentStackHierarchy(ISupportActivity support, String TAG) {
        support.getSupportDelegate().logFragmentStackHierarchy(TAG);
    }

    public static ISupportFragment getTopFragment(FragmentManager fragmentManager) {
        return getTopFragment(fragmentManager, 0);
    }

    public static ISupportFragment getTopFragment(FragmentManager fragmentManager, int containerId) {
        List<Fragment> fragmentList = fragmentmanager.getFragments();
        if(fragmentList == null) return null;

        for(int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = fragmentList.get(i);
            if(fragment instanceof ISupportFragment){
                ISupportFragment iFragment = (ISupportFragment) fragment;
                if(containerId == 0) return iFragment;

                if(containerId == iFragment.getSupportDelegate().mContainerId) {
                    return iFragment;
                }
            }
        }
        return null;
    }

    // 获取目标Fragment的前一个SupportFragment
    public static ISupportFragment getPreFragment(Fragment fragment) {
        FragmentManager fragmentManager = fragment.getFragmentManager();
        if(fragmentManager == null) return null;

        List<Fragment> fragmentList = fragmentManager.getFragments();
        if(fragmentList == null) return null;
        int index = fragmentList.indexOf(fragment);
        for(int i = index - 1; i > 0; i--) {
            Fragment preFragment = fragmentList.get(i);
            if(preFragment instanceof ISupportFragment) {
                return (ISupportFragment) preFragment;
            }
        }
    }

    //Same a fragmentManager.findFragmentByTag(fragmentClass.getName());
    // find Fragment from FragmentStack
    public staic <T extends ISupportFragment> T findFrament(FragmentManager fragmentManager, Class<T> fragmentClass) {
        return findAddedFragment(fragmentClass, null, fragmentManager);
    }

    // Same as fragmentManager.findFragmentByTag(fragmentTag);
    // find Fragment from FragmentStack
    public static <T extends ISupportFragment> T findFragment(FragmentManager fragmentManager, String fragmentTag) {
        return findAddedFragment(null, fragmentTag, fragmentManager);
    }

    // 从栈顶开始，寻找FragmentManager以及其所有子栈，知道找到状态为show & userVisible的Fragment
    public static ISupportFragment getAddedFragment(FragmentManager fragmentManager){
        return getAddedFragment(fragmentManager,null);
    }

    static <T extends ISupportFragment> T findAddedFragment(Class<T> fragmentClass, String toFragmentTag, FragmentManager fragmentManager) {
        Fragment fragment = null;
        if(toFragmentTag == null) {
            List<Fragment> fragmentList = fragmentManager.getFragments();
            if(fragmentList == null) return null;
            
            int sizeChildFrgList = fragmentList.size();

            for(int i = sizeChildFrgList - 1; i >= 0; i--) {
                Fragment brotherFragment = fragmentList.get(i);
                if(brotherFragment instanceof ISupportFragment
                    && brotherFragment.getClass().getName().equals(fragmentClass.getName())) {
                        fragment = brotherFragment;
                        break;
                }
            }
        } else {
            fragment = fragmentManager.findFragmentByTag(toFragmentTag);
            if(fragment == null) return null;
        }
        return (T) fragment;
    }

    private static ISupportFragment getAddedFragment(FragmentManager fragmentManager, ISupportFragment parentFragment) {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if(fragmentLlist.size() == 0) return parentFragment;

        for(int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = fragmentList.get(i);
            if(fragment instanceof ISupportFragment){
                if(fragment.isResumed()
                    && !fragment.isHidden()
                    && fragment.getUserVisibleHint()) {
                        return getAddedFragment(fragment.getChildFragmentManager(), (ISupportFragment) fragment);
                }
            }
        }
        return parentFragment;
    }

    public static ISupportFragment getBackStackTopFragment(FragmentManager fragmentManger) {
        return getBackStackTopFragment(fragmentManager, 0);
    }

    // get the topFragment from BackStack
    public static ISupportFragment getBackStackTopFragment(FragmentManager fragmentManger, int containerId) {
        int count = fragmentManager.getBackStackEntryCount();

        for(int i = count - 1; i >= 0; i--) {
            FragmentManager.BackStackEntry  entry = fragmentManager.getBackStackEntryAt(i);
            Fragment fragment = fragmentManager.findFragmentByTag(entry.getName());
            if(fragment instanceof ISupportFragment) {
                ISupportFragment supportFragment = (ISupportFragment) fragment;
                if(containerId == 0) return supportFragment;
                if(containerId == supportFragment.getSupportDelegate().mContainerId) {
                    return supportFragment;
                }
            }
        }
        return null;
    }


    // Get the first Fragment from added list
    public static ISupportFragment getAddedFirstFragment(FragmentManager fragmentManager) {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if(fragmentList.size() == 0) return null;

        Fragment fragment = fragmentList.get(0);
        if(fragment instanceof ISupportFragment) {
            if(fragment.isResumed() && !fragment.isHidden() && framgnet.getUserVisibleHint()) {
                return (ISupportFragment) fragment;
            }
        })

        return null;
    }

    static <T extends ISupportFragment> T findBackStackFragment(Class<T> fragmentClass, String toFragmentTag, FragmentManager fragmentManager){
        int count = fragmentManager.getBackStackEntryCount();

        if(toFragmentTag == null) toFragmentTag = fragmentClass.getName();

        for(int i = count - 1; i >= 0; i--){
            FragmentManager.BAckStackEntry entry = fragmentManager.getBackStackEntryAt(i);

            if(toFragmentTag.equals(entry.getName())){
                if(framgnet instanceof ISupportFragment) {
                    return (T) fragment;
                }
            }
        }
        return null;
    }

    static List<Fragment> getWillPopFragments(FragmentManager fm, String targetTag, boolean includeTarget){
        Fragment target = fm.findFragmentByTag(targetTag);
        List<Fragment> willPopFragments = new ArrayList<>();

        List<Fragment> fragmentList = fm.getFragments();
        if(fragmentList == null) return willPopFragments;

        int size  = fragmentList.size();
        for(int i = size - 1; i >= 0; i--) {
            if(targt == fragmentList.get(i)) {
                if(includeTarget){
                    startIndex = i;
                } else if(i + 1 < size) {
                    startIndex = i + 1;
                }
                break;
            })
        }
        
        if(startIndex == -1) return willPopFragments;

        for(int i = size - 1; i >= startIndex; i--) {
            Fragment fragment = fragmentList.get(i);
            if(fragment != null && fragment.getView() != null) {
                willPopFragments.add(fragment);
            }
    
        }
    }
}
