import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:orange_flutter/style.dart';

import 'appbar.dart';

class CacheScaffold extends StatefulWidget {
  @override
  _CacheScaffoldState createState() => _CacheScaffoldState();
}

class _CacheScaffoldState extends State<CacheScaffold> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: contentColor,
      appBar: OrangeAppBar(
        title: Text(
          "清理缓存",
          style: TextStyle(fontWeight: FontWeight.bold,fontSize: 18),
        ),
      ),
      body: Text("我是内容"),
    );
  }
}
