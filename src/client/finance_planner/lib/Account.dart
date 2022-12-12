
class Account {
  final String name;
  final String id;
  final String currency;
  final double balance;

  const Account(
      {required this.name,
      required this.id,
      required this.currency,
      required this.balance});

  factory Account.fromJson(Map<String, dynamic> json) {
    return Account(
      name: json['name'],
      id: json['id'],
      currency: json['currency'],
      balance: json['balance'],
    );
  }

  static listFromJson(List<dynamic> json) {
    var accounts = <Account>[];

    for (var element in json) {
      accounts.add(Account(
        name: element['name'],
        id: element['id'],
        currency: element['currency'],
        balance: element['balance'],
      ));
    }

    return accounts;
  }
}
