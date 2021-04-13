import React, {useState, createContext} from 'react'

export const UserContext = createContext();

export const UserProvider = props => {
    const [token, setToken] = useState("");
    const [isAuthenticated, setIsAuthenticated] = useState(false)
    const [username, setUsername] = useState("");

    return (
        <UserContext.Provider value={{
            credential: [token, setToken],
            logged: [isAuthenticated, setIsAuthenticated],
            user: [username, setUsername]
        }}>
            {props.children}
        </UserContext.Provider>
    )
}