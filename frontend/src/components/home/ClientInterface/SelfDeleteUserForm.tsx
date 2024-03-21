import {deleteUser} from "@/lib/actions";
import React from "react";
import {Session} from "@/lib/auth";

const SelfDeleteUserForm: React.FC<{ session: Session | null ,onClick: (setAction: boolean) => void; }> = ({ session, onClick }) => {
    return <>
        <div className="flex gap-x-5 items-center justify-center">
            <h2>Are you sure? </h2>
            <button className="base-btn-blue" onClick={() => deleteUser(session)}>Yes</button>
            <button className="base-btn-blue" onClick={() => onClick(false)} >No</button>
        </div>
    </>
}

export default SelfDeleteUserForm;
