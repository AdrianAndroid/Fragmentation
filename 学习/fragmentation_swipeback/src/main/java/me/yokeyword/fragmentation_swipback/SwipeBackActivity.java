public class SwipeBackActivity {
    final SwipeBackActivityDelegate mDelegate = new SwipeBackActivityDelegate(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mDelegate.getSwipeBackLayout();
    }

    // 是否可以滑动
    @Override
    public void setSwipeBackEnable(boolean enable) {
        return mDelegate.setSwipeBackEnable(enable);
    }

    @Override
    public void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel){
        mDelegate.setEdgeLevel(edgeLevel);
    }

    @Override
    public void setEdgeLevel(int widthPixel) {
        mDelegate.setEdgeLevel(widthPixel);
    }

    // 限制SwipeBack的条件， 默认栈内Fragment数《=1时，
    @Override
    public void swipBackPriority() {
        return  mDelegate.swipeBackPriority();
    }
}
