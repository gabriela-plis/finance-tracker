db = db.getSiblingDB('finance_tracker');

db.createCollection('users');
db.createCollection('categories');
db.createCollection('reports');
db.createCollection('expenses');
db.createCollection('incomes');

db.reports.insertMany([
    {
        _id: ObjectId('64f9b129a816597d71107b31'),
        name: 'GENERAL_WEEKLY_REPORT',
        subscribers: [
            {
                '$ref': 'users',
                '$id': ObjectId('64f9b13562a3f2b861109ad5')
            },
            {
                '$ref': 'users',
                '$id': ObjectId('64f9b13ac64e798011c90510')
            }
        ]
    },
    {
        _id: ObjectId('64f9b12f9f0142bc14531fd8'),
        name: 'GENERAL_MONTHLY_REPORT',
        subscribers: [
            {
                '$ref': 'users',
                '$id': ObjectId('64f9b13562a3f2b861109ad5')
            },
            {
                '$ref': 'users',
                '$id': ObjectId('64f9b13ac64e798011c90510')
            }
        ]
    }
]);

db.users.insertMany([
    {
        _id: ObjectId('64f9b13562a3f2b861109ad5'),
        username: 'johnny',
        email: 'johnny@gmail.com',
        password: '$2a$10$VYN1tawzr/GAG3BhogCtz.PAFOkTLqinxPUx5O.8NwmDi/fcYslXm',
        roles: ['USER']
    },
    {
        _id: ObjectId('64f9b13ac64e798011c90510'),
        username: 'sara',
        email: 'sara@gmail.com',
        password: '$2a$10$dPs.H5gRZV9fxefz/pNBge0ZtOyyH0640tZAK1EvOrBuQYQy/NaEG',
        roles: ['USER', 'ADMIN']
    },
]);


db.categories.insertMany([
    {
        _id: ObjectId('64f9b0ffa9cc8537d26f7e08'),
        name: 'Healthcare',
        users: [
            {
                '$ref': 'users',
                '$id': ObjectId('64f9b13562a3f2b861109ad5')
            },
            {
                '$ref': 'users',
                '$id': ObjectId('64f9b13ac64e798011c90510')
            }
        ]
    },
    {
        _id: ObjectId('64f9b11013ef5c4f934ef930'),
        name: 'Transportation',
        users: [
            {
                '$ref': 'users',
                '$id': ObjectId('64f9b13562a3f2b861109ad5')
            },
            {
                '$ref': 'users',
                '$id': ObjectId('64f9b13ac64e798011c90510')
            }
        ]
    },
    {
        _id: ObjectId('64f9b120e742144ae863c1c1'),
        name: 'Food',
        users: [
            {
                '$ref': 'users',
                '$id': ObjectId('64f9b13562a3f2b861109ad5')
            },
            {
                '$ref': 'users',
                '$id': ObjectId('64f9b13ac64e798011c90510')
            }
        ]
    }
]);

db.expenses.insertMany([
    {
        _id: ObjectId('64f9b14002a68b254868043f'),
        date: ISODate('2023-08-02'),
        category: {
            '$ref': 'categories',
            '$id': ObjectId('64f9b11013ef5c4f934ef930')
        },
        price: Decimal128('400.50'),
        user: {
            '$ref': 'users',
            '$id': ObjectId('64f9b13562a3f2b861109ad5')
        }
    },
    {
        _id: ObjectId('64f9c2776ebce83684622190'),
        date: ISODate('2023-08-07'),
        category: {
            '$ref': 'categories',
            '$id': ObjectId('64f9b120e742144ae863c1c1')
        },
        price: Decimal128('112.38'),
        user: {
            '$ref': 'users',
            '$id': ObjectId('64f9b13562a3f2b861109ad5')
        }
    },
    {
        _id: ObjectId('64f9c2856a1772c01f01a04b'),
        date: ISODate('2023-08-12'),
        category: {
            '$ref': 'categories',
            '$id': ObjectId('64f9b120e742144ae863c1c1')
        },
        price: Decimal128('15.26'),
        user: {
            '$ref': 'users',
            '$id': ObjectId('64f9b13562a3f2b861109ad5')
        }
    },
    {
        _id: ObjectId('64f9c28b09bd730abab08d8e'),
        date: ISODate('2023-08-16'),
        category: {
            '$ref': 'categories',
            '$id': ObjectId('64f9b0ffa9cc8537d26f7e08')
        },
        price: Decimal128('231.88'),
        user: {
            '$ref': 'users',
            '$id': ObjectId('64f9b13562a3f2b861109ad5')
        }
    },
    {
        _id: ObjectId('64f9c292cc41325ee8b7c485'),
        date: ISODate('2023-08-18'),
        category: {
            '$ref': 'categories',
            '$id': ObjectId('64f9b120e742144ae863c1c1')
        },
        price: Decimal128('80'),
        user: {
            '$ref': 'users',
            '$id': ObjectId('64f9b13562a3f2b861109ad5')
        }
    }
]);

db.incomes.insertMany([
    {
        _id: ObjectId('64f9c29ae8f265654418d179'),
        date: ISODate('2023-08-05'),
        amount: Decimal128('1200.50'),
        description: 'job',
        user: {
            '$ref': 'users',
            '$id': ObjectId('64f9b13562a3f2b861109ad5')
        }
    }
]);