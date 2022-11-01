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
    return Column(children: [
      Text(widget.account.name),
      Text(widget.account.currency),
    ]);
  }
}
