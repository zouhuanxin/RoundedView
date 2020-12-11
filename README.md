# RoundedView
安卓圆角库,各种基本控件上的圆角。

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
	        implementation 'com.github.zouhuanxin:RoundedView:1.0'
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
> setTargetBitmap(Bitmap bitmap)

### 示例图
![示例图](http://zhx02.run8.top/2020/12/11/0d7a41b34039e69280efa3b02b367170.png)



