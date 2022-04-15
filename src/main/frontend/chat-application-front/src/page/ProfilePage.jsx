import React, {useEffect, useState} from 'react';
import {doGetUserProfile, doPostProfileImage, doUpdateProfile} from "../Queries";
import {Button, Col, Container, Form, FormControl, Image, InputGroup, ListGroup, Row} from "react-bootstrap";

const ProfilePage = () => {

    const [file, setFile] = useState(null)
    const [userData, setUserData] = useState({})
    const [localUserData, setLocalUserData] = useState({
        description: '',
        phoneNumber: '',
        gender: null
    })

    useEffect(() => {
        // doProfileImageExists()
        //     .then(response => console.log(response))
        //     .catch(err => console.log(err))
        doGetUserProfile()
            .then(response => setUserData(response.data))
            .catch(err => console.log(err))
    }, [])

    const addProfileImage = () => {
        const imageData = new FormData()
        imageData.append('file', file)
        doPostProfileImage(imageData)
            .then(response => console.log(response))
            .catch(err => console.log(err))
    }


    const updateProfile = () => {
        doUpdateProfile(localUserData)
            .then(response => {
                setUserData({
                    ...userData,
                    description: response.data.description,
                    phoneNumber: response.data.phoneNumber,
                    gender: response.data.gender
                })
            })
            .catch(err => console.log(err))
    }


    return (
        <Container>
            <Row className={'justify-content-md-center'}>
                <Col lg={3} className='p-5 m-auto shadow-sm rounded-lg align-items-center'>
                    <div align={'center'}>
                        <Image
                            roundedCircle={true}
                            style={{width: '150px', height: '150px'}}
                            src={`http://localhost:8081/api/v1/users/profile/image/${localStorage.getItem('username')}`}/>
                        <h1>{userData.username}</h1>
                        <small>{userData.description !== null
                            ? userData.description
                            :   <InputGroup>
                                <FormControl
                                    as="textarea"
                                    aria-label="With textarea"
                                    placeholder={'Write about yourself'}
                                    onChange={event => setLocalUserData({...localUserData, description: event.target.value})}/>
                                <Button onClick={() => updateProfile()} variant={"outline-primary"}>Post</Button>
                            </InputGroup>}</small>
                    </div>
                </Col>
                <Col className='p-5 m-auto shadow-sm rounded-lg'>
                    <ListGroup variant="flush">
                        <ListGroup.Item>{userData.email}</ListGroup.Item>
                        <ListGroup.Item>{userData.phoneNumber !== null
                            ? userData.phoneNumber
                            : <InputGroup>
                                <FormControl
                                    aria-label="Example text with button addon"
                                    aria-describedby="basic-addon1"
                                    placeholder={'Write phone number'}
                                    onChange={event => setLocalUserData({...localUserData, phoneNumber: event.target.value})}
                                />
                            </InputGroup>}</ListGroup.Item>
                        <ListGroup.Item>{userData.gender !== null
                            ? userData.gender
                            : <Form.Select
                                aria-label="Gender"
                                onChange={event => setLocalUserData({...localUserData, gender: event.target.value})}>
                                <option disabled>Choose gender</option>
                                <option value="MALE">MALE</option>
                                <option value="FEMALE">FEMALE</option>
                            </Form.Select>
                        }</ListGroup.Item>
                        <Button variant={'outline-primary'} onClick={() => updateProfile()}>Save</Button>
                    </ListGroup>
                    {/*<label>Change profile image</label>*/}
                    {/*<input id={'file-input'} type={'file'} name={'file1'} placeholder={'Put here'} onChange={event => setFile(event.target.files[0])}/>*/}
                    {/*<button onClick={() => addProfileImage()}>Set image</button>*/}
                </Col>
            </Row>
        </Container>
    );
};

export default ProfilePage;