import 'dart:ui';
import 'channel.dart';
import 'package:flutter/material.dart';
import 'package:flare_flutter/flare_actor.dart';

void main() => runApp(_widgetForRoute(window.defaultRouteName));

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
  Log.i("hahah", route);
  switch (route) {
    case 'flutter':
      return MaterialApp(
        title: 'Flutter学习', // used by the OS task switcher
        home: new DayListScaffold(),
        navigatorObservers: [GLObserver()],
      );
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

  DayStyle({this.title, this.desc, this.flare,this.animation});

  static final _all = [
    DayStyle(title: "day01", desc: "一个时钟动画", flare: "assets/flare/Filip .flr",animation:"idle")
  ];
}

class DayListScaffold extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Material(
        child: new Container(
      padding: EdgeInsets.fromLTRB(0, 25, 0, 0),
      child: ListView.builder(
          padding: const EdgeInsets.all(8.0),
          itemCount: DayStyle._all.length,
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
                        return Material(
                          child: Center(
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
