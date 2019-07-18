import 'package:flutter/material.dart';

class ColorGlobal with ChangeNotifier {
  static final ColorGlobal _colorGlobal = ColorGlobal._internal();

  ColorGlobal._internal();

  factory ColorGlobal() {
    return _colorGlobal;
  }

  var _isDark = false;
  var _colorPrimary = Colors.indigo;
  var _contentBackground = Color(0xFFFFFFFF);
  var _windowBackground = Color(0xFFEAEAEA);
  var _textColorPrimary = Color(0xFF212121);
  var _textColorSecond = Color(0xFF727272);
  var _pageBackground = Color(0xF6F6F7FF);

  get isDark => _isDark;

  set isDark(value) {
    _isDark = value;
    if (_isDark) {
      contentBackground = Color(0xFF333333);
      windowBackground = Color(0xFF212121);
      textColorPrimary = Color(0xFFffffff);
      textColorSecond = Color(0xFF727272);
      pageBackground = Color(0xFF333333);
    } else {
      contentBackground = Color(0xFFFFFFFF);
      windowBackground = Color(0xFFEAEAEA);
      textColorPrimary = Color(0xFF212121);
      textColorSecond = Color(0xFF727272);
      pageBackground = Color(0xF6F6F7FF);
    }
  }

  get colorPrimary => _colorPrimary;

  set colorPrimary(value) {
    _colorPrimary = value;
    notifyListeners();
  }

  get contentBackground => _contentBackground;

  set contentBackground(value) {
    _contentBackground = value;
    notifyListeners();
  }

  get pageBackground => _pageBackground;

  set pageBackground(value) {
    _pageBackground = value;
    notifyListeners();
  }

  get windowBackground => _windowBackground;

  set windowBackground(value) {
    _windowBackground = value;
    notifyListeners();
  }

  get textColorPrimary => _textColorPrimary;

  set textColorPrimary(value) {
    _textColorPrimary = value;
    notifyListeners();
  }

  get textColorSecond => _textColorSecond;

  set textColorSecond(value) {
    _textColorSecond = value;
    notifyListeners();
  }
}
