import 'dart:ui';

import 'package:flame/flare_animation.dart';
import 'package:flame/game.dart';
import 'package:flutter/gestures.dart';

class BallGame extends Game {
  FlareAnimation flareAnimation;

  bool loaded = false;

  BallGame() {
    _start();
  }

  @override
  void render(Canvas canvas) {
    if (loaded) {
      flareAnimation.render(canvas);
    }
  }

  @override
  void update(double dt) {
    if (loaded) {
      flareAnimation.update(dt);
    }
  }

  void onTapDown(TapDownDetails d) {}

  void onTapUp(TapUpDetails d) {}

  void _start() async {
    flareAnimation = await FlareAnimation.load("assets/flare/day03.flr");
    flareAnimation.updateAnimation("start");

    flareAnimation.x = 50;
    flareAnimation.y = 50;

    flareAnimation.width = 200;
    flareAnimation.height = 200;

    loaded = true;
  }
}
