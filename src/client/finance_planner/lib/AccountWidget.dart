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
        decoration: BoxDecoration(
            color: Colors.lightBlueAccent,
            border: Border.all(color: Colors.lightBlue, width: 1),
            borderRadius: const BorderRadius.all(Radius.circular(10))),
        width: 200,
        height: 10,
        padding: const EdgeInsets.only(left: 10, top: 10),
        margin: const EdgeInsets.all(10),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                const Icon(
                  Icons.account_balance_outlined,
                  color: Colors.white,
                  size: 30.0,
                ),
                Text(widget.account.name,
                    style: const TextStyle(color: Colors.white)),
              ],
            ),
            Text('${widget.account.balance} ${widget.account.currency}'),
          ],
        ));
  }
}
