"use client";
import React, {useState} from "react";

import Table from "./Table";
import DataSelectorWrapper, {selectorItem} from "@/components/UI/DataSelectorWrapper";
import BaseDialog from "@/components/UI/BaseDialog";

import InfoIcon from '../../../public/icons/info.svg';

import {
    categories,
    data,
    officeCategories,
    officeColumns,
    officeDataItems,
    tableColumns
} from "@/data/admin/ordersTableData";
import Image from "next/image";
import Button from "../UI/BaseButton";

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
    const [showCreateDialog, setShowCreateDialog] = useState<boolean>(false)


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
                src={InfoIcon}
                alt='Message'
                className='cursor-pointer'
                onClick={() => setShowDialog(true)}
            />
            <Button fill
            onClick={() => setShowCreateDialog(true)}
            >Create a company</Button>
        </div>
        {/* create company dialog  */}
        {showCreateDialog && 
        (<BaseDialog title="Create a company" tryClose={() => setShowCreateDialog(false)}>
            <div className="flex items-center justify-center gap-x-3 pb-3">
                <label className="block  text-gray-500">Company name:</label>
                <input type="text"  id="company_name_add" className="input-info-dialog" placeholder="Speedy "></input><br/>
                <div  className='flex gap-x-2 px-5 py-3 text-gray-500'>                   
                    <button
                        className="action_btn_green px-6 py-2"
                    >
                        Add
                    </button>
                </div>
            </div>
        </BaseDialog>)
        }
        {/* company info dialog */}
        {showDialog &&
            (
                <BaseDialog  title={selectedCompany.title} tryClose={() => setShowDialog(false)}>
                <div className="flex flex-row items-center  gap-x-2 pt-4 pb-6">
                    <label className="block  text-gray-500">Company name:</label>
                    <input type="text" id="company_name_edit" className="input-info-dialog" placeholder="Speedy "></input><br />
                    <div className='flex py-3 text-gray-500'>
                        <button
                            className="action_btn_green px-6 py-2"
                        >
                            Edit
                        </button>
                    </div>
                </div>
                <div className="flex flex-row">

                    <div className="flex-column w-1/3">
                        <label className="block  text-gray-500">Employee's first name:</label>
                        <input type="text" id="employee_first_name" className="input-info-dialog" placeholder="John "></input>
                        <label className="block  text-gray-500">Employee's last name:</label>
                        <input type="text" id="employee_last_nname" className="input-info-dialog" placeholder="Doe"></input>
                        <div className='flex py-3 text-gray-500'>

                            <button
                                className="action_btn_blue px-3 py-1.5"
                            >
                                Add
                            </button>
                        </div>
                    </div>

                    <div className=" flex-column w-1/3">
                        <label className="block  text-gray-500">Client's first name:</label>
                        <input type="text" id="client_first_name" className="input-info-dialog" placeholder="Jane"></input>
                        <label className="block  text-gray-500">Client's last name:</label>
                        <input type="text" id="client_last_name" className="input-info-dialog" placeholder="Doe"></input>
                        <div className='flex py-3 text-gray-500'>
                            <button
                                className="action_btn_blue px-3 py-1.5"
                            >
                                Add
                            </button>
                        </div>
                    </div>
                    <div className=" flex-column w-1/3">
                        <label className="block  text-gray-500">Office location:</label>
                        <input type="text" id="office_location" className="input-info-dialog" placeholder="Sofia"></input>
                        <div className='flex py-3 text-gray-500'>
                            <button
                                className="action_btn_blue px-3 py-1.5"
                            >
                                Add
                            </button>
                        </div>
                    </div>

                </div>

                    
                    <button
                        className="action_btn_red px-6 py-1.5 fixed bottom-5 right-8"
                    >
                        DELETE COMPANY
                    </button>

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