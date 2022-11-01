import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

import 'Account.dart';

class AccountsApi {
  static Future<List<Account>> fetchAccounts() async {
    final response = await http.get(
      Uri.parse(
          'http://192.168.8.114:8080/api/users/633aece6e31952335e59a3e5/accounts'),
      headers: {
        HttpHeaders.authorizationHeader:
            'Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJleGFtcGxlLmNvbSIsInN1YiI6IjYzM2FlY2U2ZTMxOTUyMzM1ZTU5YTNlNSIsImV4cCI6MTY2NzM5MzE2MH0.zM7NfkZM-YIHSp6mnOAlnL9LWpX1AAKF7Zy9BcFThi4',
      },
    );

    if (response.statusCode == 200) {
      return Account.listFromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to load accounts');
    }
  }

  static Future<Account> fetchAccount() async {
    final response = await http.get(
      Uri.parse(
          'http://192.168.8.114:8080/api/users/633aece6e31952335e59a3e5/accounts/633b0585f20dd943edde4b30'),
      headers: {
        HttpHeaders.authorizationHeader:
            'Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJleGFtcGxlLmNvbSIsInN1YiI6IjYzM2FlY2U2ZTMxOTUyMzM1ZTU5YTNlNSIsImV4cCI6MTY2NzM5MzE2MH0.zM7NfkZM-YIHSp6mnOAlnL9LWpX1AAKF7Zy9BcFThi4',
      },
    );

    if (response.statusCode == 200) {
      return Account.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to load account');
    }
  }
}
