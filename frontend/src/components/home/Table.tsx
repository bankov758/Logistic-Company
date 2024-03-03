"use client";
import BaseDialog from "@/components/UI/BaseDialog";
import { ServerInsertedHTMLContext } from "next/navigation";
import React, { Fragment, useState } from "react";

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
type Option = {
    value: string;
    placeholder: string;
  };
const RadioButton: React.FC<{ options: Option[] }> = ({
    options,
  }) => {const [selectedOption, setSelectedOption] = useState<string | null>(null);
  
    const handleOptionChange = (
        event: React.ChangeEvent<HTMLInputElement>
      ) => {
        setSelectedOption(event.target.value);
      };
  
    return (
      <div>
        {options.map((option) => (
        <div key={option.value}>
          <label key={option.value}>
            <input
              type="radio"
              name="radioOptions"
              value={option.value}
              checked={selectedOption === option.value}
              onChange={handleOptionChange}
            />
            {option.placeholder}
          </label>
        </div>
        ))}
      </div>
    );
  };
  
  // Example usage:
  const options = [
    { value: "Active", placeholder: "Active" },
    { value: "Closed", placeholder: "Closed" },
  ];
const Table: React.FC<TableProps> = ({
    columns,
    categories,
    data
}) => {

    const [showDialog, setShowDialog] = useState<boolean>(false);
    const [selectedItem, setSelectedItem] = useState<item | null>(null);
    const [showEdit, setShowEdit] = useState<boolean>(false);
    const [selectedOfficeItem, setSelectedOfficeItem] = useState<item | null>(null);
    const [showOffice, setShowOffice] = useState<boolean>(false);

    const [inputType, setInputType] = useState("text");

    const handleEditClick = (item: item) => {
        setSelectedItem(item)!; 
        setShowDialog(true); 
        setShowEdit(true);
    };
    const handleEditOfficeClick = (item: item) => {
        setSelectedOfficeItem(item)!; 
        setShowDialog(true); 
        setShowOffice(true);
    };

    return (
        <div className="px-8 py-2 min-w-full">    
            <table className="min-w-full">

                {showDialog &&
                (<BaseDialog title="Edit item" tryClose={() => setShowDialog(false) }>
                    {showEdit && 
                        (<>
                            <h3 className="flex justify-center">Edit an order:</h3><br/>
                            <div className="order-div">
                                <label className="block  text-gray-500">Sender:</label>
                                <input type="text"  id="sender" className="input-info-dialog" placeholder={selectedItem?.sender.toString() ?? "Name:"}></input><br/>
                            </div>
                            <div className="order-div">    
                                <label className="block  text-gray-500">Receiver:</label>           
                                <input type="text"  id="receiver" className="input-info-dialog" placeholder={selectedItem?.receiver.toString() ?? "Name:"}></input><br/>
                            </div>
                            <div className="order-div">    
                                <label className="block  text-gray-500">Departure location:</label>           
                                <input type="text"  id="departureAddress" className="input-info-dialog" placeholder={selectedItem?.departure.toString() ?? "Departure location:"}></input><br/>
                            </div>
                            <div className="order-div">    
                                <label className="block  text-gray-500">Arrival location:</label>
                                <input type="text"  id="arrivalAddress" className="input-info-dialog" placeholder={selectedItem?.arrival.toString() ?? "Arrival location:"}></input><br/>                   
                            </div>                  
                            <div className="order-div">    
                                <label className="block  text-gray-500">Date:</label>
                                <input type={inputType} onFocus={() => setInputType('date')} onBlur={() => setInputType('text')}   id="sentDate" className="block text-gray-500 rounded-xl border-2 py-1.5 px-1 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-base sm:leading-6 whitespace-pre-line" placeholder={selectedItem?.date?.toString() ?? "Date: "} ></input><br/>
                            </div>
                            <div className="order-div">
                                <label className="block  text-gray-500">Status:</label> 
                                <div className="rounded-xl border-2 py-1 px-3"><RadioButton options={options} /></div>
                            </div>
                            <div className="order-div">    
                                <label className="block  text-gray-500">Weight (in kilodram):</label>
                                <input type="text"  id="weight" className="input-info-dialog" placeholder={selectedItem?.weight.toString() ?? "Weight:"}></input><br/>
                            </div>
                            <div className="order-div ">
                                <label className="block  text-gray-500">Employee:</label>
                                <input type="text"  id="employee" className="input-info-dialog" placeholder={selectedItem?.employee.toString() ?? "Employee:"}></input><br/>
                            </div>
                            <div className='flex justify-center py-3 text-gray-500'>
                                <button
                                    className="action_btn_green px-8 py-3"
                                >
                                    Edit
                                </button>
                            </div>
                        </>)
                    }
                    {showOffice && 
                        (<>
                            <h3 className="flex justify-center">Edit an office:</h3><br/>
                            <div className="order-div">
                                <label className="block  text-gray-500">Address:</label>
                                <input type="text"  id="adress" className="input-info-dialog" placeholder={selectedOfficeItem?.address.toString() ?? "Address:"}></input><br/>
                            </div>
                            <div className="order-div">    
                                <label className="block  text-gray-500">Company name:</label>           
                                <input type="text"  id="companyName" className="input-info-dialog" placeholder={selectedOfficeItem?.company_name.toString() ?? "Name:"}></input><br/>
                            </div>
                            <div className='flex justify-center py-3 text-gray-500'>
                                <button
                                    className="action_btn_green px-8 py-3"
                                >
                                    Edit
                                </button>
                            </div>
                         </>)
                     }
                </BaseDialog>)}

                <thead className='bg-white'>
                    <tr className='text-sm font-semibold text-black'>
                        { columns.map((column: column) => {

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

                                                        if ( column.hide ) {

                                                            if( column.code === 'actions' ) {

                                                                return (
                                                                    <td key={column.id} className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
                                                                        <button 
                                                                            className="action_btn_red"
                                                                        >
                                                                            Delete
                                                                        </button>

                                                                        <button 
                                                                            className="action_btn_blue"
                                                                            onClick={() => handleEditClick(rowItem)}
                                                                        >
                                                                            Edit
                                                                        </button>

                                                                        <button 
                                                                            className="action_btn_purple"
                                                                        >
                                                                            Close
                                                                        </button>
                                                                    </td>
                                                                )
                                                            }
                                                            if( column.code === 'actionTable' && category.code === 'clients' ) {

                                                                return (
                                                                    <td key={column.id} className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
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
                                                            }
                                                            if( column.code === 'actionTable' && category.code === 'employees' ) {

                                                                return (
                                                                    <td key={column.id} className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
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
                                                            }
                                                            if( column.code === 'actionOffice' ) {

                                                                return (
                                                                    <td key={column.id} className='flex gap-x-2 justify-center items-center px-4 py-3 text-gray-500'>
                                                                        <button 
                                                                            className="action_btn_red"
                                                                        >
                                                                            Delete
                                                                        </button>

                                                                        <button 
                                                                            className="action_btn_blue"
                                                                            onClick={() => handleEditOfficeClick(rowItem)}
                                                                        >
                                                                            Edit
                                                                        </button>
                                                                        
                                                                       
                                                                    </td>
                                                                )
                                                            }
                                                        }

                                                        if( column.code === 'status' && rowItem.status === 'Active') {
                                                            return (
                                                                <td key={column.id} className='px-4 py-3'>
                                                                    <button 
                                                                        className="action_btn_green"
                                                                    >
                                                                        {rowItem[column.code]}
                                                                    </button>
                                                                </td>
                                                            )
                                                        }

                                                        if( column.code === 'status' && rowItem.status === 'Closed') {
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