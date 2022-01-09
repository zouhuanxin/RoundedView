# RoundedView
安卓圆角库,各种基本控件上的圆角。
所有默认样式都已经重置，比如如果你使用了RoundedEditText控件，请给它赋予默认样式，否则可能会出现一些与你预想中偏差的效果。

### 引入
Add it in your root build.gradle at the end of repositories:
> allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 
Add the dependency
> dependencies {
	        implementation 'com.github.zouhuanxin:RoundedView:1.2'
	}

### 目前支持的基础控件
 1. RoundedButton
 2. RoundedEditText
 3. RoundedFrameLayout
 4. RoundedImageView
 5. RoundedLinearLayout
 6. RoundedRelativeLayout
 7. RoundedTextView
 8. RoundedView
 
 > RoundedView是继承View重写的一个基础类，进一步开发可以考虑继承RoundedView。RoundedView支持四个方向圆角，支持线性背景渐变，支持背景高斯模糊，支持边框颜色，支持自定义路径裁剪。
 
 
| 名称 | 圆角 | 线性渐变 | 高斯模糊 | 边框颜色 | 自定义路径裁剪 |
| :-----| :-----| :-----| :-----| :-----| :-----| 
| RoundedButton | ✅ | ✅ | ✅ | ✅ | ✅ |
|RoundedEditText| ✅ | ❌ | ❌ | ✅ | ✅ |
|RoundedFrameLayout| ✅ | ✅ | ✅ | ✅ | ✅ |
|RoundedImageView| ✅ | ✅ | ✅ | ✅ | ✅ |
|RoundedLinearLayout| ✅ | ✅ | ✅ | ✅ | ✅ |
|RoundedRelativeLayout| ✅ | ✅ | ✅ | ✅ | ✅ |
|RoundedTextView| ✅ | ✅ | ✅ | ✅ | ✅ |
|RoundedView| ✅ | ✅ | ✅ | ✅ | ✅ |

### 属性
> rRadius <br />
> rLeftRadius <br />
> rRightRadius <br />
> rTopRadius <br />
> rBottomRadius <br />
> rTopLeftRadius <br />
> rTopRightRadius <br />
> rBottomLeftRadius <br />
> rBottomRightRadius <br />
> rStrokeWidth <br />
> rStrokeColor <br />
> blur <br />
> lineargradientColor <br />
> lineargradientWeight <br />
> lineargradientDirectionType

### 方法
> setRadius(float radiusDp) <br />
> setRadius(float radiusTopLeftDp, float radiusTopRightDp, float radiusBottomLeftDp, float radiusBottomRightDp) <br />
> setRadiusLeft(float radiusDp) <br />
> setRadiusRight(float radiusDp) <br />
> setRadiusTop(float radiusDp) <br />
> setRadiusBottom(float radiusDp) <br />
> setRadiusTopLeft(float radiusDp) <br />
> setRadiusTopRight(float radiusDp) <br />
> setRadiusBottomLeft(float radiusDp) <br />
> setRadiusBottomRight(float radiusDp) <br />
> setStrokeWidth(float widthDp) <br />
> setStrokeColor(int color) <br />
> setBlur(int num) <br />
> setTargetBitmap(Bitmap bitmap) <br />
> setImage 方法是RoundImageView独有其它控件没有此方法，如果需要在代码中进行图片的加载调用此方法进行图片的加载，如果需要加载网络图片可以直接使用gilde进行图片的加载，然后再配合需要的高斯模糊或者其它处理，针对gilde网络图片的异步加载问题已经处理完毕，不需要再做其它处理 <br />
> setImage(int resId) <br />
> setImage(Drawable drawable)

### 注意
1. 渐变颜色（lineargradientColor）和渐变比重设置方法（lineargradientWeight）
>  app:lineargradientDirectionType="b" //方向 <br />
>  app:lineargradientColor="#ff0000-#cccccc-#4678ff" //颜色 多种颜色用 - 符号隔开 <br />
>  app:lineargradientWeight="0.2-0.3-0.7" //每种颜色权重
2. 高斯模糊blur取值范围
> 0 < num <= 25 之间 数值越大约模糊
3. 渐变方向声明
 * 线性方向 默认从上至下
 * a 从上到下
 * b 从左到右
 * c 从左上角到右下角
 * d 从右上角到左下角
>  app:lineargradientDirectionType="b" //方向 <br />

### 注意
### 示例图地址
http://zhx02.run8.top/2020/12/11/0d7a41b34039e69280efa3b02b367170.png

> 邮箱:634448817@qq.com
