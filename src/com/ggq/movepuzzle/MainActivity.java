package com.ggq.movepuzzle;

import android.os.Bundle;

import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class MainActivity extends Activity {

	//���ö���ά���鴴����Ϸ����
	private ImageView[] [] iv_game_arr=new ImageView[3][5];
	
	private GridLayout gl_main_game;
	//��ǰ�շ���
	private ImageView iv_null_ImageView;
	//��ǰ����
	private GestureDetector mGestureDetector;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return mGestureDetector.onTouchEvent(event);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.activity_main);
		mGestureDetector =new GestureDetector( this,new OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent arg0) {
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent arg0) {
			}
			
			@Override
			public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
					float arg3) {
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent arg0) {
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float vX,
					float vY) {
				int type=getDirByGes(e1.getX(), e1.getY(), e2.getX(), e2.getY());
				changeByDir(type);
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent arg0) {
				return false;
			}
		});
		//��ȡһ�Ŵ�ͼ
		Bitmap bigBm=((BitmapDrawable) getResources().getDrawable(R.drawable.meinv)).getBitmap();
		//С�����
		int tuWidth=bigBm.getWidth()/5;
		//��ʼ��
		for (int i = 0; i < iv_game_arr.length; i++) {
			for (int j = 0; j < iv_game_arr[0].length; j++) {
				//��ͼ
				Bitmap bm=Bitmap.createBitmap(bigBm,j*tuWidth,i*tuWidth,tuWidth,tuWidth);
				iv_game_arr[i][j]=new ImageView(this);
				iv_game_arr[i][j].setImageBitmap(bm);
				//���ü��
				iv_game_arr[i][j].setPadding(2, 2, 2, 2);
				//���Զ�������
				iv_game_arr[i][j].setTag(new GameData(i, j, bm));
				iv_game_arr[i][j].setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						boolean flag=isHasByNullImageView((ImageView) v);
						if(flag){
							changeDataByImageView((ImageView) v);
						}
					}
				});
			}
		}
		//���
		gl_main_game=(GridLayout) findViewById(R.id.gl_main_game);
		for (int i = 0; i < iv_game_arr.length; i++) {
			for (int j = 0; j < iv_game_arr[0].length; j++) {
				gl_main_game.addView(iv_game_arr[i][j]);
			}
		}
		setNullImageView(iv_game_arr[2][4]);
	}
	
	public void changeByDir(int type){
		GameData mNullGameData=(GameData) iv_null_ImageView.getTag();
		//����λ������
		int new_x=mNullGameData.x;
		int new_y=mNullGameData.y;
		//λ��
		if(type==1){
			new_x++;
		}else if(type==2){
			new_x--;
		}else if(type==3){
			new_y++;
		}else if(type==4){
			new_y--;
		}
		if(new_x>=0&&new_x<iv_game_arr.length&&new_y>0&&new_y<iv_game_arr[0].length ){
			changeDataByImageView(iv_game_arr[new_x][new_y]);
		}else{
			
		}
	}
	
	
	//�����жϣ���������
	/**
	 * @param s_x
	 * @param s_y
	 * @param e_x
	 * @param e_y
	 * @return 1 2 3 4
	 */
	public int getDirByGes(float s_x,float s_y,float e_x,float e_y){
		boolean isLeftOrRight=(Math.abs(s_x-e_x)>Math.abs(s_y-e_y)?true:false);
		if(isLeftOrRight){
			boolean isLeft=s_x-e_x>0?true:false;
			if(isLeft){
				return 3;
			}else{
				return 4;
			}
		}else{
			boolean isUp=s_y-e_y>0?true:false;
			if(isUp){
				return 1;
			}else{
				return 2;
			}
		}
	}
	//��������֮�󽻻�����
	public void changeDataByImageView(final ImageView iv){
		//�������������÷��򣬾���
		TranslateAnimation translateAnimation = null;
		if(iv.getX()>iv_null_ImageView.getX()){
			//����
			translateAnimation=new TranslateAnimation(0.1f,-iv.getWidth(),0.1f,0.1f);
		}
		else if(iv.getX()<iv_null_ImageView.getX()){
			//����
			translateAnimation=new TranslateAnimation(0.1f,iv.getWidth(),0.1f,0.1f);
		}
		else if(iv.getY()>iv_null_ImageView.getY()){
			//����
			translateAnimation=new TranslateAnimation(0.1f,0.1f,-iv.getWidth(),0.1f);
		}
		else if(iv.getY()<iv_null_ImageView.getY()){
			//����
			translateAnimation=new TranslateAnimation(0.1f,0.1f,iv.getWidth(),0.1f);
		}
		//���ö���ʱ��
		translateAnimation.setDuration(70);
		//�����Ƿ�ͣ��
		translateAnimation.setFillAfter(true);
		//�������ݽ���
		translateAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				iv.clearAnimation();
				GameData mGameData=(GameData) iv.getTag();
				iv_null_ImageView.setImageBitmap(mGameData.bm);
				GameData mNullGameData=(GameData) iv_null_ImageView.getTag();
				mNullGameData.bm=mGameData.bm;
				mNullGameData.p_x=mGameData.p_x;
				mNullGameData.p_y=mGameData.p_y;
				setNullImageView(iv);
			}
		});
		iv.startAnimation(translateAnimation);
	}
	//���ÿշ���
	public void setNullImageView(ImageView iv){
		iv.setImageBitmap(null);
		iv_null_ImageView=iv;
	}

	//�жϷ����Ƿ�����
	public boolean isHasByNullImageView(ImageView iv)
	{
		GameData mNullGameData=(GameData) iv_null_ImageView.getTag();
		
		GameData mGameData=(GameData) iv.getTag();
		//��ȡλ��
		if(mNullGameData.y==mGameData.y&&mGameData.x+1==mNullGameData.x){//�ϱ�
			return true;
		}else if(mNullGameData.y==mGameData.y&&mGameData.x-1==mNullGameData.x){//��
			return true;
		}else if(mNullGameData.y==mGameData.y+1&&mGameData.x==mNullGameData.x){//��
			return true;
		}else if(mNullGameData.y==mGameData.y-1&&mGameData.x==mNullGameData.x){//�ұ�
			return true;
		}
		return false;
	}
	class GameData{
		//ʵ��λ��
		public int x=0;
		public int y=0;
		//С����ͼƬ
		public Bitmap bm;
		
		public int p_x=0;
		public int p_y=0;
		public GameData(int x, int y, Bitmap bm) {
			super();
			this.x = x;
			this.y = y;
			this.bm = bm;
			this.p_x = x;
			this.p_y = y;
		}
		
		
	}
	//�������˳��
	public void randomMove(){
		for (int i = 0; i < 100; i++) {
			int type=(int)(Math.random()*4)+1;
			changeByDir(type);
		}
	}
}
