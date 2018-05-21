# **小圆点TextView**


## 介绍
 可自由设置红点在TextView中任意位置的控件。一般适用需要显示圆点提醒的控件(例如检查更新)。


##  详细特性
- 设置圆点颜色值
- 设置圆点半径
- 设置圆点显示位置
- 支持圆点显示位置微调
- debug模式查看测试数据（鸡肋功能）

##  项目演示
![image](https://raw.githubusercontent.com/jewelbao/DotTextView/master/art/demo.gif)

## 简单用例

1.在XML布局中添加DotTextView


```
 <com.jewel.components.DotTextView
        android:id="@+id/dot"
        android:layout_width="match_parent"
        android:layout_height="360dp"
        android:gravity="center"
        android:text="@string/contentMulti"
        android:textColor="@android:color/black"
        android:textSize="15sp"
        app:dotColor="@color/colorPrimaryDark"
        app:dotOffsetX="-10dp"
        app:dotOffsetY="10dp"
        app:dotRadius="10"
        app:dotGravity="rightTop"
        app:isDebug="false" />
```

2.在Java代码里设置


```
DotTextView dotTextView = findViewById(R.id.dot);
dotTextView.setRefreshIImmediately(false);  // 关闭立即刷新开关，执行以下代码set**时就不会更新View

dotTextView.setDotPaddingBottom(10);
dotTextView.setDotPaddingLeft(20);
dotTextView.setDotPaddingRight(30);
dotTextView.setDotPaddingTop(40);
// 或者使用以下方法
//dotTextView.setDotOffsetX(10);
//dotTextView.setDotOffsetY(10);

dotTextView.setDotRadius(10);

dotTextView.setDotGravity(DotTextView.RIGHT_TOP);

dotTextView.setRefreshIImmediately(true); // 重新开启刷新开关
dotTextView.refresh(); // 刷新,调用此方法，无论是否设置了上面的方法，都会刷新UI
```

## License

```
Copyright 2018 jewelbao

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
