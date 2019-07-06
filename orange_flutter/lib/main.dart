import 'dart:ui';
import 'log.dart';
import 'package:flutter/material.dart';

void main() => runApp(_widgetForRoute(window.defaultRouteName));

Widget _widgetForRoute(String route) {
  Log.i("hahah", route);
  switch (route) {
    case 'flutter':
      return new MaterialApp(
        title: 'My app', // used by the OS task switcher
        home: new MyScaffold(),
      );
    default:
      return Center(
        child: Text('路由错误: $route', textDirection: TextDirection.ltr),
      );
  }
}

class MyScaffold extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // Material 是UI呈现的“一张纸”
    return new Material(
      // Column is 垂直方向的线性布局.
      child: new Column(
        children: <Widget>[
          new Expanded(
            child: new Center(
              child: new Text('Hello, world!'),
            ),
          ),
        ],
      ),
    );
  }
}
