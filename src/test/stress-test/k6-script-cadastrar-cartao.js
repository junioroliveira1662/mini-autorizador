import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomString } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

export const options = {
    stages: [
        { duration: '30s', target: 10 },
        { duration: '30s', target: 25 },
        { duration: '1m0s', target: 15 },
        { duration: '20s', target: 0 },
    ],
};

export default function () {

    const randomNumeroCartao = randomString(16, '0123456789');

    const randomSenha = randomString(4, '0123456789');

    let data = { numeroCartao: randomNumeroCartao, senha: randomSenha };

    const res = http.post('http://localhost:8080/cartoes', JSON.stringify(data), {
        headers: { 'Content-Type': 'application/json' },
    });
    
    check(res, { 'status was 200': (r) => r.status == 201 });
    
    sleep(1);
}