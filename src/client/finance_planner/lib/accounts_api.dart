import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

import 'account.dart';

class AccountsApi {
  Future<String> getJwt() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString('jwt') ?? '';
  }

  setJWT(String jwt) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setString('jwt', jwt);
  }

  Future<String> getUserId() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString('userid') ?? '';
  }

  setUserId(String userId) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setString('userid', userId);
  }

  Future<String> login(String email, String password) async {
    var response = await http.post(
      Uri.parse('http://localhost:8081/api/tokens'),
      headers: {HttpHeaders.contentTypeHeader: "application/json"},
      body: '{"email": "$email","password": "$password"}',
    );

    if (response.statusCode == 201) {
      var responseBody = jsonDecode(response.body);
      await setJWT(responseBody['token']);
      await setUserId(responseBody['user_id']);

      return getUserId();
    } else {
      throw Exception('Failed to login');
    }
  }

  Future<void> logout() async {
    await setUserId('');
    await setJWT('');
  }

  Future<List<Account>> fetchAccounts() async {
    var response = await http.get(
      Uri.parse(
          'http://localhost:8080/api/users/${await getUserId()}/accounts'),
      headers: {
        HttpHeaders.authorizationHeader: 'Bearer ${await getJwt()}',
      },
    );

    if (response.statusCode == 200) {
      return Account.listFromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to load accounts');
    }
  }
}
