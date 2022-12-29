import 'package:finance_planner/account_widget.dart';
import 'package:flutter/material.dart';

import 'account.dart';
import 'accounts_api.dart';
import 'login_widget.dart';

class AccountsListWidget extends StatefulWidget {
  //final String userId;
  final AccountsApi accountsApi;

  const AccountsListWidget({super.key, required this.accountsApi});

  @override
  State<AccountsListWidget> createState() => _AccountsListWidgetState();
}

class _AccountsListWidgetState extends State<AccountsListWidget> {
  late Future<List<Account>> futureAccounts;
  String userId = '';

  @override
  void initState() {
    super.initState();
    print('accounts list widget init state');
    //if (widget.userId != '') {
      //futureAccounts = widget.accountsApi.fetchAccounts();
    //}
  }

  void _onUserChange(String newUserId){
    setState(() {
      futureAccounts = widget.accountsApi.fetchAccounts();
      userId = newUserId;
    });
  }

  @override
  Widget build(BuildContext context) {
    print('accounts list widget build');
    if (userId != '') {
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
                            //print("Tapped ${account.name}");
                          },
                          child: AccountWidget(account: account)))
              ]);
            } else if (snapshot.hasError) {
              return Text('${snapshot.error}');
            }

            return const Text('Loading');
          });
    }

    return LoginWidget(
        onUserChange: _onUserChange, accountsApi: widget.accountsApi);
  }
}
