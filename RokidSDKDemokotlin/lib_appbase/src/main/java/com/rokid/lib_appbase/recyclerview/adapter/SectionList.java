package com.rokid.lib_appbase.recyclerview.adapter;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

import com.rokid.mobile.lib.base.util.Logger;
import com.rokid.lib_appbase.recyclerview.item.BaseFoot;
import com.rokid.lib_appbase.recyclerview.item.BaseHead;
import com.rokid.lib_appbase.recyclerview.item.BaseItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description: RecyclerAdapter 分组数据
 * Author: Shper
 * Version: V0.1 2017/6/7
 */
class SectionList<I extends BaseItem> implements Cloneable {

    public static final int SECTION_KEY_DEFAULT = 0;

    private SparseArrayCompat<Section> mSectionList = new SparseArrayCompat<>();

    public int setItemViewList(@IntRange(from = 0) int key, @NonNull List<I> itemList) {
        Logger.d("Stat to add the itemList, itemList size: " + itemList.size());

        Section section = checkSection(key);

        int oldSize = section.getItemViewList().size();
        section.setItemViewList(itemList);

        shiftBelowStartPosition(key, itemList.size() - oldSize);
        return section.getStartPosition() + section.getHeadCount();
    }

    public int addItemView(@IntRange(from = 0) int key, @NonNull I item) {
        Logger.d("Stat to add the Item.");

        Section section = checkSection(key);
        section.getItemViewList().add(item);

        shiftBelowStartPosition(key, 1);
        return section.getStartPosition() + section.getHeadCount() + section.getItemViewList().indexOf(item);
    }

    public int insertItemView(@IntRange(from = 0) int key, @IntRange(from = 0) int index, @NonNull I item) {
        Section section = checkSection(key);
        if (index > section.getItemCount()) {
            Logger.w("The index is greater than the section count, so can't insert the item.");
            return -1;
        }

        section.getItemViewList().add(index, item);
        shiftBelowStartPosition(key, 1);

        return section.getStartPosition() + section.getHeadCount() + index;
    }

    public int updateItemView(@IntRange(from = 0) int key, @NonNull I item) {
        Section section = findSectionByKey(key);
        if (null == section || !section.getItemViewList().contains(item)) {
            Logger.w("Can't find the section or The section does not contain the item.");
            return -1;
        }

        int index = section.getItemViewList().indexOf(item);
        section.getItemViewList().set(index, item);

        return section.getStartPosition() + section.getHeadCount() + index;
    }

    public int removeItemView(@IntRange(from = 0) int position) {
        Section section = findSection(position);
        if (null == section) {
            Logger.w("Can't find the section.");
            return -1;
        }

        section.getItemViewList().remove(position - section.getStartPosition() - section.getHeadCount());
        shiftBelowStartPosition(section.getSectionKey(), -1);
        return position;
    }

    public int removeItemView(@IntRange(from = 0) int key, @IntRange(from = 0) int index) {
        Section section = findSectionByKey(key);
        if (null == section || index >= section.getItemCount()) {
            Logger.w("Can't find the section or The index is greater than the section count.");
            return -1;
        }
        section.getItemViewList().remove(index);

        shiftBelowStartPosition(key, -1);
        return section.getStartPosition() + section.getHeadCount() + index;
    }

    public int removeItemView(@IntRange(from = 0) int key, @NonNull I item) {
        Section section = findSectionByKey(key);
        if (null == section || !section.getItemViewList().contains(item)) {
            Logger.w("Can't find the section or The section does not contain the item.");
            return -1;
        }

        int indexOfItem = section.getItemViewList().indexOf(item);
        section.getItemViewList().remove(indexOfItem);

        shiftBelowStartPosition(key, -1);
        return section.getStartPosition() + section.getHeadCount() + indexOfItem;
    }

    public int addItemViewList(@IntRange(from = 0) int key, @NonNull List<I> itemList) {
        Logger.d("Start to add the itemList.");

        Section section = checkSection(key);

        int size = section.getItemViewList().size();
        section.getItemViewList().addAll(itemList);

        shiftBelowStartPosition(key, itemList.size());
        return section.getStartPosition() + section.getHeadCount() + size;
    }

