import './NewChat.css';
import USER from '../../assets/man.svg';
import BACK from '../../assets/white-back.png';
import { useEffect } from 'react';
import { BASE_URL, NEW_CHAT } from '../../Constants';

const NewChat = ({ id, users, setOpenNewChatPage, user, setUser }) => {
    useEffect(() => {}, []);

    const initiateNewChat = (targetUser) => {
        if (targetUser) {
            let chats = user?.chats;
            fetch(
                `${BASE_URL}${NEW_CHAT}?userIds=${id},${targetUser}&groupName=`,
                {
                    method: 'POST',
                    headers: {
                        Accept: 'application/json',
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                }
            )
                .then((response) => response.json())
                .catch((err) => console.error(err))
                .then((data) =>
                    setUser({
                        ...user,
                        chats: chats.push(data) ? chats : chats,
                    })
                );
        }
    };

    return (
        <div id="newChatMenu" className="new-chat-menu">
            <div className="new-chat-menu-header">
                <img
                    className="new-chat-menu-back-icon icon"
                    src={BACK}
                    onClick={() => setOpenNewChatPage(false)}
                />
                <div className="new-chat-menu-label">New chat</div>
            </div>
            <div className="new-chat-menu-body">
                {users?.map((user) => (
                    <div
                        className="new-chat-menu-user-row"
                        key={user?.id}
                        data-user-id={user?.id}
                        onClick={(e) => {
                            e.preventDefault();
                            initiateNewChat(e.currentTarget.dataset['userId']);
                        }}
                    >
                        <img className="chat-user-icon" src={USER} />
                        <div className="new-chat-menu-row-name">
                            {user?.name}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default NewChat;
