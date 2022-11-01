import 'package:finance_planner/AccountWidget.dart';
import 'package:flutter/material.dart';

import 'Account.dart';
import 'AccountsApi.dart';

class AccountsListWidget extends StatefulWidget {
  const AccountsListWidget({super.key});

  @override
  State<AccountsListWidget> createState() => _AccountsListWidgetState();
}

class _AccountsListWidgetState extends State<AccountsListWidget> {
  late Future<List<Account>> futureAccounts;

  @override
  void initState() {
    super.initState();
    futureAccounts = AccountsApi.fetchAccounts();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<Account>>(
      future: futureAccounts,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          return Column(children: [
            for (Account account in snapshot.data!)
              SizedBox(
                  width: 250,
                  height: 150,
                  child: GestureDetector(
                      onTap: () {
                        print("Tapped ${account.name}");
                      },
                      child: AccountWidget(account: account)))
          ]);
        } else if (snapshot.hasError) {
          return Text('${snapshot.error}');
        }

        // By default, show a loading spinner.
        return const CircularProgressIndicator();
      },
    );
  }
}
