package com.nineton.guoguo.listener;

import com.nineton.guoguo.R;
import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.view.MyWebView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MineOnClickListener implements OnClickListener {

	private MainActivity activity;

	public MineOnClickListener(MainActivity mainActivity) {
		this.activity = mainActivity;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_favourite:
			activity.goMyFavourite();
			activity.setState(3,MainActivity.MINE_NEXT);
			break;
		case R.id.my_order:
			activity.goMyTaobao(MyWebView.ORDER_URL,"我的订单");
			activity.setState(3,MainActivity.MINE_NEXT);
			break;
		case R.id.colloction:
			activity.goMyTaobao(MyWebView.COLLOCT_URL,"我的收藏");
			activity.setState(3, MainActivity.MINE_NEXT);
			break;
		case R.id.shop_car:
			activity.goMyTaobao(MyWebView.BUY_CAR_URL,"购物车");
			activity.setState(3, MainActivity.MINE_NEXT);
			break;
		case R.id.clear_cach:
			ImageLoader.getInstance().clearDiscCache();
			Toast.makeText(activity, "清理完毕", Toast.LENGTH_SHORT).show();
			break;
		case R.id.connect_us:
			FeedbackAgent agent = new FeedbackAgent(activity);
			agent.startFeedbackActivity();
			break;

		case R.id.update:
			UmengUpdateAgent.update(activity);
			UmengUpdateAgent.setUpdateAutoPopup(false);
			UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
					@Override
					public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
						switch (updateStatus) {
			            case 0: // has update
			                UmengUpdateAgent.showUpdateDialog(activity, updateInfo);
			                break;
			            case 1: // has no update
			                Toast.makeText(activity, "没有更新", Toast.LENGTH_SHORT)
			                        .show();
			                break;
			            case 2: // none wifi
			                Toast.makeText(activity, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT)
			                        .show();
			                break;
			            case 3: // time out
			                Toast.makeText(activity, "超时", Toast.LENGTH_SHORT)
			                        .show();
			                break;
			            }
					}
			});
			break;

		default:
			break;
		}
	}

}
