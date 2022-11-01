import 'dart:async';

import 'package:finance_planner/AccountWidget.dart';
import 'package:finance_planner/AccountsApi.dart';
import 'package:flutter/material.dart';

import 'Account.dart';

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
        body: const Center(child: AccountWidget()),
      ),
    );
  }
}
