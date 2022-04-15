import React, {useState} from 'react';
import {doGetChat, doGetReceiver} from "../Queries";
import {Button, Card, Col, Container, Form, Row} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

const UsersPage = () => {
    const [query, setQuery] = useState('')
    const [receiver, setReceiver] = useState({
        email: '',
        username: '',
        description: '',
        gender: ''
    })
    const navigate = useNavigate()
    const [searchStatus, setSearchStatus] = useState(false)
    const find = () => {
        doGetReceiver(query)
            .then(response => {
                setReceiver({
                    username: response.data.username,
                    email: response.data.email,
                    description: response.data.description,
                    gender: response.data.gender
                })
                console.log(response)
                setSearchStatus(true)
            })
            .catch(err => console.log(err.message()))
    }

    return (
        <Container>
            <Row>
                <Col lg={5} md={6} sm={12} className={'p-5 m-auto shadow-sm rounded-lg'}>
                    <Form>
                        <Form.Group className="mb-3">
                            <Form.Label>Username</Form.Label>
                            <Form.Control id={'users-search'} type={'text'} placeholder={'Username'} onChange={event => setQuery(event.target.value)}/>
                        </Form.Group>
                            <Button variant={'outline-info'} onClick={() => find()}>Find</Button>
                    </Form>
                </Col>
                <Col lg={5} md={6} sm={12} className={'p-5 m-auto shadow-sm rounded-lg'}>
                    {receiver.username.length !== 0 ?
                        <Card style={{ width: '18rem'}}>
                        <Card.Img variant="top" width={'100px'} height={'180px'} src={`http://localhost:8081/api/v1/users/profile/image/${receiver.username}`} />
                        <Card.Body>
                            <Card.Title>{receiver.username}</Card.Title>
                            <Card.Text>
                                {receiver.description}
                            </Card.Text>
                            <Button variant="outline-success" onClick={() => {
                                doGetChat(localStorage.getItem('username'), receiver.username)
                                    .then(response => {
                                        localStorage.setItem('currentChatId', response.data.id)
                                        localStorage.setItem('receiver', receiver.username)
                                    })
                                    .catch(err => console.log(err))
                                setTimeout(() => navigate('/chat-room'), 300)
                            }}>Start chatting</Button>
                        </Card.Body>
                    </Card>
                        : <h1>Not founded</h1> }
                </Col>
            </Row>
        </Container>
    );
};

export default UsersPage;