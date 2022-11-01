import 'package:flutter/material.dart';

import 'Account.dart';
import 'AccountsApi.dart';

class AccountWidget extends StatefulWidget {
  const AccountWidget({super.key});

  @override
  State<AccountWidget> createState() => _AccountWidgetState();
}

class _AccountWidgetState extends State<AccountWidget> {
  late Future<Account> futureAccount;

  @override
  void initState() {
    super.initState();
    futureAccount = AccountsApi.fetchAccount();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Account>(
      future: futureAccount,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          return Text(snapshot.data!.name);
        } else if (snapshot.hasError) {
          return Text('${snapshot.error}');
        }

        // By default, show a loading spinner.
        return const CircularProgressIndicator();
      },
    );
  }
}
