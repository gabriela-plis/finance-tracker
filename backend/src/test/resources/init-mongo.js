db.createUser({
    user: 'admin',
    pwd: 'admin',
    roles: [
        { role: 'readWrite', db: 'database' }
    ]
});

db.createCollection('users');

db.users.insertMany([
    {
        id: '1',
        username: 'anne',
        email: 'anne@gmail.com',
        password: 'anne123',
        roles: ['USER']
    },
    {
        id: '2',
        username: 'matthew',
        email: 'matthew@gmail.com',
        password: 'matthew123',
        roles: ['USER']
    }
]);