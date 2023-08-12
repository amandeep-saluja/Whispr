import React from 'react';
import './Login.css';
import useAllUsers from '../../hooks/useAllUsers';

const Login = ({ setUser }) => {
    const users = useAllUsers();

    return (
        <div className="login-container">
            <div className="login-box">
                <select
                    className="existing-user"
                    onChange={(event) => {
                        const selectedOption =
                            event.target.options[event.target.selectedIndex];
                        const customDataValue =
                            selectedOption.getAttribute('data-user');
                        //console.log(customDataValue);
                        //console.log(users.filter((u) => u.id === customDataValue)[0]);
                        setUser(
                            users.filter((u) => u.id === customDataValue)[0]
                        );
                    }}
                    data-user="select"
                >
                    <option value={'-1'}>Select a User</option>
                    {users?.map((user) => (
                        <option key={user?.id} data-user={user?.id}>
                            {user?.name}
                        </option>
                    ))}
                </select>
                <button className="new-user">New User</button>
            </div>
        </div>
    );
};

export default Login;
