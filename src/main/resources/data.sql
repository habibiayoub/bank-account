INSERT INTO account (account_number, type, balance, is_overdraft_authorised, overdraft_amount, deposite_limit)
VALUES ( 'ACCOUNT-0001', 'CHECKING_ACCOUNT', 2600.82, true, 500, 0 );
INSERT INTO account (account_number, type, balance, is_overdraft_authorised, overdraft_amount, deposite_limit)
VALUES ( 'ACCOUNT-0002', 'CHECKING_ACCOUNT', 1200, false, 0, 0 );
INSERT INTO account (account_number, type, balance, is_overdraft_authorised, overdraft_amount, deposite_limit)
VALUES ( 'ACCOUNT-0003', 'SAVINGS_ACCOUNT', 30000, false, 0, 100000 );

INSERT INTO transaction (type, amount, date, account_id) VALUES ( 'DEBIT', 120, '2024-05-24 11:23:56',  1);
INSERT INTO transaction (type, amount, date, account_id) VALUES ( 'DEBIT', 5340, '2024-05-30 11:23:56',  1);
INSERT INTO transaction (type, amount, date, account_id) VALUES ( 'CREDIT', 1200, '2024-05-17 11:23:56',  1);
INSERT INTO transaction (type, amount, date, account_id) VALUES ( 'CREDIT', 786, '2024-05-06 11:23:56',  1);
INSERT INTO transaction (type, amount, date, account_id) VALUES ( 'DEBIT', 900, '2024-05-06 12:23:56',  1);
INSERT INTO transaction (type, amount, date, account_id) VALUES ( 'DEBIT', 20, '2024-04-30 11:23:56',  1);
INSERT INTO transaction (type, amount, date, account_id) VALUES ( 'DEBIT', 76, '2024-04-23 11:23:56',  1);
INSERT INTO transaction (type, amount, date, account_id) VALUES ( 'CREDIT', 250, '2024-04-12 11:23:56',  1);
INSERT INTO transaction (type, amount, date, account_id) VALUES ( 'CREDIT', 620, '2024-04-02 11:23:56',  1);
INSERT INTO transaction (type, amount, date, account_id) VALUES ( 'DEBIT', 1000, '2024-03-24 11:23:56',  1);
INSERT INTO transaction (type, amount, date, account_id) VALUES ( 'CREDIT', 388, '2024-03-22 11:23:56',  1);