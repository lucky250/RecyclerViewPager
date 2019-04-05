package example.com.recyclerviewpager.Views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class ScrollPageRecyclerView extends RecyclerView {
    final private static int TouchSlop = 16;

    float xLast = 0f, yLast = 0f;

    private int direction;
    private boolean disableScroll = false;
    private int pageIndex = 0;

    private float xDown = 0f;
    private float yDown = 0f;

    private int[] consumed = new int[2];
    private int[] offsetInWindow = new int[2];

    private View.OnClickListener onClickListener;

    ArrayList<ViewPager.OnPageChangeListener> pageChangeListeners = new ArrayList<>();

    public ScrollPageRecyclerView(Context context) {
        this(context, null);
    }

    public ScrollPageRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollPageRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setNestedScrollingEnabled(true);
    }

    public void setDisableScroll(boolean disableScroll) {
        this.disableScroll = disableScroll;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        try {
            float curX = e.getX();
            float curY = e.getY();

            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDown = curX;
                    yDown = curY;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int dx = (int) (curX - xLast);
                    int dy = (int) (curY - yLast);
                    if (direction == HORIZONTAL) {
                        if (Math.abs(dy) > Math.abs(dx)) {
                            dispatchNestedPreScroll(0, dy, consumed, offsetInWindow);
                        } else {
                            dispatchNestedPreScroll(0, 0, consumed, offsetInWindow);
                        }
                    } else if (direction == VERTICAL) {
                        if (Math.abs(dy) < Math.abs(dx)) {
                            dispatchNestedPreScroll(dx, 0, consumed, offsetInWindow);
                        } else {
                            dispatchNestedPreScroll(0, 0, consumed, offsetInWindow);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    float xDiff = Math.abs(curX - xDown);
                    float yDiff = Math.abs(curY - yDown);
                    if (xDiff < TouchSlop && yDiff < TouchSlop && onClickListener != null) {
                        onClickListener.onClick(this);
                    }
                    stopNestedScroll();
                    break;
            }
            xLast = curX;
            yLast = curY;
            return super.onTouchEvent(e);
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        float curX = e.getX();
        float curY = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (direction == HORIZONTAL) {
                    if (Math.abs(curX - xDown) < Math.abs(curY - yDown)) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else if (direction == VERTICAL) {
                    if (Math.abs(curX - xDown) > Math.abs(curY - yDown)) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        xLast = curX;
        yLast = curY;
        return super.dispatchTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        try {
            super.onInterceptTouchEvent(e);

            if (disableScroll) {
                return false;
            }

            RecyclerView.Adapter adapter = getAdapter();
//            if (adapter != null && adapter instanceof PCSSrollPageViewAdapter) {
//                if (((PCSSrollPageViewAdapter) adapter).getRealItemCount() == 0) {
//                    return false;
//                }
//            }

            boolean intercept = false;

            float curX = e.getX();
            float curY = e.getY();
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDown = curX;
                    yDown = curY;
                    intercept = false;
                    if (direction == HORIZONTAL) {
                        startNestedScroll(SCROLL_AXIS_VERTICAL);
                    } else if (direction == VERTICAL) {
                        startNestedScroll(SCROLL_AXIS_HORIZONTAL);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (direction == HORIZONTAL) {
                        if (Math.abs(curX - xDown) > Math.abs(curY - yDown)) {
                            intercept = true;
                        } else {
                            if (pageIndex != -1) {
                                intercept = false;
                            } else {
                                intercept = true;
                            }
                        }
                    } else if (direction == VERTICAL) {
                        if (Math.abs(curX - xDown) < Math.abs(curY - yDown)) {
                            intercept = true;
                        } else {
                            if (pageIndex != -1) {
                                intercept = false;
                            } else {
                                intercept = true;
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    intercept = false;
                    break;
                default:
                    break;
            }
            xLast = curX;
            yLast = curY;
            return intercept;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void setOnTouchClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void notifyListenersOnPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (pageChangeListeners != null && pageChangeListeners.size()>0) {
            for (ViewPager.OnPageChangeListener listener : pageChangeListeners) {
                listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }
    }

    public void notifyListenersOnPageSelected(int position) {
        if (pageChangeListeners !=null && pageChangeListeners.size() >0) {
            for (ViewPager.OnPageChangeListener listener : pageChangeListeners) {
                listener.onPageSelected(position);
            }
        }
    }

    public void notifyListenersOnPageStateChange(int state) {
        if (pageChangeListeners != null && pageChangeListeners.size() > 0) {
            for (ViewPager.OnPageChangeListener listener : pageChangeListeners) {
                listener.onPageScrollStateChanged(state);
            }
        }
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        if (pageChangeListeners == null) {
            pageChangeListeners = new ArrayList<>();
        }
        if (!pageChangeListeners.contains(onPageChangeListener)) {
            pageChangeListeners.add(onPageChangeListener);
        }
    }

    public void removeOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        if (pageChangeListeners == null) {
            pageChangeListeners = new ArrayList<>();
        }
        if (pageChangeListeners.contains(onPageChangeListener)) {
            pageChangeListeners.remove(onPageChangeListener);
        }
    }
}
