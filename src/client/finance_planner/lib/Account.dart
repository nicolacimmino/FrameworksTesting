class Account {
  final String name;
  final String id;
  final String currency;

  const Account({
    required this.name,
    required this.id,
    required this.currency,
  });

  factory Account.fromJson(Map<String, dynamic> json) {
    return Account(
      name: json['name'],
      id: json['id'],
      currency: json['currency'],
    );
  }
}
