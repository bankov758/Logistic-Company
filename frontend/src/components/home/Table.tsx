"use client";
import React, {Fragment, useState} from "react";

import BaseDialog from "@/components/UI/BaseDialog";
import EditShipmentForm from "@/components/home/EmployeeInterface/EditShipmentForm";
import EditOfficeForm from "@/components/home/AdminInterface/EditOfficeForm";

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

export type item = Record<string, string | number> & {
    id: number;
    category: string;
};

type TableProps = {
    columns: column[];
    categories: category[];
    data: item[];
}
const Table: React.FC<TableProps> = ({
     columns,
     categories,
     data
}) => {

    const [showDialog, setShowDialog] = useState<boolean>(false);
    const [selectedItem, setSelectedItem] = useState<item | null>(null);

    const [editShipment, setEditShipment] = useState<boolean>(false);
    const [editOffice, setEditOffice] = useState<boolean>(false);

    const handleClick = (item: item, type: "editShipment" | "editOffice") => {
        if( type === "editShipment" ) {
            setEditShipment(true);
        } else if( type === "editOffice" ) {
            setEditOffice(true);
        }

        setSelectedItem(item);
        setShowDialog(true);
    };

    return (
        <div className="px-8 py-2 min-w-full">
            <table className="min-w-full">

                {showDialog &&
                    <BaseDialog
                        title="Edit item"
                        tryClose={() => {
                            setShowDialog(false);
                            setEditShipment(false);
                            setEditOffice(false);
                        }}
                    >
                        {editShipment && selectedItem &&
                            <EditShipmentForm selectedItem={selectedItem} />
                        }
                        {editOffice && selectedItem &&
                            <EditOfficeForm selectedItem={selectedItem} />
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
                                                                    <td key={column.id}
                                                                        className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
                                                                        <button
                                                                            className="action_btn_red"
                                                                        >
                                                                            Delete
                                                                        </button>

                                                                        <button
                                                                            className="action_btn_blue"
                                                                            onClick={() => handleClick(rowItem, "editShipment")}
                                                                        >
                                                                            Edit
                                                                        </button>
                                                                    </td>
                                                                )

                                                            }
                                                            if (column.code === 'adminInterfaceActions') {
                                                                if (category.code === 'clients') {

                                                                    return (
                                                                        <td key={column.id}
                                                                            className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
                                                                            <button
                                                                                className="action_btn_red"
                                                                            >
                                                                                Delete
                                                                            </button>

                                                                            <button
                                                                                className="action_btn_green"
                                                                            >
                                                                                Promote
                                                                            </button>
                                                                        </td>
                                                                    )
                                                                } else if (category.code === "employees") {
                                                                    return (
                                                                        <td key={column.id}
                                                                            className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
                                                                            <button
                                                                                className="action_btn_red"
                                                                            >
                                                                                Delete
                                                                            </button>
                                                                            <button
                                                                                className="action_btn_blue"
                                                                            >
                                                                                Demote
                                                                            </button>
                                                                            <button
                                                                                className="action_btn_green"
                                                                            >
                                                                                Add role
                                                                            </button>
                                                                        </td>
                                                                    )
                                                                } else if (category.code === "offices") {
                                                                    return (
                                                                        <td key={column.id}
                                                                            className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
                                                                            <button
                                                                                className="action_btn_red"
                                                                            >
                                                                                Delete
                                                                            </button>

                                                                            <button
                                                                                className="action_btn_blue"
                                                                                onClick={() => handleClick(rowItem, "editOffice")}
                                                                            >
                                                                                Edit
                                                                            </button>


                                                                        </td>
                                                                    )
                                                                }
                                                            }
                                                        }

                                                        if (column.code === 'status' ) {

                                                            if ( rowItem.status === 'Active' ) {
                                                                return (
                                                                    <td key={column.id} className='px-4 py-3'>
                                                                        <button
                                                                            className="action_btn_green"
                                                                        >
                                                                            {rowItem[column.code]}
                                                                        </button>
                                                                    </td>
                                                                )
                                                            } else if( rowItem.status === "Closed" ) {
                                                                return (
                                                                    <td key={column.id} className='px-4 py-3'>
                                                                        <button
                                                                            className="action_btn_purple"
                                                                        >
                                                                            {rowItem[column.code]}
                                                                        </button>
                                                                    </td>
                                                                )
                                                            }
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