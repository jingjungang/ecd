package com.ukang.clinic.main;

/**
 * Fragment底层类
 * jjg
 * 2016年6月8日 14:17:08
 */
import java.util.Observable;
import java.util.Observer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.fragments.AdverseEventListFragment;
import com.ukang.clinic.fragments.AdverseServiousFragment;
import com.ukang.clinic.fragments.BackToWork_Frgment;
import com.ukang.clinic.fragments.Barthel_Fragment;
import com.ukang.clinic.fragments.BaseFeatureFragment;
import com.ukang.clinic.fragments.BiochemicalCheckFragment;
import com.ukang.clinic.fragments.Bleed_Fragment;
import com.ukang.clinic.fragments.BloodBaseStandardFragment;
import com.ukang.clinic.fragments.BloodCoagulationCheckFragment;
import com.ukang.clinic.fragments.BloodFactorsCheckFragment;
import com.ukang.clinic.fragments.Clinical_Eval_Frgment;
import com.ukang.clinic.fragments.CombinedDrugsFragment;
import com.ukang.clinic.fragments.CuringCostsFragment;
import com.ukang.clinic.fragments.Dead_Frgment;
import com.ukang.clinic.fragments.ExamFinished_Fragment;
import com.ukang.clinic.fragments.FeeOutside_Frgment;
import com.ukang.clinic.fragments.FirstPageFragment;
import com.ukang.clinic.fragments.GenernalInfos;
import com.ukang.clinic.fragments.HCGFragment;
import com.ukang.clinic.fragments.HeartPic12Fragment;
import com.ukang.clinic.fragments.JoinGroupCheckFragment;
import com.ukang.clinic.fragments.MraFragment;
import com.ukang.clinic.fragments.NihssFrgment;
import com.ukang.clinic.fragments.ObeyMedicine_Frgment;
import com.ukang.clinic.fragments.PissBaseStandardFragment;
import com.ukang.clinic.fragments.PlateletAggregationCheckFragment;
import com.ukang.clinic.fragments.Rankin_Fragment;
import com.ukang.clinic.fragments.Recover_Frgment;
import com.ukang.clinic.fragments.ShitBaseStandardFragment;
import com.ukang.clinic.fragments.SignOfLife_Fragment;
import com.ukang.clinic.fragments.TakeMedicine_Frgment;
import com.ukang.clinic.fragments.TreatmentRecordsFragment;

public class Frg_Menu extends Fragment implements Observer {

	public FirstPageFragment f0; // 访视首页
	public Recover_Frgment f1; // 复发
	public Dead_Frgment f2; // 死亡
	public Clinical_Eval_Frgment f3; // 临床综合评价
	public BaseFeatureFragment f5;// 基本特征
	public GenernalInfos f6; // 一般资料
	public SignOfLife_Fragment f7; // 生命体征
	public HCGFragment f9; // 尿HCG
	public HeartPic12Fragment f10; // 12导心电图
	public MraFragment f11; // 头颅MRA
	public AdverseEventListFragment f12; // 不良事件
	public AdverseServiousFragment frg_AdverseServious; // 严重不良事件
	public CombinedDrugsFragment f13; // 合并用药
	public TreatmentRecordsFragment f14; // 治疗措施记录
	public Rankin_Fragment f15; // Rankin评分
	public NihssFrgment f16; // Nihss评分
	public Barthel_Fragment f17;// Barthel评分
	public FeeOutside_Frgment f18; // 院外治疗费用
	public BackToWork_Frgment f19; // 是否恢复工作
	public JoinGroupCheckFragment f20; // 入组核审
	public Bleed_Fragment f21; // 出血事件评价
	public CuringCostsFragment f22; // 治疗成本
	public TakeMedicine_Frgment f23; // 服用药记录
	public ObeyMedicine_Frgment f24; // 用药依从性
	public ExamFinished_Fragment f25;// 实验完成
	/**
	 * 实验室检查
	 */
	public BloodBaseStandardFragment f26;// 血常规
	public PissBaseStandardFragment f27; // 尿常规
	public ShitBaseStandardFragment f28; // 大便常规
	public BiochemicalCheckFragment f29; // 生化检查
	public BloodCoagulationCheckFragment f30; // 凝血试验
	public BloodFactorsCheckFragment f31; // 促凝血因子
	public PlateletAggregationCheckFragment f32;// 血小板聚集

