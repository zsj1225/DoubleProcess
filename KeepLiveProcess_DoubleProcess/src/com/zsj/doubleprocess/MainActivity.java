package com.zsj.doubleprocess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 一上来就启动两个服务。
		startService(new Intent(this, LocalService.class));
		startService(new Intent(this, RemoteService.class));
	}
}
