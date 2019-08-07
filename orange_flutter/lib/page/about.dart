import 'package:flutter/material.dart' hide Action;
import 'package:intro_views_flutter/Models/page_view_model.dart';
import 'package:intro_views_flutter/intro_views_flutter.dart';
import 'package:orange_flutter/channel/channel.dart';
import 'package:orange_flutter/theme/color.dart';
import 'package:provider/provider.dart';

class AboutScaffold extends StatefulWidget {
  @override
  _AboutScaffoldState createState() => _AboutScaffoldState();
}

class _AboutScaffoldState extends State<AboutScaffold> {
  Widget _buildAvatar(ColorGlobal color) {
    return Container(
      width: 180.0,
      height: 180.0,
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        border: Border.all(color: Colors.white30),
      ),
      margin: const EdgeInsets.only(top: 32.0, left: 16.0),
      padding: const EdgeInsets.all(3.0),
      child: CircleAvatar(
        backgroundColor: color.windowBackground,
        radius: 90,
        backgroundImage: AssetImage("assets/about/man.webp"),
      ),
    );
  }

  List<PageViewModel> _buildPages(ColorGlobal color) {
    return [
      PageViewModel(
        pageColor: color.pageBackground,
        bubbleBackgroundColor: color.colorPrimary,
        title: Container(),
        body: Column(
          children: <Widget>[
            Text('一只程序猿'),
            Text(
              'single boy',
              style: TextStyle(color: color.textColorSecond, fontSize: 16.0),
            ),
          ],
        ),
        mainImage: Center(child: _buildAvatar(color)),
        textStyle: TextStyle(color: color.textColorPrimary),
      ),
      PageViewModel(
        pageColor: color.pageBackground,
        iconColor: null,
        bubbleBackgroundColor: color.colorPrimary,
        title: Container(),
        body: Column(
          children: <Widget>[
            Text('橙子街App'),
            Text(
              'kotlin+mvvm+databinding+flutter混合开发+jetpack',
              style: TextStyle(color: color.textColorSecond, fontSize: 16.0),
            ),
          ],
        ),
        mainImage: Image.asset(
          'assets/about/page1.webp',
          width: 285.0,
          alignment: Alignment.center,
        ),
        textStyle: TextStyle(color: color.textColorPrimary),
      ),
      PageViewModel(
        pageColor: color.pageBackground,
        iconColor: null,
        bubbleBackgroundColor: color.colorPrimary,
        title: Container(),
        body: Column(
          children: <Widget>[
            Text('个人博客'),
            Text(
              'zhuzichu.com',
              style: TextStyle(color: color.textColorSecond, fontSize: 16.0),
            ),
          ],
        ),
        mainImage: Image.asset(
          'assets/about/page2.webp',
          width: 285.0,
          alignment: Alignment.center,
        ),
        textStyle: TextStyle(color: color.textColorPrimary),
      ),
    ];
  }

  @override
  Widget build(BuildContext context) {
    final color = Provider.of<ColorGlobal>(context);

    return Scaffold(
      body: Stack(
        children: <Widget>[
          IntroViewsFlutter(
            _buildPages(color),
            onTapDoneButton: () => Navigation.back(),
            showSkipButton: false,
            doneText: Text(
              "退出",
            ),
            pageButtonsColor: color.colorPrimary,
            pageButtonTextStyles: new TextStyle(
              // color: Colors.indigo,
              fontSize: 16.0,
              fontFamily: "Regular",
            ),
          ),
        ],
      ),
    );
  }
}