    public int insertItemViewList(@IntRange(from = 0) int key, @IntRange(from = 0) int index, @NonNull List<I> itemList) {
        Section section = checkSection(key);
        if (index > section.getItemViewList().size()) {
            Logger.w("The index is greater than the section count, so can't insert the itemList.");
            return -1;
        }

        section.getItemViewList().addAll(index, itemList);

        shiftBelowStartPosition(key, itemList.size());
        return section.getStartPosition() + section.getHeadCount() + index;
    }

    public int clearAllItemView(@IntRange(from = 0) int key) {
        Section section = findSectionByKey(key);
        if (null == section) {
            Logger.w("Can't find the section by key: " + key);
            return -1;
        }

        int size = section.getItemViewList().size();
        section.getItemViewList().clear();

        shiftBelowStartPosition(key, -size);

        return section.getStartPosition() + section.getHeadCount();
    }

    public void clearAllSectionItemViews() {
        Logger.d("Start to clear All Section item views.");

        for (int index = 0; index < mSectionList.size(); index++) {
            int oldSize = mSectionList.valueAt(index).clearItemViewList();
            int sectionKey = mSectionList.valueAt(index).getSectionKey();
            shiftBelowStartPosition(sectionKey, -oldSize);
        }
    }

    public boolean itemViewMove(int fromPosition, int toPosition) {
        Logger.d("Move the item from:" + fromPosition + " to " + toPosition);

        Section fromSection = findSection(fromPosition);
        Section toSection = findSection(toPosition);

        if (null == fromSection || null == toSection) {
            Logger.w("Can't find the move from Section or the move to Section");
            return false;
        }

        if (fromSection == toSection) {
            Collections.swap(fromSection.getItemViewList(),
                    fromPosition - fromSection.getStartPosition() - fromSection.getHeadCount(),
                    toPosition - toSection.getStartPosition() - toSection.getHeadCount());

            Logger.i("Move this item successful.");
            return true;
        }

        Logger.w("Move this item failed.");
        return false;
    }

    public int addHeadView(@IntRange(from = 0) int key, @NonNull BaseHead headView) {
        Logger.i("Start to add the headView");

        Section section = checkSection(key);
        section.getHeadViewList().put(headView.getViewType(), headView);

        shiftBelowStartPosition(key, 1);
        return section.getStartPosition() + section.getHeadCount();
    }

    public int updateHeadView(@IntRange(from = 0) int key, @NonNull BaseHead headView) {
        Section section = findSectionByKey(key);
        if (null == section) {
            Logger.w("Can't find the section by key: " + key);
            return -1;
        }

        Logger.i("Start to update the headView");
        int headIndex = section.getHeadViewList().indexOfValue(headView);
        section.getHeadViewList().setValueAt(headIndex, headView);

        return section.getStartPosition() + headIndex;
    }

    public int removeHeadView(@IntRange(from = 0) int key, @NonNull BaseHead headView) {
        Section section = findSectionByKey(key);
        if (null == section) {
            Logger.w("Can't find the section by key: " + key);
            return -1;
        }

        Logger.i("Start to remove the headView");
        int headKey = section.getHeadViewList().keyAt(section.getHeadViewList().indexOfValue(headView));
        section.getHeadViewList().remove(headKey);

        shiftBelowStartPosition(key, -1);
        return section.getStartPosition() + section.getHeadCount();
    }

    public BaseHead getHeadView(@IntRange(from = 0) int key, @IntRange(from = 0) int headPosition) {
        Section section = findSectionByKey(key);
        if (null == section) {
            Logger.w("Can't find the section by key: " + key);
            return null;
        }

        return section.getHeadViewList().valueAt(headPosition);
    }

    public BaseHead getHeadView(@IntRange(from = 0) int position) {
        Section section = findSection(position);
        return null == section ? null : section.getHeadView(position - section.getStartPosition());
    }

    public int addFootView(@IntRange(from = 0) int key, @NonNull BaseFoot footView) {
        Logger.i("Start to add the foot view.");

        Section section = checkSection(key);
        section.getFootViewList().put(footView.getViewType(), footView);

        shiftBelowStartPosition(key, 1);
        return section.getStartPosition() + section.getHeadCount() + section.getItemCount() + section.getFootCount();
    }

