public class ISwipeBackActivity {
    
    SwipeBackLayout getSwipeBackLayout();
    void setSwipeBackEnable(boolean enable);
    void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel);
    void setEdgeLevel(int widthPixel);
    // 限制SwipeBack的条件，默认栈内Fragment数《=1时，有限滑动退出Activit而不是Fragment
    // true：Activity可以滑动退出，并且总是优先 false：Fragment优先滑动退出
    boolean swipeBackPriority();
}
