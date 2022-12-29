import 'package:finance_planner/accounts_api.dart';
import 'package:flutter/material.dart';

class LoginWidget extends StatefulWidget {
  final Function(String userId) onUserChange;

  const LoginWidget({super.key, required this.onUserChange});

  @override
  State<LoginWidget> createState() => _LoginWidgetState();
}

class _LoginWidgetState extends State<LoginWidget> {
  final userEmailController = TextEditingController();
  final userPasswordController = TextEditingController();

  @override
  void initState() {
    super.initState();
  }

  _login() async {
    AccountsApi.logout();
    await widget.onUserChange('');
    await widget.onUserChange(await AccountsApi.login(userEmailController.text, userPasswordController.text));
  }

  @override
  Widget build(BuildContext context) {
    return Column(children: [
      TextField(
        controller: userEmailController,
        decoration: const InputDecoration(
            border: OutlineInputBorder(),
            labelText: 'User Name',
            hintText: 'Enter your email'),
      ),
      TextField(
        controller: userPasswordController,
        obscureText: true,
        decoration: const InputDecoration(
            border: OutlineInputBorder(),
            labelText: 'Password',
            hintText: 'Enter your password'),
      ),
      Container(
        height: 50,
        width: 250,
        decoration: BoxDecoration(
            color: Colors.blue, borderRadius: BorderRadius.circular(20)),
        child: TextButton(
          onPressed: () async {
            await _login();
          },
          child: const Text(
            'Login',
            style: TextStyle(color: Colors.white, fontSize: 25),
          ),
        ),
      ),
    ]);
  }
}