    public int updateFootView(@IntRange(from = 0) int key, @NonNull BaseFoot footView) {
        Section section = findSectionByKey(key);
        if (null == section) {
            Logger.w("Can't find the section by key: " + key);
            return -1;
        }

        Logger.i("Start to update the footView");
        int footIndex = section.getFootViewList().indexOfValue(footView);
        section.getFootViewList().setValueAt(footIndex, footView);

        return section.getStartPosition() + section.getHeadCount() + section.getItemCount() + footIndex;
    }

    public int removeFootView(@IntRange(from = 0) int key, @NonNull BaseFoot view) {
        Section section = findSectionByKey(key);
        if (null == section) {
            Logger.w("Can't find the section by key: " + key);
            return -1;
        }

        Logger.d("Start to remove the foot view.");
        int footKey = section.getFootViewList().keyAt(section.getFootViewList().indexOfValue(view));
        section.getFootViewList().remove(footKey);

        shiftBelowStartPosition(key, -1);
        return section.getStartPosition() + section.getHeadCount() + section.getItemCount() + section.getFootCount();
    }

    public BaseFoot getFootView(@IntRange(from = 0) int key, @IntRange(from = 0) int footPosition) {
        Section section = findSectionByKey(key);
        if (null == section) {
            Logger.w("Can't find the section by key: " + key);
            return null;
        }

        return section.getFootViewList().valueAt(footPosition);
    }

    public BaseFoot getFootView(@IntRange(from = 0) int position) {
        Section section = findSection(position);
        return null == section ? null : section.getFootView(position - section.getStartPosition() -
                section.getHeadCount() - section.getItemCount());
    }

    public int getCount() {
        int count = 0;
        for (int index = 0; index < this.mSectionList.size(); index++) {
            count = count + this.mSectionList.valueAt(index).getCount();
        }

        Logger.d("The sectionList count: " + count);
        return count;
    }

    public int getViewType(@IntRange(from = 0) int position) {
        Section section = findSection(position);
        if (null == section) {
            Logger.w("Can't find the section by position: " + position);
            return -1;
        }

        return section.getViewType(position);
    }

    public BaseHead getHeadViewByViewType(int viewType) {
        if (!isHeadViewViewType(viewType)) {
            Logger.w("The viewType is not a head viewType.");
            return null;
        }

        BaseHead headView = null;
        for (int index = 0; index < this.mSectionList.size(); index++) {
            headView = this.mSectionList.valueAt(index).getHeadViewList().get(viewType);
            if (null != headView) {
                return headView;
            }
        }

        return headView;
    }

    public I getItemView(@IntRange(from = 0) int position) {
        Section section = findSection(position);
        return null == section ? null : section.getItemView(position);
    }

    public I getItemViewBySection(@IntRange(from = 0) int key, @IntRange(from = 0) int itemPosition) {
        Section section = findSectionByKey(key);
        return null == section ? null : section.getItemViewBySection(itemPosition + section.getHeadCount());
    }

    public List<I> getItemViewList(@IntRange(from = 0) int key) {
        if (null == this.mSectionList.get(key)) {
            Logger.w("The itemView list is empty.");
            return null;
        }

        return this.mSectionList.get(key).getItemViewList();
    }

    public I getItemViewByViewType(int viewType) {
        Logger.d("Start to find ItemView by viewType: " + viewType);
        Section section;
        for (int index = 0; index < this.mSectionList.size(); index++) {
            section = this.mSectionList.valueAt(index);
            if (null == section || section.getItemCount() < 0) {
                continue;
            }

            for (int itemIndex = 0; itemIndex < section.getItemCount(); itemIndex++) {
                if (viewType == section.getItemViewList().get(itemIndex).getViewType()) {
                    return section.getItemViewList().get(itemIndex);
                }
            }
        }

        Logger.w("Can't find the item view.");
        return null;
    }

    public BaseFoot getFootViewByViewType(int viewType) {
        if (!isFootViewViewType(viewType)) {
            Logger.w("The viewType is not a foot viewType. viewType: " + viewType);
            return null;
        }

        BaseFoot footView = null;
        for (int index = 0; index < this.mSectionList.size(); index++) {
            footView = this.mSectionList.valueAt(index).getFootViewList().get(viewType);
            if (null != footView) {
                return footView;
            }
        }

        return footView;
    }

