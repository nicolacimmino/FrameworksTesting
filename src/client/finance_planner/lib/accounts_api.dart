import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

import 'account.dart';

class AccountsApi {
  static String jwt = '';

  static String userId = '';

  static bool isUserAuthenticated() {
    return jwt != '' && userId !='';
  }

  static Future<List<Account>> fetchAccounts() async {
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

  static Future<Account> fetchAccount() async {
    var response = await http.get(
      Uri.parse(
          'http://localhost:8080/api/users/$userId/accounts/633b0585f20dd943edde4b30'),
      headers: {
        HttpHeaders.authorizationHeader: 'Bearer $jwt',
      },
    );

    if (response.statusCode == 200) {
      return Account.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to load account');
    }
  }

  static void logout() {
    jwt = '';
    userId = '';
  }

  static Future<String> login(String email, String password) async {
    var response = await http.post(
      Uri.parse('http://localhost:8081/api/tokens'),
      headers: {
        HttpHeaders.contentTypeHeader: "application/json"
      },
      body: '{"email": "$email","password": "$password"}',
    );

    if (response.statusCode == 200) {
      var responseBody = jsonDecode(response.body);
      jwt = responseBody['token'];
      userId = responseBody['user_id'];

      return userId;
    } else {
      throw Exception('Failed to login');
    }
  }
}
