import 'dart:ui';

import 'package:flame/game.dart';
import 'package:flutter/gestures.dart';
import 'package:orange_flutter/birdgame/components/bird.dart';
import 'package:orange_flutter/channel.dart';

class BirdGame extends Game {
  Bird birdPlayer;
  double tileSize;
  double birdPosY;
  Size viewport;
  double floorHeight = 250;

  BirdGame() {
    initialize();
  }

  void initialize() async {
    birdPlayer = Bird(this, 0, birdPosY, tileSize, tileSize);
  }

  @override
  void render(Canvas canvas) {
    Log.i("zzc", "执行了");
    birdPlayer.render(canvas);
  }

  @override
  void update(double t) {}

  void onTapDown(TapDownDetails d) {}

  void onTapUp(TapUpDetails d) {}

  void resize(Size size) {
    viewport = size;
    tileSize = viewport.width / 6;
    birdPosY = viewport.height - floorHeight - tileSize + (tileSize / 8);
  }
}
