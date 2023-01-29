import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '30s', target: 20 },
        { duration: '1m30s', target: 10 },
        { duration: '20s', target: 0 },
    ],
};

export default function () {

    let data = { numeroCartao: '6549873025634501', senha: '1234', valor: 0.02 };

    const res = http.post('http://localhost:8080/transacoes', JSON.stringify(data), {
        headers: { 'Content-Type': 'application/json' },
    });
    
    check(res, { 'status was 200': (r) => r.status == 201 });
    
    sleep(1);
}