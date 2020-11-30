public class ISwipeBackFragment {
    View attachToSwipBack(View view);
    SwipeBackLayout getSwipBackLayout();
    void setSwipBackEnable(boolean enable);
    void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel);
    void setEdgeLevel(int widthPixel);
    void  setParallaxOffset(@FloatRange(from=0.0f, to=1.0f) float offset);
}
