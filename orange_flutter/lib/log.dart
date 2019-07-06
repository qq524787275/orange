import 'package:flutter/services.dart';

class Log {
  static const perform = const MethodChannel("android_log");

  static void i(String tag, String message) {
    perform.invokeMethod('logI', {'tag': tag, 'msg': message});
  }
}
