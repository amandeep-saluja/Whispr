import { DELETE_CHAT } from '../Constants';

export const deleteActiveChat = (chatId) => {
    return fetch(DELETE_CHAT + chatId, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then((response) => {
            if (response.status == '200') return response.json();
            return response;
        })
        .catch((err) => console.error(err));
};
