package com.example.android_work;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.Button;
import java.util.LinkedList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn3)
    Button btnCreate;
    @BindView(R.id.list_view)
    RecyclerView list;
    List<Info> contacList = new LinkedList<>();
    ListAdapter adapter;
    LinearLayoutManager listLayoutMgr;
    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new ListAdapter(contacList);

        listLayoutMgr = new LinearLayoutManager(this);
        listLayoutMgr.setOrientation(RecyclerView.VERTICAL);

        list.setLayoutManager(listLayoutMgr);//设置使用何种LayoutManager来绘制Recyclerview
        list.setAdapter(adapter);//设置adapter，为RecyclerView提供子itemview
        list.setItemAnimator(new DefaultItemAnimator());//设置RecyclerView的itemView，非必须

        adapter.notifyDataSetChanged();

        //实现左滑删除
        ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.LEFT) {
            /**
             * @param recyclerView
             * @param viewHolder 拖动的ViewHolder
             * @param target 目标位置的ViewHolder
             * @return
             */

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                contacList.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    //滑动时改变Item的透明度
                    final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(list);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(i,0);
            }
        });


    }

   @Override
   protected void onActivityResult(int a,int b, @Nullable Intent data){
        super.onActivityResult(a,b,data);
       if (a==0 && b==0){
           if (data != null){
               Info info = (Info)data.getSerializableExtra(AddActivity.INFO);
               contacList.add(info);
               adapter.notifyDataSetChanged();
           }
       }
       else if (a==1 && b==1){
           if (data != null){
               int i,j;
               String str = data.getStringExtra(EditActivity.JINDU);
               View view = listLayoutMgr.findViewByPosition(pos);
               ProgressBar p = view.findViewById(R.id.pb);
               Button t = view.findViewById(R.id.tv);
               if (!str.equals("")){
                   i = Integer.parseInt(str);
                   p.setProgress(i);
                   j = i % 100;
                   t.setText(j + "%");
               }
               else {
                   p.setProgress(0);
                   t.setText(0 + "%");
               }
               adapter.notifyDataSetChanged();
           }
       }
   }

    public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

        List<Info> contactInfos;
        public ListAdapter(List<Info> contactInfos) {

            this.contactInfos = contactInfos;
        }
        @NonNull
        @Override
        //新的ViewHolder来显示列表项
        public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.activity_info, parent, false);
            final ListViewHolder holder = new ListViewHolder(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = holder.getAdapterPosition();
                    Intent i = new Intent(MainActivity.this,EditActivity.class);
                    startActivityForResult(i,1);
                }
            });
            return holder;
        }

        @Override
        //将数据绑定在ViewHolder上
        public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
            if (contacList != null) {
                final Info info = contacList.get(position);
                holder.date.setText(info.getDate());
                holder.thing.setText(info.getThing());
                holder.pos = position;
            }
        }
        @Override
        public int getItemCount() {
            return this.contactInfos.size();
        }
    }
    public class ListViewHolder extends RecyclerView.ViewHolder {
        //ViewHolder承载每一个列表项的视图
        View v;
        TextView date;
        TextView thing;

        int pos;

        public ListViewHolder(@NonNull final View itemView) {
            super(itemView);
            v = itemView;
            date = itemView.findViewById(R.id.txt_date);
            thing = itemView.findViewById(R.id.txt_thing);
            itemView.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
                //点击完成按钮删除条目
                @Override
                public void onClick(View v) {
                    itemView.isPressed();
                    contacList.remove(pos);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}
