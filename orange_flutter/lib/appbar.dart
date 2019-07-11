import 'package:flutter/material.dart';

class OrangeAppBar extends StatefulWidget implements PreferredSizeWidget {
  final Widget title;

  const OrangeAppBar({Key key, this.title}) : super(key: key);

  @override
  _OrangeAppBarState createState() => _OrangeAppBarState();

  @override
  Size get preferredSize => new Size.fromHeight(78.0);
}

class _OrangeAppBarState extends State<OrangeAppBar> {
  @override
  Widget build(BuildContext context) {
    return Container(
        padding: EdgeInsets.fromLTRB(0, 22, 0, 0),
        color: Colors.white,
        child: Padding(padding: EdgeInsets.all(15),child: widget.title));
  }
}
