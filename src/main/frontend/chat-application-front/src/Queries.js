import axios from 'axios'

const registerUrl = 'http://localhost:8081/api/v1/registration/register'
const loginUrl = 'http://localhost:8081/api/v1/authentication/login'
const getAllUsersUrl = 'http://localhost:8081/api/v1/users'
const getReceiverUrl = 'http://localhost:8081/api/v1/users/'
const getChatUrl = 'http://localhost:8081/api/v1/chats'

export const doRegister = (data, setStatus) => {
    const register = axios.post(registerUrl, data)

    return register.then(() => {
        setStatus(true)
    })
        .catch(() => alert('Something went wrong'))
}

export const doLogin = (data, setStatus, setUsername) => {
    const login = axios.post(loginUrl, data)

    return login.then(response => {
        localStorage.setItem('token', response.data['token'])
        localStorage.setItem('refreshToken', response.data['refreshToken'])
        setUsername(response.data['username'])
        setStatus(true)
    })
        .catch(() => alert('Something went wrong'))
}

export const doGetAllUsers = () => {
    return axios.get(getAllUsersUrl, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
        }
    })
}

export const doGetReceiver = (username) => {
    return axios.get(getReceiverUrl + username, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
        }
    })
}

export const doGetChat = (member1, member2) => {
    return axios.get(getChatUrl + '/' + member1 + '/' +member2, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
        }
    })
}