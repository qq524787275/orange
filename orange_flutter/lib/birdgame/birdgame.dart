import 'dart:ui';

import 'package:flame/flame.dart';
import 'package:flame/game.dart';
import 'package:flutter/gestures.dart';
import 'package:orange_flutter/birdgame/components/bird.dart';
import 'package:orange_flutter/channel.dart';

enum GameState {
  playing,
  gameOver,
}

class BirdGame extends Game {
  Size viewport;
  GameState currentGameState = GameState.playing;

  Bird birdPlayer;
  double tileSize;
  double birdPosY;
  double birdPosYOffset = 8;
  bool isFluttering = false;
  double flutterValue = 0;
  double flutterIntensity = 20;
  double floorHeight = 250;
  // Game Score
  double currentHeight = 0;


  BirdGame() {
    initialize();
  }

  void initialize() async {
    Size initalDimensions = await Flame.util.initialDimensions();
    resize(initalDimensions);
    birdPlayer = Bird(this, 0, birdPosY, tileSize, tileSize);
  }

  @override
  void render(Canvas canvas) {
    birdPlayer.render(canvas);
  }

  @override
  void update(double t) {
    birdPlayer.update(t);
  }

  void flutterHandler() {
    if (isFluttering) {
      flutterValue = flutterValue * 0.8;
      currentHeight += flutterValue;
      birdPlayer.setRotation(-flutterValue * birdPlayer.direction * 1.5);
      // Cut the jump below 1 unit
      if (flutterValue < 1) isFluttering = false;
    } else {
      // If max. fallspeed not yet reached
      if (flutterValue < 15) {
        flutterValue = flutterValue * 1.2;
      }
      if (currentHeight > flutterValue) {
        birdPlayer.setRotation(flutterValue * birdPlayer.direction * 2);
        currentHeight -= flutterValue;
        // stop jumping below floor
      } else if (currentHeight > 0) {
        currentHeight = 0;
        birdPlayer.setRotation(0);
      }
    }
  }

  void onTapDown(TapDownDetails d) {
    birdPlayer.startFlutter();
    Log.i("zzc", "viewport: onTapDown");
    if (currentGameState != GameState.gameOver) {
      // Make the bird flutter
      birdPlayer.startFlutter();
      isFluttering = true;
      flutterValue = flutterIntensity;
      return;
    }
  }

  void onTapUp(TapUpDetails d) {
    Log.i("zzc", "viewport: onTapUp");
    birdPlayer.endFlutter();
  }

  @override
  void resize(Size size) {
    viewport = size;
    tileSize = viewport.width / 6;
    birdPosY = viewport.height - floorHeight - tileSize + (tileSize / 8);
  }
}