	private FragmentManager fragmentManager;

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.fragment_menu,
				paramViewGroup, false);
		this.fragmentManager = getChildFragmentManager();
		setTabSelection(0);
		((MWDApplication) getActivity().getApplicationContext()).PageNotificationer
				.addObserver(this);
		return localView;
	}

	public void onDestroy() {
		super.onDestroy();
	}

	public void setTabSelection(int paramInt) {
		FragmentTransaction localFragmentTransaction = fragmentManager
				.beginTransaction();
		switch (paramInt) {
		case 0:// 访视首页
			this.f0 = new FirstPageFragment();
			localFragmentTransaction.replace(R.id.content, this.f0);
			break;
		case 1: // 复发
			this.f1 = new Recover_Frgment();
			localFragmentTransaction.replace(R.id.content, this.f1);
			break;
		case 2: // 死亡
			this.f2 = new Dead_Frgment();
			localFragmentTransaction.replace(R.id.content, this.f2);
			break;
		case 3:// 临床综合评价
			this.f3 = new Clinical_Eval_Frgment();
			localFragmentTransaction.replace(R.id.content, this.f3);
			break;
		case 5:// 基本特征
			this.f5 = new BaseFeatureFragment();
			localFragmentTransaction.replace(R.id.content, this.f5);
			break;
		case 6: // 一般资料
			this.f6 = new GenernalInfos();
			localFragmentTransaction.replace(R.id.content, this.f6);
			break;
		case 7:// 生命体征
			this.f7 = new SignOfLife_Fragment();
			localFragmentTransaction.replace(R.id.content, this.f7);
			break;
		case 9:// 尿HCG
			this.f9 = new HCGFragment();
			localFragmentTransaction.replace(R.id.content, this.f9);
			break;
		case 10:// 12导心电图
			this.f10 = new HeartPic12Fragment();
			localFragmentTransaction.replace(R.id.content, this.f10);
			break;
		case 11:// 头颅MRA
			this.f11 = new MraFragment();
			localFragmentTransaction.replace(R.id.content, this.f11);
			break;
		case 12:// 不良事件
			this.f12 = new AdverseEventListFragment();
			localFragmentTransaction.replace(R.id.content, this.f12);
			break;
		case 13:// 严重不良事件
			this.frg_AdverseServious = new AdverseServiousFragment();
			localFragmentTransaction.replace(R.id.content,
					this.frg_AdverseServious);
			break;
		case 14:// 合并用药
			this.f13 = new CombinedDrugsFragment();
			localFragmentTransaction.replace(R.id.content, this.f13);
			break;
		case 15:// 治疗措施记录
			this.f14 = new TreatmentRecordsFragment();
			localFragmentTransaction.replace(R.id.content, this.f14);
			break;
		case 16: // Rankin评分
			this.f15 = new Rankin_Fragment();
			localFragmentTransaction.replace(R.id.content, this.f15);
			break;
		case 17:// Nihss评分
			this.f16 = new NihssFrgment();
			localFragmentTransaction.replace(R.id.content, this.f16);
			break;
		case 18:// Barthel评分
			this.f17 = new Barthel_Fragment();
			localFragmentTransaction.replace(R.id.content, this.f17);
			break;
		case 19:// 院外治疗费用
			this.f18 = new FeeOutside_Frgment();
			localFragmentTransaction.replace(R.id.content, this.f18);
			break;
		case 20:// 是否恢复工作
			this.f19 = new BackToWork_Frgment();
			localFragmentTransaction.replace(R.id.content, this.f19);
			break;
		case 21:// 入组核审
			this.f20 = new JoinGroupCheckFragment();
			localFragmentTransaction.replace(R.id.content, this.f20);
			break;
		case 22:// 出血事件评价
			this.f21 = new Bleed_Fragment();
			localFragmentTransaction.replace(R.id.content, this.f21);
			break;
		case 23:// 治疗成本
			this.f22 = new CuringCostsFragment();
			localFragmentTransaction.replace(R.id.content, this.f22);
			break;
		case 24:// 服用药记录
			this.f23 = new TakeMedicine_Frgment();
			localFragmentTransaction.replace(R.id.content, this.f23);
			break;
		case 25: // 用药依从性
			this.f24 = new ObeyMedicine_Frgment();
			localFragmentTransaction.replace(R.id.content, this.f24);
			break;
		case 26:// 实验完成
			this.f25 = new ExamFinished_Fragment();
			localFragmentTransaction.replace(R.id.content, this.f25);
			break;
		case 27:
			;// 血常规
			this.f26 = new BloodBaseStandardFragment();
			localFragmentTransaction.replace(R.id.content, this.f26);
			break;
		case 28:// 尿常规
			this.f27 = new PissBaseStandardFragment();
			localFragmentTransaction.replace(R.id.content, this.f27);
			break;
		case 29:// 大便常规
			this.f28 = new ShitBaseStandardFragment();
			localFragmentTransaction.replace(R.id.content, this.f28);
			break;
		case 30:// 生化检查
			this.f29 = new BiochemicalCheckFragment();
			localFragmentTransaction.replace(R.id.content, this.f29);
			break;
		case 31:// 凝血试验
			this.f30 = new BloodCoagulationCheckFragment();
			localFragmentTransaction.replace(R.id.content, this.f30);
			break;
		case 32:// 促凝血因子
			this.f31 = new BloodFactorsCheckFragment();
			localFragmentTransaction.replace(R.id.content, this.f31);
			break;
		case 33:// 血小板聚集
			this.f32 = new PlateletAggregationCheckFragment();
			localFragmentTransaction.replace(R.id.content, this.f32);
			break;
		}

		localFragmentTransaction.commit();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		setTabSelection(((MWDApplication) getActivity().getApplicationContext()).PageIndex);
	}
}