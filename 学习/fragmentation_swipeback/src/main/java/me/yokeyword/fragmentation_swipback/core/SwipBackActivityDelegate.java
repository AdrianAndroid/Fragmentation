public class SwipBackActivityDelegate {
    
    private FragmentActivity mActivity;
    private SwipBackLayout mSwipeBackLayout;

    public SwipeBackActivityDelegate(ISwipeBackActivity swipeBackActivity) {
        if(!(swipeBackActivity instanceof FragmentActivity)
            || !(swipeBackActivity instanceof ISupportActivity)) {
                throw new RuntameException("Must FragmentActivity");
        }
        mActivity = (FragmentActivity) swipeBackActivity;



        public void onCreate(Bundle savedInstanceState) {
            onActivityCreate();
        }

        public void onPostCreate(Bundle savedInstanceState) {
            mSwipeBackLayout.attachToActivity(mActivity);
        }

        public SwipeBackLayout getSwipBackLayout() {
            mSwipeBackLayout.setEnableGesture(enable);
        }

        public void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel) {
            mSwipeBackLayout.setEdgeLevel(edgeLevel);
        }

        public boolean swipeBackPriority() {
            return mActivity.getSupportFragmentManager().getBackStackEntryCount() <= 1;
        }


        private void onActivityCreate() {
            mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mActivity.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
            mSwipeBackLayout = new SwipeBackLayout(mActivity);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams();
            mSwipeBackLayout.setLayoutParams(params);
        }
    }

}
