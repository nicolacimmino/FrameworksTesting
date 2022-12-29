import 'package:finance_planner/accounts_api.dart';
import 'package:flutter/material.dart';

import 'accounts_list_widget.dart';

class FinancePlannerApp extends StatelessWidget {
  FinancePlannerApp({super.key});

  final AccountsApi accountsApi = AccountsApi();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Finance Planner',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: Scaffold(
          appBar: AppBar(
            title: const Text('Finance Planner'),
          ),
          body: Center(
              child: Column(
            children: [
              AccountsListWidget(accountsApi: accountsApi),
            ],
          )),
        ));
  }
}
