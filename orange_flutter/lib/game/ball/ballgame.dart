import 'dart:ui';

import 'package:flame/flare_animation.dart';
import 'package:flame/game.dart';
import 'package:flutter/gestures.dart';

class BallGame extends Game {
  FlareAnimation flareAnimation;
  Size viewport;
  int direction = 1;
  double movementSpeed;

  BallGame() {
    _start();
  }

  @override
  void render(Canvas canvas) {
    flareAnimation.render(canvas);
  }

  @override
  void update(double dt) {
    flareAnimation.update(dt);
    checkCollision();
    flareAnimation.x += direction * movementSpeed * dt;
  }

  void checkCollision() {
    if ( flareAnimation.x >= viewport.width - 100) {
      direction = -1;
    } else if ( flareAnimation.x <= 0) {
      direction = 1;
    }
  }

  @override
  void resize(Size size) {
    viewport = size;
    movementSpeed = viewport.width / 2;

    flareAnimation.x = viewport.width / 2 - 50;
    flareAnimation.y = viewport.height / 2 - 50;
  }

  void onTapDown(TapDownDetails d) {}

  void onTapUp(TapUpDetails d) {}

  void _start() async {
    flareAnimation = await FlareAnimation.load("assets/flare/day04.flr");
    flareAnimation.updateAnimation("start_right");

    flareAnimation.width = 100;
    flareAnimation.height = 100;

  }
}
