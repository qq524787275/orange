import 'package:flutter/material.dart';
import 'package:intro_views_flutter/Models/page_view_model.dart';
import 'package:intro_views_flutter/intro_views_flutter.dart';

import 'channel.dart';

class AboutScaffold extends StatefulWidget {
  @override
  _AboutScaffoldState createState() => _AboutScaffoldState();
}

class _AboutScaffoldState extends State<AboutScaffold> {

 static Widget _buildAvatar() {
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
        radius: 90,
        backgroundImage: AssetImage("assets/about/man.webp"),
      ),
    );
  }

  final pages = [
    PageViewModel(
      pageColor: Color(0xF6F6F7FF),
      bubbleBackgroundColor: Colors.indigo,
      title: Container(),
      body: Column(
        children: <Widget>[
          Text('一只程序猿'),
          Text(
            'single boy',
            style: TextStyle(color: Colors.black54, fontSize: 16.0),
          ),
        ],
      ),
      mainImage: Center(child: _buildAvatar()),
      textStyle: TextStyle(color: Colors.black),
    ),
    PageViewModel(
      pageColor: Color(0xF6F6F7FF),
      iconColor: null,
      bubbleBackgroundColor: Colors.indigo,
      title: Container(),
      body: Column(
        children: <Widget>[
          Text('One Touch Send Money'),
          Text(
            'Send money in just one touch and organize your wallet smart.',
            style: TextStyle(color: Colors.black54, fontSize: 16.0),
          ),
        ],
      ),
      mainImage: Image.asset(
        'assets/about/wallet2.png',
        width: 285.0,
        alignment: Alignment.center,
      ),
      textStyle: TextStyle(color: Colors.black),
    ),
    PageViewModel(
      pageColor: Color(0xF6F6F7FF),
      iconColor: null,
      bubbleBackgroundColor: Colors.indigo,
      title: Container(),
      body: Column(
        children: <Widget>[
          Text('Automatically Organize'),
          Text(
            'Organize your expenses and incomes and secure your wallet with pin.',
            style: TextStyle(color: Colors.black54, fontSize: 16.0),
          ),
        ],
      ),
      mainImage: Image.asset(
        'assets/about/wallet3.png',
        width: 285.0,
        alignment: Alignment.center,
      ),
      textStyle: TextStyle(color: Colors.black),
    ),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
          child: Stack(
        children: <Widget>[
          IntroViewsFlutter(
            pages,
            onTapDoneButton: () {
              Navigation.back();
            },
            showSkipButton: false,
            doneText: Text(
              "Get Started",
            ),
            pageButtonsColor: Colors.indigo,
            pageButtonTextStyles: new TextStyle(
              // color: Colors.indigo,
              fontSize: 16.0,
              fontFamily: "Regular",
            ),
          ),
        ],
      )),
    );
  }
}
