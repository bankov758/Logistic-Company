import React from "react";

const EmployeeInterfaceActionsButtons: React.FC<{ onClick: () => void; id: number | string; }> = ({ onClick, id }) => {

    return (
        <td key={id}
            className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
            <button
                className="action_btn_red"
            >
                Delete
            </button>

            <button
                className="action_btn_blue"
                onClick={onClick}
            >
                Edit
            </button>
        </td>
    )
}

export default EmployeeInterfaceActionsButtons;