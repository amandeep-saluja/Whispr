import React from 'react';
import './Chats.css';
import USER from '../../assets/man.svg';

const Chats = ({ user }) => {
    return (
        <div className="chats-container">
            <div className="chat-row opened">
                {/* <div className="border-top"></div> */}
                <img className="chat-user-icon" src={USER} />
                <div className="chat-user-box">
                    <div className="user-mid">
                        <div className="chat-user-name">Rahul Kumar</div>
                        <div className="chat-user-last-msg">How are you?</div>
                    </div>
                    <div className="chat-last-msg-timestamp">Yesterday</div>
                </div>
            </div>
            {Array(20)
                .fill(1)
                .map((row, index) => (
                    <div className="chat-row" key={index}>
                        {/* <div className="border-top"></div> */}
                        <img className="chat-user-icon" src={USER} />
                        <div className="chat-user-box">
                            <div className="user-mid">
                                <div className="chat-user-name">
                                    Rahul Kumar
                                </div>
                                <div className="chat-user-last-msg">
                                    How are you?
                                </div>
                            </div>
                            <div className="chat-last-msg-timestamp">
                                Yesterday
                            </div>
                        </div>
                    </div>
                ))}
        </div>
    );
};

export default Chats;
