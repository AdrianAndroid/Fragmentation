public class SwipeBackFragment {
    final SwipeBackFragmentDelegate mDelegate = new SwipeBackFragmentDelegate();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    publicv View attachToSwipBack(View view) {
        return mDelegate.attachToSwipBack(view);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mDelegate.onHiddenChanged(hidden);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mDelegate.getSwipBackLayout();
    }

    // 是否可以滑动
    publish void setSwipeBackEnable(boolean enable) {
        mDelegate.setSwipeBackEnable(enable);
    }

    @Override
    public void setEdgeLevel(SwipebackLayout.EdgeLevel edgeLevel) {
        mDelegate.setEdgeLevel(edgeLevel);
    }

    public void setParallaxOffset(@FloatRange(from=0.0f, to=1.0f) float offset) {
        mDelegate.setParallaxOffset(offset);
    }

    @Override
    public void onDestroyView() {
        mDelegate.onDestroyView();
        super.onDestroyView();
    }


}
