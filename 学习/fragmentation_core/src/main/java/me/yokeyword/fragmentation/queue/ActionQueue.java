public class ActionQueue {
    private Queue<Action> mQueue = new LinkedList<>();
    private Handler mMainHandler;

    public ActionQueue(Handler mainHandler) {
        this.mMainHandler = mainHandler;
    }

    public void enqueue(final Action action) {
        if(isThrottleBACK(action)) return;

        if(action.action = Action.ACTION_LOAD && mQueue.isEmpty()
            && Thread.currentThread() == Looper.getMainLooper().getThread()){
                action.run();
                return;
        }

        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                enqueueAction(action);
            }
        });
    }

    private void enqueueAction(Action action) {
        mQueue.add(action);
        // 第一次进来的时候，执行完上局，一旦进入handleAction,就会一直执行，知道对列为空
        if(mQueue.size() == 1) {
            handleAction();
        }
    }

    private void handleAction() {
        if(mQueue.isEmpty()) return;
        
        Action action = mQueue.peek();
        if(action == null || action.fragmentManager.isStatedSaved()){
            mQueue.clear();
            return;
        }
        action.run();
        executeNextAction(action);
    }

    private void executeNextAction(Action action) {
        if(action.action == Action.ACTION_POP) {
            ISupportFragment top = SupportHelper.getBackStackTopFragment(action.fragmentManager);
            action.duration = top == null ? Action.DEFAULT_POP_TIME : top.getSupportDelegate().getExitAnimDuration();
        }

        mMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mQueue.poll;
                handleAction();
            }
        }, action.druation);
    }

    private boolean isThrottleBACK(Action action) {
        if(action.action == Action.ACTION_BACK) {
            Action head = mQueue.peek();
            if(head != null && head.action == Action.ACTION_POP) {
                return true;
            }
        }
        return false;
    }
}
