import React from "react";
import {ActionType} from "@/components/home/Table";

const EmployeeInterfaceActionsButtons: React.FC<{ onClick: (actionType: ActionType | null) => void; }> = ({ onClick }) => {

    return (
        <td className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
            <button
                className="action_btn_red"
                onClick={() => onClick("deleteShipment")}
            >
                Delete
            </button>

            <button
                className="action_btn_blue"
                onClick={() => onClick("editShipment")}
            >
                Edit
            </button>
        </td>
    )
}

export default EmployeeInterfaceActionsButtons;