import 'dart:convert';
import 'dart:ui';

import 'package:flame/flame.dart';
import 'package:flame/util.dart';
import 'package:flare_flutter/flare_actor.dart';
import 'package:flare_flutter/flare_cache.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:orange_flutter/style.dart';
import 'package:provider/provider.dart';

import 'about.dart';
import 'birdgame/birdgame.dart';
import 'cache.dart';
import 'channel.dart';
import 'color.dart';

void main() {
  FlareCache.doesPrune = false;

  final url = window.defaultRouteName;
  final split = url.split("?");
  final route = split[0];
  final param = json.decode(split[1]);
  ColorGlobal().isDark = param["isDark"];
  ColorGlobal().colorPrimary = Color(param["colorPrimary"]);

  if (route == "game_bird") {
    Util flameUtil = Util();
    flameUtil.fullScreen();
    flameUtil.setOrientation(DeviceOrientation.portraitUp);
    Flame.images.loadAll(<String>[
      'bird-0.png',
      'bird-1.png',
      'bird-0-left.png',
      'bird-1-left.png',
      'cloud-1.png',
      'cloud-2.png',
      'cloud-3.png',
    ]);
    BirdGame game = BirdGame();
    TapGestureRecognizer tapSink = TapGestureRecognizer();
    tapSink.onTapDown = game.onTapDown;
    tapSink.onTapUp = game.onTapUp;
    runApp(game.widget);
    flameUtil.addGestureRecognizer(tapSink);
  } else {
    runApp(MainApp(route: route));
  }
}

class MainApp extends StatefulWidget {
  final String route;

  const MainApp({Key key, this.route}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _MainAppState();
}

class _MainAppState extends State<MainApp> {
  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(
          builder: (_) => ColorGlobal(),
        )
      ],
      child: _widgetForRoute(widget.route),
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
  Widget container;
  switch (route) {
    case 'flutter':
      container = DayListScaffold();
      break;
    case 'cache':
      container = CacheScaffold();
      break;
    case 'about':
      container = AboutScaffold();
      break;
    default:
      container = Center(
        child: Text('路由错误: $route', textDirection: TextDirection.ltr),
      );
      break;
  }
  return MaterialApp(
    title: '橙子街',
    theme: ThemeData(
        brightness: Brightness.light,
        primarySwatch: Colors.orange,
        canvasColor: Colors.transparent),
    routes: {
      '/': (context) => container,
      '/detail': (context) {
        DayStyle style = ModalRoute.of(context).settings.arguments as DayStyle;
        return FlareDetailScreen(dayStyle: style);
      },
    },
    navigatorObservers: [GLObserver()],
  );
}

class FlareDetailScreen extends StatefulWidget {
  final DayStyle dayStyle;

  const FlareDetailScreen({Key key, this.dayStyle}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _FlareDetailScreenState();
}

class _FlareDetailScreenState extends State<FlareDetailScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        alignment: Alignment.center,
        color: contentColor,
        child: FlareActor(
          widget.dayStyle.flare,
          alignment: Alignment.center,
          fit: BoxFit.contain,
          animation: widget.dayStyle.animation,
        ),
      ),
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
        animation: "start"),
    DayStyle(
        title: "Day02",
        desc: "一个会颜色变化的球,丑是丑了点",
        flare: "assets/flare/day02.flr",
        animation: "start"),
    DayStyle(
        title: "Day03",
        desc: "一个会身体会变形的球,丑是丑了点",
        flare: "assets/flare/day03.flr",
        animation: "start")
  ];
}

class NoAnimation extends PageRouteBuilder {
  final Widget widget;

  NoAnimation(this.widget)
      : super(
            transitionDuration: const Duration(milliseconds: 0), //设置动画时长500毫秒
            pageBuilder: (BuildContext context, Animation<double> animation1,
                Animation<double> animation2) {
              return widget;
            },
            transitionsBuilder: (BuildContext context,
                Animation<double> animation1,
                Animation<double> animation2,
                Widget child) {
              //渐变过渡
              return FadeTransition(
                //渐变过渡 0.0-1.0
                opacity: Tween(begin: 1.0, end: 1.0).animate(CurvedAnimation(
                  parent: animation1, //动画样式
                  curve: Curves.fastOutSlowIn, //动画曲线
                )),
                child: child,
              );
            });
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
                      Navigator.of(context).push(NoAnimation(FlareDetailScreen(
                        dayStyle: DayStyle._all[index],
                      )))
//                          .pushNamed("/detail", arguments: DayStyle._all[index])
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
