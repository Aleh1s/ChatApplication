import React from 'react';

const MyMessage = ({message}) => {

    return (
        <div>
            <div>
                <p style={{color: 'green'}}>{message.sender}:  {message.text}</p>
            </div>
        </div>
    );
};

export default MyMessage;