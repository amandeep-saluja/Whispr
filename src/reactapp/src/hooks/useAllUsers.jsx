import { useEffect, useState } from 'react';
import { BASE_URL, USERS_ALL } from '../Constants';

const useAllUsers = () => {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        fetchAllUsers();
    }, []);

    const fetchAllUsers = () => {
        fetch(BASE_URL + USERS_ALL)
            .then((response) => response.json())
            .catch((err) => console.error(err))
            .then((data) => setUsers(data));
    };
    return users;
};

export default useAllUsers;
