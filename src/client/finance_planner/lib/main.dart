import 'dart:async';

import 'package:finance_planner/AccountsApi.dart';
import 'package:flutter/material.dart';

import 'Account.dart';

void main() => runApp(const FinancePlannerApp());

class FinancePlannerApp extends StatefulWidget {
  const FinancePlannerApp({super.key});

  @override
  State<FinancePlannerApp> createState() => _FinancePlannerAppState();
}

class _FinancePlannerAppState extends State<FinancePlannerApp> {
  late Future<Account> futureAccount;

  @override
  void initState() {
    super.initState();
    futureAccount = AccountsApi.fetchAccount();
  }

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
          child: FutureBuilder<Account>(
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
          ),
        ),
      ),
    );
  }
}
