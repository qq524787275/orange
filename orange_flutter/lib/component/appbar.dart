import 'package:flutter/material.dart';

class OrangeAppBar extends StatefulWidget implements PreferredSizeWidget {
  final Widget title;
  final Color color;

  const OrangeAppBar({Key key, this.title, this.color = Colors.white})
      : super(key: key);

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
        color: widget.color,
        child: Padding(
            padding: EdgeInsets.fromLTRB(15, 12, 0, 12), child: widget.title));
  }
}
