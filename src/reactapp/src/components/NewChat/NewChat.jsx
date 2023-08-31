import './NewChat.css';
import USER from '../../assets/man.svg';
import BACK from '../../assets/white-back.png';
import { NEW_CHAT } from '../../Constants';
import { useDispatch, useSelector } from 'react-redux';
import { addChatId } from '../../store/userSlice';
import { addNewChat } from '../../store/chatSlice';

const NewChat = ({ user, setOpenNewChatPage }) => {
    const users = useSelector((store) => store.user.userMap);
    const dispatch = useDispatch();
    const addChatEvent = (newChat) => {
        dispatch(addNewChat(newChat));
        dispatch(addChatId(newChat.id));
    };

    const initiateNewChat = (targetUser) => {
        if (targetUser && user.id) {
            fetch(
                `${NEW_CHAT}?creatorId=${user?.id}&userIds=${user?.id},${targetUser}&groupName=`,
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
                .then((data) => {
                    addChatEvent(data);
                    setOpenNewChatPage(false);
                });
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
                {Object.values(users)
                    ?.filter((u) => u.id != user.id)
                    ?.map((u) => (
                        <div
                            className="new-chat-menu-user-row"
                            key={u?.id}
                            data-user-id={u?.id}
                            onClick={(e) => {
                                e.preventDefault();
                                initiateNewChat(
                                    e.currentTarget.dataset['userId']
                                );
                            }}
                        >
                            <img className="chat-user-icon" src={USER} />
                            <div className="new-chat-menu-row-name">
                                {u?.name}
                            </div>
                        </div>
                    ))}
            </div>
        </div>
    );
};

export default NewChat;
