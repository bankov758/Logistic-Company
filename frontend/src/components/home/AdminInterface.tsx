"use client";
import React, {useState} from "react";

import Table from "./EmployeeInterface/Table";
import DataSelectorWrapper, {selectorItem} from "@/components/UI/DataSelectorWrapper";
import BaseDialog from "@/components/UI/BaseDialog";

import MessageIcon from '../../../public/icons/message.svg';

import {
    categories,
    data,
    officeCategories,
    officeColumns,
    officeDataItems,
    tableColumns
} from "@/data/admin/ordersTableData";
import Image from "next/image";

let selectorData: selectorItem[] = [
    {
        title: 'Econt',
        code: 'econt',
    },
    {
        title: "Speedy",
        code: "speedy"
    },
    {
        title: "DHL",
        code: "dhl"
    }
];

const AdminInterface: React.FC = () => {
    const [selectedCompany, setSelectedCompany] = useState(selectorData[0]);
    const [showDialog, setShowDialog] = useState<boolean>(false)

    const updateSelectedCompany = (data: selectorItem) => {
        console.log(data);
        setSelectedCompany(data);
    }

    return <>
        <h3 className="flex justify-start w-full">
            Welcome user ...! This is your home page. You&apos;re logged in as admin.
        </h3>

        <br/>

        <div className='flex justify-center items-center gap-x-10'>
            <DataSelectorWrapper
                hasInitialPlaceholderValue
                placeholderValue={selectedCompany.title}
                selectorData={selectorData}
                onResubForNewData={updateSelectedCompany}
            />

            <Image
                src={MessageIcon}
                alt='Message'
                className='cursor-pointer'
                onClick={() => setShowDialog(true)}
            />
        </div>
        {showDialog &&
            (
                <BaseDialog title='test title' tryClose={() => setShowDialog(false)}>
                    test
                </BaseDialog>
            )
        }

        {/*/!* clients + employees *!/*/}
        <Table
            columns={tableColumns}
            categories={categories}
            data={data}
        />

        {/* offices */}
        <Table
            columns={officeColumns}
            categories={officeCategories}
            data={officeDataItems}
        />
    </>;
}

export default AdminInterface;