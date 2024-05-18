import React, {useEffect, useState} from "react";
import {useFormState} from "react-dom";

import {selectorItem} from "@/components/UI/DataSelectorWrapper";
import {addOffice, addTariff, deleteCompany, editCompany} from "@/lib/adminActions";
import {getCookies} from "@/lib/auth";
import axios from "@/lib/axios";
import {AxiosError} from "axios";

type ShowCompanyInfoProps = {
    companyData: selectorItem[],
    selectedCompany: selectorItem,
    onSuccessDelete: () => void,
    onSuccessEdit: () => void,
    onSuccessAddOffice: () => void
    onSuccessAddTariff: () => void
};

const ShowCompanyInfo: React.FC<ShowCompanyInfoProps> = ({
    selectedCompany,
    onSuccessDelete,
    onSuccessEdit,
    onSuccessAddOffice,
    onSuccessAddTariff
}) => {
    const [updatedCompanyName, setUpdatedCompanyName] = useState('');
    const [officeLocation, setOfficeLocation] = useState('');
    const [tariff, setTariff] = useState<number>(0);
    const [showTariff, setShowTariff] = useState<{pricePerKG:number, officeDiscount: number } | null | undefined>({ pricePerKG: 0, officeDiscount: 0})
    const [discount, setDiscount] = useState<number>(0);

    const [deleteCompanyState, deleteCompanyAction] = useFormState(deleteCompany.bind(null, selectedCompany), { message: '', errors: ''})
    const [editCompanyCompanyState, editCompanyAction] = useFormState(editCompany.bind(null, updatedCompanyName, selectedCompany), { message: '', errors: ''})
    const [addOfficeState, addOfficeAction] = useFormState(addOffice.bind(null, Number(selectedCompany.id), officeLocation), { message: '', errors: ''})
    const [addTariffState, addTariffAction] = useFormState(addTariff.bind(null, Number(selectedCompany.id), tariff, discount), { message: '', errors: ''})

    useEffect(() => {

        if( deleteCompanyState.message ) {
            onSuccessDelete();
        }

        if( editCompanyCompanyState.message ) {
            onSuccessEdit();
        }

        if( addOfficeState.message ) {
            onSuccessAddOffice()
        }

        if( addTariffState.message ) {
            onSuccessAddTariff()
        }

    }, [deleteCompanyState, editCompanyCompanyState, addOfficeState, addTariffState, onSuccessDelete, onSuccessEdit, onSuccessAddOffice, onSuccessAddTariff]);

    useEffect(() => {
        const setTariff = async () => {
                try {
                    const jsession = await getCookies();

                    const response = await axios.get<{
                        pricePerKG: number;
                        officeDiscount: number;
                        companyID: { id: number }
                    }[]>(`/tariffs`, {
                        headers: {
                            Cookie: `JSESSIONID=${jsession?.value}`
                        }
                    });

                    const foundTariff = response.data.find(tariff => tariff.companyID.id === selectedCompany.id)
                    console.log(foundTariff)
                    if (foundTariff) setShowTariff( {
                        pricePerKG: foundTariff.pricePerKG,
                        officeDiscount: foundTariff.officeDiscount
                    })

                } catch (err) {
                    if (err instanceof AxiosError) {
                        throw err;
                    }
                }
        }
        setTariff();
        }, []);


        return (
            <>
                <div className="flex items-center  gap-x-2 pt-3 pb-4">

                    <label className="block  text-gray-500">Delete this company:</label>

                    <button
                        className="action_btn_red px-12 py-1.5 "
                        onClick={() => deleteCompanyAction()}
                    >
                        DELETE COMPANY
                    </button>
                </div>

                <div className="flex items-center  gap-x-2 pb-4">
                    <label htmlFor="company_name_edit" className="block text-gray-500">Change company name:</label>

                    <input type="text" id="company_name_edit" name="company_name_edit" className="input-info-dialog"
                           onChange={(e) => setUpdatedCompanyName(e.target.value)}
                           placeholder={selectedCompany.title}/>

                    <div className='flex py-3 text-gray-500'>

                        <button
                            className="action_btn_green px-6 py-2"
                            onClick={() => editCompanyAction()}
                        >
                            Edit
                        </button>
                    </div>
                </div>

                <div className="flex items-center gap-x-2 pb-4">
                    <label htmlFor="office_location" className="block text-gray-500">Add a new office location:</label>

                    <input type="text" id="office_location" name="office_location" className="input-info-dialog"
                           onChange={(e) => setOfficeLocation(e.target.value)}
                           placeholder="Sofia"/>

                    <div className='flex py-3 text-gray-500'>

                        <button
                            className="action_btn_blue px-3 py-1.5"
                            onClick={() => addOfficeAction()}
                        >
                            Add
                        </button>
                    </div>
                </div>
                <div className="flex items-center gap-x-6 pb-4">
                    <label className="block text-gray-500"> Current price per kg:  {showTariff?.pricePerKG} </label>
                    <label className="block text-gray-500"> Current discount:  {showTariff?.officeDiscount} </label>
                </div>
                <div className="flex items-center gap-x-2 pb-4 justify-center">
                    <label htmlFor="tariff" className="block text-gray-500">Set price per kilogram:</label>

                    <input type="text" id="tariff" name="tariff" className="input-info-dialog"
                           onChange={(e) => setTariff(Number(e.target.value))}
                           placeholder={String(showTariff?.pricePerKG)}/>

                    <label htmlFor="discount" className="block text-gray-500">Set discount:</label>

                    <input type="text" id="discount" name="discount" className="input-info-dialog"
                           onChange={(e) => setDiscount(Number(e.target.value))}
                           placeholder={String(showTariff?.officeDiscount)}/>

                    <div className='flex py-3 text-gray-500'>

                        <button
                            className="action_btn_blue px-3 py-1.5"
                            onClick={() => addTariffAction()}
                        >
                            Set
                        </button>
                    </div>

                </div>


            </>
        );
}

export default ShowCompanyInfo;