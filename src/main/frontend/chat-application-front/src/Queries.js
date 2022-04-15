import axios from 'axios'

const registerUrl = 'http://localhost:8081/api/v1/registration/register'
const loginUrl = 'http://localhost:8081/api/v1/authentication/login'
const getAllUsersUrl = 'http://localhost:8081/api/v1/users'
const getReceiverUrl = 'http://localhost:8081/api/v1/users/'
const getMessagesUrl = 'http://localhost:8081/api/v1/messages'
const getChatUrl = 'http://localhost:8081/api/v1/chats'
const getMessagesByChatIdUrl = 'http://localhost:8081/api/v1/messages'
const getChatsByUsernameUrl = 'http://localhost:8081/api/v1/chats'
const getPagingSortedMessagePageByChatId = 'http://localhost:8081/api/v1/messages/pages/sort/'
const postProfileImageUrl = 'http://localhost:8081/api/v1/users/profile/image/'
const profileImageExistsUrl = 'http://localhost:8081/api/v1/users/profile/image/status/'
const getUserProfileUrl = 'http://localhost:8081/api/v1/users/profile/'
const postUpdateProfile = 'http://localhost:8081/api/v1/users/profile/'

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

export const doGetMessages = (member1, member2) => {
    return axios.get(getMessagesUrl + '/' + member1 + '/' +member2, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
        }
    })
}

export const doGetChat = (member1, member2) => {
    return axios.get(getChatUrl + '/' + member1 + '/' + member2, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
        }
    })
}

export const doGetMessagesByChatId = (id) => {
    return axios.get(getMessagesByChatIdUrl + '/' + id, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
        }
    })
}

export const doGetChatsByUsername = (username) => {
    return axios.get(getChatsByUsernameUrl + '/' + username, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token'),
            'Access-Control-Allow-Origin': '*'
        }
    })
}

export const doGetPagingSortedMessagePageByChatId = (chatId) => {
    return axios.get(getPagingSortedMessagePageByChatId + chatId, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
        }
    })
}

export const doPostProfileImage = (imageData) => {
    return axios.post(postProfileImageUrl + localStorage.getItem('username'), imageData, {
        headers: {
            'Authorization': localStorage.getItem('token')
        }
    })
}

export const doProfileImageExists = () => {
    return axios.get(profileImageExistsUrl + localStorage.getItem('username'), {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
        }
    })
}

export const doGetUserProfile = () => {
    return axios.get(getUserProfileUrl + localStorage.getItem('username'), {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
        }
    })
}

export const doUpdateProfile = (data) => {
    return axios.post(postUpdateProfile + localStorage.getItem('username'), data, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
        }
    })
}