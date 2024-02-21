"use client";
import { categories, data, officeColumnes, tableColumns } from "@/data/admin/ordersTableData";
import Table from "./EmployeeInterface/Table";


import Dropdown from "./AdminInterface/Dropdown";

import React, { useState } from "react";

const AdminInterface = () => { 
    interface Option {
        label: string;
        value: string;
      }
    const options: Option[] = [
        { label: "Company 1", value: "1" },
        { label: "Company 2", value: "2" },
        { label: "Company 3", value: "3" },
      ];
      const [selectedOption, setSelectedOption] = useState<Option | undefined>(undefined);

      const handleOptionSelect = (option: Option) => {
        setSelectedOption(option);
        // Perform any other actions needed when an option is selected
      };
    return <>
        <h3 className="flex justify-start w-full">Welcome user ...! This is your home page. You're logged in as admin.</h3>
        <br/>

        <Dropdown
         options={options}
         selectedOption={selectedOption}
          onSelect={handleOptionSelect}
        />
        
        
        <Table
                columns={tableColumns}
                categories={categories}
                data={data}
            />
        <Table
            columns={officeColumnes}
            categories={categories}
            data={data}
        />


    </>;
}

export default AdminInterface;