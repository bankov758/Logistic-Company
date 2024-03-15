"use client";
import React, {Fragment, useState} from "react";
import {getSession, Session} from "@/lib/auth";
import {deleteEmployee, deleteOffice, demoteEmployee, promoteUser} from "@/lib/adminActions";

import BaseDialog from "@/components/UI/BaseDialog";
import EditShipmentForm from "@/components/home/EmployeeInterface/EditShipmentForm";
import EditOfficeForm from "@/components/home/AdminInterface/EditOfficeForm";
import AddRoleForm from "@/components/home/AdminInterface/AddRoleForm";
import EmployeeInterfaceActionsButtons from "@/components/home/EmployeeInterface/EmployeeInterfaceActionsButtons";
import AdminInterfaceUserActionsButtons from "@/components/home/AdminInterface/AdminInterfaceUserActionButtons";
import AdminInterfaceEmployeeActionsButtons from "@/components/home/AdminInterface/AdminInterfaceEmployeeActionButtons";
import AdminInterfaceOfficeActionButtons from "@/components/home/AdminInterface/AdminInterfaceOfficeActionButtons";
import AdminInterfaceCourierActionsButtons from "@/components/home/AdminInterface/AdminInterfaceCourierActionButtons";

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

export type ActionType = "demoteEmployee" | "promoteUser" | "deleteUser" | "deleteEmployee" | "deleteOffice" | "editShipment" | "editOffice" | "addRole" | "deleteShipment" | "deleteCourier" | "demoteCourier" | "makeOfficeEmployee" | "makeCourier";

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


    const handleAction = async (item: item, type: ActionType | null) => {
        // TODO: there is no delete user query to refer to => possible solution : ask backend || remove delete user button
        switch (type) {
            case "deleteOffice": await deleteOffice(item, session); break;
           // case "deleteUser": await deleteEmployee(item, session); break;
            case "deleteEmployee": await deleteEmployee(item, session); break;
            case "promoteUser": await promoteUser(item, session); break;
            case "demoteEmployee": await demoteEmployee(item, session); break
            case "editOffice":
                setEditOffice(true);
                setSelectedItem(item);
                setShowDialog(true);
                break;
            case "editShipment":
                setEditShipment(true);
                setSelectedItem(item);
                setShowDialog(true);
                break;
            case "addRole":
                setAddRole(true);
                setSelectedItem(item);
                setShowDialog(true);
                break;
            default: break;
        }
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
                                                                    key={column.id}
                                                                    onClick={(actionType) => handleAction(rowItem, actionType)}
                                                                />
                                                            )

                                                        }
                                                        if (column.code === 'adminInterfaceActions') {
                                                            if (category.code === 'clients') {

                                                                return (
                                                                    <AdminInterfaceUserActionsButtons
                                                                        key={column.id}
                                                                        onClick={(actionType) => handleAction(rowItem, actionType)}
                                                                    />
                                                                )
                                                            } else if (category.code === "employees") {
                                                                return (
                                                                    <AdminInterfaceEmployeeActionsButtons
                                                                        key={column.id}
                                                                        onClick={(actionType) => handleAction(rowItem, actionType)}                                                                    />
                                                                )
                                                            } else if (category.code === "couriers") {
                                                                return (
                                                                    <AdminInterfaceCourierActionsButtons
                                                                        key={column.id}
                                                                        onClick={(actionType) => handleAction(rowItem, actionType)}                                                                    />
                                                                )
                                                            } else if (category.code === "offices") {
                                                               return (
                                                                   <AdminInterfaceOfficeActionButtons
                                                                       key={column.id}
                                                                       onClick={(actionType) => handleAction(rowItem, actionType)}
                                                                   />
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