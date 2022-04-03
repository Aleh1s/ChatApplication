import React from 'react';

const UserMessage = ({message}) => {

    return (
        <div>
            <div>
                <p style={{color: 'red'}}>{message.sender}: {message.text}</p>
            </div>
        </div>
    );
};

export default UserMessage;