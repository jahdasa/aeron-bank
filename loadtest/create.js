import http from 'k6/http';
import { check } from 'k6';

export let options = {
    vus: 1, // Number of virtual users
    duration: '50s', // Duration of the test
};

export default function () {
    let url = 'http://10.1.116.3:8080/bank/api/account/create';
    let payload = ''; // Empty payload as per your curl command

    let params = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded', // Assuming default content-type
        },
    };

    let response = http.post(url, payload, params);

    // Basic check to validate the response
    check(response, {
        'is status 200': (r) => r.status === 200,
    });
}