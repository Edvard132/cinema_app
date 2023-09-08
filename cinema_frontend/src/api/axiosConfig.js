import axios from 'axios';

export default axios.create({
    baseURL:'http://localhost:8080',
    headers:{"ngrok-skip-browser-warning": "true",
    "Content-Type": "application/json"
    
}
});

export const getAuthToken = () => {
    return window.localStorage.getItem("token")
}

export const setAuthToken = (token) => {
    window.localStorage.setItem("token", token)

    setTimeout(() => {
        removeAuthToken(token);
    }, 3600 * 10 * 10 * 10);
}

export const removeAuthToken = () => {
    window.localStorage.removeItem("token")
}

export const request = (method, url, data) => {
    let headers = {};
    if (getAuthToken() !== null && getAuthToken() !== "null"){
        headers = {'Authorization': `Bearer ${getAuthToken()}`
    }
}

return axios.request({
    method : method,
    headers: headers,
    url: url,
    data: data,
});
}