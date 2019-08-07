import 'dart:ui';

import 'package:flare_dart/math/mat2d.dart';
import 'package:flare_flutter/flare.dart';
import 'package:flare_flutter/flare_actor.dart';
import 'package:flare_flutter/flare_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:orange_flutter/component/appbar.dart';
import 'package:orange_flutter/theme/style.dart';

class CacheScaffold extends StatefulWidget {
  @override
  _CacheScaffoldState createState() => _CacheScaffoldState();
}

class _CacheScaffoldState extends State<CacheScaffold>
    with FlareController, SingleTickerProviderStateMixin {
  static const double DemoMixSpeed = 10;
  static const double FPS = 60;
  FlutterActorArtboard _artboard;
  FlareAnimationLayer _demoAnimation;
  FlareAnimationLayer _skyAnimation;
  bool isDemoMode = false;
  double _lastDemoValue = 0.0;

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
            fit: BoxFit.cover,
          ),
          Container(
            margin: const EdgeInsets.only(left: 40, right: 40),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                Text("缓存大小：178MB",
                    style: TextStyle(
                        color: Colors.white,
                        fontFamily: "Roboto",
                        fontSize: 14,
                        fontWeight: FontWeight.w700)),
                Padding(
                  padding: EdgeInsets.fromLTRB(0, 15, 0, 0),
                  child: RaisedButton(
                    color: Colors.white,
                    onPressed: () {
                      isDemoMode = !isDemoMode;
                    },
                    child: Text('清理缓存',
                        style:
                            TextStyle(fontSize: 14, color: Colors.grey[850])),
                  ),
                )
              ],
            ),
          )
        ],
      )),
    );
  }

  _checkRoom() {
    double demoFrame = _demoAnimation.time * FPS;
    double demoValue = 0.0;
    if (demoFrame <= 15) {
      demoValue =
          lerpDouble(6.0, 5.0, Curves.easeInOut.transform(demoFrame / 15));
    } else if (demoFrame <= 36) {
      demoValue = 5.0;
    } else if (demoFrame <= 50) {
      demoValue = lerpDouble(
          5.0, 4.0, Curves.easeInOut.transform((demoFrame - 36) / (50 - 36)));
    } else if (demoFrame <= 72) {
      demoValue = 4.0;
    } else if (demoFrame <= 87) {
      demoValue = lerpDouble(
          4.0, 3.0, Curves.easeInOut.transform((demoFrame - 72) / (87 - 72)));
    } else if (demoFrame <= 128) {
      demoValue = 3.0;
    } else if (demoFrame <= 142) {
      demoValue = lerpDouble(3.0, 6.0,
          Curves.easeInOut.transform((demoFrame - 128) / (142 - 128)));
    } else if (demoFrame <= 164) {
      demoValue = 6.0;
    }

    if (_lastDemoValue != demoValue) {
      _lastDemoValue = demoValue;
    }
  }

  @override
  bool advance(FlutterActorArtboard artboard, double elapsed) {
    /// Advance the background animation every frame.
    _skyAnimation.time =
        (_skyAnimation.time + elapsed) % _skyAnimation.duration;
    _skyAnimation.apply(artboard);

    /// If the app is still in demo mode, the mix is positive
    /// Otherwise quickly ramp it down to stop the animation.
    double demoMix =
        _demoAnimation.mix + DemoMixSpeed * (isDemoMode ? elapsed : -elapsed);
    demoMix = demoMix.clamp(0.0, 1.0);
    _demoAnimation.mix = demoMix;

    if (demoMix != 0.0) {
      /// Advance the time, and loop.
      _demoAnimation.time =
          (_demoAnimation.time + elapsed) % _demoAnimation.duration;
      _demoAnimation.apply(artboard);

      /// Check which number of rooms is currently visible.
      _checkRoom();
    }
    return true;
  }

  @override
  void initialize(FlutterActorArtboard artboard) {
    this._artboard = artboard;
    _demoAnimation = FlareAnimationLayer()
      ..animation = _artboard.getAnimation("Demo Mode");
    _skyAnimation = FlareAnimationLayer()
      ..animation = _artboard.getAnimation("Sun Rotate")
      ..mix = 1.0;
    ActorAnimation endAnimation = artboard.getAnimation("to 6");
    if (endAnimation != null) {
      endAnimation.apply(endAnimation.duration, artboard, 1.0);
    }
  }

  @override
  void setViewTransform(Mat2D viewTransform) {}
}
