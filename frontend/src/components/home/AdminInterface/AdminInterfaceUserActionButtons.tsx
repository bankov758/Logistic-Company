import React from "react";
import exp from "node:constants";
import {ActionType} from "@/components/home/Table";

type AdminInterfaceUserActionsButtonsProps = {
    onClick: (actionType: ActionType | null) => void;
    disableActions: boolean
}

const AdminInterfaceUserActionsButtons: React.FC<AdminInterfaceUserActionsButtonsProps> = ({ onClick, disableActions = false }) => {
    return (
        <td className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
            <button
                className="action_btn_red"
                onClick={() => onClick("deleteUser")}
            >
                Delete
            </button>

            <button
                className="action_btn_green"
                onClick={() => onClick("promoteUserIntoEmployee")}
                disabled={disableActions}
            >
                Make employee
            </button>
            <button
                className="action_btn_green"
                onClick={() => onClick("promoteUserIntoCourier")}
                disabled={disableActions}
            >
                Make courier
            </button>
        </td>
    )
}
export default AdminInterfaceUserActionsButtons;