    public int getSectionKey(@IntRange(from = 0) int position) {
        Section section = findSection(position);
        return null == section ? -1 : section.getSectionKey();
    }

    public int getSectionItemPosition(@IntRange(from = 0) int position) {
        Section section = findSection(position);
        return null == section ? -1 : position - section.getStartPosition() - section.getHeadCount();
    }

    public int getSectionHeadPosition(@IntRange(from = 0) int position) {
        Section section = findSection(position);
        return null == section ? -1 : position - section.getStartPosition();
    }

    public int getSectionFootPosition(@IntRange(from = 0) int position) {
        Section section = findSection(position);
        return null == section ? -1 : position - section.getStartPosition() - section.getHeadCount() - section.getItemCount();
    }

    public boolean isHeadViewViewType(int viewType) {
        for (int index = 0; index < this.mSectionList.size(); index++) {
            Section section = this.mSectionList.valueAt(index);
            if (null == section || null == section.getHeadViewList() || section.getHeadViewList().size() < 1) {
                continue;
            }

            for (int headIndex = 0; headIndex < section.getHeadViewList().size(); headIndex++) {
                BaseHead headView = section.getHeadViewList().valueAt(headIndex);
                if (null == headView) {
                    continue;
                }

                if (viewType == headView.getViewType()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isHeadViewPosition(@IntRange(from = 0) int position) {
        if (position > getCount()) {
            return false;
        }

        Section section = findSection(position);
        return null != section && section.isHeadPosition(position);
    }

    public boolean isItemViewPosition(@IntRange(from = 0) int position) {
        if (position > getCount()) {
            return false;
        }

        Section section = findSection(position);
        return null != section && section.isItemPosition(position);
    }

    public boolean isFootViewPosition(@IntRange(from = 0) int position) {
        if (position > getCount()) {
            return false;
        }

        Section section = findSection(position);
        return null != section && section.isFootPosition(position);
    }

    public boolean isFootViewViewType(int viewType) {
        for (int index = 0; index < this.mSectionList.size(); index++) {
            Section section = this.mSectionList.valueAt(index);
            if (null == section || null == section.getFootViewList() || section.getFootViewList().size() < 1) {
                continue;
            }

            for (int footIndex = 0; footIndex < section.getFootViewList().size(); footIndex++) {
                BaseFoot footView = section.getFootViewList().valueAt(footIndex);
                if (null == footView) {
                    continue;
                }

                if (viewType == footView.getViewType()) {
                    return true;
                }
            }
        }

        return false;
    }

    private Section checkSection(@IntRange(from = 0) int key) {
        Section section = this.mSectionList.get(key);
        if (null == section) {
            section = new Section(key);
            section.setStartPosition(findAboveSectionCount(key));
            this.mSectionList.put(key, section);
        }

        return section;
    }

    private Section findSection(@IntRange(from = 0) int position) {
        Section section;
        for (int index = 0; index < this.mSectionList.size(); index++) {
            section = this.mSectionList.valueAt(index);
            if (position >= section.getStartPosition() && position <= section.getEndPosition()) {
                return section;
            }
        }

        return null;
    }

    private Section findSectionByKey(@IntRange(from = 0) int key) {
        return this.mSectionList.get(key);
    }

    private int findAboveSectionCount(@IntRange(from = 0) int key) {
        int interKey = key - 1;
        if (interKey < 0) {
            return 0;
        }

        int count = 0;
        for (; interKey >= 0; interKey--) {
            if (null == this.mSectionList.get(interKey)) {
                continue;
            }

            count = count + this.mSectionList.get(interKey).getCount();
        }

        return count;
    }

    private void shiftBelowStartPosition(@IntRange(from = 0) int key, int offSet) {
        if (offSet == 0) {
            return;
        }

        for (int index = 0; index < this.mSectionList.size(); index++) {
            if (this.mSectionList.keyAt(index) < key + 1 || null == this.mSectionList.valueAt(index)) {
                continue;
            }

            this.mSectionList.valueAt(index).shiftStartPosition(offSet);
        }
    }

    private class Section {

        private int mSectionKey;
        private int mStartPosition;

        private SparseArrayCompat<BaseHead> mHeadViewList;
        private SparseArrayCompat<BaseFoot> mFootViewList;
        private List<I> mItemViewList;

        public Section(@IntRange(from = 0) int sectionKey) {
            this.mSectionKey = sectionKey;

            this.mHeadViewList = new SparseArrayCompat<>();
            this.mFootViewList = new SparseArrayCompat<>();
            this.mItemViewList = new ArrayList<>();
        }

        public int getViewType(@IntRange(from = 0) int position) {
            int interPosition = position - getStartPosition();
            if (isHeadPosition(position)) {
                return this.mHeadViewList.keyAt(interPosition);
            } else if (isItemPosition(position)) {
                return this.mItemViewList.get(interPosition - getHeadCount()).getViewType();
            } else if (isFootPosition(position)) {
                return this.mFootViewList.keyAt(interPosition - getHeadCount() - getItemCount());
            }

            return -1;
        }

        public I getItemView(@IntRange(from = 0) int position) {
            int itemPosition = position - getStartPosition() - getHeadCount();
            if (itemPosition < 0 || itemPosition >= getItemCount()) {
                return null;
            }
            return this.mItemViewList.get(itemPosition);
        }

        public I getItemViewBySection(@IntRange(from = 0) int position) {
            int itemPosition = position - getHeadCount();
            if (itemPosition < 0 || itemPosition >= getItemCount()) {
                return null;
            }
            return this.mItemViewList.get(itemPosition);
        }

        public BaseHead getHeadView(@IntRange(from = 0) int headPosition) {
            if (headPosition < 0 || headPosition >= getHeadCount()) {
                return null;
            }
            return this.mHeadViewList.get(this.mHeadViewList.keyAt(headPosition));
        }

        public BaseFoot getFootView(@IntRange(from = 0) int footPosition) {
            if (footPosition < 0 || footPosition >= getFootCount()) {
                return null;
            }
            return this.mFootViewList.get(this.mFootViewList.keyAt(footPosition));
        }

        public SparseArrayCompat<BaseHead> getHeadViewList() {
            return this.mHeadViewList;
        }

        public SparseArrayCompat<BaseFoot> getFootViewList() {
            return this.mFootViewList;
        }

        public List<I> getItemViewList() {
            return this.mItemViewList;
        }

        public void setItemViewList(List<I> itemViewList) {
            this.mItemViewList = itemViewList;
        }

        public int clearItemViewList() {
            int oldSize = this.mItemViewList.size();
            this.mItemViewList.clear();
            return oldSize;
        }

        public int getCount() {
            return getHeadCount() + getItemCount() + getFootCount();
        }

        public int getHeadCount() {
            return this.mHeadViewList.size();
        }

        public int getItemCount() {
            return this.mItemViewList.size();
        }

        public int getFootCount() {
            return this.mFootViewList.size();
        }

        public void setStartPosition(@IntRange(from = 0) int startPosition) {
            this.mStartPosition = startPosition;
        }

        public int getSectionKey() {
            return mSectionKey;
        }

        public int getStartPosition() {
            return this.mStartPosition;
        }

        public int getEndPosition() {
            return this.mStartPosition + getCount() - 1;
        }

        public void shiftStartPosition(@IntRange(from = 1) int offSet) {
            this.mStartPosition = this.mStartPosition + offSet;
        }

        public boolean isHeadPosition(@IntRange(from = 0) int position) {
            int interPosition = position - this.mStartPosition;
            return interPosition < this.mHeadViewList.size();
        }

        public boolean isItemPosition(@IntRange(from = 0) int position) {
            int interPosition = position - this.mStartPosition;
            return !isHeadPosition(position) && interPosition < this.mHeadViewList.size() + this.mItemViewList.size();
        }

        public boolean isFootPosition(@IntRange(from = 0) int position) {
            int interPosition = position - this.mStartPosition;
            return interPosition >= this.mHeadViewList.size() + this.mItemViewList.size() &&
                    interPosition < this.mHeadViewList.size() + this.mItemViewList.size() + this.mFootViewList.size();
        }

    }

}
