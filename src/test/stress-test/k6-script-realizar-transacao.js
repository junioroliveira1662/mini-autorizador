import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomString } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

export const options = {
    stages: [
        { duration: '30s', target: 20 },
        { duration: '10s', target: 10 },
        { duration: '20s', target: 0 },
    ],
};

export default function () {

    const randomCentavos = randomString(2, '123456789');

    let data = { numeroCartao: '6549873025634501', senha: '1234', valor: '0.' + randomCentavos };

    const res = http.post('http://localhost:8080/transacoes', JSON.stringify(data), {
        headers: { 'Content-Type': 'application/json' },
    });
    
    check(res, { 'status was 200': (r) => r.status == 201 });
    
    sleep(1);
}