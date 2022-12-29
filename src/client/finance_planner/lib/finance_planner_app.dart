import 'package:finance_planner/accounts_api.dart';
import 'package:finance_planner/login_widget.dart';
import 'package:flutter/material.dart';

import 'accounts_list_widget.dart';

class FinancePlannerApp extends StatelessWidget {
  FinancePlannerApp({super.key});

  final AccountsApi accountsApi = AccountsApi();

  // @override
  // State<FinancePlannerApp> createState() => _FinancePlannerAppState();
  void _onUserChange(userId) {
    print('new: $userId');
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
              child: Column(
            children: [
              AccountsListWidget(
                  accountsApi: accountsApi),
            ],
          )),
        ));
  }
}
//
// class _FinancePlannerAppState extends State<FinancePlannerApp> {
//   String theUserId = '';
//
//   @override
//   void initState() {
//     super.initState();
//     print('app init state');
//   }
//
//   void _onUserChange(userId) {
//     print('new: $userId');
//     print('current $theUserId');
//
//     setState(() {
//       theUserId = userId;
//     });
//
//     theUserId = userId;
//   }
//
//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//         title: 'Finance Planner',
//         theme: ThemeData(
//           primarySwatch: Colors.blue,
//         ),
//         home: Scaffold(
//           appBar: AppBar(
//             title: const Text('Finance Planner'),
//           ),
//           body: Center(
//               child: Column(
//             children: [
//               LoginWidget(onUserChange: _onUserChange),
//               AccountsListWidget(userId: theUserId),
//             ],
//           )),
//         ));
//   }
// }
