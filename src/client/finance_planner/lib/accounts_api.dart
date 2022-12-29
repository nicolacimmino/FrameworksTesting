import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

import 'account.dart';

class AccountsApi {
  String jwt = '';

  String userId = '';

  bool isUserAuthenticated() {
    return jwt != '' && userId != '';
  }

  Future<String> login(String email, String password) async {
    var response = await http.post(
      Uri.parse('http://localhost:8081/api/tokens'),
      headers: {HttpHeaders.contentTypeHeader: "application/json"},
      body: '{"email": "$email","password": "$password"}',
    );

    if (response.statusCode == 201) {
      var responseBody = jsonDecode(response.body);
      jwt = responseBody['token'];
      userId = responseBody['user_id'];

      return userId;
    } else {
      throw Exception('Failed to login');
    }
  }

  void logout() {
    jwt = '';
    userId = '';
  }

  Future<List<Account>> fetchAccounts() async {
    var response = await http.get(
      Uri.parse('http://localhost:8080/api/users/$userId/accounts'),
      headers: {
        HttpHeaders.authorizationHeader: 'Bearer $jwt',
      },
    );

    if (response.statusCode == 200) {
      return Account.listFromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to load accounts');
    }
  }
}
