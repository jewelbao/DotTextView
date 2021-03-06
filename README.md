# **小圆点TextView**

[ ![Download](https://api.bintray.com/packages/jewelbao88/ComponentsMaven/DotTextView/images/download.svg) ](https://bintray.com/jewelbao88/ComponentsMaven/DotTextView/_latestVersion)

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

1.在 build.gradle 中添加依赖

```
compile 'com.jewel.components:DotTextView:1.0.0'
```


2.在XML布局中添加DotTextView


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

3.在Java代码里设置


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

## xml属性说明



属性 | 解释
---|---
dotColor | 圆点填充颜色
dotOffsetX | 圆点X坐标偏移量
dotOffsetY | 圆点Y坐标偏移量
dotRadius | 圆点半径
showDot | 是否显示圆点
dotGravity | 圆点位置，默认为rightTop


dotGravity | 圆点显示位置
---|---
leftTop | 基于文本左上角
rightTop | 基于文本右上角
leftBottom | 基于文本左下角
rightBottom | 基于文本右下角
leftCenter | 基于文本左居中
rightCenter | 基于文本右居中
leftDrawableCenter | 基于左图标居中位置
rightDrawableCenter | 基于右图标居中位置




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
