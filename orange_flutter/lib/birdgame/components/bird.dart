import 'dart:ui';

import 'package:flame/sprite.dart';
import 'package:orange_flutter/birdgame/birdgame.dart';

import 'core/GameObject.dart';

class Bird extends GameObject {
  Bird(BirdGame game, this.x, this.y, this.width, this.height,
      [this.rotation = 0])
      : super(game) {
    collisionToleranceX = game.tileSize / 5;
    collisionToleranceY = game.tileSize / 6;
    movementSpeed = game.viewport.width / 2;
  }

  final List<List<Sprite>> characterSprites = [
    [Sprite('bird-0.png'), Sprite('bird-1.png')],
    [Sprite('bird-0-left.png'), Sprite('bird-1-left.png')]
  ];

  Rect rect;
  Paint paint;

  double x;
  double y;
  double width;
  double height;
  double rotation;

  int characterSpritesIndex = 0;
  int flutterFrame = 0;

  double collisionToleranceX;
  double collisionToleranceY;

  double movementSpeed;

  @override
  void render(Canvas c) {
    paint = Paint();
    // Transparent bounding box
    paint.color = Color(0x00000000);
    rect = Rect.fromLTWH(0, 0, width, height);
    c.save();
    c.translate(x, y);
    c.translate(width / 2, height / 2);
    c.rotate(rotation);
    c.translate(-width / 2, -height / 2);
    c.drawRect(rect, paint);
    characterSprites[characterSpritesIndex][flutterFrame]
        .renderRect(c, rect.inflate(0));
    c.restore();
  }
}
