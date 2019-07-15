import 'package:flare_dart/math/mat2d.dart';
import 'package:flare_flutter/flare.dart';
import 'package:flare_flutter/flare_actor.dart';
import 'package:flare_flutter/flare_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:orange_flutter/style.dart';

import 'appbar.dart';

class CacheScaffold extends StatefulWidget {
  @override
  _CacheScaffoldState createState() => _CacheScaffoldState();
}

class _CacheScaffoldState extends State<CacheScaffold> with FlareController {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: contentColor,
      appBar: OrangeAppBar(
        title: Text(
          "清理缓存",
          style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
        ),
      ),
      body: Container(
          child: Stack(
        fit: StackFit.expand,
        children: <Widget>[
          FlareActor(
            "assets/flare/Resizing_House.flr",
            controller: this,
            fit: BoxFit.none,
          ),
          Container(
            child: Text("内容"),
          )
        ],
      )),
    );
  }

  @override
  bool advance(FlutterActorArtboard artboard, double elapsed) {
    return false;
  }

  @override
  void initialize(FlutterActorArtboard artboard) {}

  @override
  void setViewTransform(Mat2D viewTransform) {}
}
