'use client';

import React, { FormEvent, useMemo, useState } from "react";

import Button from "@/components/UI/BaseButton";
import DataSelectorWrapper, { selectorItem } from "@/components/UI/DataSelectorWrapper";
import Input from "@/components/UI/Input";
import useInput from "@/hooks/useInput";

export type FilterOptions = { clientName: string; employeeName: string; orderType: selectorItem | null; }

const FilterOrders: React.FC<{ onFilterOrders: ({ clientName, employeeName, orderType }: FilterOptions) => void }> = ({ onFilterOrders }) => {
    const [orderFilterType, setOrderFilterType] = useState<selectorItem | null>(null);

    const orderFilterTypeData: selectorItem[] = useMemo(() => {
        return [
            {
                title: 'Active',
                code: 'active'
            },
            {
                title: 'Closed',
                code: 'Closed'
            }
        ];
    }, []);

    const changeFilterType = (data: selectorItem) => {
        setOrderFilterType(data);
    }

    const {
        value: enteredClientName,
        valueChangeHandler: clientNameChangeHandler,
        inputBlurHandler: clientNameBlurHandler,
        reset: clientNameReset
     } = useInput(
        (value) => value.trim().length > 3,
        ''
    );

    const {
        value: enteredEmployeeName,
        valueChangeHandler: employeeNameChangeHandler,
        inputBlurHandler: employeeNameBlurHandler,
        reset: employeeNameReset
     } = useInput(
        (value) => value.trim().length > 3,
        ''
    );

    const filterOrders = async (event: FormEvent) => {
        event.preventDefault();

        onFilterOrders({
            clientName: enteredClientName,
            employeeName: enteredEmployeeName,
            orderType: orderFilterType
        })
    }

    return (
        <form className="flex flex-col gap-6 justify-start items-center w-full" onSubmit={filterOrders}>
            <div className='flex justify-start items-center gap-x-3'>
                <Input
                    id='client_name'
                    type='text'
                    name='client_name'
                    placeholder="Client name"
                    enteredValue={enteredClientName}
                    onChangeHandler={clientNameChangeHandler}
                    onBlurHandler={clientNameBlurHandler}
                    reset={clientNameReset}
                    iconSrc="/icons/user.svg"
                    iconAlt="User icon"
                    showRemoveIcon
                />

                <Input
                    id='employee_name'
                    type='text'
                    name='employee_name'
                    placeholder="Employee name"
                    enteredValue={enteredEmployeeName}
                    onChangeHandler={employeeNameChangeHandler}
                    onBlurHandler={employeeNameBlurHandler}
                    reset={employeeNameReset}
                    iconSrc="/icons/user.svg"
                    iconAlt="User icon"
                    showRemoveIcon
                />
            </div>
            <div className='flex justify-start items-center gap-x-3'>
                <DataSelectorWrapper
                    hasInitialPlaceholderValue
                    placeholderValue={orderFilterType ? orderFilterType.title : "Order type"}
                    selectorData={orderFilterTypeData}
                    onResubForNewData={changeFilterType}
                />
                <Button fill type='submit'>Filter orders</Button>
            </div>
        </form>
    );
};

export default FilterOrders;