import 'package:flutter/material.dart';

import 'Account.dart';

class AccountWidget extends StatefulWidget {
  final Account account;

  const AccountWidget({Key? key, required this.account}) : super(key: key);

  @override
  State<AccountWidget> createState() => _AccountWidgetState();
}

class _AccountWidgetState extends State<AccountWidget> {
  @override
  Widget build(BuildContext context) {
    return Container(
        decoration: const BoxDecoration(
            color: Colors.blue,
            borderRadius: BorderRadius.all(Radius.circular(10))),
        width: 200,
        padding: const EdgeInsets.all(20),
        child: Column(
          children: [
            Text(widget.account.name),
            Text(widget.account.currency),
          ],
        ));
  }
}
