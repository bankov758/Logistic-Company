import React from "react";
import {addOffice, deleteCompany, editCompany} from "@/lib/adminActions";

const ShowCompanyInfo: React.FC = () => {
    //TODO: Add the expected props
    return (
        <>
            <div className="flex items-center  gap-x-2 pt-3 pb-4">
                <label className="block  text-gray-500">Delete this company:</label>
                <button
                    className="action_btn_red px-12 py-1.5 "
                    onClick={() => deleteCompany(selectedCompany.title, companyData, session)}
                >
                    DELETE COMPANY
                </button>
            </div>
            <div className="flex items-center  gap-x-2 pb-4">
                <label className="block  text-gray-500">Change company name:</label>
                <input type="text" id="company_name_edit" className="input-info-dialog"
                       onChange={(e) => setUpdatedCompanyName(e.target.value)}
                       placeholder="Speedy "></input><br/>
                <div className='flex py-3 text-gray-500'>
                    <button
                        className="action_btn_green px-6 py-2"
                        onClick={() => editCompany(selectedCompany.title, updatedCompanyName, companyData, session)}

                    >
                        Edit
                    </button>
                </div>
            </div>
            <div className="flex items-center gap-x-2 pb-4">
                <label className="block  text-gray-500">Add a new office location:</label>
                <input type="text" id="office_location" className="input-info-dialog"
                       onChange={(e) => setofficeLocation(e.target.value)}
                       placeholder="Sofia"></input>
                <div className='flex py-3 text-gray-500'>
                    <button
                        className="action_btn_blue px-3 py-1.5"
                        onClick={() => addOffice(selectedCompany.title, officeLocation, companyData, session)}
                    >
                        Add
                    </button>
                </div>
            </div>
        </>
    );
}

export default ShowCompanyInfo;