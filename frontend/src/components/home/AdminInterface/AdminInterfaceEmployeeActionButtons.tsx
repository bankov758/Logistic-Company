import React from "react";
import exp from "node:constants";
import {ActionType} from "@/components/home/Table";

const AdminInterfaceEmployeeActionsButtons: React.FC<{ onClick: (actionType: ActionType | null)=> void; }> = ({ onClick }) => {
    return (
        <td className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
            <button
                className="action_btn_red"
                onClick={() => onClick("deleteEmployee")}

            >
                Delete
            </button>
            <button
                className="action_btn_blue"
                onClick={() => onClick("demoteEmployee")}

            >
                Demote
            </button>
            <button
                className="action_btn_green"
                onClick={() => onClick("makeEmployeeIntoCourier")}

            >
                Make courier
            </button>
        </td>
    )
}
export default AdminInterfaceEmployeeActionsButtons;
