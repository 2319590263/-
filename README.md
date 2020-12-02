# 字符串万能解析

#### 介绍
个人使用，你们要用直接拿去用就行，写的不是很好，只是为了自己方便写的，见谅

#### 软件架构
软件架构说明
目前还只支持解析xml，后续会支持更多格式

#### 当前版本:0.1

1.  初步实现解析xml与反解析

#### 使用说明

1.  目前只有一个类XmlAnalysis
2.  analysisStream方法，传入一个输入流会将该输入流解析成XmlPojo并返回
3.  analysisFile方法，传入一个file对象会将对象指定的文件解析成XmlPojo并返回
4.  analysisFile的重载，传入一个文件地址会将指定地址的文件解析成XmlPojo并返回
5.  analysisString方法，传入一个xml的字符串会将该其解析成XmlPojo并返回
6.  XmlPojoToXMl方法，传入一个XmlPojo对象会将该对象解析成xml字符串并返回

#### 参与贡献

1.  Forest_Savage
