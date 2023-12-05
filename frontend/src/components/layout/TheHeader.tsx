import React from "react";
import { NavLink } from "react-router-dom";

const TheHeader: React.FC = () => {

    return (
        <nav>
            <ul>
                <li>
                    <NavLink 
                        to='/'
                        className={({isActive}) => isActive ? 'active' : undefined}
                        end
                    >
                        Home
                    </NavLink>
                </li>
                <li>
                    <NavLink 
                        to='/auth' 
                        className={({isActive}) => isActive ? 'active' : undefined}
                    >
                        Auth
                    </NavLink>
                </li>
            </ul>
        </nav>
    )
}

export default TheHeader;