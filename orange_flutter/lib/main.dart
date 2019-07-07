import 'dart:ui';
import 'package:flare_flutter/flare_cache.dart';
import 'package:orange_flutter/style.dart';

import 'channel.dart';
import 'package:flutter/material.dart';
import 'package:flare_flutter/flare_actor.dart';

void main() {
  FlareCache.doesPrune = false;
  runApp(MainApp());
}

class MainApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _MainAppState();
}

class _MainAppState extends State<MainApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter 学习',
      theme: ThemeData(
          brightness: Brightness.light,
          primarySwatch: Colors.orange,
          canvasColor: Colors.transparent),
      home: _widgetForRoute(window.defaultRouteName),
      navigatorObservers: [GLObserver()],
    );
  }
}

class GLObserver extends NavigatorObserver {
  @override
  void didPop(Route route, Route previousRoute) {
    super.didPop(route, previousRoute);
    if (previousRoute != null) Navigation.setCurrent(previousRoute.isCurrent);
  }

  @override
  void didPush(Route route, Route previousRoute) {
    super.didPush(route, previousRoute);
    if (previousRoute != null) Navigation.setCurrent(previousRoute.isCurrent);
  }
}

Widget _widgetForRoute(String route) {
  switch (route) {
    case 'flutter':
      return new DayListScaffold();
    default:
      return Center(
        child: Text('路由错误: $route', textDirection: TextDirection.ltr),
      );
  }
}

class DayStyle {
  final String title;
  final String desc;
  final String flare;
  final String animation;

  DayStyle({this.title, this.desc, this.flare, this.animation});

  static final _all = [
    DayStyle(
        title: "demo",
        desc: "一个动画demo",
        flare: "assets/flare/Filip.flr",
        animation: "idle"),
    DayStyle(
        title: "Day01",
        desc: "一个时钟动画,丑是丑了点",
        flare: "assets/flare/day01.flr",
        animation: "start")
  ];
}

class DayListScaffold extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
        body: new Container(
      alignment: Alignment.center,
      color: contentColor,
      padding: EdgeInsets.fromLTRB(0, 25, 0, 0),
      child: ListView.separated(
          padding: const EdgeInsets.all(8.0),
          itemCount: DayStyle._all.length,
          separatorBuilder: (context, index) => const Divider(),
          itemBuilder: (BuildContext context, int index) {
            return Container(
                height: 100,
                color: Colors.amber[200],
                child: Material(
                  type: MaterialType.transparency,
                  child: InkWell(
                    onTap: () => {
                      Navigator.push(context, new MaterialPageRoute(
                          builder: (BuildContext context) {
                        return Scaffold(
                          body: Container(
                            alignment: Alignment.center,
                            color: contentColor,
                            child: FlareActor(
                              DayStyle._all[index].flare,
                              alignment: Alignment.center,
                              fit: BoxFit.contain,
                              animation: DayStyle._all[index].animation,
                            ),
                          ),
                        );
                      }))
                    },
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Padding(
                          padding: new EdgeInsets.all(8.0),
                          child: Text(
                            DayStyle._all[index].title,
                            style: TextStyle(
                                fontWeight: FontWeight.bold, fontSize: 22),
                            textAlign: TextAlign.right,
                          ),
                        ),
                        Text(DayStyle._all[index].desc)
                      ],
                    ),
                  ),
                ));
          }),
    ));
  }
}
