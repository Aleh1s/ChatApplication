import React, {useEffect, useState} from 'react';
import {doLogin, doRegister} from "../Queries";
import {useNavigate} from "react-router-dom";
import {Button, Col, Container, Form, Row} from "react-bootstrap";

const LoginPage = () => {
    const navigate = useNavigate()
    const [username, setUsername] = useState('')
    const [status, setStatus] = useState(false)
    const [data, setData] = useState({
        email: '',
        password: ''
    })

    useEffect(() => {
        localStorage.setItem('logStatus', JSON.stringify(status))
        if (status) {
            localStorage.setItem('username', username)
            navigate('/users')
        }
    }, [status])

    return (
        <Container>
            <h1 className={'shadow-sm text-primary mt-5 p-3 text-center rounded'}>Login</h1>
            <Row className={'mt-5'}>
                <Col lg={5} md={6} sm={12} className={'p-5 m-auto shadow-sm rounded-lg'}>
                    <Form>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control type="email" placeholder="Enter email" onChange={event => setData({...data, email: event.target.value})}/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password" onChange={event => setData({...data, password: event.target.value})}/>
                        </Form.Group>
                        <Button variant="outline-primary col-12" onClick={() => doLogin(data, setStatus, setUsername)}>
                            Login
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default LoginPage;