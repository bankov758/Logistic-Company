"use client";
import React, {Fragment, useState} from "react";
import {getSession, Session} from "@/lib/auth";
import {deleteEmployee, deleteOffice} from "@/lib/adminActions";

import BaseDialog from "@/components/UI/BaseDialog";
import EditShipmentForm from "@/components/home/EmployeeInterface/EditShipmentForm";
import EditOfficeForm from "@/components/home/AdminInterface/EditOfficeForm";
import AddRoleForm from "@/components/home/AdminInterface/AddRoleForm";
import EmployeeInterfaceActionsButtons from "@/components/home/EmployeeInterface/EmployeeInterfaceActionsButtons";

export type column = {
    title: string;
    code: string;
    hide?: boolean;
    id: number;
}

export type category = {
    title: string;
    code: string;
    id: number
}

export type item = Record<string, any> & {
    id: number;
    category: string;
};

type ActionType = "demoteEmployee" | "promoteUser" | "deleteUser" | "deleteEmployee" | "deleteOffice" | "editShipment" | "editOffice" | "addRole";

type TableProps = {
    columns: column[];
    categories: category[];
    data: item[];
    session: Session | null;
};

const Table: React.FC<TableProps> = ({
    columns,
    categories,
    data,
    session
}) => {
    const [showDialog, setShowDialog] = useState<boolean>(false);
    const [selectedItem, setSelectedItem] = useState<item | null>(null);

    const [editShipment, setEditShipment] = useState<boolean>(false);
    const [editOffice, setEditOffice] = useState<boolean>(false);
    const [addRole, setAddRole] = useState<boolean>(false);

    const handleAction = async (item: item, type: ActionType) => {
        // TODO: turn into switch() and setShowDialog conditionally
        if (type === "deleteOffice") {
            await deleteOffice(item, session);
        } else if (type === "deleteEmployee") {
            await deleteEmployee(item, session);
        } else if (type === "deleteUser") {
            await deleteEmployee(item, session);
        } else if (type === "promoteUser") {
            await deleteEmployee(item, session);
        } else if (type === "demoteEmployee") {
            await deleteEmployee(item, session);
        } else if (type === "editShipment") {
            setEditShipment(true);
        } else if (type === "editOffice") {
            setEditOffice(true);
        } else if (type === "addRole") {
            setAddRole(true);
        }
        setSelectedItem(item);
        setShowDialog(true);
    };


    return (
        <div className="px-8 py-2 min-w-full">
            <table className="min-w-full">
                {showDialog && selectedItem &&
                    <BaseDialog
                        title="Edit item"
                        tryClose={() => {
                            setShowDialog(false);
                            setEditShipment(false);
                            setEditOffice(false);
                        }}
                    >
                        {editShipment &&
                            <EditShipmentForm selectedItem={selectedItem}/>
                        }
                        {editOffice &&
                            <EditOfficeForm selectedItem={selectedItem}/>
                        }
                        {addRole &&
                            <AddRoleForm selectedItem={selectedItem}/>
                        }
                    </BaseDialog>
                }
                <thead className='bg-white'>
                <tr className='text-sm font-semibold text-black'>
                    {columns.map((column: column) => {

                        if (column.hide) {
                            return (
                                <th key={column.id} className="text-left px-4 py-3 relative">
                                    <span className='accessibility'>{column.title}</span>
                                </th>
                            );
                        }

                        return (
                            <th key={column.id} className="text-left px-4 py-3" scope='col'>
                                {column.title}
                            </th>
                        )
                    })}
                </tr>
                </thead>
                <tbody className="bg-white">
                {categories.map((category: category) => {
                    return (
                        <Fragment key={category.id}>
                            <tr className="border-t text-sm font-semibold text-black">
                                <th
                                    className="bg-[#FAF9FB] px-4 py-3 text-left"
                                    colSpan={columns.length}
                                    scope="colgroup"
                                >
                                    {category.title}
                                </th>
                            </tr>
                            {
                                data
                                    .filter((item: item) => item.category === category.code)
                                    .map((rowItem: item, index: number) => {
                                        return (
                                            <tr key={index} className="border-t text-sm font-semibold text-black">

                                                {columns.map((column: column) => {

                                                    if (column.hide) {

                                                        if (column.code === 'employeeInterfaceActions') {

                                                            return (
                                                                <EmployeeInterfaceActionsButtons
                                                                    id={column.id}
                                                                    onClick={() => handleAction(rowItem, "editShipment")}
                                                                />
                                                            )

                                                        }
                                                        if (column.code === 'adminInterfaceActions') {
                                                            if (category.code === 'clients') {

                                                                return (
                                                                    // TODO: turn this into a separate component located at the AdminInterface folder
                                                                    <td key={column.id}
                                                                        className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
                                                                        <button
                                                                            className="action_btn_red"
                                                                            onClick={() => handleAction(rowItem, "deleteUser")}

                                                                        >
                                                                            Delete
                                                                        </button>

                                                                        <button
                                                                            className="action_btn_green"
                                                                            onClick={() => handleAction(rowItem, "promoteUser")}

                                                                        >
                                                                            Promote
                                                                        </button>
                                                                    </td>
                                                                )
                                                            } else if (category.code === "employees") {
                                                                return (
                                                                    // TODO: turn this into a separate component located at the AdminInterface folder
                                                                    <td key={column.id}
                                                                        className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
                                                                        <button
                                                                            className="action_btn_red"
                                                                            onClick={() => handleAction(rowItem, "deleteEmployee")}

                                                                        >
                                                                            Delete
                                                                        </button>
                                                                        <button
                                                                            className="action_btn_blue"
                                                                            onClick={() => handleAction(rowItem, "demoteEmployee")}

                                                                        >
                                                                            Demote
                                                                        </button>
                                                                        <button
                                                                            className="action_btn_green"
                                                                            onClick={() => handleAction(rowItem, "addRole")}

                                                                        >
                                                                            Add role
                                                                        </button>
                                                                    </td>
                                                                )
                                                            } else if (category.code === "offices") {
                                                                return (
                                                                    // TODO: turn this into a separate component located at the AdminInterface folder
                                                                    <td key={column.id}
                                                                        className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
                                                                        <button
                                                                            className="action_btn_red"
                                                                            onClick={() => handleAction(rowItem, "deleteOffice")}
                                                                        >
                                                                            Delete
                                                                        </button>

                                                                        <button
                                                                            className="action_btn_blue"
                                                                            onClick={() => handleAction(rowItem, "editOffice")}
                                                                        >
                                                                            Edit
                                                                        </button>


                                                                    </td>
                                                                )
                                                            }
                                                        }
                                                    }

                                                    if (column.code === 'status') {

                                                        return (
                                                            <td key={column.id} className='px-4 py-3'>
                                                                <button
                                                                    className={
                                                                        rowItem.status === 'Active' ?
                                                                            "action_btn_green" :
                                                                        rowItem.status === "Closed" ?
                                                                            "action_btn_purple" :
                                                                            ""
                                                                    }
                                                                >
                                                                    {rowItem[column.code]}
                                                                </button>
                                                            </td>
                                                        )
                                                    }

                                                    return (
                                                        <td key={column.id} className='px-4 py-3 text-gray-500'>
                                                            {rowItem[column.code]}
                                                        </td>
                                                    )
                                                })}
                                            </tr>
                                        )
                                    })
                            }
                        </Fragment>
                    )
                })}
                </tbody>
            </table>
        </div>
    );
};

export default Table;