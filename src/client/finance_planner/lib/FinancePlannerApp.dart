import 'package:flutter/material.dart';

import 'AccountsListWidget.dart';

class FinancePlannerApp extends StatefulWidget {
  const FinancePlannerApp({super.key});

  @override
  State<FinancePlannerApp> createState() => _FinancePlannerAppState();
}

class _FinancePlannerAppState extends State<FinancePlannerApp> {
  @override
  void initState() {
    super.initState();
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
        body: const Center(child: AccountsListWidget()),
      ),
    );
  }
}
